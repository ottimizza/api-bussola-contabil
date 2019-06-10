package br.com.ottimizza.dashboard.repositories.user;

import br.com.ottimizza.dashboard.models.users.User;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, BigInteger>, UserRepositoryCustom {

}
