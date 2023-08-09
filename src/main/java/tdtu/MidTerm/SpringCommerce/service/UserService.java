package tdtu.MidTerm.SpringCommerce.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tdtu.MidTerm.SpringCommerce.models.Cart;
import tdtu.MidTerm.SpringCommerce.models.ResponseObject;
import tdtu.MidTerm.SpringCommerce.models.User;
import tdtu.MidTerm.SpringCommerce.repositories.CartRepository;
import tdtu.MidTerm.SpringCommerce.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    CartRepository cartRepository;

    public ResponseEntity<ResponseObject> findAllUser() {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query All User Success", userRepository.findAll())
        );
    }

    public ResponseEntity<ResponseObject> findUserById(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Can't find User with id = " + userId, "")
        );

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Query User By Id Success", user)
        );
    }

    public ResponseEntity<ResponseObject> insertUser(User user) {
        List<User> foundUser = userRepository.findByEmail(user.getEmail().trim());
        // Trường lỡ ghi thêm field id
        Optional<User> foundUserWithId = (user.getId() == null) ? Optional.empty() : userRepository.findById(user.getId());

        if(foundUser.size() > 0 || foundUserWithId.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Email already taken or the same id", "")
            );
        }

        // Check Regex Email
        if(!Pattern.compile("^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$").matcher(user.getEmail()).matches()) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Please enter the correct email structure", "")
            );
        }

        // New Cart
        Cart newCart = new Cart();
        newCart.setUser(user);
        newCart.setDetailCarts(new ArrayList<>());

        // Assign cart
        user.setCart(newCart);
        user.setOrders(new ArrayList<>());

        cartRepository.save(newCart);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Insert User Success", userRepository.save(user))
        );
    }

    public ResponseEntity<ResponseObject> updateUser(User newUser, Long id) {
        User updatedUser = userRepository.findById(id)
                .map(user -> {
                    user.setName(newUser.getName());
                    user.setPassword(newUser.getPassword());

                    return userRepository.save(user);
                }).orElseGet(() -> {
                    return null;
                });

        if(updatedUser == null) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", "Can't find User with id = " + id, "")
            );
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("ok", "Update User Success", updatedUser)
        );
    }

    public ResponseEntity<ResponseObject> deleteUser(Long id) {
        Optional<User> userExist = userRepository.findById(id);

        if(userExist.isPresent()) {
            // Delete Cart
            cartRepository.deleteById(cartRepository.findByUser(userExist).getId());

            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "Delete User Success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("failed", "Cannot find User to delete", "")
        );
    }
}
