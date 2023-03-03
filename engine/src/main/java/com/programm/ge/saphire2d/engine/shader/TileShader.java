package com.programm.ge.saphire2d.engine.shader;

import com.programm.ge.saphire2d.engine.model.Sprite;
import org.joml.Matrix4f;

public class TileShader extends Shader {

    private int projection_loc;
    private int transform_loc;
    private int tex_size_loc;
    private int tex_offset_loc;

    public TileShader() {
        super("/shaders/game/TestShader");
    }

    @Override
    protected void bindAttributes() {
        bindAttribute(0, "pos");
        bindAttribute(1, "texCoord");
    }

    @Override
    protected void loadUniformLocations() {
        projection_loc = getUniformLocation("projection");
        transform_loc = getUniformLocation("transform");
        tex_size_loc = getUniformLocation("tex_size");
        tex_offset_loc = getUniformLocation("tex_offset");
    }

    public void loadProjectionMatrix(Matrix4f m){
        loadMatrix(projection_loc, m);
    }

    public void loadTransformationMatrix(Matrix4f m){
        loadMatrix(transform_loc, m);
    }

    public void loadSpriteSheet(int ssw, int ssh, int x, int y){
        loadFloat2(tex_size_loc, ssw, ssh);

        float offX = (float)x / ssw;
        float offY = (float)y / ssh;

        loadFloat2(tex_offset_loc, offX, offY);
    }

    public void loadSpriteSheet(Sprite sprite){
        loadSpriteSheet(sprite.texture.spriteSheetWidth, sprite.texture.spriteSheetHeight, sprite.spriteX, sprite.spriteY);
    }

}
