package com.example.ecombe.service;

import com.example.ecombe.entities.Category;
import com.example.ecombe.entities.Product;
import com.example.ecombe.entities.User;
import com.example.ecombe.exception.ResourceNotFoundException;
import com.example.ecombe.payload.CategoryDto;
import com.example.ecombe.payload.UserDto;
import com.example.ecombe.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    public UserDto createUser(UserDto userDto){
        User user=this.modelMapper.map(userDto, User.class);
        String encode=this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        User saveUser=this.userRepo.save(user);
        UserDto saveDto=this.modelMapper.map(saveUser, UserDto.class);
        return saveDto;
    }
    public UserDto viewById(int uid){
        User fin=this.userRepo.findByUserId(uid);
        UserDto dto=this.modelMapper.map(fin, UserDto.class);
        return dto;
    }
    public void delete(int uid){
        User viewById=this.userRepo.findById(uid)
                .orElseThrow(()->new ResourceNotFoundException(uid+" from this product id not found."));
        this.userRepo.delete(viewById);
    }
    public List<UserDto> getAll(){
        List<User> listCat=this.userRepo.findAll();
        List<UserDto> dto=listCat.stream().map(cat->this.modelMapper.map(cat, UserDto.class)).collect(Collectors.toList());
        return dto;
    }
}
