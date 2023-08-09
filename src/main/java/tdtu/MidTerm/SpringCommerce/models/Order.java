package tdtu.MidTerm.SpringCommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "tbl_Order")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"detailOrders"})
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Please input order date")
    private Date orderDate;

    @NotNull(message = "Please input delivery date")
    private Date deliveryDate;

    @NotBlank(message = "Please input status")
    private String status;

    @PositiveOrZero
    private float totalOrder;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonIgnoreProperties("order")
    private List<DetailOrder> detailOrders;

    @ManyToOne
    @JoinColumn(name = "fk_user")
    @JsonIgnoreProperties("orders")
    private User user;

    @PrePersist
    @PreUpdate
    private void calculateTotalCart() {
        float sum = 0;
        for(DetailOrder item : detailOrders) {
            sum += item.getProduct().getPrice();
        }
        this.totalOrder = sum;
    }
}
