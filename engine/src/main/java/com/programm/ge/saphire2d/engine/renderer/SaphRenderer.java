package com.programm.ge.saphire2d.engine.renderer;

import com.programm.ge.saphire2d.engine.game.objects.GameObject;
import com.programm.ge.saphire2d.engine.model.RawModel;
import com.programm.ge.saphire2d.engine.model.Sprite;
import com.programm.ge.saphire2d.engine.scene.Scene;
import com.programm.ge.saphire2d.engine.scene.TileMap;
import com.programm.ge.saphire2d.engine.shader.TileShader;
import com.programm.ge.saphire2d.engine.utils.MathUtils;
import com.programm.ge.saphire2d.engine.utils.ModelLoader;
import lombok.RequiredArgsConstructor;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.List;

@RequiredArgsConstructor
public class SaphRenderer {

    private static final Matrix4f TRANSFORM_MATRIX = new Matrix4f();

    private final TileShader tileShader = new TileShader();
    private final RawModel rawRectModel;

    public SaphRenderer() {
        rawRectModel = ModelLoader.loadModel(2,
                new float[]{
                        -0.5f,  0.5f,
                        -0.5f, -0.5f,
                        0.5f, -0.5f,
                        0.5f,  0.5f,
                },
                new float[]{
                        0.0f, 0.0f,
                        0.0f, 1.0f,
                        1.0f, 1.0f,
                        1.0f, 0.0f,
                },
                new int[]{
                        0,1,3,
                        3,1,2,
                }
        );
    }

    public void init(Matrix4f projectionMatrix){
        tileShader.start();
        tileShader.loadProjectionMatrix(projectionMatrix);
        tileShader.stop();

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LEQUAL); //Text is same depth so characters might overlay some others
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void prepare() {
//        GL11.glClearColor(1.0f, 1.0f, 1.0f, 0.0f);
        GL11.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
    }

    public void render(Scene scene){

        tileShader.start();
        GL30.glBindVertexArray(rawRectModel.vaoID);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

//        renderTileMap(scene);
        renderTileMapOptimized(scene);

        for(GameObject obj : scene.objects){
            renderObject(scene, obj);
        }


        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        tileShader.stop();
    }

    private void renderObject(Scene scene, GameObject obj){
        Sprite sprite = scene.sprites.get(obj.spriteId);
        if(sprite == null) return;

        tileShader.loadSpriteSheet(sprite);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, sprite.texture.textureID);

        Matrix4f transform = obj.transformation.asMatrix();
        tileShader.loadTransformationMatrix(transform);
        GL11.glDrawElements(GL11.GL_TRIANGLES, rawRectModel.vertexCount, GL11.GL_UNSIGNED_INT, 0);
    }

    private void renderTileMapOptimized(Scene scene){
        for(Integer spriteId : scene.tileMap.tilesOptimized.keySet()){
            Sprite sprite = scene.sprites.get(spriteId);
            if(sprite == null) continue;

            tileShader.loadSpriteSheet(sprite);
            GL13.glBindTexture(GL11.GL_TEXTURE_2D, sprite.texture.textureID);

            List<TileMap.Point> points = scene.tileMap.tilesOptimized.get(spriteId);
            for(TileMap.Point p : points){
                getTileTransformation(TRANSFORM_MATRIX, p.x, p.y, p.z, scene.tileMap.tileWidth, scene.tileMap.tileHeight);
                tileShader.loadTransformationMatrix(TRANSFORM_MATRIX);
                GL11.glDrawElements(GL11.GL_TRIANGLES, rawRectModel.vertexCount, GL11.GL_UNSIGNED_INT, 0);
            }
        }
    }

    private void renderTileMap(Scene scene){
        for(int x=0;x<scene.tileMap.width;x++){
            for(int y=0;y<scene.tileMap.height;y++){
                for(int z=0;z<scene.tileMap.depth;z++){
                    Integer texId = scene.tileMap.getTile(x, y, z);
                    if(texId == null) continue;
                    Sprite sprite = scene.sprites.get(texId);
                    if(sprite == null) continue;

//                    tileShader.loadSpriteSheetSize(sprite.texture.numberOfRows);
                    tileShader.loadSpriteSheet(sprite);


                    GL13.glBindTexture(GL11.GL_TEXTURE_2D, sprite.texture.textureID);

                    getTileTransformation(TRANSFORM_MATRIX, x, y, z, scene.tileMap.tileWidth, scene.tileMap.tileHeight);
                    tileShader.loadTransformationMatrix(TRANSFORM_MATRIX);
                    GL11.glDrawElements(GL11.GL_TRIANGLES, rawRectModel.vertexCount, GL11.GL_UNSIGNED_INT, 0);
                }
            }
        }
    }

    private void getTileTransformation(Matrix4f m, int x, int y, int z, float tileWidth, float tileHeight){
        MathUtils.getTransformation(m,
                (x + 0.5f) * tileWidth, (y + 0.5f) * tileHeight, z * 0.00001f,
                tileWidth, tileHeight);
    }

    public void cleanup() {
        tileShader.cleanup();
    }

}
