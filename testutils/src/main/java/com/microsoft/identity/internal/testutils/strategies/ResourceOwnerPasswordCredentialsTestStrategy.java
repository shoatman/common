//  Copyright (c) Microsoft Corporation.
//  All rights reserved.
//
//  This code is licensed under the MIT License.
//
//  Permission is hereby granted, free of charge, to any person obtaining a copy
//  of this software and associated documentation files(the "Software"), to deal
//  in the Software without restriction, including without limitation the rights
//  to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
//  copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions :
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
//  THE SOFTWARE.
package com.microsoft.identity.internal.testutils.strategies;

import androidx.annotation.NonNull;

import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationRequest;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsAuthorizationResponse;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Configuration;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Strategy;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsTokenRequest;
import com.microsoft.identity.common.internal.providers.oauth2.AuthorizationResult;
import com.microsoft.identity.common.internal.providers.oauth2.AuthorizationStrategy;
import com.microsoft.identity.common.internal.providers.oauth2.TokenRequest;
import com.microsoft.identity.common.internal.result.ResultFuture;
import com.microsoft.identity.common.internal.util.StringUtil;
import com.microsoft.identity.internal.testutils.MicrosoftStsRopcTokenRequest;
import com.microsoft.identity.internal.testutils.MockSuccessAuthorizationResultNetworkTests;
import com.microsoft.identity.internal.testutils.labutils.CurrentLabConfig;

import java.util.concurrent.Future;

public class ResourceOwnerPasswordCredentialsTestStrategy extends MicrosoftStsOAuth2Strategy {

    private static final String TAG = ResourceOwnerPasswordCredentialsTestStrategy.class.getSimpleName();

    public static final String USERNAME_EMPTY_OR_NULL = "username_empty_or_null";
    public static final String PASSWORD_EMPTY_OR_NULL = "password_empty_or_null";
    public static final String SCOPE_EMPTY_OR_NULL = "scope_empty_or_null";

    /**
     * Constructor of ResourceOwnerPasswordCredentialsTestStrategy.
     *
     * @param config Microsoft Sts OAuth2 configuration
     */
    public ResourceOwnerPasswordCredentialsTestStrategy(MicrosoftStsOAuth2Configuration config) {
        super(config);
    }

    /**
     * Template method for executing an OAuth2 authorization request.
     *
     * @param request               microsoft sts authorization request.
     * @param authorizationStrategy authorization strategy.
     * @return GenericAuthorizationResponse
     */
    @Override
    public Future<AuthorizationResult> requestAuthorization(
            final MicrosoftStsAuthorizationRequest request,
            final AuthorizationStrategy authorizationStrategy) {
        final MockSuccessAuthorizationResultNetworkTests authorizationResult = new MockSuccessAuthorizationResultNetworkTests();
        final ResultFuture<AuthorizationResult> future = new ResultFuture<>();
        future.setResult(authorizationResult);
        return future;
    }

    @Override
    protected void validateTokenRequest(MicrosoftStsTokenRequest request) {
        if (StringUtil.isEmpty(request.getScope())) {
            throw new IllegalArgumentException(SCOPE_EMPTY_OR_NULL);
        }

        if (request.getGrantType().equals(TokenRequest.GrantTypes.PASSWORD)) {
            validateTokenRequestForPasswordGrant(request);
        }
    }

    private void validateTokenRequestForPasswordGrant(MicrosoftStsTokenRequest request) {
        if (!(request instanceof MicrosoftStsRopcTokenRequest)) {
            throw new IllegalArgumentException("Did you make sure to pass a MicrosoftStsRopcTokenRequest?");
        }

        MicrosoftStsRopcTokenRequest ropcRequest = (MicrosoftStsRopcTokenRequest) request;

        if (StringUtil.isEmpty(ropcRequest.getUsername())) {
            throw new IllegalArgumentException(USERNAME_EMPTY_OR_NULL);
        }

        if (StringUtil.isEmpty(ropcRequest.getPassword())) {
            throw new IllegalArgumentException(PASSWORD_EMPTY_OR_NULL);
        }
    }

    String getPasswordForUser(String username) {
        return CurrentLabConfig.labUserPassword;
    }

    @Override
    public MicrosoftStsTokenRequest createTokenRequest(@NonNull final MicrosoftStsAuthorizationRequest request,
                                                       @NonNull final MicrosoftStsAuthorizationResponse response) {
        MicrosoftStsTokenRequest tokenRequest = super.createTokenRequest(request, response);

        final MicrosoftStsRopcTokenRequest ropcTokenRequest = new MicrosoftStsRopcTokenRequest();
        ropcTokenRequest.setClientId(tokenRequest.getClientId());
        ropcTokenRequest.setScope(tokenRequest.getScope());

        final String username = request.getLoginHint();
        final String password = getPasswordForUser(username);

        ropcTokenRequest.setUsername(username);
        ropcTokenRequest.setPassword(password);

        ropcTokenRequest.setGrantType(TokenRequest.GrantTypes.PASSWORD);

        return ropcTokenRequest;
    }
}
