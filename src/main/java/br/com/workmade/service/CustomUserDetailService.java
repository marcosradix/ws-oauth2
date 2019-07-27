package br.com.workmade.service;

import br.com.workmade.domain.Role;
import br.com.workmade.domain.User;
import br.com.workmade.exception.ObjectNotFoundException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = this.userService.findByEmail(email);
        if (!user.isPresent()){
            throw new UsernameNotFoundException("Usuário não existe");
        }else if(!user.get().isEnabled()){
            throw new ObjectNotFoundException("Usuário inativo");
        }
        return new UserRepositoryUserDetail(user.get());
    }


    private final List<GrantedAuthority> getGrantedAuthorities(final Collection<Role> roles){
        final List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role: roles ) {
            authorities.add(role);
        }
        return  authorities;
    }

    public final Collection<? extends GrantedAuthority> getAuthorites(final Collection<Role> roles){
        return getGrantedAuthorities(roles);
    }

    private final static class UserRepositoryUserDetail extends User implements UserDetails {
        UserRepositoryUserDetail(User user){super(user);}

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return getRoles();
        }

        @Override
        public String getUsername() {
            return getEmail();
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
    }


}
