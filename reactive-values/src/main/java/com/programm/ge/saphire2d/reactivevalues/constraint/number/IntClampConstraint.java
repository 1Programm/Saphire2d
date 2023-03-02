package com.programm.ge.saphire2d.reactivevalues.constraint.number;

import com.programm.ge.saphire2d.reactivevalues.constraint.Constraint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IntClampConstraint implements Constraint<Integer> {

    private final int min;
    private final int max;

    @Override
    public Integer constrain(Integer value) {
        return Math.max(min, Math.min(max, value));
    }
}
