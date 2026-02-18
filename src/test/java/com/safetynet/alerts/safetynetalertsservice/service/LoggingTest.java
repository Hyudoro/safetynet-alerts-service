package com.safetynet.alerts.safetynetalertsservice.service;

import com.safetynet.alerts.safetynetalertsservice.util.InMemoryAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LoggingSpringBootTest {

@Test
void testLog4j2Levels() {
    //logger creation
    Logger logger = LogManager.getLogger(LoggingSpringBootTest.class);

    LoggerContext context = (LoggerContext) LogManager.getContext(false);
    org.apache.logging.log4j.core.config.Configuration config = context.getConfiguration();

    InMemoryAppender appender = new InMemoryAppender("MEMORY");
    appender.start();

    config.addAppender(appender);

    org.apache.logging.log4j.core.Logger coreLogger =
            context.getLogger(LoggingSpringBootTest.class.getName());

    coreLogger.addAppender(appender);
    coreLogger.setAdditive(false);
    coreLogger.setLevel(org.apache.logging.log4j.Level.DEBUG);
    context.updateLoggers();
    ;


    // emit logs
    logger.info("error");
    logger.debug("debug");
    logger.error("info");


    assertTrue(appender.containsMessage("error"));

    assertTrue(appender.containsMessage("debug"));

    assertTrue(appender.containsMessage("info"));





    coreLogger.removeAppender(appender);
    appender.stop();
}
}
