package dev.jasont.leaselog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jasont.leaselog.entity.ClientPrincipal;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestHeader;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Path("/hello")
public class GreetingResource {
    @Inject
    private ObjectMapper objectMapper;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(@RestHeader("x-ms-client-principal") String credentialsHeaderEncode) {
        try {
            var credentialsHeaderDecodedBytes = Base64.getDecoder().decode(credentialsHeaderEncode);
            var decodedCredentialsHeader = new String(credentialsHeaderDecodedBytes, StandardCharsets.UTF_8);
            var clientPrincipal = objectMapper.readValue(decodedCredentialsHeader, ClientPrincipal.class);
;
            return Response.ok(clientPrincipal).build();
        } catch (JsonProcessingException e) {
            return Response.status(401).build();
        }
    }
}
