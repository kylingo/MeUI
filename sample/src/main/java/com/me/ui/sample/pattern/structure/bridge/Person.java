package com.me.ui.sample.pattern.structure.bridge;

/**
 * @author tangqi on 17-5-15.
 */
public abstract class Person {
    private Work work;

    public Person(Work work) {
        this.work = work;
        createPerson();
    }

    public void work() {
        work.doSomeThing();
    }

    public abstract void createPerson();
}
