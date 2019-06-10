package br.com.ottimizza.dashboard.repositories.user;

import br.com.ottimizza.dashboard.models.users.User;
import java.util.List;

public interface UserRepositoryCustom {

    User findByUsername(String username);

    List<User> findAll(Integer pageSize, Integer pageIndex);

    List<User> findUsersByEmail(String email);

    List<User> findUsersByEmail(String email, Integer pageSize, Integer pageIndex);

}
