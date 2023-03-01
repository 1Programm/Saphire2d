package com.programm.ge.saphire2d.reactivevalues.expression.integer;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.core.IntValueProperty;
import com.programm.ge.saphire2d.reactivevalues.core.ObservableBool;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.BoolExpression;

public abstract class IntComparisonExpression extends BoolExpression {

    public static ObservableBool EqualTo(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        return new IntComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 == val2;
            }
        };
    }

    public static ObservableBool GreaterThan(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        return new IntComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 > val2;
            }
        };
    }

    public static ObservableBool GreaterThanEqual(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        return new IntComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 >= val2;
            }
        };
    }

    public static ObservableBool LessThan(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        return new IntComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 < val2;
            }
        };
    }

    public static ObservableBool LessThanEqual(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        return new IntComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 <= val2;
            }
        };
    }

    public static ObservableBool EqualTo(ObservableValue<Integer> val1, int val2){
        return new IntComparisonExpression(val1, new IntValueProperty(val2)) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 == val2;
            }
        };
    }

    public static ObservableBool GreaterThan(ObservableValue<Integer> val1, int val2){
        return new IntComparisonExpression(val1, new IntValueProperty(val2)) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 > val2;
            }
        };
    }

    public static ObservableBool GreaterThanEqual(ObservableValue<Integer> val1, int val2){
        return new IntComparisonExpression(val1, new IntValueProperty(val2)) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 >= val2;
            }
        };
    }

    public static ObservableBool LessThan(ObservableValue<Integer> val1, int val2){
        return new IntComparisonExpression(val1, new IntValueProperty(val2)) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 < val2;
            }
        };
    }

    public static ObservableBool LessThanEqual(ObservableValue<Integer> val1, int val2){
        return new IntComparisonExpression(val1, new IntValueProperty(val2)) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 <= val2;
            }
        };
    }



    private final ObservableValue<Integer> val1;
    private final ObservableValue<Integer> val2;

    protected IntComparisonExpression(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        super(val1, val2);
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public Boolean get() {
        return compare(val1.get(), val2.get());
    }

    protected abstract Boolean compare(int val1, int val2);
}
