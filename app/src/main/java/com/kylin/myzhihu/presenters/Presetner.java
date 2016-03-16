package com.kylin.myzhihu.presenters;

import android.os.Bundle;

import com.kylin.myzhihu.ui.Ui;

/**
 * Created by kylin_gu on 2016/3/16.
 */
public abstract class Presetner<U extends Ui> {
    private U mUi;

    public void onUiReady(U ui){
        mUi = ui;
    }

    public final void onUiDestory(U ui){
        onUiUnready(ui);
        mUi = null;
    }

    public void onUiUnready(U ui){
    }

    public void onSaveInstanceState(Bundle outState){}

    public void onRestoreInstanceState(Bundle savedInstance){}

    public U getUi(){
        return mUi;
    }
}
