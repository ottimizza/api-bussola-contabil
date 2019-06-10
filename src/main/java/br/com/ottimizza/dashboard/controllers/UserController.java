package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.models.users.User;
import br.com.ottimizza.dashboard.services.UserService;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = { "/users" })
public class UserController {

    @Inject
    UserService userService;

    @GetMapping(path = { "" })
    public ResponseEntity<List<User>> findAll(@RequestParam("page_size") Integer pageSize,
            @RequestParam("page_index") Integer pageIndex) throws Exception {
        return ResponseEntity.ok(userService.findAll(pageSize, pageIndex));
    }

    @GetMapping(path = { "{id}" })
    public ResponseEntity<Optional<User>> findById(@PathVariable("id") BigInteger id) throws Exception {
        return ResponseEntity.ok(userService.findById(id));
    }

}
