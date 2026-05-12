package org.jspiders.CourierAndLogisticsTrackingSystem.entities;

import org.jspiders.CourierAndLogisticsTrackingSystem.dto.PackageEntityType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class PackageEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Enumerated(EnumType.STRING)
	private PackageEntityType packageType;
	
	private boolean fragile;
	
	private String dimensions;
	
	@OneToOne(mappedBy = "packageEntity")
	@JsonIgnore
	private Shipment shipment;

	public Shipment getShipment() {
		return shipment;
	}

	public void setShipment(Shipment shipment) {
		this.shipment = shipment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PackageEntityType getPackageType() {
		return packageType;
	}

	public void setPackageType(PackageEntityType packageType) {
		this.packageType = packageType;
	}

	public Boolean getFragile() {
		return fragile;
	}

	public void setFragile(Boolean fragile) {
		this.fragile = fragile;
	}

	public String getDimensions() {
		return dimensions;
	}

	public void setDimensions(String dimensions) {
		this.dimensions = dimensions;
	}
}
