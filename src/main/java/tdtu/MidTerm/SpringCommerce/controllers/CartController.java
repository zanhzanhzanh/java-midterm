package tdtu.MidTerm.SpringCommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tdtu.MidTerm.SpringCommerce.models.ResponseObject;
import tdtu.MidTerm.SpringCommerce.service.CartService;

@RestController
@RequestMapping(path = "")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("/cart")
    public ResponseEntity<ResponseObject> findAllCart() {
        return cartService.findAllCart();
    }

    @GetMapping("/cart/{id}")
    public ResponseEntity<ResponseObject> findCartById(@PathVariable("id") Long id) {
        return cartService.findCartById(id);
    }
}
