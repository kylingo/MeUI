package com.me.ui.sample.pattern.structure.proxy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.IBinder;
import android.os.RemoteException;

import com.free.design.structure.proxy.service.aidl.ISimpleAIDL;
import com.me.ui.sample.Global;
import com.me.ui.sample.pattern.PatternTest;
import com.me.ui.sample.pattern.structure.proxy.dynamic.DynamicProxy;
import com.me.ui.sample.pattern.structure.proxy.service.SimpleService;
import com.me.ui.sample.pattern.structure.proxy.simple.ISimpleProxy;
import com.me.ui.sample.pattern.structure.proxy.simple.SimpleClient;
import com.me.ui.sample.pattern.structure.proxy.simple.SimpleServer;

import java.lang.reflect.Proxy;
import java.util.List;

import static android.content.Context.BIND_AUTO_CREATE;

/**
 * @author tangqi on 17-5-15.
 */
public class ProxyTest extends PatternTest {
    private Context context;
    private ISimpleAIDL mSimpleAIDL;

    @Override
    public void execute() {
        // simple
        SimpleClient simpleClient = new SimpleClient();
        ISimpleProxy simpleProxy = new SimpleServer(simpleClient);
        simpleProxy.shop();

        // dynamic
        SimpleClient simpleClient1 = new SimpleClient();
        DynamicProxy dynamicProxy = new DynamicProxy(simpleClient1);
        ClassLoader loader = simpleClient.getClass().getClassLoader();
        ISimpleProxy simpleProxy1 = (ISimpleProxy) Proxy
                .newProxyInstance(loader, new Class[]{ISimpleProxy.class}, dynamicProxy);
        simpleProxy1.shop();

        this.context = Global.getContext();
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent(context, SimpleService.class);
        intent.setAction("com.free.com.free.design.proxy.SimpleService");
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mSimpleAIDL = ISimpleAIDL.Stub.asInterface(service);
                try {
                    mSimpleAIDL.shop();
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);

//        final Intent intent = new Intent();
//        intent.setAction("com.example.user.firstapp.FIRST_SERVICE");
//        final Intent eintent = new Intent(createExplicitFromImplicitIntent(this, intent));
//        bindService(eintent, conn, Service.BIND_AUTO_CREATE);
    }

    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);

        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }

        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);

        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);

        // Set the component to be explicit
        explicitIntent.setComponent(component);

        return explicitIntent;
    }
}
