package com.me.ui.sample.library.pattern.behavior.command;

import com.me.ui.sample.library.pattern.PatternTest;

/**
 * @author tangqi on 17-5-24.
 */
public class CommandTest extends PatternTest {

    public void execute() {
        // 新建一个游戏
        GameMachine gameMachine = new GameMachine();

        // 游戏的四种命令
        LeftCommand leftCommand = new LeftCommand(gameMachine);
        RightCommand rightCommand = new RightCommand(gameMachine);
        UpCommand upCommand = new UpCommand(gameMachine);
        DownCommand downCommand = new DownCommand(gameMachine);

        // 游戏手柄
        Buttons buttons = new Buttons();
        buttons.setLeftCommand(leftCommand);
        buttons.setRightCommand(rightCommand);
        buttons.setUpCommand(upCommand);
        buttons.setDownCommand(downCommand);

        // 执行手柄动作
        buttons.toLeft();
        buttons.toRight();
        buttons.toDown();
        buttons.toUp();
    }
}
