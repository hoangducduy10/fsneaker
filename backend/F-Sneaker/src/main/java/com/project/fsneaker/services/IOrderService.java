package com.project.fsneaker.services;

import com.project.fsneaker.dtos.OrderDTO;
import com.project.fsneaker.exceptions.DataNotFoundException;
import com.project.fsneaker.models.Order;
import com.project.fsneaker.responses.OrderResponse;

import java.util.List;

public interface IOrderService {

    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;

    Order getOrderById(Long id);

    Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException;

    void deleteOrder(Long id);

    List<Order> findByUserId(Long userId);

}
