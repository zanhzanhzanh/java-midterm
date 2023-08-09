package tdtu.MidTerm.SpringCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdtu.MidTerm.SpringCommerce.models.Order;
import tdtu.MidTerm.SpringCommerce.models.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(Optional<User> user);
}
