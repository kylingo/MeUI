package com.me.ui.sample.pattern.behavior.command;

/**
 * @author tangqi on 17-5-24.
 */
public class Buttons {
    private LeftCommand leftCommand;
    private RightCommand rightCommand;
    private UpCommand upCommand;
    private DownCommand downCommand;

    public void setLeftCommand(LeftCommand leftCommand) {
        this.leftCommand = leftCommand;
    }

    public void setRightCommand(RightCommand rightCommand) {
        this.rightCommand = rightCommand;
    }

    public void setUpCommand(UpCommand upCommand) {
        this.upCommand = upCommand;
    }

    public void setDownCommand(DownCommand downCommand) {
        this.downCommand = downCommand;
    }

    public void toLeft() {
        leftCommand.execute();
    }

    public void toRight() {
        rightCommand.execute();
    }

    public void toUp() {
        upCommand.execute();
    }

    public void toDown() {
        downCommand.execute();
    }
}
