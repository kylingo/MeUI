package com.me.ui.sample.library.pattern.behavior.command;

/**
 * @author tangqi on 17-5-24.
 */
public class LeftCommand implements Command {

    private GameMachine gameMachine;

    public LeftCommand(GameMachine gameMachine) {
        this.gameMachine = gameMachine;
    }

    @Override
    public void execute() {
        gameMachine.toleft();
    }
}
