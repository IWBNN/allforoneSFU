package ac.su.allforonesfu.service.social;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ac.su.sfu.dto.UserDto;

import java.util.Collection;

@Getter
public class PrincipalDetails extends User implements UserDetails {

    private final UserDto user;

    public PrincipalDetails(UserDto user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUsername(), user.getPassword(), authorities);
        this.user = user;
    }
}
