package com.me.ui.sample.pattern.behavior.visitor;

import com.me.ui.sample.pattern.PatternTest;
import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-24.
 */
public class VisitorTest extends PatternTest {

    public void execute() {
        BusinessReport businessReport = new BusinessReport();
        businessReport.collect();

        LogUtils.d(VisitorTest.class, "-------------给CEO看的报表--------------");
        businessReport.show(new CEOVisitor());

        LogUtils.d(VisitorTest.class, "-------------给CTO看的报表--------------");
        businessReport.show(new CTOVisitor());
    }
}
