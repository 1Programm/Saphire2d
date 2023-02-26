package com.programm.ge.saphire2d.engine.shader;

public class TestShader1 extends Shader {

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
    }

}
