package demo.security;

import demo.model.User;
import demo.repository.SpringDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;


@Service
public class MyUserDetailsService implements UserDetailsService {
    private SpringDAO userDao;
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public MyUserDetailsService(SpringDAO springDAO, BCryptPasswordEncoder passwordEncoder){
        userDao=springDAO;
        this.passwordEncoder=passwordEncoder;
    }
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        User user=userDao.findByUserName(s);
        if (user==null){
            System.out.println("this fucking if clause got executed");
            throw new UsernameNotFoundException(s+"not found");
        }
        System.out.println("this fucking method got executed");
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return new ArrayList<>();
            }

            @Override
            public String getPassword() {
                return passwordEncoder.encode(user.getPasswd());
            }

            @Override
            public String getUsername() {
                return user.getUserName();
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
        };
    }
}
