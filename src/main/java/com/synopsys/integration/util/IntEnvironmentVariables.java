/**
 * integration-common
 *
 * Copyright (C) 2019 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
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
package com.synopsys.integration.util;

import java.util.HashMap;
import java.util.Map;

public class IntEnvironmentVariables {
    public static final String BDS_CACERTS_OVERRIDE = "BDS_CACERTS_OVERRIDE";

    private final Map<String, String> environmentVariables = new HashMap<>();

    /**
     * By default, initialize with all system environment variables.
     */
    public IntEnvironmentVariables() {
        putAll(System.getenv());
    }

    public IntEnvironmentVariables(final boolean inheritSystemEnvironment) {
        if (inheritSystemEnvironment) {
            putAll(System.getenv());
        }
    }

    public void putAll(final Map<String, String> map) {
        environmentVariables.putAll(map);
    }

    public void put(final String key, final String value) {
        environmentVariables.put(key, value);
    }

    public boolean containsKey(final String key) {
        return environmentVariables.containsKey(key);
    }

    public String getValue(final String key) {
        return getValue(key, null);
    }

    public String getValue(final String key, final String defaultValue) {
        String value = environmentVariables.get(key);
        if (value == null && defaultValue != null) {
            value = defaultValue;
        }
        return value;
    }

    public Map<String, String> getVariables() {
        final Map<String, String> variables = new HashMap<>();
        variables.putAll(environmentVariables);
        return variables;
    }

}
