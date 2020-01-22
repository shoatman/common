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
package com.microsoft.identity.common.internal.telemetry.events;

import androidx.annotation.NonNull;

import com.microsoft.identity.common.exception.BaseException;
import com.microsoft.identity.common.exception.UserCancelException;
import com.microsoft.identity.common.internal.controllers.ExceptionAdapter;
import com.microsoft.identity.common.internal.result.AcquireTokenResult;

import static com.microsoft.identity.common.internal.telemetry.TelemetryEventStrings.Event;
import static com.microsoft.identity.common.internal.telemetry.TelemetryEventStrings.EventType;
import static com.microsoft.identity.common.internal.telemetry.TelemetryEventStrings.Key;
import static com.microsoft.identity.common.internal.telemetry.TelemetryEventStrings.Value;

public class ApiEndEvent extends BaseEvent {
    public ApiEndEvent() {
        super();
        names(Event.API_END_EVENT);
        types(EventType.API_EVENT);
    }

    public ApiEndEvent putResult(@NonNull final AcquireTokenResult result) {
        if (result == null) {
            return this;
        }

        if (result.getSucceeded() != null) {
            put(Key.IS_SUCCESSFUL, result.getSucceeded().toString());
        }

        if (null != result.getLocalAuthenticationResult()) {
            put(Key.USER_ID, result.getLocalAuthenticationResult().getUniqueId()); //pii
            put(Key.TENANT_ID, result.getLocalAuthenticationResult().getTenantId()); //pii
            put(Key.SPE_RING, result.getLocalAuthenticationResult().getSpeRing());
            put(Key.RT_AGE, result.getLocalAuthenticationResult().getRefreshTokenAge());
        }

        return this;
    }

    public ApiEndEvent putException(@NonNull final Exception exception) {
        if (exception == null) {
            return this;
        }

        final BaseException adaptedException = ExceptionAdapter.baseExceptionFromException(exception);
        if (adaptedException instanceof UserCancelException) {
            put(Key.USER_CANCEL, Value.TRUE);
        }

        put(Key.SERVER_ERROR_CODE, adaptedException.getCliTelemErrorCode());
        put(Key.SERVER_SUBERROR_CODE, adaptedException.getCliTelemSubErrorCode());
        put(Key.ERROR_CODE, adaptedException.getErrorCode());
        put(Key.SPE_RING, adaptedException.getSpeRing());
        put(Key.ERROR_DESCRIPTION, adaptedException.getMessage()); //oii
        put(Key.RT_AGE, adaptedException.getRefreshTokenAge());
        put(Key.IS_SUCCESSFUL, Value.FALSE);
        return this;
    }

    public ApiEndEvent putApiId(@NonNull final String apiId) {
        put(Key.API_ID, apiId);
        return this;
    }

    @Override
    public ApiEndEvent put(@NonNull final String propertyName, @NonNull final String propertyValue) {
        super.put(propertyName, propertyValue);
        return this;
    }

    public ApiEndEvent isApiCallSuccessful(final Boolean isSuccessful) {
        if (isSuccessful != null) {
            put(Key.IS_SUCCESSFUL, isSuccessful.toString());
        }
        return this;
    }

    public ApiEndEvent putApiErrorCode(@NonNull final String errorCode) {
        put(Key.ERROR_CODE, errorCode);
        return this;
    }
}
