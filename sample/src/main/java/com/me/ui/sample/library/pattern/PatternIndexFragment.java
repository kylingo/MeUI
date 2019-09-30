package com.me.ui.sample.library.pattern;

import com.me.ui.library.sample.SampleFragment;
import com.me.ui.sample.library.pattern.behavior.command.CommandTest;
import com.me.ui.sample.library.pattern.behavior.handler.HandlerTest;
import com.me.ui.sample.library.pattern.behavior.mediator.MediatorTest;
import com.me.ui.sample.library.pattern.behavior.memo.MemoTest;
import com.me.ui.sample.library.pattern.behavior.observer.ObserverTest;
import com.me.ui.sample.library.pattern.behavior.state.StateTest;
import com.me.ui.sample.library.pattern.behavior.strategy.StrategyTest;
import com.me.ui.sample.library.pattern.behavior.template.TemplateTest;
import com.me.ui.sample.library.pattern.behavior.visitor.VisitorTest;
import com.me.ui.sample.library.pattern.create.builder.BuilderTest;
import com.me.ui.sample.library.pattern.create.factory.FactoryTest;
import com.me.ui.sample.library.pattern.structure.bridge.BridgeTest;
import com.me.ui.sample.library.pattern.structure.decorator.DecoratorTest;
import com.me.ui.sample.library.pattern.structure.proxy.ProxyTest;

import java.util.List;

/**
 * @author kylingo
 * @since 2019/09/30 16:00
 */
public class PatternIndexFragment extends SampleFragment<PatternTest> {

    @Override
    protected void addItems(List<PatternTest> items) {
        items.add(new FactoryTest());
        items.add(new BuilderTest());
        items.add(new DecoratorTest());
        items.add(new ProxyTest());
        items.add(new BridgeTest());
        items.add(new StrategyTest());
        items.add(new TemplateTest());
        items.add(new ObserverTest());
        items.add(new HandlerTest());
        items.add(new CommandTest());
        items.add(new MemoTest());
        items.add(new StateTest());
        items.add(new VisitorTest());
        items.add(new MediatorTest());
    }

    @Override
    protected void onClickItem(PatternTest item) {
        item.execute();
    }
}
