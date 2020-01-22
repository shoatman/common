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
package com.microsoft.identity.internal.testutils.labutils;

public class LabConstants {

    public static final class UserType {
        public static final String CLOUD = "cloud";
        public static final String FEDERATED = "federated";
        public static final String ON_PREM = "onprem";
        public static final String GUEST = "guest";
        public static final String MSA = "msa";
        public static final String B2C = "b2c";
    }

    public static final class Mfa {
        public static final String NONE = "none";
        public static final String MFA_ON_ALL = "mfaonall";
        public static final String AUTO_MFA_ON_ALL = "automfaonall";
    }

    public static final class ProtectionPolicy {
        public static final String NONE = "none";
        public static final String CA = "ca";
        public static final String CADJ = "cadj";
        public static final String MAM = "mam";
        public static final String MDM = "mdm";
        public static final String MDM_CA = "mdmca";
        public static final String MAM_CA = "mamca";
        public static final String MAM_SPO = "mamspo";
    }

    public static final class HomeDomain {
        public static final String NONE = "none";
        public static final String LAB_2 = "msidlab2.com";
        public static final String LAB_3 = "msidlab3.com";
        public static final String LAB_4 = "msidlab4.com";
    }

    public static final class B2CProvider {
        public static final String NONE = "none";
        public static final String AMAZON = "amazon";
        public static final String FACEBOOK = "facebook";
        public static final String GOOGLE = "google";
        public static final String LOCAL = "local";
        public static final String MICROSOFT = "microsoft";
        public static final String TWITTER = "twitter";
    }

    public static final class FederationProvider {
        public static final String NA = "na";
        public static final String ADFS_V2 = "adfsv2";
        public static final String ADFS_V3 = "adfsv3";
        public static final String ADFS_V4 = "adfsv4";
        public static final String ADFS_V2019 = "adfsv2019";
        public static final String B2C = "b2c";
        public static final String PING = "ping";
        public static final String SHIBBOLETH = "shibboleth";
    }

    public static final class AzureEnvironment {
        public static final String AZURE_B2C_CLOUD = "azureb2ccloud";
        public static final String AZURE_CHINA_CLOUD = "azurechinacloud";
        public static final String AZURE_CLOUD = "azurecloud";
        public static final String AZURE_GERMANY_CLOUD = "azuregermanycloud";
        public static final String AZURE_PPE = "azureppe";
        public static final String AZURE_US_GOVERNMENT = "azureusgovernment";
    }

    public static final class SignInAudience {
        public static final String AZURE_AD_MY_ORG = "azureadmyorg";
        public static final String AZURE_AD_MULTIPLE_ORGS = "azureadmultipleorgs";
        public static final String AZURE_AD_AND_PERSONAL_MICROSOFT_ACCOUNT = "azureadandpersonalmicrosoftaccount";
    }
}
