package com.programm.ge.saphire2d.engine.game.objects;

import com.programm.ge.saphire2d.engine.scene.SceneContext;

public class SpriteAnimator implements Behavior {

    private final float duration;
    private final int[] spriteIds;

    private int curIndex;
    private float curTime;

    public SpriteAnimator(float duration, int... ids) {
        this.duration = duration;
        this.spriteIds = ids;
    }

    @Override
    public void action(SceneContext scene, ObjectContext obj) {
        curTime += scene.delta();
        if(curTime >= duration){
            curTime = 0;
            curIndex = (curIndex + 1) % spriteIds.length;
            obj.spriteIndex(spriteIds[curIndex]);
        }
    }
}
