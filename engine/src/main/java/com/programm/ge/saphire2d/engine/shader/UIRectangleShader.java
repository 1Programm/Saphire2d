package com.programm.ge.saphire2d.engine.shader;

import org.joml.Matrix4f;
import org.joml.Vector4f;

public class UIRectangleShader extends Shader {

    private int projection_loc;
    private int transform_loc;
    private int color_loc;

    public UIRectangleShader() {
        super("/shaders/ui/rectShader");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "pos");
    }

    @Override
    protected void loadUniformLocations() {
        projection_loc = getUniformLocation("projection");
        transform_loc = getUniformLocation("transform");
        color_loc = getUniformLocation("color");
    }

    public void loadProjectionMatrix(Matrix4f m){
        loadMatrix(projection_loc, m);
    }

    public void loadTransformationMatrix(Matrix4f m){
        loadMatrix(transform_loc, m);
    }

    public void loadColor(Vector4f color){
        loadVector(color_loc, color);
    }
}
