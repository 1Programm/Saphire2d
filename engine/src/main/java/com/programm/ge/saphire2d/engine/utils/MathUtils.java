package com.programm.ge.saphire2d.engine.utils;

import com.programm.ge.saphire2d.engine.model.GObject;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class MathUtils {

    public static void orthoProjection(Matrix4f m, float x, float y, float width, float height){
        m.identity();
        m.ortho(x, x + width, y, y + height, -1, 1);

//        float zNear = 0.01f;
//        float zFar = 10;
//        m.m00(2 / width);
//        m.m11(2 / height);
//        m.m22(-2 / (zFar - zNear));
//        m.m23(-(zFar + zNear) / (zFar - zNear));
//        m.m33(1);
    }

    public static void getTransformation(Matrix4f m, GObject obj){
        getTransformation(m, obj.position, obj.rotation, obj.size);
    }

    public static void getTransformation(Matrix4f m, Vector3f pos, Vector3f rot, Vector2f size){
        m.identity();

        m.translate(pos.x, pos.y, pos.z);
        m.rotate(rot.x, 1, 0, 0);
        m.rotate(rot.y, 0, 1, 0);
        m.rotate(rot.z, 0, 0, 1);
        m.scale(size.x, size.y, 1);
    }

//    public static void getTransformation(Matrix4f m, float x, float y, float z, float w, float h){
//        m.identity();
//
//        m.translate(x, y, z);
//        m.scale(w, h, 1);
//    }

}
