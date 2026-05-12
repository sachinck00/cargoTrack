package org.jspiders.CourierAndLogisticsTrackingSystem.repositories;

import java.util.List;
import java.util.Optional;

import org.jspiders.CourierAndLogisticsTrackingSystem.entities.DeliveryAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface DeliveryAgentRepository extends JpaRepository<DeliveryAgent, Integer>{
	@Query("select da from DeliveryAgent da where da.vehicleNumber=?1")
	Optional<DeliveryAgent> findAgentByVehicleNumber(String vehicleNumber);
	
	@Query("select da from DeliveryAgent da where da.contact=?1")
	Optional<DeliveryAgent> findAgentByContact(long contact);
	
	@Query("select da from DeliveryAgent da where da.rating>?1")
	List<DeliveryAgent> findAgentsByRatingGreaterThan(double rating);
	
	
}
