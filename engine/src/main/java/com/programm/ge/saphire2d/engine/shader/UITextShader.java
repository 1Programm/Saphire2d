package com.programm.ge.saphire2d.engine.shader;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class UITextShader extends Shader {

    private int projection_loc;
    private int transform_loc;
    private int tex_offset_loc;
    private int tex_scale_loc;
    private int color_loc;

    public UITextShader() {
        super("/shaders/ui/textShader");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "pos");
        bindAttribute(1, "texCoord");
    }

    @Override
    protected void loadUniformLocations() {
        projection_loc = getUniformLocation("projection");
        transform_loc = getUniformLocation("transform");
        tex_offset_loc = getUniformLocation("tex_offset");
        tex_scale_loc = getUniformLocation("tex_scale");
        color_loc = getUniformLocation("color");
    }

    public void loadProjectionMatrix(Matrix4f m){
        loadMatrix(projection_loc, m);
    }

    public void loadTransformationMatrix(Matrix4f m){
        loadMatrix(transform_loc, m);
    }

    public void loadTextureOffsetAndScale(float offX, float offY, float scaleX, float scaleY){
        loadFloat2(tex_offset_loc, offX, offY);
        loadFloat2(tex_scale_loc, scaleX, scaleY);
    }

    public void loadColor(Vector4f color){
        loadVector(color_loc, color);
    }

}
