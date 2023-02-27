package com.programm.ge.saphire2d.test;

import com.programm.ge.saphire2d.engine.SaphEngine;
import com.programm.ge.saphire2d.engine.SaphObjectHandler;
import com.programm.ge.saphire2d.engine.model.GObject;
import com.programm.ge.saphire2d.engine.model.RawModel;
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

        RawModel quadModel = createQuad();
        TexturedModel quad1 = new TexturedModel(quadModel, ModelLoader.loadTexture("/textures/Test.png", 1));


        SaphObjectHandler handler = new SaphObjectHandler();

        GObject obj = new GObject();
        obj.textureIndex = 2;
        obj.size.set(100, 100);
        obj.position.set(50, 50, 0);
        handler.add(quad1, obj);

        obj = new GObject();
        obj.textureIndex = 3;
        obj.size.set(100, 100);
        obj.position.set(160, 50, 0);
        handler.add(quad1, obj);

        obj = new GObject();
        obj.textureIndex = 0;
        obj.size.set(100, 100);
        obj.position.set(50, 160, 0);
        handler.add(quad1, obj);

        obj = new GObject();
        obj.textureIndex = 1;
        obj.size.set(100, 100);
        obj.position.set(160, 160, 0);
        handler.add(quad1, obj);



        engine.run(handler);


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
