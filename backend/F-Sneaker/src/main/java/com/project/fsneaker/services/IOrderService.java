package com.project.fsneaker.services;

import com.project.fsneaker.dtos.OrderDTO;
import com.project.fsneaker.exceptions.DataNotFoundException;
import com.project.fsneaker.responses.OrderResponse;

import java.util.List;

public interface IOrderService {

    OrderResponse createOrder(OrderDTO orderDTO) throws Exception;

    OrderResponse getOrderById(Long id);

    OrderResponse updateOrder(Long id, OrderDTO orderDTO);

    void deleteOrder(Long id);

    List<OrderResponse> getAllOrders(Long userId);

}
