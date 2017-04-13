package com.pzv.platform.persistence.service;

import org.springframework.stereotype.Component;

@Component("samplebean")
public class SampleBean {
	public String ping(){
		return "pong";
	}
}
