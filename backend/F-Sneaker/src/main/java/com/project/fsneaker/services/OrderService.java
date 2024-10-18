package com.project.fsneaker.services;

import com.project.fsneaker.dtos.CartItemDTO;
import com.project.fsneaker.dtos.OrderDTO;
import com.project.fsneaker.exceptions.DataNotFoundException;
import com.project.fsneaker.models.Order;
import com.project.fsneaker.models.OrderDetail;
import com.project.fsneaker.models.OrderStatus;
import com.project.fsneaker.models.Product;
import com.project.fsneaker.models.User;
import com.project.fsneaker.repositories.OrderDetailRepository;
import com.project.fsneaker.repositories.OrderRepository;
import com.project.fsneaker.repositories.ProductRepository;
import com.project.fsneaker.repositories.UserRepository;
import com.project.fsneaker.responses.OrderResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final ProductRepository productRepository;
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional
    public OrderResponse createOrder(OrderDTO orderDTO) throws Exception {
        User user = userRepository.findById(orderDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find user with id: "+orderDTO.getUserId()));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        Order order = new Order();
        modelMapper.map(orderDTO, order);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING);
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : orderDTO.getShippingDate();
        if(shippingDate.isBefore(LocalDate.now())){
            throw new DataNotFoundException("Date must be at least today!");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        order.setTotalMoney(orderDTO.getTotalMoney());
        orderRepository.save(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for (CartItemDTO cartItemDTO : orderDTO.getCartItems()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);

            // Lay thong tin product tu cartItemDTO
            Long productId = cartItemDTO.getProductId();
            int quantity = cartItemDTO.getQuantity();

            // Tim thong tin product tu db
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new DataNotFoundException("Cannot find product with id: "+productId));

            // Set thong tin cho OrderDetail
            orderDetail.setProduct(product);
            orderDetail.setNumberOfProducts(quantity);
            orderDetail.setPrice(product.getPrice());

            // Them OrderDetail vao danh sach
            orderDetails.add(orderDetail);
        }
        orderDetailRepository.saveAll(orderDetails);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Order updateOrder(Long id, OrderDTO orderDTO) throws DataNotFoundException {
        Order existingOrder = orderRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Cannot find order with id: "+id));
        User existingUser = userRepository.findById(orderDTO.getUserId()).orElseThrow(
                () -> new DataNotFoundException("Cannot find order with id: "+id));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, existingOrder);
        existingOrder.setUser(existingUser);
        return orderRepository.save(existingOrder);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        // Ko xoa cung => xoa mem
        if(order != null){
            order.setActive(false);
            orderRepository.save(order);
        }
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }
}
