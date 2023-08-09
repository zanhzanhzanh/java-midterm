package tdtu.MidTerm.SpringCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdtu.MidTerm.SpringCommerce.models.Cart;
import tdtu.MidTerm.SpringCommerce.models.User;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(Optional<User> userExist);
}
