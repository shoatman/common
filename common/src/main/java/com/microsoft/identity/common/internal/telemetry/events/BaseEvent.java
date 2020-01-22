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
import androidx.annotation.Nullable;

import com.microsoft.identity.common.internal.logging.DiagnosticContext;
import com.microsoft.identity.common.internal.telemetry.Properties;

import static com.microsoft.identity.common.internal.telemetry.TelemetryEventStrings.Key;

public class BaseEvent extends Properties {
    public BaseEvent() {
        super();
        occurs(System.currentTimeMillis());
        correlationId(DiagnosticContext.getRequestContext().get(DiagnosticContext.CORRELATION_ID));
    }

    /**
     * Put the event name value into the properties map.
     *
     * @param eventName String of the event name
     * @return the event object
     */
    public BaseEvent names(@NonNull String eventName) {
        put(Key.EVENT_NAME, eventName);
        return this;
    }

    public BaseEvent types(@NonNull String eventType) {
        put(Key.EVENT_TYPE, eventType);
        return this;
    }

    /**
     * Put the event occurring time into the properties map.
     *
     * @param eventStartTime Long of the event start time. If null, then put the current time as the start time.
     * @return the event object
     */
    public BaseEvent occurs(@Nullable Long eventStartTime) {
        if (null == eventStartTime) {
            put(Key.OCCUR_TIME, String.valueOf(System.currentTimeMillis()));
        } else {
            put(Key.OCCUR_TIME, eventStartTime.toString());
        }

        return this;
    }

    /**
     * Set the event correlation id.
     *
     * @param correlationId correlation id String
     * @return the event object
     */
    public BaseEvent correlationId(final String correlationId) {
        put(Key.CORRELATION_ID, correlationId);
        return this;
    }
}
