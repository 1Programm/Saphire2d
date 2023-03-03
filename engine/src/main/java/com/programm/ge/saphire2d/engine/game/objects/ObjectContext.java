package com.programm.ge.saphire2d.engine.game.objects;

import com.programm.ge.saphire2d.engine.utils.Transformation;

public interface ObjectContext {

    Transformation transformation();

    void move(float x, float y);

    void spriteIndex(int id);

}
