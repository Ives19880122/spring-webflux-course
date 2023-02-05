package com.ives.orderservicepractice.repository;

import com.ives.orderservicepractice.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Integer> {
    List<PurchaseOrder> findByUserId(int userId);
}
