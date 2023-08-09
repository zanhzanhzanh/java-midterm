package tdtu.MidTerm.SpringCommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tdtu.MidTerm.SpringCommerce.models.Cart;
import tdtu.MidTerm.SpringCommerce.models.ResponseObject;
import tdtu.MidTerm.SpringCommerce.models.Product;
import tdtu.MidTerm.SpringCommerce.repositories.ProductRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public ResponseEntity<ResponseObject> findAllProduct() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All Product Success", productRepository.findAll())
        );
    }

    public ResponseEntity<ResponseObject> findProductById(Long productId) {
        Optional<Product> product = productRepository.findById(productId);

        if(!product.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find Product with id = " + productId, "")
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query Product By Id Success", product)
        );
    }

    public ResponseEntity<ResponseObject> insertProduct(Product product) {
        List<Product> foundProduct = productRepository.findByName(product.getName().trim());
        // Trường lỡ ghi thêm field id
        Optional<Product> foundProductWithId = (product.getId() == null) ? Optional.empty() : productRepository.findById(product.getId());

        if(foundProduct.size() > 0 || foundProductWithId.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Name Product already taken or the same id", "")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert Product Success", productRepository.save(product))
        );
    }

    public ResponseEntity<ResponseObject> updateProduct(Product newProduct, Long id) {
        List<Product> foundProduct = productRepository.findByName(newProduct.getName().trim());
        if(foundProduct.size() > 0) {
            // Trường hợp update nhưng tên product trùng thì chỉ cho thay đổi field khi trùng id
            if(foundProduct.get(0).getId() != id) {
                return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                        new ResponseObject("failed", "Name Product already taken", "")
                );
            }
        }

        Product updatedProduct = productRepository.findById(id)
                .map(product -> {
                    product.setName(newProduct.getName());
                    product.setPrice(newProduct.getPrice());
                    product.setBrand(newProduct.getBrand());
                    product.setColor(newProduct.getColor());
                    product.setCategory(newProduct.getCategory());

                    return productRepository.save(product);
                }).orElseGet(() -> {
                    return null;
                });

        if(updatedProduct == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Can't find Product with id = " + id, "")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update Product Success", updatedProduct)
        );
    }

    public ResponseEntity<ResponseObject> deleteProduct(Long id) {
        Optional<Product> productExist = productRepository.findById(id);

        if(productExist.isPresent()) {
            productRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete Product Success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find Product to delete", "")
        );
    }

    public ResponseEntity<ResponseObject> findProductByNameOrBrandOrColor(String key) {
        List<Product> foundProduct = new ArrayList<>();

        if(Pattern.compile("[+-]?([0-9]*[.])?[0-9]+").matcher(key).matches()) {
            foundProduct.addAll(productRepository.findByPrice(Float.parseFloat(key)));
        }

        foundProduct.addAll(productRepository.findByNameContainsOrBrandContainsOrColorContainsOrCategoryContains(key, key, key, key));

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query Product Success", foundProduct)
        );
    }
}
