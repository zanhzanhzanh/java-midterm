package tdtu.MidTerm.SpringCommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_DetailCart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailCart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_cart")
    @JsonIgnoreProperties("detailCarts")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "fk_product")
    @JsonIgnoreProperties("detailCarts")
    private Product product;

}
