package com.programm.ge.saphire2d.reactivevalues.constraint.number;

import com.programm.ge.saphire2d.reactivevalues.constraint.Constraint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FloatMaxConstraint implements Constraint<Float> {

    private final float max;

    @Override
    public Float constrain(Float value) {
        return Math.min(max, value);
    }
}
