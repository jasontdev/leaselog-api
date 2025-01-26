package dev.jasont.leaselog.service;

import dev.jasont.leaselog.entity.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserRepository {
    @Inject
    private DataSource dataSource;

    public Optional<User> getUserByOidcId(String oidcId) {
        var query = "SELECT * FROM users u WHERE u.oidc_id=?";

        try(Connection connection = dataSource.getConnection();
            PreparedStatement getUserQuery = connection.prepareStatement(query))
        {
            getUserQuery.setString(1, oidcId);
            var results = getUserQuery.executeQuery();

            results.next();
            var user = new User();
            user.setId(UUID.fromString(results.getString("id")));
            user.setOidcId(results.getString("oidc_id"));

            return Optional.of(user);
        } catch (SQLException e) {
            // should really throw an exception here to distinguish between errors
            // and users not existing
            return Optional.empty();
        }
    }
}
