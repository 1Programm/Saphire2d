package com.programm.ge.saphire2d.engine.scene;

import com.programm.ge.saphire2d.engine.game.objects.Behavior;
import com.programm.ge.saphire2d.engine.game.objects.GameObject;
import com.programm.ge.saphire2d.engine.model.Sprite;
import com.programm.ge.saphire2d.engine.model.Texture;
import com.programm.ge.saphire2d.engine.utils.ModelLoader;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class Scene {

    private final Map<String, Texture> cachedTextures = new HashMap<>();
    public final Map<Integer, Sprite> sprites = new HashMap<>();
    public final TileMap tileMap;

    public final List<GameObject> objects = new ArrayList<>();

    public void update(SceneContext ctx){
        for(int i=0;i<objects.size();i++){
            GameObject obj = objects.get(i);
            for(Behavior behavior : obj.behaviors){
                behavior.action(ctx, obj.context);
            }

            obj.update();
        }
    }


    public void loadSprite(int id, String path, int ssw, int ssh, int ssx, int ssy){
        Texture texture = cachedTextures.get(path);

        if(texture == null){
            texture = ModelLoader.loadTexture(path, ssw, ssh);
            cachedTextures.put(path, texture);
        }

        Sprite sprite = new Sprite(texture, ssx, ssy);
        sprites.put(id, sprite);
    }

    public void loadSprite(int id, String path){
        loadSprite(id, path, 1, 1, 0, 0);
    }

}
