/*
 * Azure Identity Labs API
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.0.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.microsoft.identity.internal.test.labapi.api;

import com.google.gson.reflect.TypeToken;
import com.microsoft.identity.internal.test.labapi.ApiCallback;
import com.microsoft.identity.internal.test.labapi.ApiClient;
import com.microsoft.identity.internal.test.labapi.ApiException;
import com.microsoft.identity.internal.test.labapi.ApiResponse;
import com.microsoft.identity.internal.test.labapi.Configuration;
import com.microsoft.identity.internal.test.labapi.Pair;
import com.microsoft.identity.internal.test.labapi.ProgressRequestBody;
import com.microsoft.identity.internal.test.labapi.ProgressResponseBody;
import com.microsoft.identity.internal.test.labapi.model.AppInfo;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppApi {
    private ApiClient apiClient;

    public AppApi() {
        this(Configuration.getDefaultApiClient());
    }

    public AppApi(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public ApiClient getApiClient() {
        return apiClient;
    }

    public void setApiClient(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    /**
     * Build call for getApp
     *
     * @param appid                   Provide the Application ID to query Lab App Info (required)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getAppCall(String appid, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/App/{appid}"
                .replaceAll("\\{" + "appid" + "\\}", apiClient.escapeString(appid.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getAppValidateBeforeCall(String appid, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {

        // verify the required parameter 'appid' is set
        if (appid == null) {
            throw new ApiException("Missing the required parameter 'appid' when calling getApp(Async)");
        }


        com.squareup.okhttp.Call call = getAppCall(appid, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Gets App Info based on App ID
     *
     * @param appid Provide the Application ID to query Lab App Info (required)
     * @return List&lt;AppInfo&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<AppInfo> getApp(String appid) throws ApiException {
        ApiResponse<List<AppInfo>> resp = getAppWithHttpInfo(appid);
        return resp.getData();
    }

    /**
     * Gets App Info based on App ID
     *
     * @param appid Provide the Application ID to query Lab App Info (required)
     * @return ApiResponse&lt;List&lt;AppInfo&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<AppInfo>> getAppWithHttpInfo(String appid) throws ApiException {
        com.squareup.okhttp.Call call = getAppValidateBeforeCall(appid, null, null);
        Type localVarReturnType = new TypeToken<List<AppInfo>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Gets App Info based on App ID (asynchronously)
     *
     * @param appid    Provide the Application ID to query Lab App Info (required)
     * @param callback The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getAppAsync(String appid, final ApiCallback<List<AppInfo>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getAppValidateBeforeCall(appid, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<AppInfo>>() {
        }.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }

    /**
     * Build call for getAppByParam
     *
     * @param azureenvironment        Allowed Values :  \&quot;azureb2ccloud\&quot;, \&quot;azurechinacloud\&quot;, \&quot;azurecloud\&quot;, \&quot;azuregermanycloud\&quot;, \&quot;azureppe\&quot;, \&quot;azureusgovernment\&quot; (optional, default to azurecloud)
     * @param signinaudience          Allowed Values :  \&quot;azureadmyorg\&quot;, \&quot;azureadmultipleorgs\&quot;, \&quot;azureadandpersonalmicrosoftaccount\&quot; (optional, default to azureadmultipleorgs)
     * @param progressListener        Progress listener
     * @param progressRequestListener Progress request listener
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public com.squareup.okhttp.Call getAppByParamCall(String azureenvironment, String signinaudience, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {
        Object localVarPostBody = null;

        // create path and map variables
        String localVarPath = "/api/App";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (azureenvironment != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("azureenvironment", azureenvironment));
        if (signinaudience != null)
            localVarQueryParams.addAll(apiClient.parameterToPair("signinaudience", signinaudience));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();

        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = {
                "application/json"
        };
        final String localVarAccept = apiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {

        };
        final String localVarContentType = apiClient.selectHeaderContentType(localVarContentTypes);
        localVarHeaderParams.put("Content-Type", localVarContentType);

        if (progressListener != null) {
            apiClient.getHttpClient().networkInterceptors().add(new com.squareup.okhttp.Interceptor() {
                @Override
                public com.squareup.okhttp.Response intercept(com.squareup.okhttp.Interceptor.Chain chain) throws IOException {
                    com.squareup.okhttp.Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .body(new ProgressResponseBody(originalResponse.body(), progressListener))
                            .build();
                }
            });
        }

        return apiClient.buildCall(localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarFormParams, progressRequestListener);
    }

    @SuppressWarnings("rawtypes")
    private com.squareup.okhttp.Call getAppByParamValidateBeforeCall(String azureenvironment, String signinaudience, final ProgressResponseBody.ProgressListener progressListener, final ProgressRequestBody.ProgressRequestListener progressRequestListener) throws ApiException {


        com.squareup.okhttp.Call call = getAppByParamCall(azureenvironment, signinaudience, progressListener, progressRequestListener);
        return call;

    }

    /**
     * Gets App Info based on Azure Environment or Sign-in Audience
     *
     * @param azureenvironment Allowed Values :  \&quot;azureb2ccloud\&quot;, \&quot;azurechinacloud\&quot;, \&quot;azurecloud\&quot;, \&quot;azuregermanycloud\&quot;, \&quot;azureppe\&quot;, \&quot;azureusgovernment\&quot; (optional, default to azurecloud)
     * @param signinaudience   Allowed Values :  \&quot;azureadmyorg\&quot;, \&quot;azureadmultipleorgs\&quot;, \&quot;azureadandpersonalmicrosoftaccount\&quot; (optional, default to azureadmultipleorgs)
     * @return List&lt;AppInfo&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public List<AppInfo> getAppByParam(String azureenvironment, String signinaudience) throws ApiException {
        ApiResponse<List<AppInfo>> resp = getAppByParamWithHttpInfo(azureenvironment, signinaudience);
        return resp.getData();
    }

    /**
     * Gets App Info based on Azure Environment or Sign-in Audience
     *
     * @param azureenvironment Allowed Values :  \&quot;azureb2ccloud\&quot;, \&quot;azurechinacloud\&quot;, \&quot;azurecloud\&quot;, \&quot;azuregermanycloud\&quot;, \&quot;azureppe\&quot;, \&quot;azureusgovernment\&quot; (optional, default to azurecloud)
     * @param signinaudience   Allowed Values :  \&quot;azureadmyorg\&quot;, \&quot;azureadmultipleorgs\&quot;, \&quot;azureadandpersonalmicrosoftaccount\&quot; (optional, default to azureadmultipleorgs)
     * @return ApiResponse&lt;List&lt;AppInfo&gt;&gt;
     * @throws ApiException If fail to call the API, e.g. server error or cannot deserialize the response body
     */
    public ApiResponse<List<AppInfo>> getAppByParamWithHttpInfo(String azureenvironment, String signinaudience) throws ApiException {
        com.squareup.okhttp.Call call = getAppByParamValidateBeforeCall(azureenvironment, signinaudience, null, null);
        Type localVarReturnType = new TypeToken<List<AppInfo>>() {
        }.getType();
        return apiClient.execute(call, localVarReturnType);
    }

    /**
     * Gets App Info based on Azure Environment or Sign-in Audience (asynchronously)
     *
     * @param azureenvironment Allowed Values :  \&quot;azureb2ccloud\&quot;, \&quot;azurechinacloud\&quot;, \&quot;azurecloud\&quot;, \&quot;azuregermanycloud\&quot;, \&quot;azureppe\&quot;, \&quot;azureusgovernment\&quot; (optional, default to azurecloud)
     * @param signinaudience   Allowed Values :  \&quot;azureadmyorg\&quot;, \&quot;azureadmultipleorgs\&quot;, \&quot;azureadandpersonalmicrosoftaccount\&quot; (optional, default to azureadmultipleorgs)
     * @param callback         The callback to be executed when the API call finishes
     * @return The request call
     * @throws ApiException If fail to process the API call, e.g. serializing the request body object
     */
    public com.squareup.okhttp.Call getAppByParamAsync(String azureenvironment, String signinaudience, final ApiCallback<List<AppInfo>> callback) throws ApiException {

        ProgressResponseBody.ProgressListener progressListener = null;
        ProgressRequestBody.ProgressRequestListener progressRequestListener = null;

        if (callback != null) {
            progressListener = new ProgressResponseBody.ProgressListener() {
                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    callback.onDownloadProgress(bytesRead, contentLength, done);
                }
            };

            progressRequestListener = new ProgressRequestBody.ProgressRequestListener() {
                @Override
                public void onRequestProgress(long bytesWritten, long contentLength, boolean done) {
                    callback.onUploadProgress(bytesWritten, contentLength, done);
                }
            };
        }

        com.squareup.okhttp.Call call = getAppByParamValidateBeforeCall(azureenvironment, signinaudience, progressListener, progressRequestListener);
        Type localVarReturnType = new TypeToken<List<AppInfo>>() {
        }.getType();
        apiClient.executeAsync(call, localVarReturnType, callback);
        return call;
    }
}
