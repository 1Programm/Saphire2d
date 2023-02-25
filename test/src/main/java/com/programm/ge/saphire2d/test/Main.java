package com.programm.ge.saphire2d.test;

import com.programm.ge.saphire2d.engine.LWJGLTest;
import com.programm.ioutils.log.api.ILogger;
import com.programm.ioutils.log.api.Logger;
import com.programm.ioutils.log.jlogger.JLogger;

@Logger("Main")
public class Main {

    private static JLogger createLogger(){
        JLogger log = new JLogger();
        log.format("[$TIME] [%5<($LVL)] [%30>($LOG?{$CLS.$MET})]: $MSG");
        log.printStacktraceForExceptions(true);
        log.level(ILogger.LEVEL_INFO);

        return log;
    }

    public static void main(String[] args) {
        JLogger log = createLogger();
        LWJGLTest.main(args);
    }

}
