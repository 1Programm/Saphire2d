package com.programm.ge.saphire2d.engine;

import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.engine.controls.SaphKeyboard;
import com.programm.ge.saphire2d.engine.controls.SaphMouse;
import com.programm.saphire2d.ui.IComponent;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;

public class SaphWindow implements IBounds {

    private final SaphEngine engine;
    private final long windowID;
    private boolean visible;
    private int width, height;

    private final SaphMouse mouse;
    private final SaphKeyboard keyboard;

    public IComponent ui;

    public SaphWindow(SaphEngine engine, long windowID) {
        this.engine = engine;
        this.windowID = windowID;

        mouse = new SaphMouse(this);
        GLFW.glfwSetCursorPosCallback(windowID, mouse::receiveMousePosInput);
        GLFW.glfwSetMouseButtonCallback(windowID, mouse::receiveMouseButtonInput);

        keyboard = new SaphKeyboard(this);
        GLFW.glfwSetKeyCallback(windowID, keyboard::receiveKeyInput);
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

        engine.onWindowResize(width, height);
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

    public void width(int width){
        if(this.width == width) return;
        this.width = width;
        GLFW.glfwSetWindowSize(windowID, width, height);
    }

    public void height(int height){
        if(this.height == height) return;
        this.height = height;
        GLFW.glfwSetWindowSize(windowID, width, height);
    }

    @Override
    public float x() {
        return 0;
    }

    @Override
    public float y() {
        return 0;
    }

    @Override
    public float width(){
        return width;
    }

    @Override
    public float height(){
        return height;
    }

    public float aspectRatio(){
        return width() / height();
    }

    public boolean shouldClose(){
        return GLFW.glfwWindowShouldClose(windowID);
    }

    public void notifyMousePressed(int button){
        if(ui == null) return;
        ui.onMousePressed(this, mouse, button);
    }

    public void notifyMouseReleased(int button){
        if(ui == null) return;
        ui.onMouseReleased(this, mouse, button);
    }

    public void notifyMouseMoved(){
        if(ui == null) return;
        ui.onMouseMoved(this, mouse);
    }

    public void notifyMouseDragged(int button){
        if(ui == null) return;
        ui.onMouseDragged(this, mouse, button);
    }

    public void notifyKeyPressed(int key){
        if(ui == null) return;
        ui.onKeyReleased(this, keyboard, key);
    }

    public void notifyKeyReleased(int key){
        if(ui == null) return;
        ui.onKeyReleased(this, keyboard, key);
    }

}
