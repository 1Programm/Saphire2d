package com.programm.ge.saphire2d.engine.utils;

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

    public static void getTransformation(Matrix4f m, Transformation trans){
        getTransformation(m, trans.position, trans.rotation, trans.size);
    }

    public static void getTransformation(Matrix4f m, Vector3f pos, Vector3f rot, Vector2f size){
        getTransformation(m, pos.x, pos.y, pos.z, rot.x, rot.y, rot.z, size.x, size.y);
    }

    public static void getTransformation(Matrix4f m, float x, float y, float z, float rx, float ry, float rz, float w, float h){
        m.identity();

        m.translate(x, y, z);
        m.rotate(rx, 1, 0, 0);
        m.rotate(ry, 0, 1, 0);
        m.rotate(rz, 0, 0, 1);
        m.scale(w, h, 1);
    }

    public static void getTransformation(Matrix4f m, float x, float y, float z, float w, float h){
        m.identity();

        m.translate(x, y, z);
        m.scale(w, h, 1);
    }

//    public static void getTransformation(Matrix4f m, float x, float y, float z, float w, float h){
//        m.identity();
//
//        m.translate(x, y, z);
//        m.scale(w, h, 1);
//    }

}
