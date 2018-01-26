package com.blackducksoftware.integration.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class NameVersionTest {
    @Test
    public void testNameVersion() {
        final NameVersion nameVersion = new NameVersion();
        assertNotNull(nameVersion);
        assertNull(nameVersion.getName());
        assertNull(nameVersion.getVersion());

        NameVersion populatedNameVersion = new NameVersion("name", "version");
        assertEquals("name", populatedNameVersion.getName());
        assertEquals("version", populatedNameVersion.getVersion());

        populatedNameVersion = new NameVersion("name2");
        assertEquals("name2", populatedNameVersion.getName());
        assertNull(populatedNameVersion.getVersion());
    }

}
