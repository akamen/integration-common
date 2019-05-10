/**
 * detectable
 *
 * Copyright (c) 2019 Synopsys, Inc.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.synopsys.integration.executable;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;

public class Executable {
    private final File workingDirectory;
    private final Map<String, String> environmentVariables = new HashMap<>();
    private final List<String> commandWithArguments = new ArrayList<>();

    public static Executable create(final File workingDirectory, Map<String, String> environmentVariables, final List<String> commandWithArguments) {
        return new Executable(workingDirectory, environmentVariables, commandWithArguments);
    }

    public static Executable create(final File workingDirectory, Map<String, String> environmentVariables, final String command, final List<String> arguments) {
        List<String> commandWithArguments = new ArrayList<>();
        commandWithArguments.add(command);
        commandWithArguments.addAll(arguments);
        return new Executable(workingDirectory, environmentVariables, commandWithArguments);
    }

    public static Executable create(final File workingDirectory, final List<String> commandWithArguments) {
        return create(workingDirectory, Collections.emptyMap(), commandWithArguments);
    }

    public static Executable create(final File workingDirectory, final String command, final List<String> arguments) {
        return create(workingDirectory, Collections.emptyMap(), command, arguments);
    }

    public static Executable create(final File workingDirectory, Map<String, String> environmentVariables, File executableFile) {
        return create(workingDirectory, environmentVariables, executableFile.getAbsolutePath(), Collections.emptyList());
    }

    public static Executable create(final File workingDirectory, Map<String, String> environmentVariables, File executableFile, List<String> arguments) {
        return create(workingDirectory, environmentVariables, executableFile.getAbsolutePath(), arguments);
    }

    public static Executable create(final File workingDirectory, File executableFile) {
        return create(workingDirectory, executableFile.getAbsolutePath(), Collections.emptyList());
    }

    public static Executable create(final File workingDirectory, File executableFile, List<String> arguments) {
        return create(workingDirectory, executableFile.getAbsolutePath(), arguments);
    }

    public Executable(final File workingDirectory, final Map<String, String> environmentVariables, final List<String> commandWithArguments) {
        this.workingDirectory = workingDirectory;
        this.environmentVariables.putAll(environmentVariables);
        this.commandWithArguments.addAll(commandWithArguments);
    }

    public ProcessBuilder createProcessBuilder() {
        final ProcessBuilder processBuilder = new ProcessBuilder(commandWithArguments);
        processBuilder.directory(workingDirectory);
        final Map<String, String> processBuilderEnvironment = processBuilder.environment();
        for (final String key : environmentVariables.keySet()) {
            populateEnvironmentMap(processBuilderEnvironment, key, environmentVariables.get(key));
        }
        return processBuilder;
    }

    public String getExecutableDescription() {
        return StringUtils.join(commandWithArguments, ' ');
    }

    public List<String> getCommandWithArguments() {
        return commandWithArguments;
    }

    private void populateEnvironmentMap(final Map<String, String> environment, final Object key, final Object value) {
        // ProcessBuilder's environment's keys and values must be non-null java.lang.String's
        if (key != null && value != null) {
            final String keyString = key.toString();
            final String valueString = value.toString();
            if (keyString != null && valueString != null) {
                environment.put(keyString, valueString);
            }
        }
    }
}