package edu.scs.vds.controller.api;

import edu.scs.vds.model.User;
import edu.scs.vds.model.dto.UserDto;
import edu.scs.vds.model.enums.UserRole;
import edu.scs.vds.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@Api(value = "API Controller", produces = MediaType.APPLICATION_JSON_VALUE, tags = {"User management"}, description = "API List")
@RequestMapping("/api/v1/")
public class UserApiController {

    @Autowired
    UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/users")
    public List<User> list() {
        return userService.listAll();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> get(@PathVariable Integer id) {
        try {
            User user = userService.get(id);
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(path = "/user",consumes =MediaType.APPLICATION_JSON_VALUE )
    public User addUser(@RequestBody UserDto userDto){
        User user = userDto.getUser();
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(UserRole.USER);
        user.setAppointmentStep(0);
        user.setActive(true);
        userService.save(user);
        return user;
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> update(@RequestBody User user, @PathVariable Integer id) {
        try {
            User existingUser = userService.get(id);
            userService.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/user/{id}")
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

}
