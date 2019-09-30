/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.letvsz.code.style;

/**
 * 类的大体描述放在这里。
 *
 * @author 作者名
 * @since 2013-XX-XX
 */
@SuppressWarnings("unused")
public class SampleCode {

    /** 公有的常量注释 */
    public static final String ACTION_MAIN = "android.intent.action.MAIN";

    /** 私有的常量注释（同类型的常量可以分块并紧凑定义） */
    private static final int MSG_AUTH_NONE    = 0;
    private static final int MSG_AUTH_SUCCESS = 1;
    private static final int MSG_AUTH_FAILED  = 2;

    /** 保护的成员变量注释 */
    protected Object mObject0;

    /** 私有的成员变量 mObject1 注释（同类型的成员变量可以分块并紧凑定义） */
    private Object mObject1;
    /** 私有的成员变量 mObject2 注释 */
    private Object mObject2;
    /** 私有的成员变量 mObject3 注释 */
    private Object mObject3;

    /**
    * 对于注释多于一行的，采用这种方式来
    * 定义该变量
    */
    private Object mObject4;

    /**
    * 公有方法描述...
    *
    * @param param1  参数1描述...
    * @param param2  参数2描述...
    * @param paramXX 参数XX描述... （注意：请将参数、描述都对齐）
    */
    public void doSomething(int param1, float param2, String paramXX) {
      // 以下注释标签可以通过Eclipse内置的Task插件看到
      // TODO  使用TODO来标记代码，说明标识处有功能代码待编写
      // FIXME 使用FIXME来标记代码，说明标识处代码需要修正，甚至代码是
      //       错误的，不能工作，需要修复
      // XXX   使用XXX来标记代码，说明标识处代码虽然实现了功能，但是实现
      //       的方法有待商榷，希望将来能改进
    }

    /**
    * 保护方法描述...
    */
    @Deprecated
    protected void doSomething() {
      // ...implementation
    }

    /**
    * 私有方法描述...
    *
    * @param param1  参数1描述...
    * @param param2  参数2描述...
    */
    private void doSomethingInternal(int param1, float param2) {
      // ...implementation
    }

    /**
    * 条件表达式原则。
    */
    private void conditionFun() {
      boolean condition1 = true;
      boolean condition2 = false;
      boolean condition3 = false;
      boolean condition4 = false;
      boolean condition5 = false;
      boolean condition6 = false;

      // 原则： 1. 所有 if 语句必须用 {} 包括起来，即便只有一句，禁止使用不带{}的语句
      //       2. 在含有多种运算符的表达式中，使用圆括号来避免运算符优先级问题
      //       3. 判断条件很多时，请将其它条件换行
      if (condition1) {
          // ...implementation
      }

      if (condition1) {
          // ...implementation
      } else {
          // ...implementation
      }

      if (condition1)          /* 禁止使用不带{}的语句 */
          condition3 = true;

      if ((condition1 == condition2)
              || (condition3 == condition4)
              || (condition5 == condition6)) {

      }
    }

    /**
    * Switch语句原则。
    */
    private void switchFun() {

      // 原则： 1. switch 语句中，break 与下一条 case 之间，空一行
      //       2. 对于不需要 break 语句的，请使用 /* Falls through */来标注
      //       3. 请默认写上 default 语句，保持完整性
      int code = MSG_AUTH_SUCCESS;
      switch (code) {
      case MSG_AUTH_SUCCESS:
          break;

      case MSG_AUTH_FAILED:
          break;

      case MSG_AUTH_NONE:
          /* Falls through */
      default:
          break;
      }
    }

    /**
    * 循环表达式。
    */
    private void circulationFun() {

      // 原则： 1. 尽量使用for each语句代替原始的for语句
      //       2. 循环中必须有终止循环的条件或语句，避免死循环
      //       3. 循环要尽可能的短, 把长循环的内容抽取到方法中去
      //       4. 嵌套层数不应超过3层, 要让循环清晰可读

      int array[] = { 1, 2, 3, 4, 5 };
      for (int data : array) {
          // ...implementation
      }

      int length = array.length;
      for (int ix = 0; ix < length; ix++) {
          // ...implementation
      }

      boolean condition = true;
      while (condition) {
          // ...implementation
      }

      do {
          // ...implementation
      } while (condition);
    }

    /**
    * 异常捕获原则。
    */
    private void exceptionFun() {

      // 原则： 1. 捕捉异常是为了处理它，通常在异常catch块中输出异常信息。
      //       2. 资源释放的工作，可以放到 finally 块部分去做。如关闭 Cursor 等。
      try {
          // ...implementation
      } catch (Exception e) {
          e.printStackTrace();
      } finally {

      }
    }

    /**
    * 其它原则（整理中...）。
    */
    private void otherFun() {
      // TODO
    }
}
