package com.project.fsneaker.services;

import com.project.fsneaker.dtos.OrderDTO;
import com.project.fsneaker.exceptions.DataNotFoundException;
import com.project.fsneaker.models.Order;
import com.project.fsneaker.models.OrderStatus;
import com.project.fsneaker.models.User;
import com.project.fsneaker.repositories.OrderRepository;
import com.project.fsneaker.repositories.UserRepository;
import com.project.fsneaker.responses.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find user with id: "+orderDTO.getUserId()));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(new Date());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Date must be at least today!");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        orderRepository.save(order);
        modelMapper.typeMap(Order.class, OrderResponse.class);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        return null;
    }

    @Override
    public OrderResponse updateOrder(Long id, OrderDTO orderDTO) {
        return null;
    }

    @Override
    public void deleteOrder(Long id) {

    }

    @Override
    public List<OrderResponse> getAllOrders(Long userId) {
        return List.of();
    }
}
