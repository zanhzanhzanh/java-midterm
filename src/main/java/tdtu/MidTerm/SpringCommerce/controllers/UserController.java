package tdtu.MidTerm.SpringCommerce.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tdtu.MidTerm.SpringCommerce.models.ResponseObject;
import tdtu.MidTerm.SpringCommerce.models.User;
import tdtu.MidTerm.SpringCommerce.service.UserService;

@RestController
@RequestMapping(path = "")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/user")
    public ResponseEntity<ResponseObject> findAllUser() {
        return userService.findAllUser();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ResponseObject> findUserById(@PathVariable("id") Long id) {
        return userService.findUserById(id);
    }

    @PostMapping("/user")
    public ResponseEntity<ResponseObject> insertUser(@Valid @RequestBody User user) {
        return userService.insertUser(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<ResponseObject> updateUser(@Valid @RequestBody User user, @PathVariable("id") Long id) {
        return userService.updateUser(user, id);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<ResponseObject> deleteUser(@PathVariable("id") Long id) {
        return userService.deleteUser(id);
    }
}
