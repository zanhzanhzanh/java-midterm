package tdtu.MidTerm.SpringCommerce.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.MidTerm.SpringCommerce.models.Order;
import tdtu.MidTerm.SpringCommerce.models.Product;
import tdtu.MidTerm.SpringCommerce.models.ResponseObject;
import tdtu.MidTerm.SpringCommerce.service.OrderService;

@RestController
@RequestMapping(path = "")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("/order/{userId}")
    public ResponseEntity<ResponseObject> findAllOrderByUserId(@PathVariable("userId") Long id) {
        return orderService.findAllOrderByUserId(id);
    }

    @PostMapping("/order/{userId}")
    public ResponseEntity<ResponseObject> insertOrder(@Valid @RequestBody Order order, @PathVariable("userId") Long userId) {
        return orderService.insertOrder(order, userId);
    }

    @PutMapping("/order/{id}")
    public ResponseEntity<ResponseObject> updateOrder(@Valid @RequestBody Order order, @PathVariable("id") Long id) {
        return orderService.updateOrder(order, id);
    }

    @DeleteMapping("/order/{id}")
    public ResponseEntity<ResponseObject> deleteOrder(@PathVariable("id") Long id) {
        return orderService.deleteOrder(id);
    }
}
