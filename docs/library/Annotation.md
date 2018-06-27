# 简易版ButterKnife
[Github地址](https://github.com/android-sample/AnnotationSample)

通过注解的方式，简单实现视图绑定，事件点击功能，主要包括4个模块
- app：示例代码，依赖以下模块
- bindview：``android-library``，申明外部API接口
- bindview-annotation：``java-library``，申明注解
- bindview-compile：``java-library``，注解处理器，动态生成代码

## 参考文献
- [Java&Android 基础知识梳理(1) - 注解](https://www.jianshu.com/p/2585d2a7cd97)
- [Android 利用 APT 技术在编译期生成代码](https://brucezz.itscoder.com/use-apt-in-android)

## 示例代码
```java
public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_hello)
    TextView tvHello;

    @BindView(R.id.btn_test)
    Button btnTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewFinder.inject(this);
    }

    @OnClick(R.id.tv_hello)
    public void onClickHello() {
        Toast.makeText(this, "onClickHello", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.btn_test)
    public void onClickTest() {
        Toast.makeText(this, "onClickTest", Toast.LENGTH_SHORT).show();
    }
}
```

## 申明注解
```java
// 绑定视图
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface BindView {
    int value();
}

// 点击事件
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface OnClick {
    int[] value();
}
```

## 外部API接口
新建一个``bindview``的``android-library``，用来提供对外接口

Provider类和二个实现类
```java
public interface Provider {

    Context getContext(Object source);

    View findView(Object source, int resId);
}

public class ActivityProvider implements Provider {

    @Override
    public Context getContext(Object source) {
        return (Activity) source;
    }

    @Override
    public View findView(Object source, int resId) {
        return ((Activity) source).findViewById(resId);
    }
}

public class ViewProvider implements Provider {

    @Override
    public Context getContext(Object source) {
        return ((View) source).getContext();
    }

    @Override
    public View findView(Object source, int resId) {
        return ((View) source).findViewById(resId);
    }
}
```

定义Finder接口，后面我们会根据被@BindView注解所修饰的变量所在类（host）来生成不同的Finder实现类，
而这个判断的过程并不需要使用者去关心，而是由框架的实现者在编译器时就处理好的了。
```java
public interface Finder<T> {
    /**
     * @param host     持有注解的类
     * @param source   调用方法的所在的类
     * @param provider 执行方法的类
     */
    void inject(T host, Object source, Provider provider);
}
```

ViewFinder是ViewFinder框架的使用者唯一需要关心的类，
当在Activity/Fragment/View中调用了inject方法时，会经过一下几个过程：
- 获得调用inject方法所在类的类名xxx，也就是注解类。
- 获得属于该类的xxx$$Finder，调用xxx$$Finder的inject方法。
```java
public class ViewFinder {

    private static final ActivityProvider PROVIDER_ACTIVITY = new ActivityProvider();
    private static final ViewProvider PROVIDER_VIEW = new ViewProvider();

    private static final Map<String, Finder> FINDER_MAP = new HashMap<>();

    public static void inject(Activity activity) {
        inject(activity, activity, PROVIDER_ACTIVITY);
    }

    public static void inject(View view) {
        inject(view, view);
    }

    public static void inject(Object host, View view) {
        // for fragment
        inject(host, view, PROVIDER_VIEW);
    }

    public static void inject(Object host, Object source, Provider provider) {
        String className = host.getClass().getName();
        try {
            Finder finder = FINDER_MAP.get(className);
            if (finder == null) {
                Class<?> finderClass = Class.forName(className + "$$Finder");
                finder = (Finder) finderClass.newInstance();
                FINDER_MAP.put(className, finder);
            }
            finder.inject(host, source, provider);
        } catch (Exception e) {
            throw new RuntimeException("Unable to inject for " + className, e);
        }
    }
}
```
那么这上面所有的xxx$$Finder类，到底是什么时候产生的呢，它们的inject方法里面又做了什么呢，
这就需要涉及到下面注解处理器的创建。

## 创建注解处理器
创建``bindview-compile``的``java-library``

在build.gradle中导入下面需要的类
```
apply plugin: 'java-library'

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])

    compile project(':bindview-annotation')
    compile 'com.squareup:javapoet:1.9.0'
    compile 'com.google.auto.service:auto-service:1.0-rc3'
}

sourceCompatibility = "1.7"
targetCompatibility = "1.7"
```

TypeUtil定义了需要用到的类的包名和类名
```java
public class TypeUtil {
    public static final ClassName ANDROID_VIEW = ClassName.get("android.view", "View");
    public static final ClassName ANDROID_ON_CLICK_LISTENER = ClassName.get("android.view", "View", "OnClickListener");
    public static final ClassName FINDER = ClassName.get("com.kylingo.annotation", "Finder");
    public static final ClassName PROVIDER = ClassName.get("com.kylingo.annotation.provider", "Provider");
}
```

每个BindViewField和注解类中使用了@BindView修饰的View是一一对应的关系。
```java
public class BindViewField {

    private VariableElement mFieldElement;
    private int mResId;
    private String mInitValue;

    public BindViewField(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.FIELD) { //判断被注解修饰的是否是变量.
            throw new IllegalArgumentException(String.format("Only fields can be annotated with @%s", BindView.class.getSimpleName()));
        }
        mFieldElement = (VariableElement) element; //获得被修饰变量.
        BindView bindView = mFieldElement.getAnnotation(BindView.class); //获得被修饰变量的注解.
        mResId = bindView.value(); //获得注解的值.
    }

    /**
     * @return 被修饰变量的名字.
     */
    public Name getFieldName() {
        return mFieldElement.getSimpleName();
    }

    /**
     * @return 被修饰变量的注解的值, 也就是它的id.
     */
    public int getResId() {
        return mResId;
    }

    /**
     * @return 被修饰变量的注解的值.
     */
    public String getInitValue() {
        return mInitValue;
    }

    /**
     * @return 被修饰变量的类型.
     */
    public TypeMirror getFieldType() {
        return mFieldElement.asType();
    }
}
```

每个OnClickMethod和注解类中使用了@OnClick修饰的View是一一对应的关系。
```java
public class OnClickMethod {

    private ExecutableElement methodElement;
    private Name mMethodName;
    public int[] ids;

    public OnClickMethod(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.METHOD) {
            throw new IllegalArgumentException(
                    String.format("Only methods can be annotated with @%s", OnClick.class.getSimpleName()));
        }
        this.methodElement = (ExecutableElement) element;
        this.ids = methodElement.getAnnotation(OnClick.class).value();

        if (ids == null) {
            throw new IllegalArgumentException(String.format("Must set valid ids for @%s", OnClick.class.getSimpleName()));
        } else {
            for (int id : ids) {
                if (id < 0) {
                    throw new IllegalArgumentException(String.format("Must set valid id for @%s", OnClick.class.getSimpleName()));
                }
            }
        }

        this.mMethodName = methodElement.getSimpleName();
        // method params count must equals 0
        List<? extends VariableElement> parameters = methodElement.getParameters();
        if (parameters.size() > 0) {
            throw new IllegalArgumentException(
                    String.format("The method annotated with @%s must have no parameters", OnClick.class.getSimpleName()));
        }
    }

    public Name getMethodName() {
        return mMethodName;
    }
}
```

AnnotatedClass封装了添加被修饰注解element，通过element列表生成JavaFile这两个过程，AnnotatedClass和注解类是一一对应的关系：
```java
public class AnnotatedClass {
    public TypeElement mClassElement;
    public List<BindViewField> mFields;
    public List<OnClickMethod> mMethods;
    public Elements mElementUtils;

    public AnnotatedClass(TypeElement classElement, Elements elementUtils) {
        this.mClassElement = classElement;
        this.mFields = new ArrayList<>();
        this.mMethods = new ArrayList<>();
        this.mElementUtils = elementUtils;
    }

    public String getFullClassName() {
        return mClassElement.getQualifiedName().toString();
    }

    public void addField(BindViewField bindViewField) {
        mFields.add(bindViewField);
    }

    public void addMethod(OnClickMethod method) {
        mMethods.add(method);
    }

    public JavaFile generateFinder() {
        // 生成inject方法的参数.
        MethodSpec.Builder methodBuilder = MethodSpec
                .methodBuilder("inject") //方法名.
                .addModifiers(Modifier.PUBLIC) //访问权限.
                .addAnnotation(Override.class) //注解.
                .addParameter(TypeName.get(mClassElement.asType()), "host", Modifier.FINAL) //参数.
                .addParameter(TypeName.OBJECT, "source")
                .addParameter(TypeUtil.PROVIDER, "provider");

        // 在inject方法中,生成重复的findViewById(R.id.xxx)的语句.
        for (BindViewField field : mFields) {
            methodBuilder.addStatement(
                    "host.$N = ($T)(provider.findView(source, $L))",
                    field.getFieldName(),
                    ClassName.get(field.getFieldType()),
                    field.getResId());
        }

        if (mMethods.size() > 0) {
            methodBuilder.addStatement("$T listener", TypeUtil.ANDROID_ON_CLICK_LISTENER);
        }
        for (OnClickMethod method : mMethods) {
            // declare OnClickListener anonymous class
            TypeSpec listener = TypeSpec.anonymousClassBuilder("")
                    .addSuperinterface(TypeUtil.ANDROID_ON_CLICK_LISTENER)
                    .addMethod(MethodSpec.methodBuilder("onClick")
                            .addAnnotation(Override.class)
                            .addModifiers(Modifier.PUBLIC)
                            .returns(TypeName.VOID)
                            .addParameter(TypeUtil.ANDROID_VIEW, "view")
                            .addStatement("host.$N()", method.getMethodName())
                            .build())
                    .build();
            methodBuilder.addStatement("listener = $L ", listener);
            for (int id : method.ids) {
                // set listeners
                methodBuilder.addStatement("provider.findView(source, $L).setOnClickListener(listener)", id);
            }
        }

        // 生成Host$$Finder类.
        TypeSpec finderClass = TypeSpec
                .classBuilder(mClassElement.getSimpleName() + "$$Finder")
                .addModifiers(Modifier.PUBLIC)
                .addSuperinterface(ParameterizedTypeName.get(TypeUtil.FINDER, TypeName.get(mClassElement.asType())))
                .addMethod(methodBuilder.build())
                .build();

        // 获得包名.
        String packageName = mElementUtils.getPackageOf(mClassElement).getQualifiedName().toString();
        return JavaFile.builder(packageName, finderClass).build();
    }
}
```

在做完前面所有的准备工作之后，后面的事情就很清楚了：
- 编译时，系统会调用所有AbstractProcessor子类的process方法，也就是调用我们的ViewFinderProcess的类。
- 在ViewFinderProcess中，我们获得工程下所有被@BindView注解所修饰的View。
- 遍历这些被@BindView修饰的View变量，获得它们被声明时所在的类，首先判断是否已经为所在的类生成了对应的AnnotatedClass，
如果没有，那么生成一个，并将View封装成BindViewField添加进入AnnotatedClass的列表，
反之添加即可，所有的AnnotatedClass被保存在一个map当中。
- 当遍历完所有被注解修饰的View后，开始遍历之前生成的AnnotatedClass，每个AnnotatedClass会生成一个对应的$$Finder类。
- 如果我们在n个类中使用了@BindView来修饰里面的View，那么我们最终会得到n个$$Finder类，
并且无论我们最终有没有在这n个类中调用ViewFinder.inject方法，都会生成这n个类；
而如果我们调用了ViewFinder.inject，那么最终就会通过反射来实例化它对应的$$Finder类，
通过调用inject方法来给被它里面被@BindView所修饰的View执行findViewById操作。
```java
@AutoService(Processor.class)
public class ViewFinderProcess extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElementUtils;
    private Messager mMessager;

    private Map<String, AnnotatedClass> mAnnotatedClassMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(BindView.class.getCanonicalName());
        types.add(OnClick.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        mAnnotatedClassMap.clear();
        try {
            processBindView(roundEnv);
            processOnClick(roundEnv);
        } catch (IllegalArgumentException e) {
            return true;
        }
        for (AnnotatedClass annotatedClass : mAnnotatedClassMap.values()) { //遍历所有要生成$$Finder的类.
            try {
                info("Generating file for %s", annotatedClass.getFullClassName());
                annotatedClass.generateFinder().writeTo(mFiler); //一次性生成.
            } catch (IOException e) {
                error("Generate file failed, reason: %s", e.getMessage());
                return true;
            }
        }
        return true;
    }

    private void processBindView(RoundEnvironment roundEnv) throws IllegalArgumentException {
        for (Element element : roundEnv.getElementsAnnotatedWith(BindView.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            BindViewField field = new BindViewField(element);
            annotatedClass.addField(field);
        }
    }

    private void processOnClick(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(OnClick.class)) {
            AnnotatedClass annotatedClass = getAnnotatedClass(element);
            OnClickMethod method = new OnClickMethod(element);
            annotatedClass.addMethod(method);
        }
    }

    private AnnotatedClass getAnnotatedClass(Element element) {
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = classElement.getQualifiedName().toString();
        AnnotatedClass annotatedClass = mAnnotatedClassMap.get(fullClassName);
        if (annotatedClass == null) {
            annotatedClass = new AnnotatedClass(classElement, mElementUtils);
            mAnnotatedClassMap.put(fullClassName, annotatedClass);
        }
        return annotatedClass;
    }

    private void error(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        mMessager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
```