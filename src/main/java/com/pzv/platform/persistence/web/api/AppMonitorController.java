package com.pzv.platform.persistence.web.api;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.pzv.platform.persistence.model.Route;
import com.pzv.platform.persistence.repo.dao.RouterDAO;

import io.swagger.annotations.Api;

@Api(value = "Application Health")
@RestController
public class AppMonitorController {
	private static final Logger LOG = LoggerFactory.getLogger(AppMonitorController.class);

	@Autowired
	private RouterDAO routerDao;
	
	@Value("${application.name}")
	private String applicatioName;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView("redirect:/swagger-ui.html");
	}

	@RequestMapping(value = "/monitor/ping", method = RequestMethod.GET)
	@Transactional
	public ResponseEntity<?> ping() {
		String reply = applicatioName;
		Route route = routerDao.getOne(2l);
		ResponseEntity<?> response = new ResponseEntity<>(route, HttpStatus.OK);
		LOG.info("[{}] Response => {}",reply,route);
		return response;
	}

}
