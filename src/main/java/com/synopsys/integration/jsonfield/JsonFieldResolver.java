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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonFieldResolver {
    private final Gson gson;

    public JsonFieldResolver(final Gson gson) {
        this.gson = gson;
    }

    public <T> SingleJsonFieldResult<T> resolve(final String json, final SingleJsonField<T> jsonField) {
        final JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        return resolve(jsonObject, jsonField);
    }

    public <T> ListJsonFieldResult<T> resolve(final String json, final ListJsonField<T> jsonField) {
        final JsonObject jsonObject = gson.fromJson(json, JsonObject.class);
        return resolve(jsonObject, jsonField);
    }

    public <T> SingleJsonFieldResult<T> resolve(final JsonObject jsonObject, final SingleJsonField<T> jsonField) {
        final List<JsonElement> foundElements = getElements(jsonObject, jsonField.getFieldPath());

        if (!foundElements.isEmpty()) {
            final T value = gson.fromJson(foundElements.get(0), jsonField.getFieldClass());
            return new SingleJsonFieldResult<>(jsonObject, foundElements, value);
        } else {
            return new SingleJsonFieldResult<>(jsonObject, foundElements);
        }
    }

    public <T> ListJsonFieldResult<T> resolve(final JsonObject jsonObject, final ListJsonField<T> jsonField) {
        final List<JsonElement> foundElements = getElements(jsonObject, jsonField.getFieldPath());

        final List<T> values = new ArrayList<>();
        for (final JsonElement foundElement : foundElements) {
            final T value = gson.fromJson(foundElement, jsonField.getFieldClass());
            values.add(value);
        }

        return new ListJsonFieldResult<>(jsonObject, foundElements, values);
    }

    private List<JsonElement> getElements(final JsonElement element, final List<String> fieldPath) {
        JsonElement currentElement = element;
        int currentPathIndex = 0;

        while (currentPathIndex < fieldPath.size() && null != currentElement) {
            final String key = fieldPath.get(currentPathIndex);

            if (currentElement.isJsonObject()) {
                final JsonObject jsonObject = currentElement.getAsJsonObject();
                currentElement = jsonObject.get(key);
                currentPathIndex++;
            } else if (currentElement.isJsonArray()) {
                final JsonArray foundArray = currentElement.getAsJsonArray();
                final List<JsonElement> foundValues = new ArrayList<>(foundArray.size());
                for (final JsonElement arrayElement : foundArray) {
                    foundValues.addAll(getElements(arrayElement, fieldPath.subList(currentPathIndex, fieldPath.size())));
                }
                return foundValues;
            }
        }

        if (null == currentElement) {
            return Collections.emptyList();
        }

        return Collections.singletonList(currentElement);
    }

}
