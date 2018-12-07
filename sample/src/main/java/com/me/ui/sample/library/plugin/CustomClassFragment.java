package com.me.ui.sample.library.plugin;

import android.util.Log;
import android.view.View;

import com.kylingo.plugin.custom.IPlugin;
import com.me.ui.library.sample.AbstractSampleFragment;
import com.me.ui.sample.R;

/**
 * 类加载器加载apk
 * Author:  Kevin.Tang
 * Date:    18/1/18 上午12:52
 */
public class CustomClassFragment extends AbstractSampleFragment {

    private static final String TAG = "CustomClassFragment";

    private IPlugin iPlugin;

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
                if (iPlugin != null) {
                    iPlugin.init(getActivity());
                } else {
                    Log.e(TAG, "lib null,load class error!!!!");
                }
            }
        });

        view.findViewById(R.id.btn_plugin_toast).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlugin != null) {
                    iPlugin.showToast();
                } else {
                    Log.e(TAG, "lib null,load class error!!!!");
                }
            }
        });

        view.findViewById(R.id.btn_plugin_operation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlugin != null) {
                    iPlugin.operation();
                } else {
                    Log.e(TAG, "lib null,load class error!!!!");
                }
            }
        });

        view.findViewById(R.id.btn_plugin_destroy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (iPlugin != null) {
                    iPlugin.destroy();
                } else {
                    Log.e(TAG, "lib null,load class error!!!!");
                }
            }
        });
    }

    private void loadPlugin() {
        Class<?> libProviderClazz;
        libProviderClazz = PluginLoader.loadDexClass(getContext(), PluginLoader.PLUGIN_CLASS, PluginLoader.PLUGIN_IMPL);
        try {
            iPlugin = (IPlugin) libProviderClazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
