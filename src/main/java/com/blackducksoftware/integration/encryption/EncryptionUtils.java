/**
 * integration-common
 *
 * Copyright (C) 2018 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
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
package com.blackducksoftware.integration.encryption;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import com.blackducksoftware.integration.exception.EncryptionException;
import com.blackducksoftware.integration.util.ResourceUtil;

public class EncryptionUtils {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static final String EMBEDDED_SUN_KEY_FILE = "Sun-Key.jceks";
    private static final String EMBEDDED_IBM_KEY_FILE = "IBM-Key.jceks";

    // needs to be at least 8 characters
    private static final char[] KEY_PASS = { 'b', 'l', 'a', 'c', 'k', 'd', 'u', 'c', 'k', '1', '2', '3', 'I', 'n', 't', 'e', 'g', 'r', 'a', 't', 'i', 'o', 'n' };

    public String alterString(final String password, final InputStream encryptionKeyStream, final int cipherMode) throws EncryptionException {
        assertValidPassword(password);

        final Key key = getKey(encryptionKeyStream);

        final String alteredString = getAlteredString(password, cipherMode, key);
        return alteredString;
    }

    private void assertValidPassword(final String password) {
        if (StringUtils.isBlank(password)) {
            throw new IllegalArgumentException("Please provide a non-blank password.");
        }
    }

    private Key getKey(final InputStream encryptionKeyStream) throws EncryptionException {
        Key key = null;
        if (encryptionKeyStream != null) {
            try {
                key = retrieveKeyFromInputStream(encryptionKeyStream);
            } catch (final Exception e) {
                throw new EncryptionException("Failed to retrieve the encryption key from the provided input stream.");
            }
        } else {
            try {
                key = retrieveKeyFromFile(EMBEDDED_SUN_KEY_FILE);
            } catch (final Exception e) {
                try {
                    key = retrieveKeyFromFile(EMBEDDED_IBM_KEY_FILE);
                } catch (final Exception e1) {
                    throw new EncryptionException("Failed to retrieve the encryption key from classpath", e);
                }
            }
        }

        if (key == null) {
            throw new EncryptionException("The encryption key is null");
        }

        return key;
    }

    private String getAlteredString(final String original, final int cipherMode, final Key key) throws EncryptionException {
        String alteredString = null;
        try {
            byte[] bytes = null;
            final Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
            bytes = original.getBytes(UTF8);
            final int originalBytesLength = bytes.length;
            // The buffer must be a multiple of 8 or else
            // the Cipher cant handle it
            final int bufferSize = originalBytesLength + (8 - originalBytesLength % 8);
            cipher.init(cipherMode, key);
            bytes = Arrays.copyOf(bytes, bufferSize);
            if (Cipher.ENCRYPT_MODE == cipherMode) {
                alteredString = encrypt(cipher, bytes);
            } else {
                alteredString = decrypt(cipher, bytes);
            }
        } catch (final Exception e) {
            throw new EncryptionException(e);
        }

        return alteredString;
    }

    private String encrypt(final Cipher cipher, final byte[] bytes) throws IllegalBlockSizeException, BadPaddingException {
        final byte[] buffer = cipher.doFinal(bytes);
        final String encryptedPassword = new String(Base64.encodeBase64(buffer), UTF8).trim();
        return encryptedPassword;
    }

    private String decrypt(final Cipher cipher, final byte[] bytes) throws IllegalBlockSizeException, BadPaddingException {
        final byte[] buffer = cipher.doFinal(Base64.decodeBase64(bytes));

        final String decryptedString = new String(buffer, UTF8).trim();
        return decryptedString;
    }

    private Key retrieveKeyFromFile(final String keyFile) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
        try (InputStream inputStream = ResourceUtil.getResourceAsStream(keyFile)) {
            return retrieveKeyFromInputStream(inputStream);
        }
    }

    private Key retrieveKeyFromInputStream(final InputStream inputStream) throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException, UnrecoverableKeyException {
        final KeyStore keystore = KeyStore.getInstance("JCEKS");
        keystore.load(inputStream, KEY_PASS);
        final Key key = keystore.getKey("keyStore", KEY_PASS);
        return key;
    }

}
