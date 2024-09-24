package com.project.fsneaker.services;

import com.project.fsneaker.dtos.OrderDetailDTO;
import com.project.fsneaker.exceptions.DataNotFoundException;
import com.project.fsneaker.models.OrderDetail;

import java.util.List;

public interface IOrderDetaiilService {

    OrderDetail createOrderDetail(OrderDetailDTO orderDetailDTO) throws Exception;

    OrderDetail getOrderDetail(Long id) throws DataNotFoundException;

    OrderDetail updateOrderDetail(Long id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    void deleteById(Long id);

    List<OrderDetail> findByOrderId(Long orderId);

}
