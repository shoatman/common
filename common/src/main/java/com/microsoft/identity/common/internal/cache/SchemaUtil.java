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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.text.TextUtils;

import com.microsoft.identity.common.adal.internal.AuthenticationConstants;
import com.microsoft.identity.common.adal.internal.util.StringExtensions;
import com.microsoft.identity.common.exception.ServiceException;
import com.microsoft.identity.common.internal.dto.CredentialType;
import com.microsoft.identity.common.internal.logging.Logger;
import com.microsoft.identity.common.internal.providers.microsoft.MicrosoftIdToken;
import com.microsoft.identity.common.internal.providers.microsoft.azureactivedirectory.AzureActiveDirectoryIdToken;
import com.microsoft.identity.common.internal.providers.microsoft.azureactivedirectory.ClientInfo;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsIdToken;
import com.microsoft.identity.common.internal.providers.oauth2.IDToken;

import java.util.Map;

/**
 * Utility class for performing common actions needed for the common cache schema.
 */
public final class SchemaUtil {

    private static final String TAG = SchemaUtil.class.getSimpleName();
    private static final String EXCEPTION_CONSTRUCTING_IDTOKEN = "Exception constructing IDToken. ";
    public static final String MISSING_FROM_THE_TOKEN_RESPONSE = "Missing from the token response";

    private SchemaUtil() {
        // Utility class.
    }

    /**
     * Returns the authority (issuer) for the supplied IDToken.
     *
     * @param idToken The IDToken to parse.
     * @return The issuer or null if the IDToken cannot be parsed or the issuer claim is empty.
     */
    public static String getAuthority(final IDToken idToken) {
        final String methodName = "getAuthority";

        String issuer = null;

        if (null != idToken) {
            final Map<String, ?> idTokenClaims = idToken.getTokenClaims();

            if (null != idTokenClaims) {
                issuer = (String) idTokenClaims.get(MicrosoftIdToken.ISSUER);
                Logger.verbosePII(TAG + ":" + methodName, "Issuer: " + issuer);

                if (null == issuer) {
                    Logger.warn(TAG + ":" + methodName, "Environment was null or could not be parsed.");
                }
            } else {
                Logger.warn(TAG + ":" + methodName, "IDToken claims were null");
            }
        } else {
            Logger.warn(TAG + ":" + methodName, "IDToken was null");
        }

        return issuer;
    }

    /**
     * Returns the 'avatar url' for the supplied IDToken.
     *
     * @param idToken The IDToken to parse.
     * @return The environment or null if the IDToken cannot be parsed or the picture claim is empty.
     */
    public static String getAvatarUrl(final IDToken idToken) {
        final String methodName = "getAvatarUrl";

        String avatarUrl = null;

        if (null != idToken) {
            final Map<String, ?> idTokenClaims = idToken.getTokenClaims();

            if (null != idTokenClaims) {
                avatarUrl = (String) idTokenClaims.get(IDToken.PICTURE);

                Logger.verbosePII(TAG + ":" + methodName, "Avatar URL: " + avatarUrl);

                if (null == avatarUrl) {
                    Logger.warn(TAG + ":" + methodName, "Avatar URL was null.");
                }
            } else {
                Logger.warn(TAG + ":" + methodName, "IDToken claims were null.");
            }
        } else {
            Logger.warn(TAG + ":" + methodName, "IDToken was null.");
        }

        return avatarUrl;
    }

    /**
     * Returns the 'alternative_account_id' for the supplied IDToken.
     *
     * @param idToken The IDToken to parse.
     * @return The alternative_account_id or null if the IDToken cannot be parsed or the altsecid
     * claim is empty.
     */
    public static String getAlternativeAccountId(final IDToken idToken) {
        final String methodName = "getAlternativeAccountId";

        String alternativeAccountId = null;

        if (null != idToken) {
            final Map<String, ?> idTokenClaims = idToken.getTokenClaims();

            if (null != idTokenClaims) {
                alternativeAccountId = (String) idTokenClaims.get("altsecid");

                Logger.verbosePII(TAG + ":" + methodName, "alternative_account_id: " + alternativeAccountId);

                if (null == alternativeAccountId) {
                    Logger.warn(TAG + ":" + methodName, "alternative_account_id was null.");
                }
            } else {
                Logger.warn(TAG + ":" + methodName, "IDToken claims were null.");
            }
        } else {
            Logger.warn(TAG + ":" + methodName, "IDToken was null.");
        }

        return alternativeAccountId;
    }

    public static String getCredentialTypeFromVersion(@Nullable final String idTokenString) {
        final String methodName = "getCredentialTypeFromVersion";

        // Default is v2
        String idTokenVersion = CredentialType.IdToken.name();

        if (!TextUtils.isEmpty(idTokenString)) {
            IDToken idToken;
            try {
                idToken = new IDToken(idTokenString);
                final Map<String, ?> idTokenClaims = idToken.getTokenClaims();
                final String aadVersion = (String) idTokenClaims.get(
                        AuthenticationConstants.OAuth2.AAD_VERSION
                );

                if (!TextUtils.isEmpty(aadVersion)
                        && aadVersion.equalsIgnoreCase(AuthenticationConstants.OAuth2.AAD_VERSION_V1)) {
                    idTokenVersion = CredentialType.V1IdToken.name();
                }
            } catch (ServiceException e) {
                Logger.warn(TAG + ":" + methodName, EXCEPTION_CONSTRUCTING_IDTOKEN + e.getMessage());
            }
        }

        return idTokenVersion;
    }

    public static String getIdentityProvider(final String idTokenString) {
        final String methodName = "getIdentityProvider";

        String idp = null;

        if (null != idTokenString) {
            IDToken idToken;
            try {
                idToken = new IDToken(idTokenString);
                final Map<String, ?> idTokenClaims = idToken.getTokenClaims();

                if (null != idTokenClaims) {
                    // IDP claim is present only in case of guest scenerio and is empty for home tenants.
                    // Few Apps consuming ADAL use this to differentiate between home vs guest accounts.
                    idp = (String) idTokenClaims.get(AzureActiveDirectoryIdToken.IDENTITY_PROVIDER);

                    Logger.verbosePII(TAG + ":" + methodName, "idp: " + idp);
                    if (null == idp) {
                        Logger.info(TAG + ":" + methodName, "idp claim was null.");
                    }
                } else {
                    Logger.warn(TAG + ":" + methodName, "IDToken claims were null.");
                }
            } catch (ServiceException e) {
                Logger.warn(TAG + ":" + methodName, EXCEPTION_CONSTRUCTING_IDTOKEN + e.getMessage());
            }
        } else {
            Logger.warn(TAG + ":" + methodName, "IDToken was null.");
        }

        return idp;
    }

    /**
     * Get the home account id with the client info.
     *
     * @param clientInfo ClientInfo
     * @return String home account id
     */
    public static String getHomeAccountId(final ClientInfo clientInfo) {
        final String methodName = ":getHomeAccountId";

        String homeAccountId = null;

        if (null != clientInfo) {
            final String uid = clientInfo.getUid();
            final String utid = clientInfo.getUtid();

            if (StringExtensions.isNullOrBlank(uid)) {
                Logger.warn(TAG + ":" + methodName, "uid was null/blank");
            }

            if (StringExtensions.isNullOrBlank(utid)) {
                Logger.warn(TAG + ":" + methodName, "utid was null/blank");
            }

            if (!StringExtensions.isNullOrBlank(uid) && !StringExtensions.isNullOrBlank(utid)) {
                homeAccountId = uid + "." + utid;
            }

            Logger.verbosePII(TAG + ":" + methodName, "home_account_id: " + homeAccountId);

        } else {
            Logger.warn(TAG + ":" + methodName, "ClientInfo was null.");
        }

        return homeAccountId;
    }

    /**
     * Get tenant id claim from Id token , if not present returns the tenant id from client info
     *
     * @param clientInfoString : ClientInfo
     * @param idTokenString    : Id Token
     * @return tenantId
     */
    @Nullable
    public static String getTenantId(@Nullable final String clientInfoString,
                                     @Nullable final String idTokenString) {

        String tenantId = null;

        try {
            if (!TextUtils.isEmpty(idTokenString) && !TextUtils.isEmpty(clientInfoString)) {
                final IDToken idToken = new IDToken(idTokenString);
                final ClientInfo clientInfo = new ClientInfo(clientInfoString);
                final Map<String, ?> claims = idToken.getTokenClaims();

                if (!TextUtils.isEmpty((CharSequence) claims.get(AzureActiveDirectoryIdToken.TENANT_ID))) {
                    tenantId = (String) claims.get(AzureActiveDirectoryIdToken.TENANT_ID);
                } else if (!TextUtils.isEmpty(clientInfo.getUtid())) {
                    Logger.warn(TAG, "realm is not returned from server. Use utid as realm.");
                    tenantId = clientInfo.getUtid();
                } else {
                    Logger.warn(TAG,
                            "realm and utid is not returned from server. " +
                                    "Using empty string as default tid."
                    );
                }
            }
        } catch (final ServiceException e) {
            Logger.errorPII(
                    TAG,
                    "Failed to construct IDToken or ClientInfo",
                    e
            );
        }

        return tenantId;

    }

    public static String getDisplayableId(@NonNull final Map<String, ?> claims) {
        if (!StringExtensions.isNullOrBlank((String) claims.get(MicrosoftStsIdToken.PREFERRED_USERNAME))) {
            return (String) claims.get(MicrosoftStsIdToken.PREFERRED_USERNAME);
        } else if (!StringExtensions.isNullOrBlank((String) claims.get(MicrosoftStsIdToken.EMAIL))) {
            return (String) claims.get(MicrosoftStsIdToken.EMAIL);
        } else if (!StringExtensions.isNullOrBlank((String) claims.get(AzureActiveDirectoryIdToken.UPN))) {
            // TODO : Temporary Hack to read and store V1 id token in Cache for V2 request
            return (String) claims.get(AzureActiveDirectoryIdToken.UPN);
        } else {
            Logger.warn(TAG, "The preferred username is not returned from the IdToken.");
            return MISSING_FROM_THE_TOKEN_RESPONSE;
        }
    }
}
