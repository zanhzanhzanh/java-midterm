package tdtu.MidTerm.SpringCommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tbl_Product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"detailCarts", "detailOrders"})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please input name")
    private String name;

    @NotBlank(message = "Please input category")
    private String category;

    @NotNull
    @PositiveOrZero
    private float price;

    @NotBlank(message = "Please input brand")
    private String brand;

    @NotBlank(message = "Please input color")
    private String color;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnoreProperties("product")
    private List<DetailCart> detailCarts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    @JsonIgnoreProperties("product")
    private List<DetailOrder> detailOrders;
}
