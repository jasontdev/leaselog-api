package dev.jasont.leaselog.service;

import dev.jasont.leaselog.data.LeaseRepository;
import dev.jasont.leaselog.entity.ClientPrincipal;
import dev.jasont.leaselog.entity.Lease;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class LeaseService {
    @Inject
    private LeaseRepository leaseRepository;
    @Inject
    private UserRepository userRepository;

    public List<Lease> getAllByUser(ClientPrincipal clientPrincipal) {
        // TODO: need to cache this
        var maybeUser = userRepository.getUserByOidcId(clientPrincipal.getUserId());

        if(maybeUser.isEmpty())
            return new ArrayList<>();

        return leaseRepository.getAllByUserId(maybeUser.get().getId());
    }
}
