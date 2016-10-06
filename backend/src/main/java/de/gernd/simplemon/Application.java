package de.gernd.simplemon;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(Application.class, args);
        Logger logger = Logger.getLogger("Application");

        // print out all bean names if log level debug is set
        if (logger.getEffectiveLevel().equals(Level.DEBUG)) {
            logger.debug("Printing all bean names");

            String[] beanNames = context.getBeanDefinitionNames();
            for (String beanName : beanNames) {
                logger.debug(beanName);
            }
        }
    }
}
