package com.programm.ge.saphire2d.reactivevalues.constraint.number;

import com.programm.ge.saphire2d.reactivevalues.constraint.Constraint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FloatMinConstraint implements Constraint<Float> {

    private final float min;

    @Override
    public Float constrain(Float value) {
        return Math.max(min, value);
    }
}
