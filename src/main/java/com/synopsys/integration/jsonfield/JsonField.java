/**
 * integration-common
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
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
package com.synopsys.integration.jsonfield;

import java.util.List;

import com.synopsys.integration.util.Stringable;

public abstract class JsonField<T> extends Stringable {
    private final List<String> fieldPath;
    private final Class<T> fieldClass;

    public JsonField(final List<String> fieldPath, final Class<T> fieldClass) {
        this.fieldPath = fieldPath;
        this.fieldClass = fieldClass;
    }

    public List<String> getFieldPath() {
        return fieldPath;
    }

    public Class<T> getFieldClass() {
        return fieldClass;
    }

}
