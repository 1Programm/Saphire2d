package com.programm.ge.saphire2d.reactivevalues.expression.number;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.core.IntPropertyValue;
import com.programm.ge.saphire2d.reactivevalues.core.BoolObservable;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.BoolExpression;

public abstract class IntComparisonExpression extends BoolExpression {

    public static BoolObservable EqualTo(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        return new IntComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 == val2;
            }
        };
    }

    public static BoolObservable GreaterThan(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        return new IntComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 > val2;
            }
        };
    }

    public static BoolObservable GreaterThanEqual(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        return new IntComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 >= val2;
            }
        };
    }

    public static BoolObservable LessThan(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        return new IntComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 < val2;
            }
        };
    }

    public static BoolObservable LessThanEqual(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        return new IntComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 <= val2;
            }
        };
    }

    public static BoolObservable EqualTo(ObservableValue<Integer> val1, int val2){
        return new IntComparisonExpression(val1, new IntPropertyValue(val2)) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 == val2;
            }
        };
    }

    public static BoolObservable GreaterThan(ObservableValue<Integer> val1, int val2){
        return new IntComparisonExpression(val1, new IntPropertyValue(val2)) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 > val2;
            }
        };
    }

    public static BoolObservable GreaterThanEqual(ObservableValue<Integer> val1, int val2){
        return new IntComparisonExpression(val1, new IntPropertyValue(val2)) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 >= val2;
            }
        };
    }

    public static BoolObservable LessThan(ObservableValue<Integer> val1, int val2){
        return new IntComparisonExpression(val1, new IntPropertyValue(val2)) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 < val2;
            }
        };
    }

    public static BoolObservable LessThanEqual(ObservableValue<Integer> val1, int val2){
        return new IntComparisonExpression(val1, new IntPropertyValue(val2)) {
            @Override
            protected Boolean compare(int val1, int val2) {
                return val1 <= val2;
            }
        };
    }



    protected IntComparisonExpression(ObservableValue<Integer> val1, ObservableValue<Integer> val2){
        super(val1, val2);
    }

    @Override
    protected Boolean express(Object[] values) {
        return compare((int)values[0], (int)values[1]);
    }

    protected abstract Boolean compare(int val1, int val2);
}
