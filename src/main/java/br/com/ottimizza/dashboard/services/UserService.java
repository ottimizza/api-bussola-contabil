package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.models.users.User;
import br.com.ottimizza.dashboard.repositories.user.UserRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Inject
    UserRepository userRepository;

    public User findByUsername(String username) throws Exception {
        return userRepository.findByUsername(username);
    }

    public List<User> findAll(Integer pageSize, Integer pageIndex) throws Exception {
        return userRepository.findAll(pageSize, pageIndex);
    }

    public Optional<User> findById(BigInteger id) throws Exception {
        return userRepository.findById(id);
    }

}
