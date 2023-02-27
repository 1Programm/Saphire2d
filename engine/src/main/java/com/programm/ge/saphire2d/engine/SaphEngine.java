package com.programm.ge.saphire2d.engine;

import com.programm.ge.saphire2d.core.bounds.ConstantBounds;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.engine.renderer.SaphRenderer;
import com.programm.ge.saphire2d.engine.renderer.UIRenderer;
import com.programm.ge.saphire2d.engine.utils.MathUtils;
import com.programm.saphire2d.ui.elements.*;
import com.programm.saphire2d.ui.utils.Colors;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class SaphEngine {

    private static final Matrix4f PROJECTION_MATRIX = new Matrix4f();

    private final SaphWindow window;
//    private final SaphMouse mouse;
//    private final SaphKeyboard keyboard;

    private final SaphRenderer renderer;
    private final UIRenderer uiRenderer;

    public SaphEngine(String title, int width, int height){
        init();
        window = createWindow(title, width, height);

//        mouse = new SaphMouse(window.id());
//        GLFW.glfwSetCursorPosCallback(window.id(), mouse::receiveMousePosInput);
//        GLFW.glfwSetMouseButtonCallback(window.id(), mouse::receiveMouseButtonInput);

//        keyboard = new SaphKeyboard(window.id());
//        GLFW.glfwSetKeyCallback(window.id(), keyboard::receiveKeyInput);


        //Shader shader = ShaderUtils.createShader("/shaders/TestShader1");
//        TestShader1 shader = new TestShader1();
//        shader.start();
//        Matrix4f mat = new Matrix4f();
//        MathUtils.orthoProjection(mat, 0, 0, width, height);
//        shader.loadProjectionMatrix(mat);
//        shader.stop();

        MathUtils.orthoProjection(PROJECTION_MATRIX, 0, 0, width, height);

//        GL11.glViewport(0, 0, width, height);
        renderer = new SaphRenderer();
        renderer.init(PROJECTION_MATRIX);
        uiRenderer = new UIRenderer(window);
        uiRenderer.init(PROJECTION_MATRIX);
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

        SaphWindow window = new SaphWindow(this, windowId);
        window.init();

        window.centerWindow();

        GLFW.glfwMakeContextCurrent(window.id());
        GLFW.glfwSwapInterval(1);

        window.visible(true);

        GL.createCapabilities();

        return window;
    }

    public void run(SaphObjectHandler handler){

        SUIView view = new SUIView();
        window.ui = view;

        buildUI(view);



//        cb.primary(Colors.RED);
//        cb.secondary(null);
        IBounds b = new ConstantBounds(100, 100, 300, 300);

        while(!window.shouldClose()){
            view.render(b, uiRenderer);

//            System.out.println("StrWidth: " + uiRenderer.stringWidth("Hello World!", 77));
//            uiRenderer.fillRectangle(100, 100, 428, 77, Colors.LIGHT_GRAY);
//            uiRenderer.drawString("Hello World!", 100, 100, Colors.BLACK, 77);



//            uiRenderer.drawLine(0, 0, 100, 100, Colors.RED);
//            uiRenderer.drawLine(10, 10, 100, 110, Colors.RED);
//            uiRenderer.drawLine(10, 10, 100, 120, Colors.RED);
//            uiRenderer.drawLine(10, 10, 100, 130, Colors.RED);
//            uiRenderer.drawLine(10, 10, 100, 140, Colors.RED);
//            uiRenderer.drawLine(10, 10, 100, 150, Colors.RED);

            renderer.prepare();
            renderer.render(handler);
            uiRenderer.render();

            GLFW.glfwSwapBuffers(window.id());

            GLFW.glfwPollEvents();
        }
    }

    private void buildUI(SUIView view){
//        WaveTabView tabView = new WaveTabView();
//        view.add(tabView);
//
//        tabView.

        SUIButton btn = new SUIButton("Hello");
        btn.listenPressed(() -> System.out.println("Heyyy"));
        view.add(btn);

//        WaveLabel label = new WaveLabel("My Label");
//        label.fontSize(50);
//        view.add(label);




    }

    void onWindowResize(int width, int height) {
        //Recalculate Projection with new aspectRatio
        MathUtils.orthoProjection(PROJECTION_MATRIX, 0, 0, width, height);

        renderer.init(PROJECTION_MATRIX);
        uiRenderer.init(PROJECTION_MATRIX);
    }

    public void cleanup(){
        uiRenderer.cleanup();
        renderer.cleanup();
        window.cleanup();
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

}
