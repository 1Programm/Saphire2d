package com.programm.ge.saphire2d.ui.utils;

import com.programm.ge.saphire2d.ui.SUIComponent;
import org.joml.Vector4f;

public class GFXUtils {

    public static Vector4f mixColor(Vector4f base, Vector4f other, boolean toggle){
        return (toggle && other != null) ? other : base;
    }

    public static Vector4f mixColor(Vector4f base, Vector4f o1, boolean t1, Vector4f o2, boolean t2){
        if(t1 && o1 != null) return o1;
        if(t2 && o2 != null) return o2;
        return base;
    }

    public static Vector4f primaryOr(SUIComponent component, Vector4f other, boolean toggle){
        return mixColor(component.primary(), other, toggle);
    }

    public static Vector4f primaryOrDisabled(SUIComponent component){
        return primaryOr(component, component.disabledColor(), component.disabled());
    }

    public static Vector4f secondaryOr(SUIComponent component, Vector4f other, boolean toggle){
        return mixColor(component.secondary(), other, toggle);
    }

    public static Vector4f secondaryOrDisabled(SUIComponent component){
        return secondaryOr(component, component.disabledColor(), component.disabled());
    }

}
