package com.programm.saphire2d.ui.elements;

import com.programm.ge.saphire2d.core.IMouse;
import com.programm.ge.saphire2d.core.bounds.IBounds;
import com.programm.saphire2d.ui.IPencil;
import com.programm.saphire2d.ui.SUIComponent;
import com.programm.saphire2d.ui.subscription.RunnableSubscriptionManager;
import com.programm.saphire2d.ui.subscription.Subscription;
import com.programm.saphire2d.ui.utils.GFXUtils;
import org.joml.Vector4f;

public class SUICheckbox extends SUIComponent {

    protected final RunnableSubscriptionManager valueChangeManager = new RunnableSubscriptionManager();
    protected boolean checked;

    @Override
    public void render(IBounds bounds, IPencil pen) {
        if(secondary != null){
            pen.fillRectangle(bounds, secondary, 0);
        }

        if(primary != null){
            Vector4f color = GFXUtils.primaryOrDisabled(this);
            pen.drawRectangle(bounds, color);

            if(checked) {
                float x1 = bounds.x();
                float y1 = bounds.y();
                float x2 = x1 + bounds.width();
                float y2 = y1 + bounds.height();

                pen.drawLine(x1, y1, x2, y2, color);
                pen.drawLine(x2, y1, x1, y2, color);
            }
        }
    }

    @Override
    public Float minWidth(IPencil pen) {
        return 16f;
    }

    @Override
    public Float minHeight(IPencil pen) {
        return 16f;
    }

    @Override
    public void onMousePressed(IBounds bounds, IMouse mouse, int button) {
        if(button == IMouse.BUTTON_LEFT) {
            if(mouse.inside(bounds)) {
                checked = !checked;
                valueChangeManager.notifyChange();
            }
        }
    }

    public void checked(boolean checked){
        this.checked = checked;
    }

    public boolean checked(){
        return checked;
    }

    public Subscription listenChecked(Runnable listener){
        return valueChangeManager.subscribe(listener);
    }

}
