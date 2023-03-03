package com.programm.ge.saphire2d.engine.game.objects;

import com.programm.ge.saphire2d.core.IKeyboard;
import com.programm.ge.saphire2d.engine.scene.SceneContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KeyboardControllBehavior implements Behavior{

    private final float speed;

    @Override
    public void action(SceneContext scene, ObjectContext obj) {
        IKeyboard keyboard = scene.keyboard();

        float velx = 0, vely = 0;
        if(keyboard.isKeyPressed(IKeyboard.KEY_A)){
            velx += -speed * ((float)scene.delta());
        }
        if(keyboard.isKeyPressed(IKeyboard.KEY_D)){
            velx += speed * ((float)scene.delta());
        }
        if(keyboard.isKeyPressed(IKeyboard.KEY_S)){
            vely += -speed * scene.delta();
        }
        if(keyboard.isKeyPressed(IKeyboard.KEY_W)){
            vely += speed * scene.delta();
        }

        obj.move(velx, vely);
    }
}
