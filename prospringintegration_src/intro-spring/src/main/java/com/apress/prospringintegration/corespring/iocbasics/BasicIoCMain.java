/*
 * Copyright 2002-2011 the original author or authors.
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

package com.apress.prospringintegration.corespring.iocbasics;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.SimpleThreadScope;

public class BasicIoCMain {

    public static void main(String[] args) {

        ApplicationContext app = new ClassPathXmlApplicationContext("ioc_basics.xml");

        BasicPOJO basicPOJO = (BasicPOJO) app.getBean("basic-pojo");

        assert basicPOJO != null;

        System.out.println(basicPOJO.getColor());

        ColorEnum colorEnum = (ColorEnum) app.getBean("randomColor");
        System.out.println("randomColor: " + colorEnum);
        colorEnum = (ColorEnum) app.getBean("exclusiveColor");
        System.out.println("exclusiveColor: " + colorEnum);

        ColorPicker colorPicker = (ColorPicker) app.getBean(ColorPicker.class);
        assert colorPicker != null;
        System.out.println(colorPicker.getColorRandomizer().randomColor());

        BasicIoCMain.demonstrateScopes(app);

        // Custom Scope Registration with SimpleThreadScope
        ConfigurableApplicationContext configApp = (ConfigurableApplicationContext) app;
        configApp.getBeanFactory().registerScope("thread", new SimpleThreadScope());
    }

    public static void demonstrateScopes(ApplicationContext ctx) {
        for (int i = 0; i < 5; i++) {
            System.out.println("randomeverytime: " + ctx.getBean("randomeverytime", ColorEnum.class));
            System.out.println("alwaysthesame: " + ctx.getBean("alwaysthesame", ColorEnum.class));
        }
    }
}
