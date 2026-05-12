package org.jspiders.CourierAndLogisticsTrackingSystem.entities;



import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.jspiders.CourierAndLogisticsTrackingSystem.dto.PaymentMethodType;
import org.jspiders.CourierAndLogisticsTrackingSystem.dto.PaymentStatusType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Payment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	private double amount;
	
	@Enumerated(EnumType.STRING)
	private PaymentMethodType paymentMethod;
	
	@Enumerated(EnumType.STRING)
	private PaymentStatusType paymentStatus;
	
	@CreationTimestamp
	private LocalDateTime paymentDate;
	
	@OneToOne(mappedBy="payment")
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

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public PaymentMethodType getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(PaymentMethodType paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public PaymentStatusType getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatusType paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public LocalDateTime getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDateTime paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	
}
