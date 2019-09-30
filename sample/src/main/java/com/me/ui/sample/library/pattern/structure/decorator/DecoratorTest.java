package com.me.ui.sample.library.pattern.structure.decorator;

import com.me.ui.sample.library.pattern.PatternTest;

/**
 * @author tangqi on 17-5-12.
 */
public class DecoratorTest extends PatternTest {

    public void execute() {
        Pom pom = new Pom();
        PomBlack pomBlack = new PomBlack(pom);
        PomWhite pomWhite = new PomWhite(pom);
        PomInvincible pomInvincible = new PomInvincible(pomBlack);
        pomBlack.draw();
        pomWhite.draw();
        pomInvincible.draw();
    }
}
