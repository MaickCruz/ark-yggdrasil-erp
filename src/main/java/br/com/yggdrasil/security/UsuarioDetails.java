package br.com.yggdrasil.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.yggdrasil.model.entity.User;
import br.com.yggdrasil.model.enums.UserRole;

@SuppressWarnings("serial")
public class UsuarioDetails implements UserDetails {

    private final User usuario;

    public UsuarioDetails(User usuario) {
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(usuario.getRole() == UserRole.ADMIN) {
        	return List.of(
        			new SimpleGrantedAuthority("ROLE_ADMINISTRADOR"),
                    new SimpleGrantedAuthority("ROLE_GERENTE"),
                    new SimpleGrantedAuthority("ROLE_VENDEDOR"));
        } else if (usuario.getRole() == UserRole.MANAGER) {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_GERENTE"),
                    new SimpleGrantedAuthority("ROLE_VENDEDOR"));
        }
        return List.of(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public User getUsuario() {
        return usuario;
    }
}