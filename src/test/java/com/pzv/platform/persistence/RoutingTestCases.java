package com.pzv.platform.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.pzv.platform.persistence.integration.CamelConfig;
import com.pzv.platform.persistence.integration.service.RoutingService;
import com.pzv.platform.persistence.model.Route;
import com.pzv.platform.persistence.repo.PersistenceConfig;
import com.pzv.platform.persistence.repo.dao.RouterDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={CamelConfig.class})
//@ActiveProfiles({"local"})
@ActiveProfiles({"default"})
public class RoutingTestCases {
	private static final Logger LOG = LoggerFactory.getLogger(RoutingTestCases.class);
	
	@Autowired
	private RoutingService routeServie;
	
	@Autowired
	private CamelContext camelContext;
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRoutingServiceInit(){
		assertNotNull(routeServie);
		LOG.info("{}",camelContext.getRoutes());
	}
}
