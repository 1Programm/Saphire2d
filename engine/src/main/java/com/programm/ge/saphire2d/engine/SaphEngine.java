package com.programm.ge.saphire2d.engine;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class SaphEngine {

    private static SaphWindow currentWindow;

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if(!GLFW.glfwInit()) throw new RuntimeException("Failed to initialize glfw!");
    }

    public SaphWindow createWindow(String title, int width, int height){
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        long windowId = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (windowId == MemoryUtil.NULL) throw new RuntimeException("Failed to create the GLFW window");

        SaphWindow window = new SaphWindow(windowId);
        window.init();

        return window;
    }

    public void run(SaphWindow window){
        if(currentWindow != window){
            GLFW.glfwMakeContextCurrent(window.id());
            GLFW.glfwSwapInterval(1);
            currentWindow = window;
        }

        GL.createCapabilities();

        GL11.glClearColor(0.0f, 1.0f, 0.0f, 0.0f);
        while(!window.shouldClose()){
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);

            GLFW.glfwSwapBuffers(window.id());

            GLFW.glfwPollEvents();
        }
    }

    public void cleanup(){
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }


}
