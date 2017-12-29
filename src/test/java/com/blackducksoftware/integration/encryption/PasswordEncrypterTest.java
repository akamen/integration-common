/*******************************************************************************
 * Copyright (C) 2016 Black Duck Software, Inc.
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
 *******************************************************************************/
package com.blackducksoftware.integration.encryption;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public final class PasswordEncrypterTest {
    @Test
    public void testMainEncryptPassword() throws Exception {
        final String encryptedPassword = PasswordEncrypter.encrypt("Password");
        System.out.println(encryptedPassword);
        assertEquals("SaTaqurAqc7q0nf0n6IL4Q==", encryptedPassword);
    }

    @Test
    public void testEncryptLongPassword() throws Exception {
        final String longPassword = "LongPasswordLetsSeeHowLongWeCanEncryptWithoutbreakingThingsLongPasswordLetsSeeHowLongWeCanEncryptWithoutbreakingThingsLongPasswordLetsSeeHowLongWeCanEncryptWithoutbreakingThings";
        final String encryptedPassword = PasswordEncrypter.encrypt(longPassword);
        final String decryptedPassword = PasswordDecrypter.decrypt(encryptedPassword);

        System.out.println("Long Password : " + longPassword);
        System.out.println("Long Password Length : " + longPassword.length());
        System.out.println("");
        System.out.println("Encrypted Password : " + encryptedPassword);
        System.out.println("Encrypted Password Length : " + encryptedPassword.length());
        System.out.println("");
        System.out.println("Decrypted Password : " + decryptedPassword);
        System.out.println("Decrypted Password Length : " + decryptedPassword.length());

        assertEquals(longPassword.length(), decryptedPassword.length());
        assertEquals(longPassword, decryptedPassword);
    }

}
