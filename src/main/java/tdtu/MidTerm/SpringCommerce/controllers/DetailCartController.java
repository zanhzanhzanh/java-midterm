package tdtu.MidTerm.SpringCommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.MidTerm.SpringCommerce.models.ResponseObject;
import tdtu.MidTerm.SpringCommerce.service.DetailCartService;

@RestController
@RequestMapping(path = "")
public class DetailCartController {
    @Autowired
    DetailCartService detailCartService;

    @GetMapping("/detailCart/{cartId}")
    public ResponseEntity<ResponseObject> findAllDetailCartByCartId(@PathVariable("cartId") Long id) {
        return detailCartService.findAllDetailCartByCartId(id);
    }

    @PostMapping("/detailCart/{cartId}/{productId}")
    public ResponseEntity<ResponseObject> insertDetailCart(@PathVariable("cartId") Long cartId, @PathVariable("productId") Long productId) {
        return detailCartService.insertDetailCart(cartId, productId);
    }

    @DeleteMapping("/detailCart/{id}")
    public ResponseEntity<ResponseObject> deleteDetailCart(@PathVariable("id") Long id) {
        return detailCartService.deleteDetailCart(id);
    }
}
