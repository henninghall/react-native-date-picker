package com.henninghall.date_picker.props;

import com.facebook.react.bridge.Dynamic;

public abstract class Prop<T> {
    private T value;

    public Prop() { }

    public Prop(T defaultValue) {
        value = defaultValue;
    }

    abstract T toValue(Dynamic value);

    public void setValue(Dynamic value){
        this.value = toValue(value);
    }

    public void setValue(T value){
        this.value = value;
    }

    public T getValue(){
        return value;
    }

}
