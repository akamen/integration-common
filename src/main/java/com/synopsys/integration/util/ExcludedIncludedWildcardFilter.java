/**
 * integration-common
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
package com.synopsys.integration.util;

import java.util.Set;

import org.apache.commons.io.FilenameUtils;

public class ExcludedIncludedWildcardFilter extends ExcludedIncludedFilter {
    public ExcludedIncludedWildcardFilter(final String toExclude, final String toInclude) {
        super(toExclude, toInclude);
    }

    public boolean willExclude(final String itemName) {
        return determineInclusion(itemName, excludedSet, super.willExclude(itemName));
    }

    public boolean willInclude(final String itemName) {
        return determineInclusion(itemName, includedSet, super.willInclude(itemName));
    }

    public boolean determineInclusion(final String itemName, final Set<String> includedExcludedSet, boolean defaultJudgement) {
        for (String includeToken : includedExcludedSet) {
            if (FilenameUtils.wildcardMatch(itemName, includeToken)) {
                return true;
            }
        }

        return defaultJudgement;
    }

}
