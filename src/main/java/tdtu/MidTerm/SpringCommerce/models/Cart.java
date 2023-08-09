package tdtu.MidTerm.SpringCommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tbl_Cart")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"detailCarts", "user"})
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @PositiveOrZero
    private float totalCart;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "cart")
    @JsonIgnoreProperties("cart")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    @JsonIgnoreProperties("cart")
    private List<DetailCart> detailCarts;

    @PrePersist
    @PreUpdate
    private void calculateTotalCart() {
        float sum = 0;
        for(DetailCart item : detailCarts) {
            sum += item.getProduct().getPrice();
        }
        this.totalCart = sum;
    }
}
