package com.me.ui.sample.pattern.behavior.strategy;

import com.me.ui.sample.pattern.PatternTest;

/**
 * @author tangqi on 17-5-15.
 */
public class StrategyTest extends PatternTest {

    public void execute() {
        Travel travel = new Travel();
        travel.setTrafficStrategy(new AircraftStrategy());
        travel.run();
    }
}
