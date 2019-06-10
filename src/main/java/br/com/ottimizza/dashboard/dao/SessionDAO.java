package br.com.ottimizza.dashboard.dao;

import br.com.ottimizza.dashboard.constraints.Response;
import br.com.ottimizza.dashboard.dao.database.DatabaseConnection;
import br.com.ottimizza.dashboard.models.Session;
import br.com.ottimizza.dashboard.models.users.User;
import com.fasterxml.jackson.databind.ObjectMapper;
//import io.sentry.Sentry;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import org.json.JSONArray;
import org.json.JSONObject;

public class SessionDAO {

    public SessionDAO() {
    }

    public Session create(Session session) throws Exception {
        EntityManager em = DatabaseConnection.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(session);
            em.getTransaction().commit();
        } catch (Exception ex0) {
            //Sentry.capture(ex0);
            JSONObject response = Response.Defaults.getUnexpectedExceptiontMessage();
            throw new NoResultException(response.toString());
        } finally {
            em.close();
        }
        return session;
    }

    public Session update(BigInteger id, Session session) throws Exception {
        EntityManager em = DatabaseConnection.createEntityManager();
        try {
            session.setId(id);
            em.getTransaction().begin();
            em.merge(session);
            em.getTransaction().commit();
        } catch (Exception ex0) {
            //Sentry.capture(ex0);
            JSONObject response = Response.Defaults.getUnexpectedExceptiontMessage();
            throw new NoResultException(response.toString());
        } finally {
            em.close();
        }
        return session;
    }

    public User getUserByToken(String token) throws NonUniqueResultException, NoResultException, Exception {
        List<User> records = new ArrayList<>();
        EntityManager em = DatabaseConnection.createEntityManager();
        ObjectMapper mapper = new ObjectMapper();

        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select _user.* from authorized_tokens authorizedToken ");
            sb.append("   inner join users _user ");
            sb.append("       on (authorizedToken.fk_users_id = _user.id) ");
            sb.append(" where authorizedToken.token = :token ");

            Query query = em.createNativeQuery(sb.toString(), User.class);
            query.setParameter("token", token);

            records = query.getResultList();

            if (records.isEmpty()) {
                throw new NoResultException();
            } else if (records.size() > 1) {
                throw new NonUniqueResultException();
            }
        } catch (NoResultException ex1) {
            JSONObject response = Response.Defaults.getNoResultMessage();
            throw new NoResultException(response.toString());
        } catch (NonUniqueResultException ex2) {
            JSONArray results = new JSONArray(mapper.writeValueAsString(records));
            JSONObject response = Response.Defaults.getNonUniqueResultMessage(results);
            throw new NoResultException(response.toString());
        } catch (Exception ex0) {
            //Sentry.capture(ex0);
            JSONObject response = Response.Defaults.getUnexpectedExceptiontMessage();
            throw new NoResultException(response.toString());
        } finally {
            em.close();
        }
        return records.get(0);
    }

}
