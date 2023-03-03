package com.programm.ge.saphire2d.engine;

import com.programm.ge.saphire2d.core.IKeyboard;
import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.ConstantBounds;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.engine.renderer.SaphRenderer;
import com.programm.ge.saphire2d.engine.renderer.UIRenderer;
import com.programm.ge.saphire2d.engine.scene.Scene;
import com.programm.ge.saphire2d.engine.scene.SceneContext;
import com.programm.ge.saphire2d.engine.utils.MathUtils;
import com.programm.ge.saphire2d.ui.elements.SUIView;
import com.programm.ge.saphire2d.ui.utils.Colors;
import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

public class SaphEngine {

    private class InnerSceneContext implements SceneContext {
        @Override
        public IMouse mouse() {
            return window.mouse;
        }

        @Override
        public IKeyboard keyboard() {
            return window.keyboard;
        }

        @Override
        public double delta() {
            return delta;
        }
    }

    private static final Matrix4f PROJECTION_MATRIX = new Matrix4f();

    private final SaphWindow window;

    private final SaphRenderer renderer;
    private final UIRenderer uiRenderer;

    private final SceneContext sceneContext = new InnerSceneContext();
    private double delta = 0;

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

    public void run(Scene scene){


//        IBounds b = new ConstantBounds(100, 50, 300, 300);

        double last = GLFW.glfwGetTime();
        while(!window.shouldClose()){
            window.ui.render(window, uiRenderer);

            scene.update(sceneContext);

            renderer.prepare();
            renderer.render(scene);
            uiRenderer.render();

            GLFW.glfwSwapBuffers(window.id());

            GLFW.glfwPollEvents();

            double cur = GLFW.glfwGetTime();
            delta = cur - last;
            last = cur;
        }
    }

    private void buildUI(SUIView view){


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

    public SaphWindow window(){
        return window;
    }

}
