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
package com.microsoft.identity.common;

import androidx.test.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.microsoft.identity.common.exception.ClientException;
import com.microsoft.identity.common.internal.authscheme.AbstractAuthenticationScheme;
import com.microsoft.identity.common.internal.authscheme.BearerAuthenticationSchemeInternal;
import com.microsoft.identity.common.internal.cache.ICacheRecord;
import com.microsoft.identity.common.internal.cache.MicrosoftFamilyOAuth2TokenCache;
import com.microsoft.identity.common.internal.dto.AccountRecord;
import com.microsoft.identity.common.internal.dto.CredentialType;
import com.microsoft.identity.common.internal.providers.microsoft.MicrosoftAccount;
import com.microsoft.identity.common.internal.providers.microsoft.MicrosoftRefreshToken;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationRequest;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Strategy;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsTokenResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import static com.microsoft.identity.common.SharedPreferencesAccountCredentialCacheTest.AUTHORITY_TYPE;
import static com.microsoft.identity.common.SharedPreferencesAccountCredentialCacheTest.CACHED_AT;
import static com.microsoft.identity.common.SharedPreferencesAccountCredentialCacheTest.CLIENT_ID;
import static com.microsoft.identity.common.SharedPreferencesAccountCredentialCacheTest.ENVIRONMENT;
import static com.microsoft.identity.common.SharedPreferencesAccountCredentialCacheTest.EXPIRES_ON;
import static com.microsoft.identity.common.SharedPreferencesAccountCredentialCacheTest.REALM;
import static com.microsoft.identity.common.SharedPreferencesAccountCredentialCacheTest.SECRET;
import static com.microsoft.identity.common.SharedPreferencesAccountCredentialCacheTest.TARGET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class MicrosoftFamilyOAuth2TokenCacheTest extends MsalOAuth2TokenCacheTest {

    private static final AbstractAuthenticationScheme BEARER_SCHEME = new BearerAuthenticationSchemeInternal();

    private MicrosoftFamilyOAuth2TokenCache<
            MicrosoftStsOAuth2Strategy,
            MicrosoftStsAuthorizationRequest,
            MicrosoftStsTokenResponse,
            MicrosoftAccount,
            MicrosoftRefreshToken> mOauth2TokenCache;

    @Before
    @Override
    public void setUp() throws Exception {
        super.setUp();
        mOauth2TokenCache = new MicrosoftFamilyOAuth2TokenCache<>(
                InstrumentationRegistry.getTargetContext(),
                accountCredentialCache,
                mockCredentialAdapter
        );
    }

    @After
    public void tearDown() {
        super.tearDown();
    }

    @Test
    public void testRetrieveFrt() throws ClientException {
        final String randomHomeAccountId = UUID.randomUUID().toString();

        final AccountCredentialTestBundle frtTestBundle = new AccountCredentialTestBundle(
                MicrosoftAccount.AUTHORITY_TYPE_V1_V2,
                UUID.randomUUID().toString(),
                "test.user@tenant.onmicrosoft.com",
                randomHomeAccountId,
                ENVIRONMENT,
                UUID.randomUUID().toString(),
                TARGET,
                CACHED_AT,
                EXPIRES_ON,
                SECRET,
                CLIENT_ID,
                SECRET,
                MicrosoftStsAccountCredentialAdapterTest.MOCK_ID_TOKEN_WITH_CLAIMS,
                "1",
                CredentialType.IdToken
        );

        when(
                mockCredentialAdapter.createAccount(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle.mGeneratedAccount);

        when(
                mockCredentialAdapter.createAccessToken(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle.mGeneratedAccessToken);

        when(
                mockCredentialAdapter.createRefreshToken(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle.mGeneratedRefreshToken);

        when(
                mockCredentialAdapter.createIdToken(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle.mGeneratedIdToken);

        // Save the family token data
        mOauth2TokenCache.save(
                mockStrategy,
                mockRequest,
                mockResponse
        );

        final ICacheRecord familyCacheRecord = mOauth2TokenCache.loadByFamilyId(
                null,
                TARGET,
                frtTestBundle.mGeneratedAccount,
                BEARER_SCHEME
        );

        assertNotNull(familyCacheRecord);
        assertNotNull(familyCacheRecord.getAccount());
        assertNotNull(familyCacheRecord.getRefreshToken());
        assertNull(familyCacheRecord.getIdToken());
        assertNull(familyCacheRecord.getAccessToken());

        final ICacheRecord familyCacheRecordWithClientId = mOauth2TokenCache.loadByFamilyId(
                CLIENT_ID,
                TARGET,
                frtTestBundle.mGeneratedAccount,
                BEARER_SCHEME
        );

        assertNotNull(familyCacheRecordWithClientId);
        assertNotNull(familyCacheRecordWithClientId.getAccount());
        assertNotNull(familyCacheRecordWithClientId.getRefreshToken());
        assertNotNull(familyCacheRecordWithClientId.getIdToken());
        assertNotNull(familyCacheRecordWithClientId.getAccessToken());

        final ICacheRecord familyCacheRecordWithClientIdButNonMatchingTarget =
                mOauth2TokenCache.loadByFamilyId(
                        CLIENT_ID,
                        TARGET,
                        frtTestBundle.mGeneratedAccount,
                        BEARER_SCHEME
                );

        assertNotNull(familyCacheRecordWithClientIdButNonMatchingTarget);
        assertNotNull(familyCacheRecordWithClientIdButNonMatchingTarget.getAccount());
        assertNotNull(familyCacheRecordWithClientIdButNonMatchingTarget.getRefreshToken());
        assertNotNull(familyCacheRecordWithClientIdButNonMatchingTarget.getIdToken());
        assertNotNull(familyCacheRecordWithClientIdButNonMatchingTarget.getAccessToken());

        final ICacheRecord wrongClientIdResult =
                mOauth2TokenCache.loadByFamilyId(
                        "12345",
                        TARGET,
                        frtTestBundle.mGeneratedAccount,
                        BEARER_SCHEME
                );

        assertNotNull(wrongClientIdResult);
        assertNotNull(wrongClientIdResult.getAccount());
        assertNotNull(wrongClientIdResult.getRefreshToken());
        assertNull(wrongClientIdResult.getIdToken());
        assertNull(wrongClientIdResult.getAccessToken());
    }

    @Test
    public void testOnlyOneFrtMayExistAcrossClientsForAccount() throws ClientException {
        // Save an FRT
        final String randomHomeAccountId = UUID.randomUUID().toString();
        final String localAccountId = UUID.randomUUID().toString();
        final String realm = UUID.randomUUID().toString();

        final AccountCredentialTestBundle frtTestBundle = new AccountCredentialTestBundle(
                MicrosoftAccount.AUTHORITY_TYPE_V1_V2,
                localAccountId,
                "test.user@tenant.onmicrosoft.com",
                randomHomeAccountId,
                ENVIRONMENT,
                realm,
                TARGET,
                CACHED_AT,
                EXPIRES_ON,
                SECRET,
                CLIENT_ID,
                SECRET,
                MicrosoftStsAccountCredentialAdapterTest.MOCK_ID_TOKEN_WITH_CLAIMS,
                "1",
                CredentialType.IdToken
        );

        when(
                mockCredentialAdapter.createAccount(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle.mGeneratedAccount);

        when(
                mockCredentialAdapter.createAccessToken(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle.mGeneratedAccessToken);

        when(
                mockCredentialAdapter.createRefreshToken(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle.mGeneratedRefreshToken);

        when(
                mockCredentialAdapter.createIdToken(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle.mGeneratedIdToken);

        mOauth2TokenCache.save(
                mockStrategy,
                mockRequest,
                mockResponse
        );

        // Save another FRT, this time with a different client id
        final AccountCredentialTestBundle frtTestBundle2 = new AccountCredentialTestBundle(
                MicrosoftAccount.AUTHORITY_TYPE_V1_V2,
                localAccountId,
                "test.user@tenant.onmicrosoft.com",
                randomHomeAccountId,
                ENVIRONMENT,
                realm,
                TARGET,
                CACHED_AT,
                EXPIRES_ON,
                SECRET,
                CLIENT_ID + "2",
                SECRET,
                MicrosoftStsAccountCredentialAdapterTest.MOCK_ID_TOKEN_WITH_CLAIMS,
                "1",
                CredentialType.IdToken
        );

        when(
                mockCredentialAdapter.createAccount(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle2.mGeneratedAccount);

        when(
                mockCredentialAdapter.createAccessToken(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle2.mGeneratedAccessToken);

        when(
                mockCredentialAdapter.createRefreshToken(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle2.mGeneratedRefreshToken);

        when(
                mockCredentialAdapter.createIdToken(
                        mockStrategy,
                        mockRequest,
                        mockResponse
                )
        ).thenReturn(frtTestBundle2.mGeneratedIdToken);

        // Save the family token data
        mOauth2TokenCache.save(
                mockStrategy,
                mockRequest,
                mockResponse
        );

        // Test only one FRT exists and it is the second one saved...
        final ICacheRecord cacheRecord = mOauth2TokenCache.loadByFamilyId(
                CLIENT_ID,
                TARGET,
                frtTestBundle2.mGeneratedAccount,
                BEARER_SCHEME
        );

        assertNotNull(cacheRecord);
        assertNotNull(cacheRecord.getRefreshToken());
        assertNotNull(cacheRecord.getAccessToken());
        assertNotNull(cacheRecord.getIdToken());
        assertEquals(
                CLIENT_ID + "2",
                cacheRecord.getRefreshToken().getClientId()
        );

        // Check querying for the FRT in the second app yields the same FRT
        final ICacheRecord cacheRecord2 = mOauth2TokenCache.loadByFamilyId(
                CLIENT_ID + "2",
                TARGET,
                frtTestBundle2.mGeneratedAccount,
                BEARER_SCHEME
        );

        assertNotNull(cacheRecord2);
        assertNotNull(cacheRecord2.getRefreshToken());
        assertNotNull(cacheRecord2.getAccessToken());
        assertNotNull(cacheRecord2.getIdToken());
        assertEquals(
                CLIENT_ID + "2",
                cacheRecord2.getRefreshToken().getClientId()
        );

        // Test querying with a different account yields nothing at all....

        final AccountRecord randomAcct = new AccountRecord();
        randomAcct.setAuthorityType(AUTHORITY_TYPE);
        randomAcct.setLocalAccountId(UUID.randomUUID().toString());
        randomAcct.setUsername("foo@bar.com");
        randomAcct.setHomeAccountId(UUID.randomUUID().toString());
        randomAcct.setEnvironment(ENVIRONMENT);
        randomAcct.setRealm(REALM);

        final ICacheRecord cacheRecord3 = mOauth2TokenCache.loadByFamilyId(
                CLIENT_ID + "2",
                TARGET,
                randomAcct,
                BEARER_SCHEME
        );

        assertNotNull(cacheRecord3);
        assertNotNull(cacheRecord3.getAccount());
        assertNull(cacheRecord3.getRefreshToken());
        assertNull(cacheRecord3.getAccessToken());
        assertNull(cacheRecord3.getIdToken());
    }

}
