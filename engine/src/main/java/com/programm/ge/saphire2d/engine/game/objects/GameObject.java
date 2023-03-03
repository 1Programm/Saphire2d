package com.programm.ge.saphire2d.engine.game.objects;

import com.programm.ge.saphire2d.engine.utils.Transformation;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private class InnerContext implements ObjectContext {
        private final Vector3f velocity = new Vector3f();

        @Override
        public Transformation transformation() {
            return transformation;
        }

        @Override
        public void move(float x, float y) {
            velocity.add(x, y, 0);
        }

        @Override
        public void spriteIndex(int id) {
            spriteId = id;
        }
    }

    public final Transformation transformation = new Transformation();
    public final List<Behavior> behaviors = new ArrayList<>();
    public final InnerContext context = new InnerContext();
    public int spriteId;



    public void update(){
        if(context.velocity.x < 0) {
            if(transformation.size.x > 0) {
                transformation.size.x *= -1;
            }
        }
        else if(context.velocity.x > 0){
            if(transformation.size.x < 0){
                transformation.size.x *= -1;
            }
        }

        transformation.position.add(context.velocity);
        context.velocity.set(0, 0, 0);
    }

}
