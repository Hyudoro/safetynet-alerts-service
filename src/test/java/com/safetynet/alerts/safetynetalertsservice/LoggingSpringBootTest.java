package com.safetynet.alerts.safetynetalertsservice;

import com.safetynet.alerts.safetynetalertsservice.util.InMemoryAppender;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertTrue;

class LoggingSpringBootTest {

    @Test
    void testLog4j2Levels() {

        Logger logger = LogManager.getLogger(LoggingSpringBootTest.class);

        LoggerContext context = (LoggerContext) LogManager.getContext(false);
        Configuration config = context.getConfiguration();

        InMemoryAppender appender = new InMemoryAppender("MEMORY");
        appender.start();

        config.addAppender(appender);

        LoggerConfig loggerConfig =
                config.getLoggerConfig(LoggingSpringBootTest.class.getName());

        // If the loggerConfig is inherited from parent, create a dedicated one
        if (!loggerConfig.getName().equals(LoggingSpringBootTest.class.getName())) {
            loggerConfig = new LoggerConfig(
                    LoggingSpringBootTest.class.getName(),
                    Level.DEBUG,
                    false
            );
            config.addLogger(LoggingSpringBootTest.class.getName(), loggerConfig);
        }

        loggerConfig.setLevel(Level.DEBUG);
        loggerConfig.addAppender(appender, Level.DEBUG, null);

        context.updateLoggers();

        // Emit logs
        logger.info("info");
        logger.debug("debug");
        logger.error("error");

        assertTrue(appender.containsMessage("info"));
        assertTrue(appender.containsMessage("debug"));
        assertTrue(appender.containsMessage("error"));

        // Cleanup
        loggerConfig.removeAppender("MEMORY");
        appender.stop();
    }
}
