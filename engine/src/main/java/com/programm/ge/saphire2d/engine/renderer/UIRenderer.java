package com.programm.ge.saphire2d.engine.renderer;

import com.programm.ge.saphire2d.core.bounds.ConstantBounds;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.engine.SaphWindow;
import com.programm.ge.saphire2d.engine.model.RawModel;
import com.programm.ge.saphire2d.engine.model.Texture;
import com.programm.ge.saphire2d.engine.model.font.Character;
import com.programm.ge.saphire2d.engine.model.font.FontMetadata;
import com.programm.ge.saphire2d.engine.shader.TestShader2;
import com.programm.ge.saphire2d.engine.shader.UILineShader;
import com.programm.ge.saphire2d.engine.shader.UIRectangleShader;
import com.programm.ge.saphire2d.engine.shader.UITextShader;
import com.programm.ge.saphire2d.engine.utils.ModelLoader;
import com.programm.ge.saphire2d.ui.IPencil;
import lombok.RequiredArgsConstructor;
import org.joml.Matrix4f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class UIRenderer implements IPencil {

    private static final float DEPTH_INCREASE = 0.0001f;


    @RequiredArgsConstructor
    private static class LineInfo {
        final float x1, y1, x2, y2;
        final float depth;
        final float lineSize;
        final Vector4f color;
        final IBounds clipSpace;
    }

    @RequiredArgsConstructor
    private static class RectInfo {
        final float x, y, w, h;
        final float depth;
        final Vector4f color;
        final float edge;
        final IBounds clipSpace;
    }

    @RequiredArgsConstructor
    private static class TextInfo {
        final float x, y;
        final float depth;
        final Vector4f color;
        final String text;
        final float fontSize;
        final IBounds clipSpace;
    }

    private static final Matrix4f TRANSFORM_MATRIX = new Matrix4f();

    private final SaphWindow window;
    private final RawModel rawLineModel;
    private final RawModel rawRectModel;
    private final RawModel rawTextureRectModel;

    private final Texture fontTexture;
    private final FontMetadata fontMetaFile;
    private final Texture testTexture;

    private final List<LineInfo> lineInfoList = new ArrayList<>();
    private final List<RectInfo> rectInfoList = new ArrayList<>();
    private final List<TextInfo> textInfoList = new ArrayList<>();

    private final UIRectangleShader rectShader = new UIRectangleShader();
    private final UILineShader lineShader = new UILineShader();
    private final UITextShader textShader = new UITextShader();
    private final TestShader2 testShader = new TestShader2();

    private float curDepth;
    private final Stack<IBounds> clippingStack = new Stack<>();
    private boolean clipCurrentlyActive;


    public UIRenderer(SaphWindow window) {
        this.window = window;
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
        rawTextureRectModel = ModelLoader.loadModel(2, new float[]{
                        -0.5f,  0.5f, //0
                        -0.5f, -0.5f, //1
                         0.5f, -0.5f, //2
                         0.5f,  0.5f, //3
                },
                new float[]{
                        0.0f, 0.0f, //Unten Links
                        0.0f, 1.0f, //Oben Links
                        1.0f, 1.0f, //Oben Rechts
                        1.0f, 0.0f, //Unten Rechts
                },
                new int[]{
                        0,1,3,
                        3,1,2,
                }
        );

        fontTexture = ModelLoader.loadTexture("/ui/fonts/arial.png", 1);
        fontMetaFile = FontMetadata.load("/ui/fonts/arial.fnt", window.aspectRatio());
        testTexture = ModelLoader.loadTexture("/ui/fonts/Test.png", 1);
    }

    public void init(Matrix4f projectionMatrix){
        rectShader.start();
        rectShader.loadProjectionMatrix(projectionMatrix);
        rectShader.stop();
        lineShader.start();
        lineShader.loadProjectionMatrix(projectionMatrix);
        lineShader.stop();
        textShader.start();
        textShader.loadProjectionMatrix(projectionMatrix);
        textShader.stop();
        testShader.start();
        testShader.loadProjectionMatrix(projectionMatrix);
        testShader.stop();


        //GL11.glScissor(0, 0, (int)(window.width() * 2), (int) (window.height() * 2));
    }






    //// =======PENCIL====== ////

    @Override
    public float stringWidth(String s, float fontSize) {
        float width = 0;
        for(int i=0;i<s.length();i++){
            char _c = s.charAt(i);
            if(_c == ' '){
                width += fontMetaFile.spaceWidth * fontSize;
                continue;
            }

            Character c = fontMetaFile.getCharacter(_c);
            width += c.xAdvance * fontSize;
        }

        return width;
    }

    @Override
    public float stringHeight(float fontSize) {
        return fontMetaFile.lineHeight * fontSize;
    }

    @Override
    public void pushClipping(float x, float y, float w, float h) {
        clippingStack.push(new ConstantBounds(x, y, w, h));
    }

    @Override
    public void popClipping() {
        clippingStack.pop();
    }

    @Override
    public void drawLine(float x1, float y1, float x2, float y2, Vector4f color, float lineSize) {
        lineInfoList.add(new LineInfo(x1, y1, x2, y2, getIncreaseDepth(), lineSize, color, getCurClipSpace()));
    }

    @Override
    public void drawRectangle(float x, float y, float width, float height, Vector4f color, float edge, float lineSize) {
        float depth = getIncreaseDepth();

        lineInfoList.add(new LineInfo(x, y, x + width, y, depth, lineSize, color, getCurClipSpace()));
        lineInfoList.add(new LineInfo(x + width, y, x + width, y + height, depth, lineSize, color, getCurClipSpace()));
        lineInfoList.add(new LineInfo(x, y + height, x + width, y + height, depth, lineSize, color, getCurClipSpace()));
        lineInfoList.add(new LineInfo(x, y, x, y + height, depth, lineSize, color, getCurClipSpace()));
    }

    @Override
    public void fillRectangle(float x, float y, float width, float height, Vector4f color, float edge) {
        rectInfoList.add(new RectInfo(x, y, width, height, getIncreaseDepth(), color, edge, getCurClipSpace()));
    }

    @Override
    public void drawString(String s, float x, float y, Vector4f color, float fontSize) {
        textInfoList.add(new TextInfo(x, y, getIncreaseDepth(), color, s, fontSize, getCurClipSpace()));
    }

    @Override
    public void drawStringCentered(String s, float x, float y, Vector4f color, float fontSize) {
        float strWidth = stringWidth(s, fontSize);
        float strHeight = stringHeight(fontSize);

//        drawRectangle(x - strWidth/2f, y + strHeight/2.5f, strWidth, strHeight, Colors.RED);
        drawString(s, x - strWidth/2f, y - strHeight/2.5f, color, fontSize);
    }

    @Override
    public void drawStringVCentered(String s, float x, float y, Vector4f color, float fontSize) {
        float strHeight = stringHeight(fontSize);
        drawString(s, x, y - strHeight/2.5f, color, fontSize);
    }

    @Override
    public void drawStringVCenteredRightAligned(String s, float x, float y, float width, Vector4f color, float fontSize) {
        float strWidth = stringWidth(s, fontSize);
        float strHeight = stringHeight(fontSize);

        drawString(s, x + width - strWidth, y - strHeight/2.5f, color, fontSize);
    }

    @Override
    public void drawStringHCentered(String s, float x, float y, Vector4f color, float fontSize) {
        float strWidth = stringWidth(s, fontSize);
        drawString(s, x - strWidth/2f, y, color, fontSize);
    }

    //// =================== ////



    private float getIncreaseDepth(){
        float depth = curDepth;
        curDepth += DEPTH_INCREASE;
        return depth;
    }

    private IBounds getCurClipSpace(){
        return clippingStack.isEmpty() ? null : clippingStack.peek();
    }

    private void activateClipSpace(IBounds bounds){
//        if(bounds == null) return;
        if(bounds == null){
            if(clipCurrentlyActive){
                clipCurrentlyActive = false;

//                GL11.glClear(GL11.GL_SCISSOR_BIT);
                GL11.glDisable(GL11.GL_SCISSOR_TEST);
            }
            return;
        }

//        System.out.println("Clip Space: " + bounds);

        if(!clipCurrentlyActive){
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
        }

        int x = (int)(bounds.x() * 2f);
        int y = (int)((window.height() - (bounds.y() + bounds.height())) * 2f);
        int w = (int)(bounds.width() * 2f);
        int h = (int)(bounds.height() * 2f);
        GL11.glScissor(x, y, w, h);
        clipCurrentlyActive = true;
    }


    public void render(){
//        System.out.println("Redering [" + lineInfoList.size() + "] Lines, [" + rectInfoList.size() + "] Rectangles and [" + textInfoList.size() + "] Textcomponents, Depth: [" + curDepth + "]!");

//        testShader.start();
//        GL30.glBindVertexArray(rawTextureRectModel.vaoID);
//        GL20.glEnableVertexAttribArray(0);
//        GL20.glEnableVertexAttribArray(1);
//
//        GL13.glActiveTexture(GL13.GL_TEXTURE0);
//        GL13.glBindTexture(GL11.GL_TEXTURE_2D, testTexture.textureID);
//
//        getUITransformation(TRANSFORM_MATRIX, 20, 20, 0, 300, 300);
//        testShader.loadTransformationMatrix(TRANSFORM_MATRIX);
//        GL11.glDrawElements(GL11.GL_TRIANGLES, rawTextureRectModel.vertexCount, GL11.GL_UNSIGNED_INT, 0);
//
//        GL20.glDisableVertexAttribArray(0);
//        GL20.glDisableVertexAttribArray(1);
//        GL30.glBindVertexArray(0);
//        testShader.stop();

        int[] scissorBox = new int[4];
        GL11.glGetIntegerv(GL11.GL_SCISSOR_BOX, scissorBox);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);














        lineShader.start();
        GL30.glBindVertexArray(rawLineModel.vaoID);
        GL20.glEnableVertexAttribArray(0);

        for(LineInfo info : lineInfoList) {
            activateClipSpace(info.clipSpace);

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
            activateClipSpace(info.clipSpace);

            getUITransformation(TRANSFORM_MATRIX, info.x, info.y, info.depth, info.w, info.h);
            rectShader.loadTransformationMatrix(TRANSFORM_MATRIX);
            rectShader.loadColor(info.color);

            GL11.glDrawElements(GL11.GL_TRIANGLES, rawRectModel.vertexCount, GL11.GL_UNSIGNED_INT, 0);
        }

        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);
        rectShader.stop();




        textShader.start();
        GL30.glBindVertexArray(rawTextureRectModel.vaoID);
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        GL13.glBindTexture(GL11.GL_TEXTURE_2D, fontTexture.textureID);

        for(TextInfo info : textInfoList) {
            activateClipSpace(info.clipSpace);

            textShader.loadColor(info.color);

            float curX = info.x;
            for(int i=0;i<info.text.length();i++){
                char _c = info.text.charAt(i);

                if(_c == ' '){
                    curX += fontMetaFile.spaceWidth * info.fontSize;
                    continue;
                }

                Character c = fontMetaFile.getCharacter(_c);


                getUITransformation(TRANSFORM_MATRIX, curX, info.y + c.offY * info.fontSize, info.depth, c.width * info.fontSize, c.height * info.fontSize);
                textShader.loadTransformationMatrix(TRANSFORM_MATRIX);
                textShader.loadTextureOffsetAndScale(c.texCoordX, c.texCoordY, c.texCoordMaxX, c.texCoordMaxY);

                GL11.glDrawElements(GL11.GL_TRIANGLES, rawTextureRectModel.vertexCount, GL11.GL_UNSIGNED_INT, 0);

                curX += c.xAdvance * info.fontSize;
            }
        }

        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);
        textShader.stop();



        curDepth = 0;
        lineInfoList.clear();
        rectInfoList.clear();
        textInfoList.clear();



        GL11.glScissor(scissorBox[0], scissorBox[1], scissorBox[2], scissorBox[3]);
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        clippingStack.clear();
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
        int vz = ny < 0 ? -1 : 1;

//        mat.translate(x1, window.height() - y1, depth);
        mat.translate(x1 - (nx * lineSize / 2f), window.height() - y1 + (ny * lineSize / 2f), depth);
//        mat.translate(x1 + (nx * (lineSize - 1)), window.height() - y1 + (ny * (lineSize - 1)), depth);
        mat.rotate(rotation, 0.0f, 0.0f, vz * 1.0f);
        mat.scale(dist + lineSize, lineSize, 1);
    }

    public void cleanup(){
        rectShader.cleanup();
    }

}
