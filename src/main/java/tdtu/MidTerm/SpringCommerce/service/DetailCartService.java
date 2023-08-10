package tdtu.MidTerm.SpringCommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tdtu.MidTerm.SpringCommerce.models.Cart;
import tdtu.MidTerm.SpringCommerce.models.DetailCart;
import tdtu.MidTerm.SpringCommerce.models.Product;
import tdtu.MidTerm.SpringCommerce.models.ResponseObject;
import tdtu.MidTerm.SpringCommerce.repositories.CartRepository;
import tdtu.MidTerm.SpringCommerce.repositories.DetailCartRepository;
import tdtu.MidTerm.SpringCommerce.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DetailCartService {
    @Autowired
    DetailCartRepository detailCartRepository;
    @Autowired
    CartRepository cartRepository;
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<ResponseObject> findAllDetailCartByCartId(Long cartId) {
        Optional<Cart> cart = cartRepository.findById(cartId);

        if(!cart.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find Cart with id = " + cartId, "")
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All DetailCart By Cart Success", detailCartRepository.findAllByCart(cart.get()))
        );
    }

    public ResponseEntity<ResponseObject> insertDetailCart(Long cartId, Long productId) {
        Optional<Cart> cart = cartRepository.findById(cartId);

        if(!cart.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find Cart with id = " + cartId, "")
        );

        Optional<Product> product = productRepository.findById(productId);

        if(!product.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find Product with id = " + productId, "")
        );

        DetailCart newDetailCart = new DetailCart();
        newDetailCart.setCart(cart.get());
        newDetailCart.setProduct(product.get());

        List<DetailCart> boxDetailCart = new ArrayList<>(cart.get().getDetailCarts());
        boxDetailCart.add(newDetailCart);
        cart.get().setDetailCarts(boxDetailCart);
        Cart resCart = cartRepository.save(cart.get());

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert DetailCart Success", resCart.getDetailCarts())
        );
    }

    public ResponseEntity<ResponseObject> deleteDetailCart(Long id) {
        boolean isExist = detailCartRepository.existsById(id);

        if(isExist) {
            detailCartRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete DetailCart Success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find DetailCart to delete", "")
        );
    }
}
