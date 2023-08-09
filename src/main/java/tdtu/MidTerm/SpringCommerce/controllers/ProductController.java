package tdtu.MidTerm.SpringCommerce.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.MidTerm.SpringCommerce.models.ResponseObject;
import tdtu.MidTerm.SpringCommerce.models.Product;
import tdtu.MidTerm.SpringCommerce.service.ProductService;

@RestController
@RequestMapping(path = "")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/product")
    public ResponseEntity<ResponseObject> findAllProduct() {
        return productService.findAllProduct();
    }

    @GetMapping("/product/{id}")
    public ResponseEntity<ResponseObject> findProductById(@PathVariable("id") Long id) {
        return productService.findProductById(id);
    }

    @GetMapping("/productQuery/{key}") // Find Request
    public ResponseEntity<ResponseObject> findProductByNameOrBrandOrColor(@PathVariable("key") String key) {
        return productService.findProductByNameOrBrandOrColor(key);
    }

    @PostMapping("/product")
    public ResponseEntity<ResponseObject> insertProduct(@Valid @RequestBody Product product) {
        return productService.insertProduct(product);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@Valid @RequestBody Product product, @PathVariable("id") Long id) {
        return productService.updateProduct(product, id);
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable("id") Long id) {
        return productService.deleteProduct(id);
    }
}
