package br.com.ottimizza.dashboard.services;

import br.com.ottimizza.dashboard.dao.SessionDAO;
import br.com.ottimizza.dashboard.dao.UserDAO;
import br.com.ottimizza.dashboard.models.Session;
import br.com.ottimizza.dashboard.models.users.User;
//import br.com.ottimizza.dashboard.models.users.UserWithContact;
import br.com.ottimizza.dashboard.models.Credentials;
import com.fasterxml.jackson.databind.ObjectMapper;
//import io.sentry.Sentry;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.UUID;
import javax.persistence.NoResultException;
import org.json.JSONObject;

public class AuthorizationService {

    private static final String SECRET_KEY = "asBej5xdAO1CxFL1";

    private static HashMap<String, BigInteger> tickets = new HashMap<>();

    //<editor-fold defaultstate="collapsed" desc="Tickets">
    public JSONObject generateTicket(User user) {
        JSONObject response = new JSONObject();

        String ticket = "";

        try {
            ticket = UUID.randomUUID().toString();

            if (!AuthorizationService.tickets.containsKey(ticket) && AuthorizationService.tickets.get(ticket) == null) {
                AuthorizationService.tickets.put(ticket, user.getId());
            }

            response.put("status", "success");
            response.put("ticket", ticket);

        } catch (Exception e) {
            //Sentry.capture(e);

            response.put("status", "error");
            response.put("message", "Something went wrong while generating a valid ticket!");

            return response;
        }

        return response;
    }

    public boolean validateTicket(String ticket, BigInteger userId) {
        boolean validTicket = Boolean.FALSE;
        try {
            // checks if there is a ticket stored
            if (AuthorizationService.tickets.containsKey(ticket) && AuthorizationService.tickets.get(ticket) != null) {

                BigInteger ticketUserIdReference = tickets.get(ticket);

                // checks if ticket belongs to the current user.
                if (userId.compareTo(ticketUserIdReference) == 0) {
                    try {
                        // removes the ticket from the map to keep uniqueness
                        tickets.remove(ticket);

                        validTicket = Boolean.TRUE;
                    } catch (Exception ex0) {
                        return false;
                    }
                }
            }
        } catch (Exception ex) {
            return false;
        }
        return validTicket;
    }//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Tokens">
    public JSONObject generateToken(Credentials credentials) throws Exception {
        JSONObject response = new JSONObject();

        String token = "";

        try {
            token = UUID.randomUUID().toString();

            UserDAO userDAO = new UserDAO();
            SessionDAO sessionDAO = new SessionDAO();

            String email = credentials.getEmail();
            String password = credentials.getPassword();

            User user = userDAO.getUserByEmailAndPassword(email, password, User.class);
            //UserWithContact userWithContact = userDAO.getUserById(user.getId(), UserWithContact.class);
            User userWithContact = userDAO.getUserById(user.getId(), User.class);

            Session authorizedToken = new Session();
            authorizedToken.setToken(token);
            authorizedToken.setUser(user);

            sessionDAO.create(authorizedToken);

            JSONObject record = new JSONObject();
            record.put("token", token);
            record.put("user", new JSONObject(new ObjectMapper().writeValueAsString(userWithContact)));

            response.put("status", "success");
            response.put("record", record);

        } catch (NoResultException ex0) {
            response.put("status", "error");
            response.put("message", "Invalid credentials.");

            throw new Exception(response.toString());
        } catch (Exception ex0) {
            response.put("status", "error");
            response.put("message", "Something went wrong.");
            throw new Exception(response.toString());
        }

        return response;
    }
    //</editor-fold>

}
