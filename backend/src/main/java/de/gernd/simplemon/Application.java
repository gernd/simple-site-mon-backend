package de.gernd.simplemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Application.class, args);

        // print out all bean names
        System.out.println("Printing all bean names");

        String[] beanNames = context.getBeanDefinitionNames();
        for (String beanName : beanNames){
            System.out.println(beanName);
        }
    }
}
