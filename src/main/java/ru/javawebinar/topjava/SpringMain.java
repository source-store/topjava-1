package ru.javawebinar.topjava;

import java.util.Arrays;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class SpringMain {

  public static void main(String[] args) {
    try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
      System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
    }
//        System.out.format("Hello TopJava Enterprise!");
  }
}
