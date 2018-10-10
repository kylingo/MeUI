package com.me.ui.sample.thirdparty.arouter.extend;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.facade.service.SerializationService;

import java.lang.reflect.Type;

/**
 * @author kylingo on 18/10/10
 */
// If you need to pass a custom object, Create a new class(Not the custom object class),implement the SerializationService, And use the @Route annotation annotation, E.g:
@Route(path = "/service/json")
public class JsonServiceImpl implements SerializationService {
    @Override
    public void init(Context context) {

    }

    @Override
    public <T> T json2Object(String text, Class<T> clazz) {
//        return JSON.parseObject(text, clazz);
        return null;
    }

    @Override
    public String object2Json(Object instance) {
//        return JSON.toJSONString(instance);
        return null;
    }

    @Override
    public <T> T parseObject(String input, Type clazz) {
        return null;
    }
}
