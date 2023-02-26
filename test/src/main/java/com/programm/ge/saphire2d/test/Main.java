package com.programm.ge.saphire2d.test;

import com.programm.ge.saphire2d.engine.SaphEngine;
import com.programm.ge.saphire2d.engine.SaphWindow;
import com.programm.ge.saphire2d.engine.model.RawModel;
import com.programm.ge.saphire2d.engine.model.Texture;
import com.programm.ge.saphire2d.engine.model.TexturedModel;
import com.programm.ge.saphire2d.engine.utils.ModelLoader;
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

        SaphEngine engine = new SaphEngine("Hello World!", 600, 500);
//        engine.debugMode();

        RawModel myModel = createQuad();
        Texture texTest = ModelLoader.loadTexture2("/textures/Test.png");
        TexturedModel tModel = new TexturedModel(myModel, texTest);


        engine.run(tModel);


        ModelLoader.cleanup();
        engine.cleanup();
    }

    private static RawModel createQuad(){
        return ModelLoader.loadModel(2, new float[]{
                -0.5f,  0.5f, //0
                -0.5f, -0.5f, //1
                 0.5f, -0.5f, //2
                 0.5f,  0.5f, //3
            },
            new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f,
            },
            new int[]{
                0,1,3,
                3,1,2,
            }
        );
    }

}
