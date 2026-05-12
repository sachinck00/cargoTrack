package org.jspiders.CourierAndLogisticsTrackingSystem.dto;

import java.time.LocalDate;

public class ShipmentDTO {
	private Integer shipmentId;
	
	private String source;
	
	private String destination;
	
	private double weight;
		
	private LocalDate deliveryDate;
	
	private ShipmentStatusType shipmentStatus;
	
	private double amount;
	
	
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Integer getShipmentId() {
		return shipmentId;
	}
	public void setShipmentId(Integer shipmentId) {
		this.shipmentId = shipmentId;
	}
	
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getDestination() {
		return destination;
	}
	public void setDestination(String destination) {
		this.destination = destination;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}
	
	
	public ShipmentStatusType getShipmentStatus() {
		return shipmentStatus;
	}
	public void setShipmentStatus(ShipmentStatusType shipmentStatus) {
		this.shipmentStatus = shipmentStatus;
	}
}
