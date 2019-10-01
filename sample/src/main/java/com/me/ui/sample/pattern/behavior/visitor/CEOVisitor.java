package com.me.ui.sample.pattern.behavior.visitor;

import com.me.ui.util.LogUtils;

/**
 * @author tangqi on 17-5-24.
 */
public class CEOVisitor implements Visitor {
    @Override
    public void visit(Engineer engineer) {
        LogUtils.d(CEOVisitor.class, "工程师：" + engineer.name + ", kpi:" + engineer.kpi);
    }

    @Override
    public void visit(Manager manager) {
        LogUtils.d(CEOVisitor.class, "经理：" + manager.name + ", kpi:" + manager.kpi);
    }
}
