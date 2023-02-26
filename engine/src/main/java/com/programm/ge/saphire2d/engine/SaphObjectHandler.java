package com.programm.ge.saphire2d.engine;

import com.programm.ge.saphire2d.engine.model.GObject;
import com.programm.ge.saphire2d.engine.model.TexturedModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaphObjectHandler {

    public final Map<TexturedModel, List<GObject>> objects = new HashMap<>();

    public void add(TexturedModel model, GObject obj){
        objects.computeIfAbsent(model, q -> new ArrayList<>()).add(obj);
    }


}
