package com.programm.ge.saphire2d.test;

import com.programm.ge.saphire2d.engine.SaphEngine;
import com.programm.ge.saphire2d.engine.game.objects.GameObject;
import com.programm.ge.saphire2d.engine.game.objects.KeyboardControllBehavior;
import com.programm.ge.saphire2d.engine.game.objects.SpriteAnimator;
import com.programm.ge.saphire2d.engine.model.RawModel;
import com.programm.ge.saphire2d.engine.scene.Scene;
import com.programm.ge.saphire2d.engine.scene.TileMap;
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






        Scene sceneA = new Scene(new TileMap(10, 10, 2, 32, 32));
        sceneA.loadSprite(0, "/textures/GroundSpriteSheet.png", 8, 8, 0, 0);
        sceneA.loadSprite(1, "/textures/GroundSpriteSheet.png", 8, 8, 1, 0);
        sceneA.loadSprite(2, "/textures/GroundSpriteSheet.png", 8, 8, 2, 0);


        for(int x=0;x<8;x++){
            for(int y=0;y<4;y++){
                sceneA.tileMap.setTile(x, y, 0, 0);
            }
        }

        for(int i=0;i<8;i++) sceneA.tileMap.setTile(i, 4, 0, 1);
        for(int i=0;i<8;i++) sceneA.tileMap.setTile(i, 5, 0, 2);


        sceneA.loadSprite(10, "/textures/Char1SS.png", 8, 4, 1, 0);
        sceneA.loadSprite(11, "/textures/Char1SS.png", 8, 4, 2, 1);
        sceneA.loadSprite(12, "/textures/Char1SS.png", 8, 4, 3, 1);
        GameObject obj = new GameObject();
        obj.spriteId = 10;
        obj.transformation.position.set(100, 100, 0);
        obj.transformation.size.set(50, 50);
        obj.behaviors.add(new KeyboardControllBehavior(300));
        obj.behaviors.add(new SpriteAnimator(0.2f, 11, 12));
        sceneA.objects.add(obj);


        engine.run(sceneA);


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
