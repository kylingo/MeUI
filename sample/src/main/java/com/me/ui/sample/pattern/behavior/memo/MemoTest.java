package com.me.ui.sample.pattern.behavior.memo;

import com.me.ui.sample.pattern.PatternTest;

/**
 * @author tangqi on 17-5-24.
 */
public class MemoTest extends PatternTest {

    public void execute() {
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
