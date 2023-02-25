package com.programm.ge.saphire2d.test;

import com.programm.ge.saphire2d.engine.Test;
import com.programm.ioutils.log.api.ILogger;
import com.programm.ioutils.log.api.Logger;
import com.programm.ioutils.log.jlogger.JLogger;

@Logger("Main")
public class Main {

    private static final String DEFAULT_LOG_FORMAT = "[$TIME] [%5<($LVL)] [%30>($LOG?{$CLS.$MET})]: $MSG";

    public static void main(String[] args) {
        JLogger log = new JLogger();
        log.format(DEFAULT_LOG_FORMAT);
        log.printStacktraceForExceptions(true);
        log.level(ILogger.LEVEL_INFO);

        log.info("Hello");
        Test.testMethod(log);
    }

}
