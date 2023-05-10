package com.example.ecombe.controller;

import com.example.ecombe.payload.ApiResponse;
import com.example.ecombe.payload.CategoryDto;
import com.example.ecombe.payload.UserDto;
import com.example.ecombe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        Date date=new Date();
        userDto.setDate(date);
        UserDto createUser=this.userService.createUser(userDto);
        return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);
    }
    @GetMapping("findById/{userId}")
    public ResponseEntity<UserDto> getById(@PathVariable int uid){
        UserDto byId=this.userService.viewById(uid);
        return new ResponseEntity<>(byId, HttpStatus.FOUND);
    }
    @DeleteMapping("/del/{uid}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int uid){
        this.userService.delete(uid);
        return new ResponseEntity<ApiResponse>(new ApiResponse("deleted", true), HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<UserDto>> getAll(){
        List<UserDto> all=this.userService.getAll();
        return new ResponseEntity<List<UserDto>>(all, HttpStatus.OK);
    }
}
