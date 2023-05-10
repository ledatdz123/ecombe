package com.example.ecombe.service;

import com.example.ecombe.entities.Category;
import com.example.ecombe.exception.ResourceNotFoundException;
import com.example.ecombe.payload.CategoryDto;
import com.example.ecombe.repository.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository catRepo;
    @Autowired
    private ModelMapper modelMapper;
    public CategoryDto create(CategoryDto dto){
        Category cat = this.modelMapper.map(dto, Category.class);
        Category save=this.catRepo.save(cat);
        return this.modelMapper.map(save, CategoryDto.class);
    }
    public CategoryDto update(CategoryDto newCat, int catId){
        Category oldCat=this.catRepo.findById(catId)
                .orElseThrow(()->new ResourceNotFoundException(catId+"id not found"));
        oldCat.setTitle(newCat.getTitle());
        Category save=this.catRepo.save(oldCat);
        return this.modelMapper.map(save, CategoryDto.class);
    }
    public void delete(int catId){
        Category cat=this.catRepo.findById(catId)
                .orElseThrow(()->new ResourceNotFoundException(catId+"id not found"));
        this.catRepo.delete(cat);
    }
    public CategoryDto getById(int catId){
        Category cat=this.catRepo.findById(catId)
                .orElseThrow(()->new ResourceNotFoundException(catId+"id not found"));
        return this.modelMapper.map(cat, CategoryDto.class);
    }
    public List<CategoryDto> getAll(){
        List<Category> listCat=this.catRepo.findAll();
        List<CategoryDto> dto=listCat.stream().map(cat->this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
        return dto;
    }
}
