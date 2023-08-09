package tdtu.MidTerm.SpringCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdtu.MidTerm.SpringCommerce.models.Cart;
import tdtu.MidTerm.SpringCommerce.models.DetailCart;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailCartRepository extends JpaRepository<DetailCart, Long> {
    List<DetailCart> findAllByCart(Cart cart);
}
