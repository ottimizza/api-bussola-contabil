package br.com.ottimizza.dashboard.controllers;

import br.com.ottimizza.dashboard.dao.SessionDAO;
import br.com.ottimizza.dashboard.models.users.User;
import br.com.ottimizza.dashboard.models.Credentials;
import br.com.ottimizza.dashboard.services.AuthorizationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

@Path("auth")
public class AuthorizationController {

    @Context
    HttpServletRequest request;

    @POST
    @Path("token")
    //<editor-fold defaultstate="collapsed" desc="Http Token Request">
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response token(Credentials credentials) {
        try {
            AuthorizationService authorizationServide = new AuthorizationService();
            User user = (User) request.getAttribute("user");
            return Response.ok(authorizationServide.generateToken(credentials).toString()).build();
        } catch (Exception ex0) {
            return Response.ok(ex0.getMessage()).build();
        }
    }//</editor-fold>

    @GET
    @Path("authorize")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response authorize() {
        SessionDAO sessionDAO = new SessionDAO();

        try {
            User user = (User) request.getAttribute("user");

            JSONObject response = new JSONObject();
            response.put("status", "success");
            response.put("record", new ObjectMapper().writeValueAsString(user));

            return Response.ok(response.toString()).build();
        } catch (Exception ex0) {
            return Response.ok(ex0.getMessage()).build();
        }
    }

    @GET
    @Path("unauthorize")
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response unauthorize() {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @GET
    @Path("ws/ticket")
    //<editor-fold defaultstate="collapsed" desc="WebSocket Ticket Request">
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response ticket() {
        try {
            User user = (User) request.getAttribute("user");
            System.out.println(String.format("['/services/auth/ws/ticket'] -> %s", user.getEmail()));

            AuthorizationService authorizationServide = new AuthorizationService();
            JSONObject response = authorizationServide.generateTicket(user);

            return Response.ok(response.toString()).build();
        } catch (Exception ex0) {
            return Response.ok(ex0.getMessage()).build();
        }
    }
    //</editor-fold>

}
