package com.programm.ge.saphire2d.reactivevalues;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public abstract class AbstractProperty <T> extends AbstractObservableValue<T> implements SettableValue<T> {

    public static List<AbstractProperty<?>> GetAll(Object object){
        List<AbstractProperty<?>> properties = new ArrayList<>();

        for(Field field : object.getClass().getDeclaredFields()){
            if(AbstractProperty.class.isAssignableFrom(field.getType())){
                try{
                    boolean isFieldPrivate = !field.isAccessible();
                    field.setAccessible(true);
                    properties.add((AbstractProperty<?>)field.get(object));
                    if(isFieldPrivate) {
                        field.setAccessible(false);
                    }
                }catch (IllegalAccessException ignored){}
            }
        }

        return properties;
    }

    private List<Constraint<T>> constraints;

    @SafeVarargs
    public final void constrain(Constraint<T>... constraints){
        if(this.constraints == null){
            this.constraints = new ArrayList<>();
        }

        this.constraints.addAll(Arrays.asList(constraints));

        set(get());
    }

    @Override
    public final void set(T value) {
        if(constraints != null){
            for(Constraint<T> constraint : constraints){
                value = constraint.constrain(value);
            }
        }

        if(!isValueEqual(value)){
            setNotificationsEnabled(false);
            setDirectly(value);
            setNotificationsEnabled(true);
            notifyChange();
        }
    }

    public final void set(ObservableValue<T> value){
        set(value.get());
    }

    protected boolean isValueEqual(T value){
        return Objects.equals(get(), value);
    }

    protected abstract void setDirectly(T value);

    @Override
    public String toString() {
        return get().toString();
    }

    public interface Constraint<T> {
        T constrain(T value);
    }
}
