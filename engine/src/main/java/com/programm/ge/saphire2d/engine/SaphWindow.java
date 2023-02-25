package com.programm.ge.saphire2d.engine;

import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class SaphWindow {

    private final long windowID;
    private boolean visible;
    private int width, height;

    public SaphWindow(long windowID) {
        this.windowID = windowID;
    }

    void init(){
        GLFW.glfwSetWindowSizeCallback(windowID, this::resizeCallback);
        int[] widthArr = new int[1];
        int[] heightArr = new int[1];

        GLFW.glfwGetWindowSize(windowID, widthArr, heightArr);
        width = widthArr[0];
        height = heightArr[0];
    }

    private void resizeCallback(long windowID, int width, int height){
        this.width = width;
        this.height = height;
    }

    public void cleanup(){
        Callbacks.glfwFreeCallbacks(windowID);
        GLFW.glfwDestroyWindow(windowID);
    }

    public void centerWindow(){
        long primaryMonitorID = GLFW.glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = GLFW.glfwGetVideoMode(primaryMonitorID);
        int monitorWidth = vidmode.width();
        int monitorHeight = vidmode.height();

        position(monitorWidth / 2 - width / 2, monitorHeight / 2 - height / 2);
    }

    public long id(){
        return windowID;
    }

    public boolean visible(){
        return visible;
    }

    public void visible(boolean visible){
        if(this.visible == visible) return;
        if(visible){
            GLFW.glfwShowWindow(windowID);
        }
        else {
            GLFW.glfwHideWindow(windowID);
        }

        this.visible = visible;
    }

    public void position(int x, int y){
        GLFW.glfwSetWindowPos(windowID, x, y);
    }

    public void size(int width, int height){
        this.width = width;
        this.height = height;
        GLFW.glfwSetWindowSize(windowID, width, height);
    }

    public int width(){
        return width;
    }

    public void width(int width){
        if(this.width == width) return;
        this.width = width;
        GLFW.glfwSetWindowSize(windowID, width, height);
    }

    public int height(){
        return height;
    }

    public void height(int height){
        if(this.height == height) return;
        this.height = height;
        GLFW.glfwSetWindowSize(windowID, width, height);
    }

    public boolean shouldClose(){
        return GLFW.glfwWindowShouldClose(windowID);
    }

}
