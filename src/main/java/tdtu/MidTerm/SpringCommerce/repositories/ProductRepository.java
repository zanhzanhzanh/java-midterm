package tdtu.MidTerm.SpringCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdtu.MidTerm.SpringCommerce.models.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByName(String trim);

    List<Product> findByNameContainsOrBrandContainsOrColorContainsOrCategoryContains(String key, String key1, String key2, String key3);

    List<Product> findByPrice(float parseFloat);
}
