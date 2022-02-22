package api.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    ROLE_ADMIN, ROLE_HR, ROLE_USER;

    public String getAuthority() {
        return name();
    }
}