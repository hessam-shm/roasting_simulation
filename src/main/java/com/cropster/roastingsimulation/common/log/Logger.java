package com.cropster.roastingsimulation.common.log;

import org.slf4j.LoggerFactory;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class Logger {

    public static void info(Class c, String message){
        LoggerFactory.getLogger(c).info(message);
    }
    public static void error(Class c, String message){ LoggerFactory.getLogger(c).error(message); }

    public static void debug(Class c, String message){
        LoggerFactory.getLogger(c).debug(message);
    }

    public static void logException(Class c,Throwable t){
        LoggerFactory.getLogger(c).debug(stackTraceToString(t));
    }

    private static String stackTraceToString(Throwable t){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        t.printStackTrace(pw);
        return sw.toString();
    }
}
