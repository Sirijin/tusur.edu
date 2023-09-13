package ru.tusur.edu.logger;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Log {

    private final Marker marker;
    private final Logger logger;

    public Log(String tag, Logger logger) {
        this.marker = MarkerFactory.getMarker(tag);
        this.logger = logger;
    }

    public void error(String format, Object... param) {
        logger.error(marker, format, param);
    }

    public void warn(String format, Object... param) {
        logger.warn(marker, format, param);
    }

    public void info(String format, Object... param) {
        logger.info(marker, format, param);
    }

    public void debug(String format, Object... param) {
        logger.debug(marker, format, param);
    }

    public void trace(String format, Object... param) {
        logger.trace(marker, format, param);
    }
}