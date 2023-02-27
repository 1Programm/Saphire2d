package com.programm.ge.saphire2d.engine.utils;

import com.programm.ge.saphire2d.engine.model.RawModel;
import com.programm.ge.saphire2d.engine.model.Texture;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ModelLoader {
    private static final List<Integer> VAOS = new ArrayList<>();
    private static final List<Integer> VBOS = new ArrayList<>();
    private static final List<Integer> TEXTURES = new ArrayList<>();

    public static Texture loadTexture2(String name, int numRows){
        BufferedImage image;
        try {
            InputStream is = ModelLoader.class.getResourceAsStream(name);
            if(is == null) throw new RuntimeException("Failed to load image [" + name + "]!");
            image = ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read image [" + name + "]!", e);
        }

        int width = image.getWidth();
        int height = image.getHeight();
        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        int[] data = new int[width * height];
        for (int i = 0; i < width * height; i++) {
            int a = (pixels[i] & 0xff000000) >> 24;
            int r = (pixels[i] & 0xff0000) >> 16;
            int g = (pixels[i] & 0xff00) >> 8;
            int b = (pixels[i] & 0xff);

            data[i] = a << 24 | b << 16 | g << 8 | r;
        }

        int textureID = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        IntBuffer buffer = ByteBuffer.allocateDirect(data.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        buffer.put(data).flip();

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);


        return new Texture(textureID, numRows);
    }

//    public static Texture loadTexture(String name){
//        InputStream is = ModelLoader.class.getResourceAsStream(name);
//        if(is == null) throw new RuntimeException("Could not find resource: [" + name + "]!");
//
//        byte[] imageBytes;
//        try {
//            imageBytes = is.readAllBytes();
//        }
//        catch (IOException e){
//            throw new RuntimeException("Failed to read bytes from resource image: [" + name + "]!", e);
//        }
//
//        ByteBuffer buffer = BufferUtils.createByteBuffer(imageBytes.length);
//        buffer.put(imageBytes);
//        buffer.flip();
//
//        int[] width = new int[1];
//        int[] height = new int[1];
//        int[] channels = new int[1];
//        ByteBuffer data = STBImage.stbi_load_from_memory(buffer, width, height, channels, 0);
//
//        if(data == null) throw new RuntimeException("Failed to load image: [" + name + "]!");
//
//        return _loadTexture(data, width[0], height[0]);
//    }
//
//    public static Texture loadTextureFromAbsulutePath(String name){
//        int[] width = new int[1];
//        int[] height = new int[1];
//        int[] channels = new int[1];
//        ByteBuffer data = STBImage.stbi_load(name, width, height, channels, 0);
//
//        if(data == null) throw new RuntimeException("Failed to load image: [" + name + "]!");
//
//        return _loadTexture(data, width[0], height[0]);
//    }
//
//    private static Texture _loadTexture(ByteBuffer data, int width, int height){
//        int textureID = GL11.glGenTextures();
//        TEXTURES.add(textureID);
//
//        GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureID);
//
////        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR_MIPMAP_LINEAR);
////        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
//
//        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, width, height, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, data);
////        GL30.glGenerateMipmap(textureID);
//
////        STBImage.stbi_image_free(data);
//
//        return new Texture(textureID);
//    }

    public static RawModel loadLineModel(int dim, float[] positions){
        int vaoID = createVAO();
        storeDataInAttribList(0, dim, positions);
        unbindVAO();
        return new RawModel(vaoID, positions.length / 2);
    }

    public static RawModel loadModel(int dim, float[] positions, int[] indices){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttribList(0, dim, positions);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public static RawModel loadModel(int dim, float[] positions, float[] texCoords, int[] indices){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttribList(0, dim, positions);
        storeDataInAttribList(1, 2, texCoords);
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public static int loadAndReturnIndex(int dim, float[] positions, float[] texCoords){
        int vaoID = createVAO();
        storeDataInAttribList(0, dim, positions);
        storeDataInAttribList(1, 2, texCoords);
        unbindVAO();
        return vaoID;
    }

    public static void cleanup(){
        for(int vaoID : VAOS){
            GL30.glDeleteVertexArrays(vaoID);
        }

        for(int vboID : VBOS){
            GL15.glDeleteBuffers(vboID);
        }

        for(int texture : TEXTURES){
            GL11.glDeleteTextures(texture);
        }
    }

    private static int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        VAOS.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    private static void storeDataInAttribList(int attribId, int dim, float[] data){
        int vboID = GL15.glGenBuffers();
        VBOS.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);

        FloatBuffer fb = floatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, fb, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attribId, dim, GL11.GL_FLOAT, false, 0, 0);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    private static void unbindVAO(){
        GL30.glBindVertexArray(0);
    }

    private static void bindIndicesBuffer(int[] indices){
        int vboID = GL15.glGenBuffers();
        VBOS.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);

        IntBuffer buffer = intBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

//        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);//?
    }

    private static IntBuffer intBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    private static FloatBuffer floatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }
}
