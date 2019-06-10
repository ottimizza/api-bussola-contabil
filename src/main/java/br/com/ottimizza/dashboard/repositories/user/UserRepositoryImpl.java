package br.com.ottimizza.dashboard.repositories.user;

import br.com.ottimizza.dashboard.models.users.QUser;
import br.com.ottimizza.dashboard.models.users.User;
import com.querydsl.jpa.impl.JPAQuery;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepositoryCustom {

    @PersistenceContext
    EntityManager em;

    private QUser user = QUser.user;

    @Override
    public User findByUsername(String username) {
        JPAQuery<User> query = new JPAQuery<User>(em).from(user).where(user.username.like("%" + username + "%"));

        return query.orderBy(user.email.desc()).fetch().get(0);
    }

    @Override
    public List<User> findAll(Integer pageSize, Integer pageIndex) {
        JPAQuery<User> query = new JPAQuery<User>(em).from(user);

        if (pageSize != null && pageSize > 0) {
            query.limit(pageSize);
            if (pageIndex != null && pageIndex > 0) {
                query.offset(pageSize * pageIndex);
            }
        }

        return query.orderBy(user.email.asc()).fetch();
    }

    @Override
    public List<User> findUsersByEmail(String email) {
        JPAQuery<User> query = new JPAQuery<User>(em).from(user).where(user.email.like("%" + email + "%"));

        return query.orderBy(user.email.desc()).fetch();
    }

    @Override
    public List<User> findUsersByEmail(String email, Integer pageSize, Integer pageIndex) {
        JPAQuery<User> query = new JPAQuery<User>(em).from(user).where(user.email.like("%" + email + "%"));

        if (pageSize != null && pageSize > 0) {
            query.limit(pageSize);
            if (pageIndex != null && pageIndex > 0) {
                query.offset(pageSize * pageIndex);
            }
        }

        return query.orderBy(user.email.asc()).fetch();
    }

}
