package com.me.ui.sample.pattern.behavior.visitor;

/**
 * @author tangqi on 17-5-24.
 */
public interface Visitor {
    void visit(Engineer engineer);
    void visit(Manager manager);
}
