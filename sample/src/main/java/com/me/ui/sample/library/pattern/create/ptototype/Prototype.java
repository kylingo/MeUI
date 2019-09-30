package com.me.ui.sample.library.pattern.create.ptototype;

import java.util.ArrayList;

/**
 * @author studiotang on 17/5/11
 */
public class Prototype implements Cloneable {

    private ArrayList<String> list = new ArrayList<>();

    @SuppressWarnings("unchecked")
    @Override
    protected Object clone() throws CloneNotSupportedException {
        Prototype prototype = null;
        try {
            prototype = (Prototype) super.clone();

            // 深拷贝
            prototype.list = (ArrayList<String>) this.list.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return prototype;
    }
}
