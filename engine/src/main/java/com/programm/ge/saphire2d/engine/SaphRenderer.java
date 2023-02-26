package com.programm.ge.saphire2d.engine;

import com.programm.ge.saphire2d.engine.model.TexturedModel;
import com.programm.ge.saphire2d.engine.shader.TestShader1;
import lombok.RequiredArgsConstructor;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

@RequiredArgsConstructor
public class SaphRenderer {

    private final TestShader1 shader;

    public void prepare() {
        GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
    }

    public void renderModel(TexturedModel texturedModel) {
        shader.start();

        GL30.glBindVertexArray(texturedModel.model.vaoID);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.texture.textureID);
        GL11.glDrawElements(GL11.GL_TRIANGLES, texturedModel.model.vertexCount, GL11.GL_UNSIGNED_INT, 0);

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);

        shader.stop();
    }

    public void cleanup() {
        shader.cleanup();
    }

}
