package com.example.ecombe.controller;

import com.example.ecombe.entities.Product;
import com.example.ecombe.payload.AppConstants;
import com.example.ecombe.payload.ProductDto;
import com.example.ecombe.payload.ProductResponse;
import com.example.ecombe.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @PostMapping("/create/{catId}")
    @ResponseBody
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto pDto,  @PathVariable int catId){
        ProductDto createProduct=productService.createProduct(pDto, catId);
        return new ResponseEntity<ProductDto>(createProduct, HttpStatus.CREATED);
    }
    @GetMapping("/viewAll")
    public ProductResponse viewAllProduct(
            @RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER_STRING, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE_STRING, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY_STRING, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR_STRING, required = false) String sortDir){
        ProductResponse viewAll=productService.viewAll(pageNumber, pageSize, sortBy, sortDir);
        return viewAll;
    }
    @GetMapping("/view/{productId}")
    public ResponseEntity<ProductDto> viewProductById(@PathVariable int productId){
        ProductDto viewById=productService.viewProductById(productId);
        return new ResponseEntity<ProductDto>(viewById, HttpStatus.OK);
    }
    @GetMapping("/category/{catId}")
    public ResponseEntity<List<ProductDto>> viewProductByCategory(@PathVariable int catId){
        List<ProductDto> viewByCat=productService.viewProductByCategory(catId);
        return new ResponseEntity<List<ProductDto>>(viewByCat, HttpStatus.ACCEPTED);
    }
    @DeleteMapping("/del/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable int productId){
        productService.deleteProduct(productId);
        return new ResponseEntity<>("Product deleted", HttpStatus.OK);
    }
    @PutMapping("/update/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable int productId, @RequestBody ProductDto newProduct){
        ProductDto updateProduct=productService.updateProduct(productId, newProduct);
        return new ResponseEntity<ProductDto>(updateProduct, HttpStatus.ACCEPTED);
    }
}
