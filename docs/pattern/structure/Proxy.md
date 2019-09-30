# 代理模式
为其他对象提供一种代理以控制这个对象的访问。

## 使用场景
不想直接访问，或者访问困难时，使用代理模式间接访问。为了保证客户端使用透明性，委托对象和代理对象实现相同的接口。

## UML类图
Client ->ProxySubject -> RealSubject -> Subject

## 实例
Android 的Binder，Service, AIDL

## 实战
### 简单代理模式
代理模式的简单实现，和装饰着模式一样
```Java
// 接口定义
public interface ISimpleProxy {
    void shop();
}

// 客户类
public class SimpleClient implements ISimpleProxy {
    @Override
    public void shop() {
        LogUtils.d(SimpleClient.class, "I'm client want to shop");
    }
}

// 代理类
public class SimpleServer implements ISimpleProxy {
    private ISimpleProxy simpleProxy;

    public SimpleServer(ISimpleProxy simpleProxy) {
        this.simpleProxy = simpleProxy;
    }

    @Override
    public void shop() {
        simpleProxy.shop();
        LogUtils.d(SimpleServer.class, "I'm server support shop service");
    }
}

// 测试代码
public class ProxyTest {
    public static void execute() {
        SimpleClient simpleClient = new SimpleClient();
        ISimpleProxy simpleProxy = new SimpleServer(simpleClient);
        simpleProxy.shop();
    }
}

// 运行结果
05-15 10:16:25.739 20056-20056/com.free.com.free.design D/SimpleClient: I'm client want to shop
05-15 10:16:25.739 20056-20056/com.free.com.free.design D/SimpleServer: I'm server support shop service
```

### 动态代理模式
```Java
public class DynamicProxy implements InvocationHandler {
    private Object obj;

    public DynamicProxy(Object obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(obj, args);
    }
}

public class ProxyTest {
    public static void execute() {
        SimpleClient simpleClient1 = new SimpleClient();
        DynamicProxy dynamicProxy = new DynamicProxy(simpleClient1);
        ClassLoader loader = simpleClient.getClass().getClassLoader();
        ISimpleProxy simpleProxy1 = (ISimpleProxy) Proxy
                .newProxyInstance(loader, new Class[]{ISimpleProxy.class}, dynamicProxy);
        simpleProxy1.shop();
    }
}

// 运行结果
05-15 10:28:46.737 30686-30686/com.free.com.free.design D/SimpleClient: I‘m client want to shop
```

### 远程服务
```Java
// 定义AIDL
interface ISimpleAIDL {
    void shop();
}

// 远程Binder
public class SimpleBinder extends ISimpleAIDL.Stub {
    @Override
    public void shop() throws RemoteException {
        LogUtils.d(SimpleBinder.class, "shop");
    }
}

// 远程service
public class SimpleService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        LogUtils.d(SimpleService.class, "onBind");
        return new SimpleBinder();
    }
}

// 测试代码
private void bindService() {
    Intent intent = new Intent(this, SimpleService.class);
    intent.setAction("com.free.com.free.design.proxy.SimpleService");
    bindService(intent, new ServiceConnection() {
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
}

// 测试结果
05-15 11:08:02.916 15373-15373/? D/SimpleService: onBind
05-15 11:08:02.922 15373-15384/? D/SimpleBinder: shop
```
