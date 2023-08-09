package tdtu.MidTerm.SpringCommerce.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.aspectj.weaver.ast.Or;

import java.util.List;

@Entity
@Table(name = "tbl_User")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "orders")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "Please input name")
    private String name;

    @NotBlank(message = "Please input email")
    private String email;

    @NotBlank(message = "Please input password")
    private String password;

    @OneToOne
    @JoinColumn(name = "fk_cart")
    @JsonIgnoreProperties("user")
    private Cart cart;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnoreProperties("user")
    private List<Order> orders;
}
