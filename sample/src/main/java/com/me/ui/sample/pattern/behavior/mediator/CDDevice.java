package com.me.ui.sample.pattern.behavior.mediator;

/**
 * @author tangqi on 17-5-24.
 */
public class CDDevice extends Colleague {

    private String data;

    public CDDevice(Mediator mediator) {
        super(mediator);
    }

    public void load() {
        data = "大话西游, 一生所爱";
        mediator.change(this);
    }

    public String getData() {
        return data;
    }
}
