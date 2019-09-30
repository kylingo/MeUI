package com.me.ui.sample.library.pattern.structure.proxy.service;

import android.os.RemoteException;

import com.free.design.structure.proxy.service.aidl.ISimpleAIDL;
import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-15.
 */
public class SimpleBinder extends ISimpleAIDL.Stub {
    @Override
    public void shop() throws RemoteException {
        LogUtils.d(SimpleBinder.class, "shop");
    }
}
