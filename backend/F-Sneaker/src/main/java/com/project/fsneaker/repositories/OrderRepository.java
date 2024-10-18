package com.project.fsneaker.repositories;

import com.project.fsneaker.models.Order;
import com.project.fsneaker.responses.OrderResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

//    @Query("""
//            SELECT new com.project.fsneaker.responses.OrderResponse(o.id, o.user.id, o.fullname, o.email, o.phoneNumber, o.address, o.note, o.orderDate, o.status, o.totalMoney, o.shippingMethod, o.shippingDate, o.shippingAddress, o.trackingNumber, o.paymentMethod, o.active)
//            FROM Order o WHERE o.user.id = :userId
//            """)
//    List<OrderResponse> findByUserId(@Param("userId") Long userId);
//
//    @Query("""
//            SELECT new com.project.fsneaker.responses.OrderResponse(o.id, o.user.id, o.fullname, o.email, o.phoneNumber, o.address, o.note, o.orderDate, o.status, o.totalMoney, o.shippingMethod, o.shippingDate, o.shippingAddress, o.trackingNumber, o.paymentMethod, o.active)
//            FROM Order o WHERE o.id = :id
//            """)
//    Optional<OrderResponse> findByOrderId(Long id);
}
