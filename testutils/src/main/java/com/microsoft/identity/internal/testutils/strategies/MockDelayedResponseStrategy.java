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

import com.microsoft.identity.common.adal.internal.AuthenticationConstants;
import com.microsoft.identity.common.internal.net.HttpResponse;
import com.microsoft.identity.common.internal.net.ObjectMapper;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsOAuth2Configuration;
import com.microsoft.identity.common.internal.providers.microsoft.microsoftsts.MicrosoftStsTokenRequest;
import com.microsoft.identity.common.internal.providers.oauth2.TokenResponse;
import com.microsoft.identity.common.internal.providers.oauth2.TokenResult;
import com.microsoft.identity.internal.testutils.MockTokenResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MockDelayedResponseStrategy extends ResourceOwnerPasswordCredentialsTestStrategy {

    private final static long RESPONSE_DELAY = 1000;

    /**
     * Constructor of MockTestStrategy.
     *
     * @param config Microsoft Sts OAuth2 configuration
     */
    public MockDelayedResponseStrategy(MicrosoftStsOAuth2Configuration config) {
        super(config);
    }

    @Override
    String getPasswordForUser(String username) {
        return "fake-password";
    }

    public TokenResult getTokenResult() {
        final TokenResponse tokenResponse = MockTokenResponse.getMockSuccessTokenResponse();
        final TokenResult tokenResult = new TokenResult(tokenResponse);
        return tokenResult;
    }

    @Override
    protected HttpResponse performTokenRequest(final MicrosoftStsTokenRequest tokenRequest) {
        final TokenResult tokenResult = getTokenResult();
        final TokenResponse tokenResponse = tokenResult.getTokenResponse();
        try {
            Thread.sleep(RESPONSE_DELAY);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        final HttpResponse httpResponse = makeHttpResponseFromResponseObject(tokenResponse);
        return httpResponse;
    }

    public HttpResponse makeHttpResponseFromResponseObject(final Object obj) {
        final String httpResponseBody = ObjectMapper.serializeObjectToJsonString(obj);
        final HashMap<String, List<String>> responseHeaders = new HashMap<>();
        responseHeaders.put(AuthenticationConstants.HeaderField.X_MS_CLITELEM, new ArrayList<>(Collections.singleton("1,0,0,,")));
        final HttpResponse httpResponse = new HttpResponse(200, httpResponseBody, responseHeaders);
        return httpResponse;
    }

}
