package tdtu.MidTerm.SpringCommerce.repositories;

import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tdtu.MidTerm.SpringCommerce.models.DetailOrder;
import tdtu.MidTerm.SpringCommerce.models.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface DetailOrderRepository extends JpaRepository<DetailOrder, Long> {
    List<DetailOrder> findAllByOrder(Order orderExist);
}
