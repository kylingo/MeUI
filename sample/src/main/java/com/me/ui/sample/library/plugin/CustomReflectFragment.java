package com.me.ui.sample.library.plugin;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.me.ui.library.sample.AbstractSampleFragment;
import com.me.ui.sample.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 类加载器用反射的方式调用api
 * Author:  Kevin.Tang
 * Date:    18/1/15 下午12:15
 */
public class CustomReflectFragment extends AbstractSampleFragment {

    private static final String TAG = "CustomReflectFragment";

    private Object pluginObject;

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_custom_plugin;
    }

    @Override
    protected void initView(View view) {
        loadPlugin();

        view.findViewById(R.id.btn_plugin_init).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokePlugin("init", getActivity());
            }
        });

        view.findViewById(R.id.btn_plugin_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokePlugin("showToast");
            }
        });

        view.findViewById(R.id.btn_plugin_operation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokePlugin("operation");
            }
        });

        view.findViewById(R.id.btn_plugin_destroy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invokePlugin("destroy");
            }
        });
    }

    private void invokePlugin(String methodName) {
        try {
            Method method = pluginObject.getClass().getMethod(methodName);
            method.invoke(pluginObject);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void invokePlugin(String methodName, Activity activity) {
        try {
            Method method = pluginObject.getClass().getMethod(methodName, Activity.class);
            method.invoke(pluginObject, activity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void loadPlugin() {
        Class<?> libProviderClazz;
        libProviderClazz = PluginLoader.loadDexClass(getContext(), PluginLoader.PLUGIN_CLASS, PluginLoader.PLUGIN_IMPL);
        try {
            Log.i(TAG, "libProviderClazz:" + libProviderClazz);
            pluginObject = libProviderClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
