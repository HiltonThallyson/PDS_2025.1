package br.imd.framework.entities;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Table(name = "usuarios")
@Entity
@Getter
@Setter
@NoArgsConstructor

@AllArgsConstructor
public class User implements UserDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Username", nullable = false, unique = true)
    String username;

    String password;

    @Column(name = "Email", nullable = false, unique = true)
    String email;

    @Column(name = "NickName")
    String nickName;

    UserRole userRole;

    public User(String username, String password, String email, String nickName, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.userRole = userRole;
    }

    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(this.userRole == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        }else if(this.userRole == UserRole.USER) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return null;
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

}