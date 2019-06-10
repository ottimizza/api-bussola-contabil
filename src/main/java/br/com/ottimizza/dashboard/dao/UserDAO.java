package br.com.ottimizza.dashboard.dao;

import br.com.ottimizza.dashboard.constraints.Response;
import br.com.ottimizza.dashboard.dao.database.DatabaseConnection;
//import br.com.ottimizza.comunicacao.entities.organizations.Organization;
import br.com.ottimizza.dashboard.models.users.User;
import com.fasterxml.jackson.databind.ObjectMapper;
//import io.sentry.Sentry;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author dmazu
 */
public class UserDAO {

    public User get(BigInteger id) {
        EntityManager em = DatabaseConnection.createEntityManager();

        return em.getReference(User.class, id);
    }

    public <T> T persist(T user) throws Exception {
        EntityManager em = DatabaseConnection.createEntityManager();

        try {
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        } catch (Exception ex0) {
            //Sentry.capture(ex0);

            ex0.printStackTrace();

            JSONObject response = new JSONObject();
            response.put("status", "error");
            response.put("message", "Algo deu errado cadastrando o usuário!");

            throw new Exception(response.toString());
        } finally {
            em.close();
        }
        return user;
    }

    public <T> T merge(T user) throws Exception {
        EntityManager em = DatabaseConnection.createEntityManager();

        try {
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();

        } catch (Exception ex0) {
            //Sentry.capture(ex0);

            ex0.printStackTrace();

            JSONObject response = new JSONObject();
            response.put("status", "error");
            response.put("message", "Algo deu errado cadastrando o usuário!");

            throw new Exception(response.toString());
        } finally {
            em.close();
        }
        return user;
    }

//    public User save(User record) throws Exception {
//        EntityManager em = DatabaseConnection.createEntityManager();
//
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            JSONObject resource = new JSONObject(mapper.writeValueAsString(record));
//
//            OrganizationDAO organizationDAO = new OrganizationDAO();
//
//            Set<Organization> organizations = record.getOrganizations();
//            record.setOrganizations(new HashSet<>());
//
//            if (organizations.size() > 0) {
//                for (Organization organization : organizations) {
//                    try {
//                        if (organization.getId() != null) {
//                            organization = organizationDAO.getOrganizationById(
//                                    organization.getId(), Organization.class
//                            );
//                            organization = organizationDAO.save(organization.getId(), organization);
//                        } else if (organization.getExternalId() != null && !organization.getExternalId().equals("")) {
//                            try {
//                                organization = organizationDAO.getOrganizationByExternalId(
//                                        organization.getExternalId(), Organization.class
//                                );
//
//                                organization.setName(organization.getName());
//
//                                organization = organizationDAO.save(organization.getId(), organization);
//                            } catch (NoResultException e) {
//                                if (organization.getName() != null && !organization.getName().equals("")) {
//                                    organization = organizationDAO.save(organization);
//                                }
//                            }
//                        } else {
//                            throw new Exception(Response.Defaults.getResponse(
//                                    "error", "Por favor informe pelo menos uma organização para o usuário!", resource
//                            ).toString());
//                        }
//                        record.getOrganizations().add(organization);
//                    } catch (NoResultException ex0) {
//                        throw new Exception(Response.Defaults.getResponse(
//                                "error", "Não foi possível encontrar a organização informada!", resource
//                        ).toString());
//                    } catch (Exception ex0) {
//                        throw new Exception(Response.Defaults.getResponse(
//                                "error", "Ocorreu um erro ao buscar a organização do usuário!", resource
//                        ).toString());
//                    }
//                }
//            } else {
//                throw new Exception(Response.Defaults.getResponse(
//                        "error", "Por favor informe pelo menos uma organização para o usuário!", resource
//                ).toString());
//            }
//
//            record.setOrganizations(record.getOrganizations());
//
//            em.getTransaction().begin();
//            em.persist(record);
//            em.getTransaction().commit();
//
//            StringBuilder sb = new StringBuilder();
//            sb.append(" insert into users_organizations ");
//            sb.append(" (fk_users_id, fk_organizations_id) values ");
//
//            int i = 0;
//            for (Organization o : record.getOrganizations()) {
//                if (o.getId() != null) {
//                    i++;
//                    sb.append(" (:userId, ").append(o.getId()).append(")");
//                }
//                if (i < record.getOrganizations().size()) {
//                    sb.append(", ");
//                }
//            }
//            sb.append(" on conflict do nothing ");
//
//            Query query = em.createNativeQuery(sb.toString());
//            query.setParameter("userId", record.getId());
//
//            em.getTransaction().begin();
//            query.executeUpdate();
//            em.getTransaction().commit();
//
//        } catch (Exception ex0) {
//            ex0.printStackTrace();
//            //Sentry.capture(ex0);
//            JSONObject message = new JSONObject();
//            message.put("exception", "Exception");
//            message.put("entity", "contact");
//
//            JSONObject exception = new JSONObject();
//            exception.put("status", "error");
//            exception.put("message", message);
//
//            throw new Exception(exception.toString());
//
//        } finally {
//            em.close();
//        }
//        return record;
//    }
//
//    public User save(BigInteger id, User record) throws Exception {
//        EntityManager em = DatabaseConnection.createEntityManager();
//
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            JSONObject resource = new JSONObject(mapper.writeValueAsString(record));
//
//            OrganizationDAO organizationDAO = new OrganizationDAO();
//
//            Set<Organization> organizations = record.getOrganizations();
//            record.setOrganizations(new HashSet<>());
//
//            if (organizations.size() > 0) {
//
//                em.getTransaction().begin();
//                em.createNativeQuery(" delete from users_organizations where fk_users_id = :userId ")
//                        .setParameter("userId", id).executeUpdate();
//                em.getTransaction().commit();
//
//                for (Organization organization : organizations) {
//                    try {
//                        if (organization.getId() != null) {
//                            organization = organizationDAO.getOrganizationById(
//                                    organization.getId(), Organization.class
//                            );
//                        } else if (organization.getExternalId() != null && !organization.getExternalId().equals("")) {
//                            try {
//                                organization = organizationDAO.getOrganizationByExternalId(
//                                        organization.getExternalId(), Organization.class
//                                );
//                            } catch (NoResultException e) {
//                                if (organization.getName() != null && !organization.getName().equals("")) {
//                                    organization = organizationDAO.save(organization);
//                                }
//                            }
//                        } else {
//                            throw new Exception(Response.Defaults.getResponse(
//                                    "error", "Por favor informe pelo menos uma organização para o usuário!", resource
//                            ).toString());
//                        }
//                        record.getOrganizations().add(organization);
//                    } catch (NoResultException ex0) {
//                        throw new Exception(Response.Defaults.getResponse(
//                                "error", "Não foi possível encontrar a organização informada!", resource
//                        ).toString());
//                    } catch (Exception ex0) {
//                        throw new Exception(Response.Defaults.getResponse(
//                                "error", "Ocorreu um erro ao buscar a organização do usuário!", resource
//                        ).toString());
//                    }
//                }
//            } else {
//                throw new Exception(Response.Defaults.getResponse(
//                        "error", "Por favor informe pelo menos uma organização para o usuário!", resource
//                ).toString());
//            }
//
//            organizations = record.getOrganizations();
//
//            record.setId(id);
//
//            em.getTransaction().begin();
//            em.merge(record);
//            em.getTransaction().commit();
//
//            StringBuilder sb = new StringBuilder();
//            sb.append(" insert into users_organizations ");
//            sb.append(" (fk_users_id, fk_organizations_id) values ");
//
//            int i = 0;
//            for (Organization o : organizations) {
//                if (o.getId() != null) {
//                    i++;
//                    sb.append(" (:userId, ").append(o.getId()).append(")");
//                }
//                if (i < organizations.size()) {
//                    sb.append(", ");
//                }
//            }
//            sb.append(" on conflict do nothing ");
//
//            Query query = em.createNativeQuery(sb.toString());
//            query.setParameter("userId", record.getId());
//
//            em.getTransaction().begin();
//            query.executeUpdate();
//            em.getTransaction().commit();
//        } catch (Exception ex0) {
//
//            ex0.printStackTrace();
//
//            //Sentry.capture(ex0);
//
//            JSONObject message = new JSONObject();
//            message.put("exception", "Exception");
//            message.put("entity", "contact");
//
//            JSONObject exception = new JSONObject();
//            exception.put("status", "error");
//            exception.put("message", message);
//
//            throw new Exception(exception.toString());
//
//        } finally {
//            em.close();
//        }
//        return record;
//    }

    //<editor-fold defaultstate="collapsed" desc="Get User By ID.">    
    public <T> T getUserById(BigInteger id, Class<? extends T> clazz) throws NoResultException, NonUniqueResultException, Exception {
        List<T> records = new ArrayList<>();
        EntityManager em = DatabaseConnection.createEntityManager();

        ObjectMapper mapper = new ObjectMapper();

        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select * from users _user ");
            sb.append(" where _user.id = ? ");

            Query query = em.createNativeQuery(sb.toString(), clazz);
            query.setParameter(1, id);

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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get User By Email.">    
    public <T> T getUserByEmail(String email, Class<? extends T> clazz) throws NoResultException, NonUniqueResultException, Exception {
        List<T> records = new ArrayList<>();
        EntityManager em = DatabaseConnection.createEntityManager();

        ObjectMapper mapper = new ObjectMapper();

        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select * from users _user ");
            sb.append(" where _user.email = :email ");

            Query query = em.createNativeQuery(sb.toString(), clazz);
            query.setParameter("email", email);

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
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Get User By Email And Password.">    
    public <T> T getUserByEmailAndPassword(String email, String password, Class<? extends T> clazz)
            throws NoResultException, NonUniqueResultException, Exception {
        List<T> records = new ArrayList<>();
        EntityManager em = DatabaseConnection.createEntityManager();

        ObjectMapper mapper = new ObjectMapper();

        try {
            StringBuilder sb = new StringBuilder();
            sb.append(" select * from users _user ");
            sb.append(" where _user.email = :email ");
            sb.append(" and _user.password = :password ");

            Query query = em.createNativeQuery(sb.toString(), clazz);
            query.setParameter("email", email);
            query.setParameter("password", password);

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
            throw new NonUniqueResultException(response.toString());
        } catch (Exception ex0) {
            //Sentry.capture(ex0);
            JSONObject response = Response.Defaults.getUnexpectedExceptiontMessage();
            throw new Exception(response.toString());
        } finally {
            em.close();
        }
        return records.get(0);
    }
    //</editor-fold>
}
