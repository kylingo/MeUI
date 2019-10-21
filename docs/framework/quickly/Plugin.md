## 插件化

## 类加载器
- DexClassLoader ：可以加载文件系统上的jar、dex、apk
- PathClassLoader ：可以加载/data/app目录下的apk，这也意味着，它只能加载已经安装的apk
- URLClassLoader ：可以加载java中的jar，但是由于dalvik不能直接识别jar，所以此方法在android中无法使用，尽管还有这个类

## 自定义
```
// 生成plugin.jar
jar -cvf plugin.jar com/kylingo/plugin/custom/PluginImpl.class
dx --dex --no-strict --output=plugin_dex.jar plugin.jar

// 生成iplugin.jar
jar -cvf iplugin.jar com/kylingo/plugin/custom/IPlugin.class

注意：dx命令在sdk/build-tools/26.0.0目录下
```

## 常见框架分析
- [滴滴VirtualAPK](https://github.com/didi/VirtualAPK)
- [携程DynamicAPK](https://github.com/CtripMobile/DynamicAPK)
- [奇虎DroidPlugin](https://github.com/DroidPluginTeam/DroidPlugin)


## 参考资料
- https://www.evernote.com/shard/s331/sh/6db20b46-e21c-4fda-bdbd-cd0f30343208/01e418f73e836120
- http://www.cnblogs.com/ligreat/p/6437564.html
- http://www.androidblog.cn/index.php/Index/detail/id/16#