package de.gernd.simplemon;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleMonitoringController {

    @RequestMapping("/")
    public String test() {
        return "This is the monitoring controller";
    }
}
