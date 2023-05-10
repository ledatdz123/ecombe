package com.example.ecombe.controller;

import com.example.ecombe.exception.ResourceNotFoundException;
import com.example.ecombe.payload.JwtRequest;
import com.example.ecombe.payload.JwtResponse;
import com.example.ecombe.payload.UserDto;
import com.example.ecombe.security.JwtHelper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager manger;
    @Autowired
    private UserDetailsService userDetailSevice;
    @Autowired
    private JwtHelper helper;
    @Autowired
    private ModelMapper model;


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.autheticateUser(request.getUsername(),request.getPassword());
        UserDetails userDetails = this.userDetailSevice.loadUserByUsername(request.getUsername());
        String token=this.helper.generateToken(userDetails);
        JwtResponse response=new JwtResponse();
        response.setToken(token);
        response.setUser(this.model.map(userDetails, UserDto.class));

        return new ResponseEntity<JwtResponse>(response,HttpStatus.ACCEPTED);

    }

    private void autheticateUser(String Username ,String password){
        try {

            manger.authenticate(new UsernamePasswordAuthenticationToken(Username, password));
        }catch(BadCredentialsException e) {
            throw new ResourceNotFoundException("Invaild username or password");

        }catch(DisabledException e) {
            throw new ResourceNotFoundException("User is not active");
        }

    }

}
