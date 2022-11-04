package com.bwell.security;

import com.bwell.user.data.model.Credentials;
import com.bwell.user.data.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserPrincipal implements UserDetails, OAuth2User {
    public static final SimpleGrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
    private final String id;

    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(String id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User appUser){
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        UserPrincipal userPrincipal = new UserPrincipal(
                appUser.getCredentials().getId(),
                appUser.getCredentials().getEmail(),
                appUser.getCredentials().getPassword(),
                authorities
        );
        return userPrincipal;
    }
    public static UserPrincipal create(Credentials appUser){
        if (appUser == null) {
            return new UserPrincipal(null, null, null, Collections.emptyList());
        }
        List<GrantedAuthority> authorities = Collections.singletonList(ROLE_USER);
        UserPrincipal userPrincipal = new UserPrincipal(
                appUser.getId(),
                appUser.getEmail(),
                appUser.getPassword(),
                authorities
        );
        return userPrincipal;
    }

    public static UserPrincipal UserPrincipal(User appUser, Map<String, Object> attributes){
        UserPrincipal userPrincipal = create(appUser);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;

    }

    public boolean hasUserRole() {
        return authorities.contains(ROLE_USER);
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }


    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                ", attributes=" + attributes +
                '}';
    }
}
