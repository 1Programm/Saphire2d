package com.programm.ge.saphire2d.engine;

import com.programm.ge.saphire2d.core.bounds.ConstantBounds;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.engine.renderer.SaphRenderer;
import com.programm.ge.saphire2d.engine.renderer.UIRenderer;
import com.programm.ge.saphire2d.engine.utils.MathUtils;
import com.programm.saphire2d.ui.elements.*;
import com.programm.saphire2d.ui.elements.layout.GridLayout;
import com.programm.saphire2d.ui.elements.layout.InheritLayout;
import com.programm.saphire2d.ui.elements.layout.VerticalLayout;
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

//        SUIView view = new SUIView();
        SUIView view = new SUIView(new InheritLayout());
        view.primary(Colors.BLACK);
        window.ui = view;

//        SUIButton btn = new SUIButton("Hellooo There");
//        btn.bounds(-50,0, 100, 20);
//        view.add(btn);


//        SUIScrollView sv = new SUIScrollView();
//        sv.setLayout(new VerticalLayout());
//        sv.secondary(Colors.RED);
//
//        SUIButton b1 = new SUIButton("Hey");
//        b1.listenPressed(() -> System.out.println("jojoo"));
//        sv.add(b1);
//        sv.add(new SUIButton("Cool"));
//        sv.add(new SUIButton("Buttons"));
//
//
//        view.add(sv);

        SUICombobox2<String> cb1 = new SUICombobox2<>();
        cb1.bounds(0, 0, 100, 30);
        cb1.scrollTrigger(100);

        cb1.addItem("Hey");
        cb1.addItem("Ich");
        cb1.addItem("Du");
        cb1.addItem("Er");
        cb1.addItem("Sie");
        cb1.addItem("Es");
        cb1.addItem("Wir");
        view.add(cb1);

        SUICombobox<String> cb2 = new SUICombobox<>();
        cb2.bounds(110, 0, 100, 30);

        cb2.addItem("Hey");
        cb2.addItem("Ich");
        cb2.addItem("Du");
        cb2.addItem("Er");
        cb2.addItem("Sie");
        cb2.addItem("Es");
        cb2.addItem("Wir");
        view.add(cb2);

//        buildUI(view);



        IBounds b = new ConstantBounds(100, 50, 300, 300);



        while(!window.shouldClose()){
            view.render(b, uiRenderer);

//            uiRenderer.setClipping(20, 20, window.width() - 40, 50);
//            uiRenderer.fillRectangle(10, 10, window.width() - 20, window.height() - 20, Colors.RED);



            renderer.prepare();
            renderer.render(handler);
            uiRenderer.render();

            GLFW.glfwSwapBuffers(window.id());

            GLFW.glfwPollEvents();
        }
    }

    private void buildUI(SUIView view){
        WaveTabView tabView = new WaveTabView();
        tabView.primary(Colors.BLACK);
        view.add(tabView);

        SUIView v1 = new SUIView(new GridLayout(3, 3));
        v1.secondary(Colors.WHITE);
        v1.add(new SUIButton("My Button 1"), 4);
        tabView.add(v1, "Tab 1");

        SUIView v2 = new SUIView(new GridLayout(3, 3));
        v2.secondary(Colors.WHITE);
        v2.add(new SUIButton("My Button 2"), 0);
        tabView.add(v2, "Cooler Tab 2");

//        SUIButton btn = new SUIButton("Hello");
//        btn.listenPressed(() -> System.out.println("Heyyy"));
//        view.add(btn);

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
