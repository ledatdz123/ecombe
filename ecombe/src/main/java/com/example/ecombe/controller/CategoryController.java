package com.example.ecombe.controller;

import com.example.ecombe.payload.ApiResponse;
import com.example.ecombe.payload.CategoryDto;
import com.example.ecombe.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    private CategoryDto categoryDto;
    @PostMapping("/create")
    public ResponseEntity<CategoryDto> create(@RequestBody CategoryDto dto){
        CategoryDto create=this.categoryService.create(dto);
        return new ResponseEntity<CategoryDto>(create, HttpStatus.CREATED);
    }
    @PutMapping("/update/{catId}")
    public ResponseEntity<CategoryDto> update(@PathVariable int catId,@RequestBody CategoryDto dto){
        CategoryDto update=this.categoryService.update(dto, catId);
        return new ResponseEntity<CategoryDto>(update, HttpStatus.OK);
    }
    @DeleteMapping("/del/{catId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable int catId){
        this.categoryService.delete(catId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("deleted", true), HttpStatus.OK);
    }
    @GetMapping("/getById/{catId}")
    public ResponseEntity<CategoryDto> getById(@PathVariable int catId){
        CategoryDto dto=this.categoryService.getById(catId);
        return new ResponseEntity<CategoryDto>(dto, HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<CategoryDto>> getAll(){
        List<CategoryDto> all=this.categoryService.getAll();
        return new ResponseEntity<List<CategoryDto>>(all, HttpStatus.OK);
    }
}
