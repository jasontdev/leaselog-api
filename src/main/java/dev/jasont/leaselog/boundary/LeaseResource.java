package dev.jasont.leaselog.boundary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.jasont.leaselog.entity.ClientPrincipal;
import dev.jasont.leaselog.service.LeaseService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestHeader;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Path("/leases")
public class LeaseResource {
    @Inject
    private ObjectMapper objectMapper;

    @Inject
    private LeaseService leaseService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllByUser(@RestHeader ("x-ms-client-principal") String credentialsHeaderEncoded) {
        // TODO: create helper function for credentials extraction
        try {
            var credentialsHeaderDecodedBytes = Base64.getDecoder().decode(credentialsHeaderEncoded);
            var decodedCredentialsHeader = new String(credentialsHeaderDecodedBytes, StandardCharsets.UTF_8);
            var clientPrincipal = objectMapper.readValue(decodedCredentialsHeader, ClientPrincipal.class);

            var leases = leaseService.getAllByUser(clientPrincipal);
            if(leases.isEmpty())
                return Response.status(404).build();

            return Response.ok(leases).build();
        } catch (JsonProcessingException e) {
            return Response.status(401).build();
        }
    }
}
