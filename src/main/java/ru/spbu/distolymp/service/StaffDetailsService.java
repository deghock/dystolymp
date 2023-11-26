package ru.spbu.distolymp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.spbu.distolymp.entity.users.Staff;
import ru.spbu.distolymp.repository.users.StaffRepository;
import ru.spbu.distolymp.security.StaffDetails;

import java.util.Optional;

@Service
public class StaffDetailsService implements UserDetailsService {
    private final StaffRepository staffRepository;

    @Autowired
    public StaffDetailsService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Staff> staff = staffRepository.findByLogin(s);

        if (staff.isEmpty())
            throw new UsernameNotFoundException("User not found");

        return new StaffDetails(staff.get());
    }
}
