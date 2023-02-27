package com.programm.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.saphire2d.ui.IPencil;
import com.programm.saphire2d.ui.elements.layout.ILayout;
import com.programm.saphire2d.ui.utils.GFXUtils;
import org.joml.Vector4f;

public class WaveLabel extends AbstractTextComponent {

    static void renderText(AbstractTextComponent c, IBounds bounds, IPencil pen){
        if(c.textColor != null && c.text != null){
            Vector4f color = GFXUtils.mixColor(c.textColor, c.disabledColor(), c.disabled());

            if(c.textAlign == ILayout.ALIGN_CENTER) {
                pen.drawStringCentered(c.text, bounds.x() + bounds.width() / 2, bounds.y() + bounds.height() / 2, color);
            }
            else if(c.textAlign == ILayout.ALIGN_LEFT){
                pen.drawStringVCentered(c.text, bounds.x() + 2, bounds.y() + bounds.height() / 2, color);
            }
            else if(c.textAlign == ILayout.ALIGN_RIGHT){
                pen.drawStringVCenteredRightAligned(c.text, bounds.x() - 2, bounds.y() + bounds.height() / 2, bounds.width(), color);
            }
        }
    }

    public WaveLabel(String text) {
        this();
        this.text = text;
    }

    public WaveLabel() {
        primary = null;
    }

    @Override
    public void render(IBounds bounds, IPencil pen) {
        if(secondary != null){
            pen.fillRectangle(bounds, secondary);
        }

        if(primary != null){
            Vector4f color = GFXUtils.primaryOrDisabled(this);
            pen.drawRectangle(bounds, color);
        }

        renderText(this, bounds, pen);
    }


}
