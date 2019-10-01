package com.me.ui.sample.pattern.behavior.visitor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tangqi on 17-5-24.
 */
public class BusinessReport {
    private List<Staff> staffs = new ArrayList<>();

    public void collect() {
        staffs.add(new Manager("李经理"));
        staffs.add(new Engineer("小马"));
        staffs.add(new Engineer("小张"));
        staffs.add(new Engineer("小刘"));
    }

    public void show(Visitor visitor) {
        for(Staff staff : staffs) {
            staff.accept(visitor);
        }
    }
}
