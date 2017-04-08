package com.pzv.platform.persistence.repo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pzv.platform.persistence.model.Route;

@Repository
public interface RouterDAO extends JpaRepository<Route, Long> {

	@Query("SELECT r FROM Route r WHERE r.active = 1")
	public List<Route> findAllActiveRoutes();
	
	@Query("SELECT r FROM Route r WHERE LOWER(r.tenantId) = LOWER(:tenantId) AND LOWER(r.featureId) = LOWER(:featureId)")
	public Route findByTenantAndFeatureId(@Param("tenantId") String tenantId,@Param("featureId") String featureId);
}
