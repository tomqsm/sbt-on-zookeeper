package biz.lwb.system.holder.authentication.controller;

import biz.lwb.system.holder.authentication.LwbholderClient;
import biz.lwb.system.holder.authentication.jwt.JwtRequest;
import biz.lwb.system.holder.authentication.jwt.JwtResponse;
import biz.lwb.system.holder.authentication.jwt.JwtTokenUtils;
import biz.lwb.system.holder.authentication.jwt.JwtUserDetailsService;
import biz.lwb.system.holder.inmemory.service.db.api.FiveServiceSpringData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class IndexController {

    @Autowired
    private ControllerProperties controllerProperties;

    @Autowired
    private LwbholderClient lwbholderClient;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping(value = "authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping(value = "/register")
    public ResponseEntity<?> saveUser(@RequestBody JwtRequest user) throws Exception {
        return ResponseEntity.ok(userDetailsService.save(user));
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("client")
    public List<FiveServiceSpringData> getClient(){
        return lwbholderClient.helloWorld();
    }

}
