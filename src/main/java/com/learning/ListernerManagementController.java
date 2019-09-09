package com.learning;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.integration.jms.JmsMessageDrivenEndpoint;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.ApplicationScope;

@RestController
@RequestMapping("/listener")
public class ListernerManagementController {

	@Autowired
	ApplicationContext context;
// application get mapping
	@GetMapping(value = "/command")
	public String command(@RequestParam String adaptor, @RequestParam String command) {
		
		JmsMessageDrivenEndpoint drivenEndpoint = context.getBean(adaptor, JmsMessageDrivenEndpoint.class);
		if ("start".equalsIgnoreCase(command)) {
			drivenEndpoint.start();
		} else if ("stop".equalsIgnoreCase(command)) {
			drivenEndpoint.stop();
		}
		return "Successfully executed the command. Now the ".concat(drivenEndpoint.isRunning()?adaptor.concat(" is running"):adaptor.concat(" is stopped"));
	}
	
	@GetMapping(value = "/runningStatus")
	public String command(@RequestParam String adaptor) {
		JmsMessageDrivenEndpoint drivenEndpoint = context.getBean(adaptor, JmsMessageDrivenEndpoint.class);
		return drivenEndpoint.isRunning()?adaptor.concat(" is running"):adaptor.concat(" is stopped");
	}
}
