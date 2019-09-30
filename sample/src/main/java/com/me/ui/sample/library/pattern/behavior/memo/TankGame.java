package com.me.ui.sample.library.pattern.behavior.memo;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-24.
 */
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

    public Memo createMemo() {
        Memo memo = new Memo();
        memo.role = role;
        memo.life = life;
        memo.level = level;
        return memo;
    }

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
}
