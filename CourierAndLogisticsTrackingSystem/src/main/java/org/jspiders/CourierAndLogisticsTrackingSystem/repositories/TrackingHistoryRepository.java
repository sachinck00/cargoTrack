package org.jspiders.CourierAndLogisticsTrackingSystem.repositories;

import java.util.List;

import org.jspiders.CourierAndLogisticsTrackingSystem.entities.TrackingHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackingHistoryRepository extends JpaRepository<TrackingHistory, Integer>{
	List<TrackingHistory> findByShipmentId(int id);
}
