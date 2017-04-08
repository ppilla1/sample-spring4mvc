package com.pzv.platform.persistence;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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

import com.pzv.platform.persistence.model.Route;
import com.pzv.platform.persistence.repo.PersistenceConfig;
import com.pzv.platform.persistence.repo.dao.RouterDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={PersistenceConfig.class})
//@ActiveProfiles({"local"})
@ActiveProfiles({"default"})
public class PersistenceTestCases {
	private static final Logger LOG = LoggerFactory.getLogger(PersistenceTestCases.class);
	
	@Autowired
	private RouterDAO routerDao;
	
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

//	@Test
	public void testJdbcTemplateConfig(){
		assertNotNull(jdbcTemplate);
		String selectQuery = "select * from route r where r.tenantid = :tenantId and r.featureid = :featureId";
		SqlParameterSource namedParameters = new MapSqlParameterSource("tenantId","DANA-US").addValue("featureId", "HENDERSON-AVAILABILITY");
		
		List<Route> routes = jdbcTemplate.query(selectQuery, namedParameters, new RowMapper<Route>() {

			@Override
			public Route mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				Route route = new Route();
				route.setId(resultSet.getLong("id"));
				route.setTenantId(resultSet.getString("tenantid"));
				route.setFeatureId(resultSet.getString("featureid"));
				route.setRouteId(resultSet.getString("routeid"));
				route.setActive(resultSet.getInt("active")==1?true:false);
				route.setRouteXML(resultSet.getString("routexml"));
				return route ;
			}
		});
		
		assertNotNull(routes);
		LOG.info("\nRoutes --> {}",routes);
	}
	
	@Test
	public void testFindAllRoutesByTenantId() {
		
		List<Route> danaRoutes = routerDao.findAll();
		danaRoutes = routerDao.findAllActiveRoutes();
		
		Route hendersonRoute = routerDao.findByTenantAndFeatureId("DANA-US", "HENDERSON-AVAILABILITY");
		
		assertNotNull(danaRoutes);
		assertNotNull(hendersonRoute);
		assertTrue(!danaRoutes.isEmpty());
		
		LOG.info("\nRoutes --> {}\nRoute --> {}",danaRoutes,hendersonRoute);
	}

}
