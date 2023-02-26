package com.programm.ge.saphire2d.engine.utils;

import org.lwjgl.opengl.GL20;

import java.io.*;

public class ShaderUtils {

    public static int createVertFragShader(String path){
        return createVertFragShader(path + ".vs", path + ".fs");
    }

    public static int createVertFragShader(String vertexShaderPath, String fragmentShaderPath){
        InputStream vis = ShaderUtils.class.getResourceAsStream(vertexShaderPath);
        InputStream fis = ShaderUtils.class.getResourceAsStream(fragmentShaderPath);

        if(vis == null) throw new RuntimeException("Could not find resource [" + vertexShaderPath + "]!");
        if(fis == null) throw new RuntimeException("Could not find resource [" + fragmentShaderPath + "]!");

        String visContent;
        try {
            visContent = readContentFromInputStream(vis);
        }
        catch (IOException e){
            throw new RuntimeException("Failed to read the vertex shader file!", e);
        }

        String fisContent;
        try {
            fisContent = readContentFromInputStream(fis);
        }
        catch (IOException e){
            throw new RuntimeException("Failed to read the fragment shader file!", e);
        }

        return createShaderFromSource(visContent, fisContent);
    }

    private static int createShaderFromSource(String vertexShader, String fragmentShader){
        int vShaderID;
        try {
            vShaderID = compileShader(vertexShader, GL20.GL_VERTEX_SHADER);
        }
        catch (RuntimeException e){
            throw new RuntimeException("Failed to compile vertex-shader!", e);
        }

        int fShaderID;
        try {
            fShaderID = compileShader(fragmentShader, GL20.GL_FRAGMENT_SHADER);
        }
        catch (RuntimeException e){
            throw new RuntimeException("Failed to compile fragment-shader!", e);
        }

        int shaderProgram = GL20.glCreateProgram();
        GL20.glAttachShader(shaderProgram, vShaderID);
        GL20.glAttachShader(shaderProgram, fShaderID);

        GL20.glDeleteShader(vShaderID);
        GL20.glDeleteShader(fShaderID);

        return shaderProgram;
    }

    public static void linkShader(int programID){
        GL20.glLinkProgram(programID);

        if(GL20.glGetProgrami(programID, GL20.GL_LINK_STATUS) == 0){
            String infoLog = GL20.glGetProgramInfoLog(programID, 512);
            throw new RuntimeException("Error linking program:\n" + infoLog);
        }
    }

    private static int compileShader(String shader, int type){
        int shaderID = GL20.glCreateShader(type);

        GL20.glShaderSource(shaderID, shader);
        GL20.glCompileShader(shaderID);

        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0){
            String infoLog = GL20.glGetShaderInfoLog(shaderID, 512);
            throw new RuntimeException("Error compiling shader:\n" + infoLog);
        }

        return shaderID;
    }

    private static String readContentFromInputStream(InputStream is) throws IOException {
        try(BufferedReader vbr = new BufferedReader(new InputStreamReader(is))){
            StringBuilder sb = new StringBuilder();
            String line;
            while(((line = vbr.readLine())) != null){
                if(!sb.isEmpty()) sb.append("\n");
                sb.append(line);
            }
            return sb.toString();
        }
    }
}
