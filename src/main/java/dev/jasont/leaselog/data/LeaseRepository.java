package dev.jasont.leaselog.data;

import dev.jasont.leaselog.entity.Contact;
import dev.jasont.leaselog.entity.Lease;
import dev.jasont.leaselog.entity.Unit;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class LeaseRepository {
    @Inject
    private DataSource dataSource;

    public List<Lease> getAllByUserId(UUID userId) {
        var query = "SELECT * FROM leases l JOIN contacts c ON l.contact_id=c.id WHERE l.user_id=?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement leasesQuery = connection.prepareStatement(query)) {
            leasesQuery.setString(1, userId.toString());
            var results = leasesQuery.executeQuery();

            var leases = new ArrayList<Lease>();

            while(results.next()) {
                var contact = new Contact();
                var lease = new Lease();

                lease.setId(UUID.fromString(results.getString("l.id")));
                lease.setName(results.getString("l.name"));
                lease.setStartDate(results.getDate("l.start_date").toLocalDate());

                var endDate = results.getDate("l.end_date");
                if(endDate != null) {
                    lease.setEndDate(results.getDate("l.end_date").toLocalDate());
                }
                lease.setRate(results.getBigDecimal("l.rate"));
                lease.setFrequency(results.getInt("l.frequency"));
                lease.setUnit(Unit.DAYS); // TODO: correctly assign Unit
                lease.setUserId(UUID.fromString(results.getString("l.user_id")));

                contact.setId(UUID.fromString(results.getString("c.id")));
                contact.setName(results.getString("c.name"));
                contact.setId(UUID.fromString(results.getString("c.user_id")));

                lease.setContact(contact);
                leases.add(lease);
            }

            return leases;
        } catch(SQLException exception) {
            return new ArrayList<Lease>();
        }
    }
}
