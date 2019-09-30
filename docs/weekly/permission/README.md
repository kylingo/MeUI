# Android动态权限分析
分析Android动态权限的申请方法，管理权限弹窗，兼容国产机型。

权限申请官方文档：[在运行时请求权限](https://developer.android.google.cn/training/permissions/requesting.html)

第三方博客介绍
- [Android 6.0 运行时权限管理最佳实践](http://blog.csdn.net/yanzhenjie1003/article/details/52503533)
- [Android权限适配全攻略](http://blog.csdn.net/u013085697/article/details/78182530)

## 权限申请流程
- 判断有没有权限
  - 有权限，直接调用具体功能
  - 没权限，申请权限，是否需要弹权限申请理由框(Rationale)
    - 是，用户第一次拒绝后，第二次提示，用户可以在系统弹框勾选不再询问，就不会弹窗权限申请理由对话框。
      - 是，则调用权限申请
      - 否，则回调权限拒绝
    - 否，直接申请权限，用户点击确认，则回调权限允许；用户点击取消，则回调权限拒绝。如果用户第一次拒绝了，且选择了不再提示，则系统不弹权限提示框，
    直接返回权限拒绝。

## 权限弹窗
结合以上逻辑，可能需要弹窗的地方：
- 权限解释弹窗，app弹窗，用户第一次拒绝后，再次申请权限前弹出，接口：shouldShowRequestPermissionRationale
- 权限申请弹窗，系统弹窗，各个系统都不一样，接口：requestPermissions
- 权限拒绝弹窗，app弹窗，跳转到权限设置页面，接口：onPermissionsDenied

## 代码相关
主要四个接口
- ContextCompat.checkSelfPermission()
- ActivityCompat.requestPermissions()
- ActivityCompat.shouldShowRequestPermissionRationale()
- onRequestPermissionsResult()

开源框架
- [Google EasyPermissions](https://github.com/googlesamples/easypermissions)
- [AndPermission](https://github.com/yanzhenjie/AndPermission)
- [PermissionsDispatcher](https://github.com/permissions-dispatcher/PermissionsDispatcher)
- [RxPermissions](https://github.com/tbruyelle/RxPermissions)


## 业务逻辑相关
- 点击入口处判断，没有权限不能使用具体功能或者跳转具体页面
- 页面入口处判断，Activity嵌套Fragment类，在Activity中加载Fragment时判断权限，没有权限则不加载Fragment。
  存在的问题，Activity没加载Fragment之前，背景为黑色（Activity默认的样式）。
- 页面内判断，需要加载权限后再执行页面逻辑。存在问题，页面布局加载和业务逻辑会正常进行，对代码改动量比较大。
- 部分权限拒绝，页面正常进入，功能缺失，例如小游戏录音权限，定位权限等。

## 兼容国产机型
部分国产手机，得的的是假权限，需要在使用需要权限的代码中try catch异常
或者在onPermissionsGranted权限获取后，执行try catch，如果有异常，返回onPermissionsDenied

使用AppOpsManager类，api大于19，检查应用是否有某个权限。

可能存在的问题
- ActivityCompat.shouldShowRequestPermissionRationale(Activity, String) 无法弹出权限申请对话框
- 明明授权失败，却回调的是权限申请成功方法
- 用户拒绝且不再提示，无法弹出权限申请对话框？
- 5.0系统权限申请问题
- 部分中国厂商生产手机（例如小米某型号）的Rationale功能，在第一次拒绝后，第二次申请时不会返回true，并且会回调申请失败，也就是说在第一次决绝后默认勾选了不再提示，所以建议一定使用SettingDialog：提示用户在系统设置中授权。
- 部分中国厂商生产手机（例如小米、华为某型号）在申请权限时，用户点击确定授权后，还是回调我们申请失败，这个时候其实我们是拥有权限的，所以我们可以在失败的方法中使用AppOpsManager进行权限判断，
- 部分中国厂商生产手机（例如vivo、pppo某型号）在用户允许权限，并且回调了权限授权陈功的方法，但是实际执行代码时并没有这个权限，建议开发者在回调成功的方法中也利用AppOpsManager判断下：
- 小米手机上请求权限的时候，会默认返回有权限，但是到设置中看的时候，还是属于询问的状态。这个要如何判断呢？
- 多个权限问题，只同意部分权限
- 部分手机没有权限也能进入业务，例如锤子手机，默认就有对应的权限，在调用具体的接口时，例如录音才会去申请权限。
- Native代码需要权限的，如何避免
