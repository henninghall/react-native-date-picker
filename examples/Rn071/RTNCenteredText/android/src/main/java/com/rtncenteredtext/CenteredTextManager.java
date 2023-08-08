package com.rtncenteredtext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.annotations.ReactModule;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.viewmanagers.RTNCenteredTextManagerInterface;
import com.facebook.react.viewmanagers.RTNCenteredTextManagerDelegate;

@ReactModule(name = CenteredTextManager.NAME)
public class CenteredTextManager extends SimpleViewManager<CenteredText>
        implements RTNCenteredTextManagerInterface<CenteredText> {

    private final ViewManagerDelegate<CenteredText> mDelegate;

    static final String NAME = "RTNCenteredText";

    public CenteredTextManager(ReactApplicationContext context) {
        mDelegate = new RTNCenteredTextManagerDelegate<>(this);
    }

    @Nullable
    @Override
    protected ViewManagerDelegate<CenteredText> getDelegate() {
        return mDelegate;
    }

    @NonNull
    @Override
    public String getName() {
        return CenteredTextManager.NAME;
    }

    @NonNull
    @Override
    protected CenteredText createViewInstance(@NonNull ThemedReactContext context) {
        return new CenteredText(context);
    }

    @Override
    @ReactProp(name = "text")
    public void setText(CenteredText view, @Nullable String text) {
        view.setText(text);
    }
}