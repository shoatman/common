// Copyright (c) Microsoft Corporation.
// All rights reserved.
//
// This code is licensed under the MIT License.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files(the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions :
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.
package com.microsoft.identity.common.internal.cache;

import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.annotations.SerializedName;
import com.microsoft.identity.common.adal.internal.util.StringExtensions;
import com.microsoft.identity.common.internal.dto.AccessTokenRecord;
import com.microsoft.identity.common.internal.dto.AccountCredentialBase;
import com.microsoft.identity.common.internal.dto.AccountRecord;
import com.microsoft.identity.common.internal.dto.Credential;
import com.microsoft.identity.common.internal.dto.IdTokenRecord;
import com.microsoft.identity.common.internal.dto.RefreshTokenRecord;
import com.microsoft.identity.common.internal.logging.Logger;
import com.microsoft.identity.common.internal.providers.oauth2.TokenRequest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.microsoft.identity.common.internal.cache.CacheKeyValueDelegate.CacheKeyReplacements.AUTH_SCHEME;
import static com.microsoft.identity.common.internal.cache.CacheKeyValueDelegate.CacheKeyReplacements.CLIENT_ID;
import static com.microsoft.identity.common.internal.cache.CacheKeyValueDelegate.CacheKeyReplacements.CREDENTIAL_TYPE;
import static com.microsoft.identity.common.internal.cache.CacheKeyValueDelegate.CacheKeyReplacements.ENVIRONMENT;
import static com.microsoft.identity.common.internal.cache.CacheKeyValueDelegate.CacheKeyReplacements.HOME_ACCOUNT_ID;
import static com.microsoft.identity.common.internal.cache.CacheKeyValueDelegate.CacheKeyReplacements.REALM;
import static com.microsoft.identity.common.internal.cache.CacheKeyValueDelegate.CacheKeyReplacements.TARGET;

/**
 * Uses Gson to serialize instances of <T> into {@link String}s.
 */
public class CacheKeyValueDelegate implements ICacheKeyValueDelegate {

    private static final String TAG = CacheKeyValueDelegate.class.getSimpleName();

    /**
     * String of cache value separator.
     */
    public static final String CACHE_VALUE_SEPARATOR = "-";
    private static final String FOCI_PREFIX = "foci-";

    private final Gson mGson;

    /**
     * Default constructor of CacheKeyValueDelegate.
     */
    public CacheKeyValueDelegate() {
        mGson = new Gson();
        Logger.verbose(TAG, "Init: " + TAG);
    }

    static class CacheKeyReplacements {
        static final String HOME_ACCOUNT_ID = "<home_account_id>";
        static final String ENVIRONMENT = "<environment>";
        static final String REALM = "<realm>";
        static final String CREDENTIAL_TYPE = "<credential_type>";
        static final String CLIENT_ID = "<client_id>";
        static final String TARGET = "<target>";
        static final String AUTH_SCHEME = "<auth_scheme>";
    }

    private static String sanitizeNull(final String input) {
        String outValue = null == input ? "" : input.toLowerCase(Locale.US).trim();

        return outValue;
    }

    @Override
    public String generateCacheKey(AccountRecord account) {
        String cacheKey = HOME_ACCOUNT_ID
                + CACHE_VALUE_SEPARATOR
                + ENVIRONMENT
                + CACHE_VALUE_SEPARATOR
                + REALM;
        cacheKey = cacheKey.replace(HOME_ACCOUNT_ID, sanitizeNull(account.getHomeAccountId()));
        cacheKey = cacheKey.replace(ENVIRONMENT, sanitizeNull(account.getEnvironment()));
        cacheKey = cacheKey.replace(REALM, sanitizeNull(account.getRealm()));

        return cacheKey;
    }

    private String generateCacheValueInternal(final Object baseObject) {
        JsonElement outboundElement = mGson.toJsonTree(baseObject);
        JsonObject outboundObject = outboundElement.getAsJsonObject();

        if (baseObject instanceof AccountCredentialBase) {
            final AccountCredentialBase accountCredentialBase = (AccountCredentialBase) baseObject;
            // This basically acts as a custom serializer for AccountCredentialBase objects
            // by iterating over the additionalFields Map and JSON-ifying them
            for (final String key : accountCredentialBase.getAdditionalFields().keySet()) {
                outboundObject.add(key, accountCredentialBase.getAdditionalFields().get(key));
            }
        }

        final String json = mGson.toJson(outboundObject);

        return json;
    }

    @Override
    public String generateCacheValue(AccountRecord account) {
        final String result = generateCacheValueInternal(account);

        return result;
    }

    @SuppressWarnings("checkstyle:innerassignment")
    @Override
    public String generateCacheKey(Credential credential) {
        String cacheKey =
                HOME_ACCOUNT_ID + CACHE_VALUE_SEPARATOR
                        + ENVIRONMENT + CACHE_VALUE_SEPARATOR
                        + CREDENTIAL_TYPE + CACHE_VALUE_SEPARATOR
                        + CLIENT_ID + CACHE_VALUE_SEPARATOR
                        + REALM + CACHE_VALUE_SEPARATOR
                        + TARGET;
        cacheKey = cacheKey.replace(HOME_ACCOUNT_ID, sanitizeNull(credential.getHomeAccountId()));
        cacheKey = cacheKey.replace(ENVIRONMENT, sanitizeNull(credential.getEnvironment()));
        cacheKey = cacheKey.replace(CREDENTIAL_TYPE, sanitizeNull(credential.getCredentialType()));

        RefreshTokenRecord rt;
        if ((credential instanceof RefreshTokenRecord)
                && !StringExtensions.isNullOrBlank((rt = (RefreshTokenRecord) credential).getFamilyId())) {
            String familyIdForCacheKey = rt.getFamilyId();

            if (familyIdForCacheKey.startsWith(FOCI_PREFIX)) {
                familyIdForCacheKey = familyIdForCacheKey.replace(FOCI_PREFIX, "");
            }

            cacheKey = cacheKey.replace(CLIENT_ID, familyIdForCacheKey);
        } else {
            cacheKey = cacheKey.replace(CLIENT_ID, sanitizeNull(credential.getClientId()));
        }

        if (credential instanceof AccessTokenRecord) {
            final AccessTokenRecord accessToken = (AccessTokenRecord) credential;
            cacheKey = cacheKey.replace(REALM, sanitizeNull(accessToken.getRealm()));
            cacheKey = cacheKey.replace(TARGET, sanitizeNull(accessToken.getTarget()));

            if (TokenRequest.TokenType.POP.equalsIgnoreCase(accessToken.getAccessTokenType())) {
                cacheKey += CACHE_VALUE_SEPARATOR + AUTH_SCHEME;
                cacheKey = cacheKey.replace(AUTH_SCHEME, sanitizeNull(accessToken.getAccessTokenType()));
            }

        } else if (credential instanceof RefreshTokenRecord) {
            final RefreshTokenRecord refreshToken = (RefreshTokenRecord) credential;
            cacheKey = cacheKey.replace(REALM, "");
            cacheKey = cacheKey.replace(TARGET, sanitizeNull(refreshToken.getTarget()));
        } else if (credential instanceof IdTokenRecord) {
            final IdTokenRecord idToken = (IdTokenRecord) credential;
            cacheKey = cacheKey.replace(REALM, sanitizeNull(idToken.getRealm()));
            cacheKey = cacheKey.replace(TARGET, "");
        }

        return cacheKey;
    }

    @Override
    public String generateCacheValue(Credential credential) {
        final String result = generateCacheValueInternal(credential);

        return result;
    }

    @Override
    public <T extends AccountCredentialBase> T fromCacheValue(String string, Class<? extends AccountCredentialBase> t) {
        final String methodName = "fromCacheValue";

        try {
            @SuppressWarnings("unchecked")
            final T resultObject = (T) mGson.fromJson(string, t);

            if (!StringExtensions.isNullOrBlank(string)) {
                // Turn the incoming String into a JSONObject
                final JsonObject incomingJson = new JsonParser().parse(string).getAsJsonObject();

                // Get all of the fields we were expecting to get
                final Set<String> expectedFields = getExpectedJsonFields(t);

                // Remove the expected fields from the initial JSONObject
                for (final String expectedField : expectedFields) {
                    incomingJson.remove(expectedField);
                }

                // Add whatever is leftover to the additionalFields Map
                final Map<String, JsonElement> additionalFields = new HashMap<>();

                for (final String key : incomingJson.keySet()) {
                    additionalFields.put(key, incomingJson.get(key));
                }

                resultObject.setAdditionalFields(additionalFields);
            }

            // return the fully-formed object
            return resultObject;
        } catch (JsonSyntaxException e) {
            Logger.error(
                    TAG + ":" + methodName,
                    "Failed to parse cache value.",
                    null
            );
            return null;
        }
    }

    /**
     * For the supplied Class, return a Set of expected JSON values as dictated by @SerializedName
     * declared on its Fields.
     *
     * @param clazz The Class to inspect.
     * @return A Set of expected JSON values, as Strings.
     */
    private static Set<String> getExpectedJsonFields(final Class<? extends AccountCredentialBase> clazz) {
        final Set<String> serializedNames = new HashSet<>();
        final List<Field> fieldsToInspect = getFieldsUpTo(clazz, AccountCredentialBase.class);
        final List<Field> annotatedFields = getSerializedNameAnnotatedFields(fieldsToInspect);

        for (final Field annotatedField : annotatedFields) {
            final SerializedName serializedName = annotatedField.getAnnotation(SerializedName.class);
            serializedNames.add(serializedName.value());
        }

        return serializedNames;
    }

    /**
     * For the supplied List of Fields, return those which are annotated with @SerializedName.
     *
     * @param fieldsToInspect The Fields to inspect.
     * @return Those Fields which are annotated with @SerializedName.
     */
    private static List<Field> getSerializedNameAnnotatedFields(final List<Field> fieldsToInspect) {
        final List<Field> annotatedFields = new ArrayList<>();

        for (final Field field : fieldsToInspect) {
            SerializedName serializedName = field.getAnnotation(SerializedName.class);
            if (null != serializedName) {
                annotatedFields.add(field);
            }
        }

        return annotatedFields;
    }

    /**
     * Recursively inspect the supplied Class to obtain its Fields up the inheritance hierarchy
     * to supplied upper-bound Class.
     *
     * @param startClass The base Class to inspect.
     * @param upperBound The Class' upper-bounded inheritor or null, if Object should be used.
     * @return A List of Fields on the supplied object and its superclasses.
     */
    private static List<Field> getFieldsUpTo(
            final Class<?> startClass,
            @Nullable Class<?> upperBound) {
        List<Field> currentClassFields = new ArrayList<>(Arrays.asList(startClass.getDeclaredFields()));
        Class<?> parentClass = startClass.getSuperclass();

        if (parentClass != null && (upperBound == null || !(parentClass.equals(upperBound)))) {
            List<Field> parentClassFields = getFieldsUpTo(parentClass, upperBound);
            currentClassFields.addAll(parentClassFields);
        }

        return currentClassFields;
    }
}
