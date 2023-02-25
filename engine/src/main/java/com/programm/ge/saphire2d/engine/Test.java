package com.programm.ge.saphire2d.engine;

import com.programm.ioutils.log.api.ILogger;
import com.programm.ioutils.log.api.Logger;

@Logger("Test Class")
public class Test {

    public static void testMethod(ILogger log){
        log.trace("A");
        log.debug("B");
        log.info("C");
        log.warn("D");
        log.error("E");
        log.logException("F", new RuntimeException("Test Exception"));
    }

}
