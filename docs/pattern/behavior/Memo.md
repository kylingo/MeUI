# 备忘录模式
不破坏封闭的情况下，捕获一个对象的内部状态并保存，再此启动时可以恢复到原先保存的状态。

## 使用场景
- 需要保存一个对象在某一时刻的状态或部分状态
- 一个对象不希望外部直接访问器内部状态，通过中间对象可以间接访问其内部状态

## UML图
- Originator， 创建一个备忘录，可以记录、恢复自身的内部状态
- Memo, 备忘录角色，用于存储Originator的内部状态，防止Originator以外的对象访问Memo
- Caretaker, 负责存储和获取备忘录，不进行其他操作。

## 实例
Android的onSaveInstanceState和onRestoreInstanceState

## 实战
```Java
// 坦克大战游戏
public class TankGame {
    // 角色
    private String role;

    // 生命值
    private int life;

    // 关卡
    private int level;

    public TankGame() {
        init();
    }

    public void init() {
        level = 1;
        life = 3;
        role = "舒克";
    }

    public void play() {
        play(level);
    }

    public void play(final int level) {
        this.level = level;
        LogUtils.d(TankGame.class, "开始第" + level + "关");
    }

    public void quit() {
        LogUtils.d(TankGame.class, "退出游戏前属性：" + toString());
        LogUtils.d(TankGame.class, "退出游戏");
    }

    // 保存进度
    public Memo createMemo() {
        Memo memo = new Memo();
        memo.role = role;
        memo.life = life;
        memo.level = level;
        return memo;
    }

    // 恢复进度
    public void restore(Memo memo) {
        this.role = memo.role;
        this.life = memo.life;
        this.level = memo.level;
        LogUtils.d(TankGame.class, "恢复游戏属性：" + toString());
    }

    @Override
    public String toString() {
        return "TankGame{" +
                "role='" + role + '\'' +
                ", life=" + life +
                ", level=" + level +
                '}';
    }

// 备忘录实体类
public class Memo {
    public String role;
    public int life;
    public int level;
}

// 存储备忘录，需要持久化
public class Caretaker {
    private Memo memo;


    public Memo getMemo() {
        return memo;
    }

    public void setMemo(Memo memo) {
        this.memo = memo;
    }
}

// 测试代码
public class MemoTest {

    public static void execute() {
        // 构建游戏对象
        TankGame tankGame = new TankGame();
        tankGame.play(10);

        // 存档
        Caretaker caretaker = new Caretaker();
        Memo memo = tankGame.createMemo();
        caretaker.setMemo(memo);
        tankGame.quit();

        // 恢复游戏
        TankGame newGame = new TankGame();
        newGame.restore(caretaker.getMemo());
        newGame.play();
    }
}

// 测试结果
05-24 11:24:58.167 24647-24647/? D/TankGame: 开始第10关
05-24 11:24:58.168 24647-24647/? D/TankGame: 退出游戏前属性：TankGame{role='舒克', life=3, level=10}
05-24 11:24:58.168 24647-24647/? D/TankGame: 退出游戏
05-24 11:24:58.168 24647-24647/? D/TankGame: 恢复游戏属性：TankGame{role='舒克', life=3, level=10}
05-24 11:24:58.168 24647-24647/? D/TankGame: 开始第10关
```
