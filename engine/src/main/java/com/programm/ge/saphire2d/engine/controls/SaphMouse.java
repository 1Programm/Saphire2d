package com.programm.ge.saphire2d.engine.controls;

import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.engine.SaphWindow;
import com.programm.saphire2d.ui.IComponent;
import lombok.RequiredArgsConstructor;
import org.lwjgl.glfw.GLFW;

@RequiredArgsConstructor
public class SaphMouse implements IMouse {

    private final SaphWindow window;
    private float x, y, dragX, dragY;

    public void receiveMousePosInput(long windowID, double xpos, double ypos){
        if(GLFW.glfwGetMouseButton(windowID, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS){
            dragX = (float) xpos;
            dragY = (float) ypos;
            window.notifyMouseDragged(BUTTON_LEFT);
        }
        else {
            x = (float) xpos;
            y = (float) ypos;
            window.notifyMouseMoved();
        }
    }

    public void receiveMouseButtonInput(long windowID, int button, int action, int mods){
        if(action == GLFW.GLFW_PRESS){
            window.notifyMousePressed(button);
        }
        else if(action == GLFW.GLFW_RELEASE){
            window.notifyMouseReleased(button);
        }
        else {
            System.out.println("### MOUSE");
        }
    }

    @Override
    public float x() {
        return x;
    }

    @Override
    public float y() {
        return y;
    }

    @Override
    public float dragX() {
        return dragX;
    }

    @Override
    public float dragY() {
        return dragY;
    }

    @Override
    public boolean isButtonDown(int button) {
        return GLFW.glfwGetMouseButton(window.id(), button) == GLFW.GLFW_PRESS;
    }

}
