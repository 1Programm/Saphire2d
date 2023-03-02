package com.programm.ge.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.ge.saphire2d.ui.elements.layout.ILayout;
import com.programm.ge.saphire2d.ui.IPencil;
import com.programm.ge.saphire2d.ui.utils.GFXUtils;
import org.joml.Vector4f;

public class SUILabel extends AbstractTextComponent {

    static void renderText(AbstractTextComponent c, IBounds bounds, IPencil pen){
        String text = c.text().get();
        Vector4f textColor = c.textColor().get();
        float fontSize = c.fontSize().get();
        int textAlign = c.textAlign().get();

        if(textColor != null && text != null){
            Vector4f color = GFXUtils.mixColor(textColor, c.disabledColor(), c.disabled());

            if(textAlign == ILayout.ALIGN_CENTER) {
                pen.drawStringCentered(text, bounds.x() + bounds.width() / 2, bounds.y() + bounds.height() / 2, color, fontSize);
            }
            else if(textAlign == ILayout.ALIGN_LEFT){
                pen.drawStringVCentered(text, bounds.x() + 2, bounds.y() + bounds.height() / 2, color, fontSize);
            }
            else if(textAlign == ILayout.ALIGN_RIGHT){
                pen.drawStringVCenteredRightAligned(text, bounds.x() - 2, bounds.y() + bounds.height() / 2, bounds.width(), color, fontSize);
            }
        }
    }

    public SUILabel(String text) {
        super(text);
    }

    public SUILabel() {}

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
