package com.example.ecombe.service;

import com.example.ecombe.entities.Category;
import com.example.ecombe.entities.Product;
import com.example.ecombe.exception.ResourceNotFoundException;
import com.example.ecombe.payload.CategoryDto;
import com.example.ecombe.payload.ProductDto;
import com.example.ecombe.payload.ProductResponse;
import com.example.ecombe.repository.CategoryRepository;
import com.example.ecombe.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepo;
    @Autowired
    private CategoryRepository catRepo;
    @Autowired
    private ModelMapper modelMapper;
    public ProductDto createProduct(ProductDto pDto, int catId){
        Category cat=this.catRepo.findById(catId)
                .orElseThrow(()->new ResourceNotFoundException(catId+" not found"));
        Product product=toEntity(pDto);
        product.setCategory(cat);
        Product save = this.productRepo.save(product);
        ProductDto dto=toDto(save);
        return dto;
    }
    public ProductResponse viewAll(int pageNumber, int pageSize, String sortBy, String sortDir){
        Sort sort=null;
        if (sortDir.trim().toLowerCase().equals("asc")){
            sort=Sort.by(sortBy).ascending();
        }else {
            sort=Sort.by(sortBy).descending();
        }
        Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page=this.productRepo.findAll(pageable);
        List<Product> pageProduct=page.getContent();
        List<Product> product=pageProduct.stream().filter(p -> p.isLive()).collect(Collectors.toList());
        List<ProductDto> productDto=product.stream().map(p -> this.toDto(p)).collect(Collectors.toList());
        ProductResponse response=new ProductResponse();
        response.setContent(productDto);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());

//        List<ProductDto> viewAllDto=viewAll
//                .stream()
//                .map(product -> this.toDto(product))
//                .collect(Collectors.toList());
        return response;
    }
    public ProductDto viewProductById(int productId){
        Product viewById=productRepo.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException(productId+" from this product id not found."));
        ProductDto dto=this.toDto(viewById);
        return dto;
    }
    public List<ProductDto> viewProductByCategory(int catId){
        Category cat=catRepo.findById(catId)
                .orElseThrow(()->new ResourceNotFoundException(catId+" from this category id not found."));
        List<Product> product=this.productRepo.findByCategory(cat);
        List<ProductDto> viewByCat=product
                .stream().map(p->this.toDto(p)).collect(Collectors.toList());
        return viewByCat;
    }
    public void deleteProduct(int productId){
        Product viewById=productRepo.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException(productId+" from this product id not found."));
        productRepo.delete(viewById);
    }
    public ProductDto updateProduct(int pid, ProductDto p){
        Product old=productRepo.findById(pid).orElseThrow(null);
        old.setProductName(p.getProductName());
        old.setLive(p.isLive());
        old.setStock(p.isStock());
        old.setProductPrice(p.getProductPrice());
        old.setProductDesc(p.getProductDesc());
        old.setImageName(p.getImageName());
        old.setProductQuantity(p.getProductQuantity());
        Product save=productRepo.save(old);
        ProductDto dto=this.toDto(save);
        return dto;
    }
    public Product toEntity(ProductDto pDto){
        Product p=new Product();
        p.setProductId(pDto.getProductId());
        p.setProductName(pDto.getProductName());
        p.setLive(pDto.isLive());
        p.setStock(pDto.isStock());
        p.setProductPrice(pDto.getProductPrice());
        p.setProductDesc(pDto.getProductDesc());
        p.setImageName(pDto.getImageName());
        p.setProductQuantity(pDto.getProductQuantity());
        return p;
    }
    public ProductDto toDto(Product p){
        ProductDto pDto=new ProductDto();
        pDto.setProductId(p.getProductId());
        pDto.setProductName(p.getProductName());
        pDto.setLive(p.isLive());
        pDto.setStock(p.isStock());
        pDto.setProductPrice(p.getProductPrice());
        pDto.setProductDesc(p.getProductDesc());
        pDto.setImageName(p.getImageName());
        pDto.setProductQuantity(p.getProductQuantity());
        CategoryDto catDto=new CategoryDto();
        catDto.setCategoryId(p.getCategory().getCategoryId());
        catDto.setTitle(p.getCategory().getTitle());
        Category category=this.modelMapper.map(catDto, Category.class);
        pDto.setCategory(category);
        return pDto;
    }
}
