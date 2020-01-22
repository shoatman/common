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

import com.microsoft.identity.common.internal.ui.AuthorizationAgent;

import static com.microsoft.identity.common.internal.telemetry.TelemetryEventStrings.Event;
import static com.microsoft.identity.common.internal.telemetry.TelemetryEventStrings.EventType;
import static com.microsoft.identity.common.internal.telemetry.TelemetryEventStrings.Key;

public class UiStartEvent extends BaseEvent {
    public UiStartEvent() {
        super();
        names(Event.UI_START_EVENT);
        types(EventType.UI_EVENT);
    }

    public UiStartEvent putUserAgent(final AuthorizationAgent userAgent) {
        if (userAgent == null) {
            return this;
        }

        put(Key.USER_AGENT, userAgent.name());
        return this;
    }

    public UiStartEvent putLoginHint(final String loginHint) {
        put(Key.LOGIN_HINT, loginHint);
        return this;
    }

    public UiStartEvent isForcePrompt(final boolean isForcePrompt) {
        put(Key.IS_FORCE_PROMPT, String.valueOf(isForcePrompt));
        return this;
    }
}
