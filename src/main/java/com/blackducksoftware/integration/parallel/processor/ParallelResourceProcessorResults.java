/**
 * hub-common
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
package com.blackducksoftware.integration.parallel.processor;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class ParallelResourceProcessorResults<R> {
    private final List<R> results;

    private final List<Exception> exceptions;

    public ParallelResourceProcessorResults(final List<R> results, final List<Exception> exceptionMessages) {
        if (results == null) {
            this.results = new ArrayList<>();
        } else {
            this.results = results;
        }
        this.exceptions = exceptionMessages;
    }

    public List<R> getResults() {
        return results;
    }

    public List<Exception> getExceptions() {
        return exceptions;
    }

    public boolean isError() {
        if ((exceptions != null) && (exceptions.size() > 0)) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, RecursiveToStringStyle.JSON_STYLE);
    }
}
