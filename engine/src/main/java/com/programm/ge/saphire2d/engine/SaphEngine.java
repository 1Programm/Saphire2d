package com.programm.ge.saphire2d.engine;

import com.programm.ge.saphire2d.engine.model.GObject;
import com.programm.ge.saphire2d.engine.model.TexturedModel;
import com.programm.ge.saphire2d.engine.shader.TestShader1;
import com.programm.ge.saphire2d.engine.utils.MathUtils;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class SaphEngine {

    private final SaphWindow window;
    private final SaphRenderer renderer;

    public SaphEngine(String title, int width, int height){
        init();
        window = createWindow(title, width, height);

        //Shader shader = ShaderUtils.createShader("/shaders/TestShader1");
        TestShader1 shader = new TestShader1();
        shader.start();
        Matrix4f mat = new Matrix4f();
        MathUtils.orthoProjection(mat, 0, 0, width, height);
        shader.loadProjectionMatrix(mat);
        shader.stop();

//        GL11.glViewport(0, 0, width, height);
        renderer = new SaphRenderer(shader);
    }

    public void debugMode(){
//        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE)
        GL11.glPolygonMode(GL11.GL_FRONT_AND_BACK, GL11.GL_LINE);
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if(!GLFW.glfwInit()) throw new RuntimeException("Failed to initialize glfw!");
    }

    private SaphWindow createWindow(String title, int width, int height){
        GLFW.glfwDefaultWindowHints();
        GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);

        //Fix for mac
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
        GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GLFW.GLFW_TRUE);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 4);
        GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 1);
        //---

        long windowId = GLFW.glfwCreateWindow(width, height, title, MemoryUtil.NULL, MemoryUtil.NULL);
        if (windowId == MemoryUtil.NULL) throw new RuntimeException("Failed to create the GLFW window");

        SaphWindow window = new SaphWindow(windowId);
        window.init();

        window.centerWindow();

        GLFW.glfwMakeContextCurrent(window.id());
        GLFW.glfwSwapInterval(1);

        window.visible(true);

        GL.createCapabilities();

        return window;
    }

    public void run(SaphObjectHandler handler){
        while(!window.shouldClose()){
            renderer.prepare();
            renderer.render(handler);

            GLFW.glfwSwapBuffers(window.id());

            GLFW.glfwPollEvents();
        }
    }

    public void cleanup(){
        renderer.cleanup();
        window.cleanup();
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

}
