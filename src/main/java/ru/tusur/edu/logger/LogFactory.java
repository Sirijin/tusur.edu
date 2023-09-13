package ru.tusur.edu.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum LogFactory {
    rabbit("rabbit"),
    api("api"),
    service("service"),
    root("root");

    final Logger logger;

    LogFactory(String name) {
        logger = LoggerFactory.getLogger(name);
    }

    public Log get(String tag) {
        return new Log(tag, logger);
    }

    public Log get(Class<?> clazz) {
        return get(clazz.getSimpleName());
    }
}
