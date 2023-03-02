package com.programm.ge.saphire2d.reactivevalues.constraint.number;

import com.programm.ge.saphire2d.reactivevalues.constraint.Constraint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FloatClampConstraint implements Constraint<Float> {

    private final float min;
    private final float max;

    @Override
    public Float constrain(Float value) {
        return Math.max(min, Math.min(max, value));
    }
}
