package com.programm.ge.saphire2d.engine.handler;

import com.programm.ge.saphire2d.engine.model.font.GUIText;
import com.programm.ge.saphire2d.engine.model.font.SaphFont;
import com.programm.ge.saphire2d.engine.model.font.TextMeshData;
import com.programm.ge.saphire2d.engine.renderer.FontRenderer;
import com.programm.ge.saphire2d.engine.utils.ModelLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TextHandler {

    private final Map<SaphFont, List<GUIText>> texts = new HashMap<>();
    private final FontRenderer fontRenderer;

    public TextHandler() {
        fontRenderer = new FontRenderer();
    }

    public void loadText(GUIText text){
        TextMeshData data = text.font.loadText(text);
        text.textMeshVao = ModelLoader.loadAndReturnIndex(2, data.vertexPositions, data.textureCoords);
        text.vertexCount = data.getVertexCount();

        texts.computeIfAbsent(text.font, f -> new ArrayList<>()).add(text);
    }

    public void removeText(GUIText text){
        List<GUIText> batch = texts.get(text.font);
        if(batch == null) return;

        batch.remove(text);
        if(batch.isEmpty()){
            texts.remove(text.font);
        }
    }

    public void cleanup(){
        fontRenderer.cleanup();
    }
}
