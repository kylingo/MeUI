package com.me.ui.sample.pattern.behavior.handler;

import com.me.ui.sample.pattern.PatternTest;

/**
 * @author studiotang on 17/5/23
 */
public class HandlerTest extends PatternTest {

    public void execute() {
        GroupLeader groupLeader = new GroupLeader();
        Manager manager = new Manager();
        Boss boss = new Boss();

        groupLeader.nextHandler = manager;
        manager.nextHandler = boss;

        groupLeader.handleRequest(800);
        groupLeader.handleRequest(2000);
        groupLeader.handleRequest(10000);
    }
}
