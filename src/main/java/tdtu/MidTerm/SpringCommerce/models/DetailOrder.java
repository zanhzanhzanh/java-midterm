package tdtu.MidTerm.SpringCommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_DetailOrder")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_product")
    @JsonIgnoreProperties({"detailOrders", "detailCarts"})
    private Product product;

    @ManyToOne
    @JoinColumn(name = "fk_order")
    @JsonIgnore
    private Order order;
}
