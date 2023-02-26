package com.programm.ge.saphire2d.engine.shader;

import com.programm.ge.saphire2d.engine.utils.ShaderUtils;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;

public abstract class Shader {

    private static final FloatBuffer MATRIX_BUFFER = BufferUtils.createFloatBuffer(16);

    private final int programID;

    public Shader(int programID) {
        this.programID = programID;
        bindAttributes();
        ShaderUtils.linkShader(programID);
        loadUniformLocations();
    }

    public Shader(String path){
        this(ShaderUtils.createVertFragShader(path));
    }

    protected abstract void bindAttributes();
    protected abstract void loadUniformLocations();

    protected final void bindAttribute(int attribute, String name){
        GL20.glBindAttribLocation(programID, attribute, name);
    }

    protected final int getUniformLocation(String name){
        return GL20.glGetUniformLocation(programID, name);
    }

    protected final void loadFloat(int location, float v){
        GL20.glUniform1f(location, v);
    }

    protected final void loadVector(int location, Vector2f v){
        GL20.glUniform2f(location, v.x, v.y);
    }

    protected final void loadVector(int location, Vector3f v){
        GL20.glUniform3f(location, v.x, v.y, v.z);
    }

    protected final void loadMatrix(int location, Matrix4f v){
        v.get(MATRIX_BUFFER);
        MATRIX_BUFFER.flip();
        GL20.glUniformMatrix2fv(location, false, MATRIX_BUFFER);
    }

    public final void start(){
        GL20.glUseProgram(programID);
    }

    public final void stop(){
        GL20.glUseProgram(0);
    }

    public final void cleanup(){
        GL20.glDeleteProgram(programID);
    }

}