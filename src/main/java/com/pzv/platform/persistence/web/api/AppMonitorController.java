package com.pzv.platform.persistence.web.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.swagger.annotations.Api;

@Api(value = "Application Health")
@RestController
public class AppMonitorController {
	private static final Logger LOG = LoggerFactory.getLogger(AppMonitorController.class);

	@Value("${application.name}")
	private String applicationName;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView("redirect:/swagger-ui.html");
	}

	@RequestMapping(value = "/monitor/ping", method = RequestMethod.GET)
	public ResponseEntity<?> ping() {
		String reply = applicationName;
		ResponseEntity<?> response = new ResponseEntity<>(reply, HttpStatus.OK);
		return response;
	}

}
