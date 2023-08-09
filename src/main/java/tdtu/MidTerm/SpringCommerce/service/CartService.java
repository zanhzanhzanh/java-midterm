package tdtu.MidTerm.SpringCommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tdtu.MidTerm.SpringCommerce.models.Cart;
import tdtu.MidTerm.SpringCommerce.models.ResponseObject;
import tdtu.MidTerm.SpringCommerce.repositories.CartRepository;

import java.util.Optional;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    public ResponseEntity<ResponseObject> findAllCart() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All Cart Success", cartRepository.findAll())
        );
    }

    public ResponseEntity<ResponseObject> findCartById(Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);

        if(!cart.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find Cart with id = " + cartId, "")
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query Cart By Id Success", cart)
        );
    }
}
