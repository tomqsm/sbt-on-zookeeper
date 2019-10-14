package biz.lwb.system.holder.authentication.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private PasswordEncoder bcryptEncoder;

    Map<String, String> requestMap = new HashMap<>();

    public UserDetails loadUserByUsername(String username) {
        return new User(username,requestMap.get(username), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
    }

    public Object save(JwtRequest user) {
        requestMap.put(user.getUsername(), bcryptEncoder.encode(user.getPassword()));
        return requestMap;
    }
}
