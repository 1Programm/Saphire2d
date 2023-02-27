package com.programm.ge.saphire2d.engine.renderer;

import com.programm.ge.saphire2d.engine.SaphWindow;
import com.programm.ge.saphire2d.engine.model.RawModel;
import com.programm.ge.saphire2d.engine.shader.UiLineShader;
import com.programm.ge.saphire2d.engine.shader.UiRectangleShader;
import com.programm.ge.saphire2d.engine.utils.ModelLoader;
import com.programm.saphire2d.ui.IPencil;
import lombok.RequiredArgsConstructor;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;

public class UIRenderer implements IPencil {

    private static final float DEPTH_INCREASE = 0.0001f;

    @RequiredArgsConstructor
    private static class LineInfo {
        final float x1, y1, x2, y2;
        final float depth;
        final float lineSize;
        final Vector4f color;
    }

    @RequiredArgsConstructor
    private static class RectInfo {
        final float x, y, w, h;
        final float depth;
        final Vector4f color;
        final float edge;
    }

    private static final Matrix4f TRANSFORM_MATRIX = new Matrix4f();

    private final SaphWindow window;
    private final RawModel rawLineModel;
    private final RawModel rawRectModel;

    private final List<LineInfo> lineInfoList = new ArrayList<>();
    private final List<RectInfo> rectInfoList = new ArrayList<>();

    private final UiRectangleShader rectShader = new UiRectangleShader();
    private final UiLineShader lineShader = new UiLineShader();
    private float curDepth;


    public UIRenderer(SaphWindow window) {
        this.window = window;
//        rawLineModel = ModelLoader.loadLineModel(2,
//                new float[]{
//                        0.0f, 0.0f,
//                        1.0f, 0.0f,
//                });
        rawLineModel = ModelLoader.loadModel(2,
                new float[]{
                        0.0f,  0.5f,
                        0.0f, -0.5f,
                        1.0f, -0.5f,
                        1.0f,  0.5f,
                },
                new int[]{
                        0,1,3,
                        3,1,2,
                }
        );
        rawRectModel = ModelLoader.loadModel(2,
                new float[]{
                        -0.5f,  0.5f,
                        -0.5f, -0.5f,
                         0.5f, -0.5f,
                         0.5f,  0.5f,
                },
                new int[]{
                        0,1,3,
                        3,1,2,
                }
        );
    }

    public void init(Matrix4f projectionMatrix){
        rectShader.start();
        rectShader.loadProjectionMatrix(projectionMatrix);
        rectShader.stop();
        lineShader.start();
        lineShader.loadProjectionMatrix(projectionMatrix);
        lineShader.stop();
    }


    //// =================== ////

    @Override
    public float stringWidth(String s) {
        return 0;//TODO
    }

    @Override
    public float stringHeight() {
        return 0;
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2, Vector4f color, float lineSize) {
        lineInfoList.add(new LineInfo(x1, y1, x2, y2, getIncreaseDepth(), lineSize, color));
    }

    @Override
    public void drawRectangle(float x, float y, float width, float height, Vector4f color, float edge, float lineSize) {
        float depth = getIncreaseDepth();

        lineInfoList.add(new LineInfo(x, y, x + width, y, depth, lineSize, color));
        lineInfoList.add(new LineInfo(x + width, y, x + width, y + height, depth, lineSize, color));
        lineInfoList.add(new LineInfo(x, y + height, x + width, y + height, depth, lineSize, color));
        lineInfoList.add(new LineInfo(x, y, x, y + height, depth, lineSize, color));
    }

    @Override
    public void fillRectangle(float x, float y, float width, float height, Vector4f color, float edge) {
        rectInfoList.add(new RectInfo(x, y, width, height, getIncreaseDepth(), color, edge));
    }

    @Override
    public void drawString(String s, float x, float y, Vector4f color) {

    }

    @Override
    public void drawStringCentered(String s, float x, float y, Vector4f color) {

    }

    @Override
    public void drawStringVCentered(String s, float x, float y, Vector4f color) {

    }

    @Override
    public void drawStringVCenteredRightAligned(String s, float x, float y, float width, Vector4f color) {

    }

    @Override
    public void drawStringHCentered(String s, float x, float y, Vector4f color) {

    }

    //// =================== ////

    private float getIncreaseDepth(){
        float depth = curDepth;
        curDepth += DEPTH_INCREASE;
        return depth;
    }

    public void render(){
//        System.out.println("Redering [" + lineInfoList.size() + "] Lines and [" + rectInfoList.size() + "] Rectangles, Depth: [" + curDepth + "]!");

        lineShader.start();
        GL30.glBindVertexArray(rawLineModel.vaoID);
        GL20.glEnableVertexAttribArray(0);

        for(LineInfo info : lineInfoList) {
            getUILineTransformation(TRANSFORM_MATRIX, info.x1, info.y1, info.x2, info.y2, info.depth, info.lineSize);
            lineShader.loadTransformationMatrix(TRANSFORM_MATRIX);
            lineShader.loadColor(info.color);

//            GL11.glDrawArrays(GL11.GL_LINES, 0, rawLineModel.vertexCount);
            GL11.glDrawElements(GL11.GL_TRIANGLES, rawLineModel.vertexCount, GL11.GL_UNSIGNED_INT, 0);
        }

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        lineShader.stop();




        rectShader.start();
        GL30.glBindVertexArray(rawRectModel.vaoID);
        GL20.glEnableVertexAttribArray(0);

        for(RectInfo info : rectInfoList) {
            getUITransformation(TRANSFORM_MATRIX, info.x, info.y, info.depth, info.w, info.h);
            rectShader.loadTransformationMatrix(TRANSFORM_MATRIX);
            rectShader.loadColor(info.color);

            GL11.glDrawElements(GL11.GL_TRIANGLES, rawRectModel.vertexCount, GL11.GL_UNSIGNED_INT, 0);
        }

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        rectShader.stop();


        curDepth = 0;
        lineInfoList.clear();
        rectInfoList.clear();
    }

    private void getUITransformation(Matrix4f mat, float x, float y, float z, float w, float h){
        mat.identity();

        mat.translate(x + w/2f, window.height() - (y + h/2f), z);
        mat.scale(w, h, 1);
    }

    private void getUILineTransformation(Matrix4f mat, float x1, float y1, float x2, float y2, float depth, float lineSize){
        mat.identity();

        float dx = x2 - x1;
        float dy = y2 - y1;
        float dist = (float) Math.sqrt(dx * dx + dy * dy);
        float nx = dx / dist;
        float ny = dy / dist;

        float rotation = (float) (Math.PI * 2f - Math.acos(nx));

//        mat.translate(x1, window.height() - y1, depth);
        mat.translate(x1 - (nx * lineSize / 2f), window.height() - y1 + (ny * lineSize / 2f), depth);
//        mat.translate(x1 + (nx * (lineSize - 1)), window.height() - y1 + (ny * (lineSize - 1)), depth);
        mat.rotate(rotation, 0.0f, 0.0f, 1.0f);
        mat.scale(dist + lineSize, lineSize, 1);
    }

    public void cleanup(){
        rectShader.cleanup();
    }

}
