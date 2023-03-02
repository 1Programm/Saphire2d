package com.programm.ge.saphire2d.reactivevalues.expression.number;

import com.programm.ge.saphire2d.reactivevalues.ObservableValue;
import com.programm.ge.saphire2d.reactivevalues.core.BoolObservable;
import com.programm.ge.saphire2d.reactivevalues.core.FloatPropertyValue;
import com.programm.ge.saphire2d.reactivevalues.expression.bool.BoolExpression;

public abstract class FloatComparisonExpression extends BoolExpression {

    public static BoolObservable EqualTo(ObservableValue<Float> val1, ObservableValue<Float> val2){
        return new FloatComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(float val1, float val2) {
                return val1 == val2;
            }
        };
    }

    public static BoolObservable GreaterThan(ObservableValue<Float> val1, ObservableValue<Float> val2){
        return new FloatComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(float val1, float val2) {
                return val1 > val2;
            }
        };
    }

    public static BoolObservable GreaterThanEqual(ObservableValue<Float> val1, ObservableValue<Float> val2){
        return new FloatComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(float val1, float val2) {
                return val1 >= val2;
            }
        };
    }

    public static BoolObservable LessThan(ObservableValue<Float> val1, ObservableValue<Float> val2){
        return new FloatComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(float val1, float val2) {
                return val1 < val2;
            }
        };
    }

    public static BoolObservable LessThanEqual(ObservableValue<Float> val1, ObservableValue<Float> val2){
        return new FloatComparisonExpression(val1, val2) {
            @Override
            protected Boolean compare(float val1, float val2) {
                return val1 <= val2;
            }
        };
    }

    public static BoolObservable EqualTo(ObservableValue<Float> val1, float val2){
        return new FloatComparisonExpression(val1, new FloatPropertyValue(val2)) {
            @Override
            protected Boolean compare(float val1, float val2) {
                return val1 == val2;
            }
        };
    }

    public static BoolObservable GreaterThan(ObservableValue<Float> val1, float val2){
        return new FloatComparisonExpression(val1, new FloatPropertyValue(val2)) {
            @Override
            protected Boolean compare(float val1, float val2) {
                return val1 > val2;
            }
        };
    }

    public static BoolObservable GreaterThanEqual(ObservableValue<Float> val1, float val2){
        return new FloatComparisonExpression(val1, new FloatPropertyValue(val2)) {
            @Override
            protected Boolean compare(float val1, float val2) {
                return val1 >= val2;
            }
        };
    }

    public static BoolObservable LessThan(ObservableValue<Float> val1, float val2){
        return new FloatComparisonExpression(val1, new FloatPropertyValue(val2)) {
            @Override
            protected Boolean compare(float val1, float val2) {
                return val1 < val2;
            }
        };
    }

    public static BoolObservable LessThanEqual(ObservableValue<Float> val1, float val2){
        return new FloatComparisonExpression(val1, new FloatPropertyValue(val2)) {
            @Override
            protected Boolean compare(float val1, float val2) {
                return val1 <= val2;
            }
        };
    }



    private final ObservableValue<Float> val1;
    private final ObservableValue<Float> val2;

    protected FloatComparisonExpression(ObservableValue<Float> val1, ObservableValue<Float> val2){
        super(val1, val2);
        this.val1 = val1;
        this.val2 = val2;
    }

    @Override
    public Boolean get() {
        return compare(val1.get(), val2.get());
    }

    protected abstract Boolean compare(float val1, float val2);
}
