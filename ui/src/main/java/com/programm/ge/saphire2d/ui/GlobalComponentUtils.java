package com.programm.ge.saphire2d.ui;

public class GlobalComponentUtils {

//    public static boolean isDirty(SUIComponent component){
//        return component.dirty;
//    }
//
//    public static void setDirty(SUIComponent component, boolean dirty) {
//        component.dirty = dirty;
//    }

    public static boolean xUntouched(SUIComponent component) {
        return component.xUntouched;
    }

    public static boolean yUntouched(SUIComponent component) {
        return component.yUntouched;
    }

    public static boolean widthUntouched(SUIComponent component) {
        return component.widthUntouched;
    }

    public static boolean heightUntouched(SUIComponent component) {
        return component.heightUntouched;
    }

    public static float getWidthConstrainedByMinMax(IPencil pen, SUIComponent component, float width){
        Float min = component.minWidth(pen);
        //Float max = component.maxWidth(pen);

        if(min != null){
            width = Math.max(width, min);
        }

//        if(max != null){
//            width = Math.min(width, max);
//        }

        return width;
    }

    public static float getWidthConstrainedByMinMax(IPencil pen, SUIComponent component){
        return getWidthConstrainedByMinMax(pen, component, component.width());
    }

    public static float getHeightConstrainedByMinMax(IPencil pen, SUIComponent component, float height){
        Float min = component.minHeight(pen);
        //Float max = component.maxHeight(pen);

        if(min != null){
            height = Math.max(height, min);
        }

//        if(max != null){
//            height = Math.min(height, max);
//        }

        return height;
    }

    public static float getHeightConstrainedByMinMax(IPencil pen, SUIComponent component){
        return getHeightConstrainedByMinMax(pen, component, component.height());
    }

    private GlobalComponentUtils(){}
}
