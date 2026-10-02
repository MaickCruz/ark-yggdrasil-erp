package br.com.yggdrasil.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.yggdrasil.model.entity.User;
import br.com.yggdrasil.model.enums.UserRole;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = -1580255196750491830L;
	private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(user.getRole() == UserRole.ADMIN) {
        	return List.of(
        			new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_MANAGER"),
                    new SimpleGrantedAuthority("ROLE_SALESPERSON"));
        } else if (user.getRole() == UserRole.MANAGER) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_MANAGER"),
                    new SimpleGrantedAuthority("ROLE_SALESPERSON"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_SALESPERSON"));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public User getUser() {
        return user;
    }
}