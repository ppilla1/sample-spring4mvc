package com.pzv.platform.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="route")
public class Route {
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name="tenantid")
	private String tenantId;
	
	@Column(name="featureid")
	private String featureId;
	
	@Column(name="routeid")
	private String routeId;
	
	@Column(name="active")
	private Boolean active;
	
	@Column(name="routexml")
	private String routeXML;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}
	public String getFeatureId() {
		return featureId;
	}
	public void setFeatureId(String featureId) {
		this.featureId = featureId;
	}
	public String getRouteId() {
		return routeId;
	}
	public void setRouteId(String routeId) {
		this.routeId = routeId;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	public String getRouteXML() {
		return routeXML;
	}
	public void setRouteXML(String routeXML) {
		this.routeXML = routeXML;
	}
	
	@Override
	public String toString() {
		return String.format("Route [id=%s, tenantId=%s, featureId=%s, routeId=%s, active=%s, routeXML=%s]", id,
				tenantId, featureId, routeId, active, routeXML);
	}
}
