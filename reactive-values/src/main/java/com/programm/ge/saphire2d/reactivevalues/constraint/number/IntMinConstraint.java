package com.programm.ge.saphire2d.reactivevalues.constraint.number;

import com.programm.ge.saphire2d.reactivevalues.constraint.Constraint;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IntMinConstraint implements Constraint<Integer> {

    private final int min;

    @Override
    public Integer constrain(Integer value) {
        return Math.max(min, value);
    }
}
