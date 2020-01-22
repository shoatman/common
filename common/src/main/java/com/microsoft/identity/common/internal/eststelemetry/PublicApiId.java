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
package com.microsoft.identity.common.internal.eststelemetry;

public final class PublicApiId {

    //region Silent APIs

    // PublicClientApplication
    //==============================================================================================
    public static final String PCA_ACQUIRE_TOKEN_SILENT_WITH_PARAMETERS = "21";
    public static final String PCA_ACQUIRE_TOKEN_SILENT_ASYNC_WITH_PARAMETERS = "22";

    // SingleAccountPublicClientApplication
    //==============================================================================================
    public static final String SINGLE_ACCOUNT_PCA_ACQUIRE_TOKEN_SILENT_WITH_PARAMETERS = "23";
    public static final String SINGLE_ACCOUNT_PCA_ACQUIRE_TOKEN_SILENT_ASYNC_WITH_PARAMETERS = "24";
    public static final String SINGLE_ACCOUNT_PCA_ACQUIRE_TOKEN_SILENT_WITH_SCOPES_AUTHORITY = "25";
    public static final String SINGLE_ACCOUNT_PCA_ACQUIRE_TOKEN_SILENT_ASYNC_WITH_SCOPES_AUTHORITY_CALLBACK = "26";

    // MultipleAccountPublicClientApplication
    //==============================================================================================
    public static final String MULTIPLE_ACCOUNT_PCA_ACQUIRE_TOKEN_SILENT_WITH_SCOPES_ACCOUNT_AUTHORITY = "27";
    public static final String MULTIPLE_ACCOUNT_PCA_ACQUIRE_TOKEN_SILENT_ASYNC_WITH_SCOPES_ACCOUNT_AUTHORITY_CALLBACK = "28";

    // BrokerClientApplication
    //==============================================================================================
    public static final String BROKER_ACQUIRE_TOKEN_SILENT_WITH_PARAMETERS_CALLBACK = "29";

    //endregion

    //region Interactive APIs

    // PublicClientApplication
    //==============================================================================================
    public static final String PCA_ACQUIRE_TOKEN_WITH_PARAMETERS = "121";
    public static final String PCA_ACQUIRE_TOKEN_WITH_ACTIVITY_SCOPES_CALLBACK = "122";

    // SingleAccountPublicClientApplication;
    //==============================================================================================
    public static final String SINGLE_ACCOUNT_PCA_SIGN_IN = "123";
    public static final String SINGLE_ACCOUNT_PCA_ACQUIRE_TOKEN_WITH_PARAMETERS = "124";
    public static final String SINGLE_ACCOUNT_PCA_ACQUIRE_TOKEN_WITH_ACTIVITY_SCOPES_CALLBACK = "125";

    // MultipleAccountPublicClientApplication
    //==============================================================================================
    public static final String MULTIPLE_ACCOUNT_PCA_ACQUIRE_TOKEN_WITH_ACTIVITY_SCOPES_LOGINHINT_CALLBACK = "126";

    // BrokerClientApplication
    //==============================================================================================
    public static final String BROKER_ACQUIRE_TOKEN_WITH_PARAMETERS_CALLBACK = "127";
    public static final String BROKER_ADD_ACCOUNT_WITH_ACTIVITY = "128";
    public static final String BROKER_CHOOSE_ACCOUNT_WITH_ACTIVITY_ACCOUNTNAME = "129";

    //endregion

    // region GET Accounts

    // SingleAccountPublicClientApplication
    //==============================================================================================
    public static final String SINGLE_ACCOUNT_PCA_GET_CURRENT_ACCOUNT = "921";
    public static final String SINGLE_ACCOUNT_PCA_GET_CURRENT_ACCOUNT_ASYNC = "922";

    // MultipleAccountPublicClientApplication
    //==============================================================================================
    public static final String MULTIPLE_ACCOUNT_PCA_GET_ACCOUNTS = "923";
    public static final String MULTIPLE_ACCOUNT_PCA_GET_ACCOUNTS_WITH_CALLBACK = "924";
    public static final String MULTIPLE_ACCOUNT_PCA_GET_ACCOUNT_WITH_IDENTIFIER = "925";
    public static final String MULTIPLE_ACCOUNT_PCA_GET_ACCOUNT_WITH_IDENTIFIER_CALLBACK = "926";

    //endregion

    // region REMOVE account

    // SingleAccountPublicClientApplication
    //==============================================================================================
    public static final String SINGLE_ACCOUNT_PCA_SIGN_OUT = "927";
    public static final String SINGLE_ACCOUNT_PCA_SIGN_OUT_WITH_CALLBACK = "928";

    // MultipleAccountPublicClientApplication
    //==============================================================================================
    public static final String MULTIPLE_ACCOUNT_PCA_REMOVE_ACCOUNT_WITH_ACCOUNT = "929";
    public static final String MULTIPLE_ACCOUNT_PCA_REMOVE_ACCOUNT_WITH_ACCOUNT_CALLBACK = "930";

    //endregion
}
