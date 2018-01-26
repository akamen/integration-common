package com.blackducksoftware.integration.log;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PrintStreamIntLoggerTest {
    private ByteArrayOutputStream byteArrayOutputStream;
    private PrintStream printStream;

    @Before
    public void setup() throws UnsupportedEncodingException {
        byteArrayOutputStream = new ByteArrayOutputStream();
        printStream = new PrintStream(byteArrayOutputStream, true, StandardCharsets.UTF_8.name());
    }

    @After
    public void tearDown() {
        printStream.close();
    }

    private String getPrintStreamContent() {
        return new String(byteArrayOutputStream.toByteArray(), StandardCharsets.UTF_8);
    }

    @Test
    public void testError() {
        final PrintStreamIntLogger logger = new PrintStreamIntLogger(printStream, LogLevel.ERROR);
        assertEquals("", getPrintStreamContent());
        assertEquals(LogLevel.ERROR, logger.getLogLevel());

        logger.error("error one");
        assertEquals("ERROR: error one\n", getPrintStreamContent());
    }

    @Test
    public void testWarn() {

    }

    @Test
    public void testInfo() {

    }

    @Test
    public void testDebug() {

    }

    @Test
    public void testTrace() {

    }

}
