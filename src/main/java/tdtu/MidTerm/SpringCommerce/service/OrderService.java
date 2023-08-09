package tdtu.MidTerm.SpringCommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tdtu.MidTerm.SpringCommerce.models.*;
import tdtu.MidTerm.SpringCommerce.repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    DetailCartRepository detailCartRepository;
    @Autowired
    DetailOrderRepository detailOrderRepository;

    public ResponseEntity<ResponseObject> findAllOrderByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find User with id = " + userId, "")
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All Order By User Success", orderRepository.findAllByUser(user))
        );
    }

    public ResponseEntity<ResponseObject> insertOrder(Order order, Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find User with id = " + userId, "")
        );

        Cart cart = cartRepository.findByUser(user);
        List<DetailCart> boxDetailCart = detailCartRepository.findAllByCart(cart);

        order.setStatus("Delivering");
        order.setTotalOrder(cart.getTotalCart());
        order.setDetailOrders(new ArrayList<>());
        order.setUser(user.get());

        // Create DetailCart
        List<DetailOrder> boxItem = new ArrayList<>();
        float sumMoney = 0;
        for(DetailCart item : boxDetailCart) {
            DetailOrder newDetailOrder = new DetailOrder();
            newDetailOrder.setOrder(order);
            newDetailOrder.setProduct(item.getProduct());
            sumMoney += item.getProduct().getPrice();
            boxItem.add(newDetailOrder);
        }

        Order resOrder = orderRepository.save(order);
        detailOrderRepository.saveAll(boxItem);
        resOrder.setTotalOrder(sumMoney);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Order Success", orderRepository.save(resOrder))
        );
    }

    public ResponseEntity<ResponseObject> updateOrder(Order newOrder, Long id) {
        Order updatedOrder = orderRepository.findById(id)
                .map(order -> {
                    order.setOrderDate(newOrder.getOrderDate());
                    order.setDeliveryDate(newOrder.getDeliveryDate());
                    order.setStatus(newOrder.getStatus());

                    return orderRepository.save(order);
                }).orElseGet(() -> {
                    return null;
                });

        if(updatedOrder == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Can't find Order with id = " + id, "")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Order Success", updatedOrder)
        );
    }

    public ResponseEntity<ResponseObject> deleteOrder(Long id) {
        Optional<Order> orderExist = orderRepository.findById(id);

        if(orderExist.isPresent()) {
            for(DetailOrder item : detailOrderRepository.findAllByOrder(orderExist.get())) {
                detailOrderRepository.deleteById(item.getId());
            }
            orderRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Order Success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find Order to delete", "")
        );
    }
}
