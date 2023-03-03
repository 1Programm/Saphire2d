package com.programm.ge.saphire2d.engine.controls;

import com.programm.ge.saphire2d.core.IKeyboard;
import com.programm.ge.saphire2d.engine.SaphWindow;
import lombok.RequiredArgsConstructor;
import org.lwjgl.glfw.GLFW;

@RequiredArgsConstructor
public class SaphKeyboard implements IKeyboard {

    private final SaphWindow window;

    public void receiveKeyInput(long windowID, int key, int scancode, int action, int mods){
        if(action == GLFW.GLFW_PRESS){
            window.notifyKeyPressed(key);
        }
        else if(action == GLFW.GLFW_RELEASE){
            window.notifyKeyReleased(key);
        }
    }

    @Override
    public boolean isKeyPressed(int code) {
        return GLFW.glfwGetKey(window.id(), code) == GLFW.GLFW_PRESS;
    }

}
