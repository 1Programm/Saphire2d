package com.programm.ge.saphire2d.engine.shader;

import org.joml.Matrix4f;

public class TestShader1 extends Shader {

    private int projection_loc;
    private int transform_loc;
    private int tex_numRows_loc;
    private int tex_offset_loc;

    public TestShader1() {
        super("/shaders/TestShader1");
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
        tex_numRows_loc = getUniformLocation("tex_numRows");
        tex_offset_loc = getUniformLocation("tex_offset");
    }

    public void loadProjectionMatrix(Matrix4f m){
        loadMatrix(projection_loc, m);
    }

    public void loadTransformationMatrix(Matrix4f m){
        loadMatrix(transform_loc, m);
    }

    public void loadNumberOfRows(int numRows){
        loadFloat(tex_numRows_loc, numRows);
    }

    public void loadTextureIndex(int numRows, int texIndex){
        int col = texIndex % numRows;
        float offX = (float)col / numRows;

        int row = texIndex / numRows;
        float offY = (float)row / numRows;

        loadFloat2(tex_offset_loc, offX, offY);
    }

}
