package com.pzv.platform.persistence.integration.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

import javax.annotation.PostConstruct;

import org.apache.camel.CamelContext;
import org.apache.camel.NoTypeConversionAvailableException;
import org.apache.camel.TypeConversionException;
import org.apache.camel.TypeConverter;
import org.apache.camel.model.RoutesDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pzv.platform.persistence.model.Route;
import com.pzv.platform.persistence.repo.dao.RouterDAO;
import com.pzv.platform.persistence.service.SampleBean;

@Service
class CamelRoutingSevice implements RoutingService {
	private static Logger LOG = LoggerFactory.getLogger(CamelRoutingSevice.class);
	
	@Autowired
	private CamelContext camelContext;
	
	@Autowired
	private RouterDAO routerDAO;
	
	@PostConstruct
	private void init(){
		List<Route> activeRoutes = routerDAO.findAllActiveRoutes();
		TypeConverter typeConverter = camelContext.getTypeConverter();
		
		for(Route route:activeRoutes){
			try (InputStream in = typeConverter.mandatoryConvertTo(InputStream.class, route.getRouteXML())){
				RoutesDefinition routesDefinition = camelContext.loadRoutesDefinition(in);
				
				if (
						Objects.nonNull(routesDefinition) &&
						Objects.nonNull(routesDefinition.getRoutes()) && 
						!routesDefinition.getRoutes().isEmpty()){
					
					camelContext.addRouteDefinitions(routesDefinition.getRoutes());
					
				}
			} catch (TypeConversionException e) {
				LOG.error("{}",e.getMessage(),e);
			} catch (NoTypeConversionAvailableException e) {
				LOG.error("{}",e.getMessage(),e);
			} catch (IOException e) {
				LOG.error("{}",e.getMessage(),e);
			} catch (Exception e) {
				LOG.error("{}",e.getMessage(),e);
			}
		}
	}
	
	
}
