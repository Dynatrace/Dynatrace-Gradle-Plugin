/*
 * Dynatrace Gradle Plugin
 * Copyright (c) 2008-2016, DYNATRACE LLC
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 *  Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *  Neither the name of the dynaTrace software nor the names of its contributors
 * may be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 * BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
 * DAMAGE.
 */

package com.dynatrace.diagnostics.automation.gradle;

import com.dynatrace.sdk.server.exceptions.ServerConnectionException;
import com.dynatrace.sdk.server.exceptions.ServerResponseException;
import com.dynatrace.sdk.server.sessions.Sessions;
import com.dynatrace.sdk.server.sessions.models.RecordingOption;
import com.dynatrace.sdk.server.sessions.models.StoreSessionRequest;
import org.gradle.api.tasks.Input;
import org.gradle.api.tasks.TaskAction;
import org.gradle.tooling.BuildException;

/**
 * Gradle task to store pure paths
 */
public class DtStorePurePaths extends DtServerProfileBase {
    public static final String NAME = "DtStorePurePaths";

    @Input
    private String recordingOption = "all";

    @Input
    private boolean sessionLocked = false;

    @Input
    private boolean appendTimestamp = false;

    /**
     * Executes gradle task
     *
     * @throws BuildException whenever connecting to the server, parsing a response or execution fails
     */
    @TaskAction
    public void executeTask() throws BuildException {
        Sessions sessions = new Sessions(this.getDynatraceClient());

        StoreSessionRequest storeSessionRequest = new StoreSessionRequest(this.getProfileName());
        storeSessionRequest.setSessionLocked(this.isSessionLocked());
        storeSessionRequest.setAppendTimestamp(this.isAppendTimestamp());

        if (this.getRecordingOption() != null) {
            storeSessionRequest.setRecordingOption(RecordingOption.fromInternal(this.getRecordingOption()));
        }

        this.getLogger().info(String.format("Storing pure paths with recording option '%s' in '%s' system profile", this.recordingOption, this.getProfileName()));

        try {
            sessions.store(storeSessionRequest);
        } catch (ServerConnectionException | ServerResponseException e) {
            throw new BuildException(String.format("Error while trying to store pure paths in '%s' system profile: %s",  this.getProfileName(), e.getMessage()), e);
        }
    }

    public String getRecordingOption() {
        return recordingOption;
    }

    public void setRecordingOption(String recordingOption) {
        this.recordingOption = recordingOption;
    }

    public boolean isSessionLocked() {
        return sessionLocked;
    }

    public void setSessionLocked(boolean sessionLocked) {
        this.sessionLocked = sessionLocked;
    }

    public boolean isAppendTimestamp() {
        return appendTimestamp;
    }

    public void setAppendTimestamp(boolean appendTimestamp) {
        this.appendTimestamp = appendTimestamp;
    }
}
