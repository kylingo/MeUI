# 上传bintray方法

## 项目中步骤
根目录build.gradle添加
```
dependencies {
    classpath 'com.novoda:bintray-release:0.5.0'
}
```

库目录build.gradle添加
```
apply plugin: 'com.novoda.bintray-release'

android {
    ...
    
    lintOptions {
        // 设置编译的lint开关，程序在build的时候，会执行lint检查，有任何的错误或者警告提示，都会终止构建
        abortOnError false
    }
}

publish {
    userOrg = 'kylingo'
    groupId = 'com.github.kylingo'
    artifactId = 'meui'
    publishVersion = '1.0.0'
    desc = 'Android common ui library'
    website = 'https://github.com/kylingo/MeUI'
}
```

## 发布
```
./gradlew clean build bintrayUpload -PbintrayUser=[username] -PbintrayKey=[key] -PdryRun=false
```

## Issue
### Java版本问题
```
A problem occurred configuring project ':library'.
> Failed to notify project evaluation listener.
   > Could not initialize class com.android.sdklib.repository.AndroidSdkHandler

去掉Java9环境
```

### JDK需要1.8以上
```
A problem occurred evaluating project ':library'.
> java.lang.UnsupportedClassVersionError: com/android/build/gradle/LibraryPlugin : Unsupported major.minor version 52.0
```

### gradle版本
```
A problem occurred configuring project ':library'.
> No such property: FOR_RUNTIME for class: org.gradle.api.attributes.Usage
```