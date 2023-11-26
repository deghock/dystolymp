package ru.spbu.distolymp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.spbu.distolymp.entity.users.Staff;

import java.util.Collection;

public class StaffDetails implements UserDetails {
    private final Staff staff;

    public StaffDetails(Staff staff) {
        this.staff = staff;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return this.staff.getPassword();
    }

    @Override
    public String getUsername() {
        return this.staff.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Staff getStaff() {
        return this.staff;
    }
}
