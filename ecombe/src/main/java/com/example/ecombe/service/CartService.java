package com.example.ecombe.service;

import com.example.ecombe.entities.Cart;
import com.example.ecombe.entities.CartItem;
import com.example.ecombe.entities.Product;
import com.example.ecombe.entities.User;
import com.example.ecombe.exception.ResourceNotFoundException;
import com.example.ecombe.payload.CartDto;
import com.example.ecombe.payload.ItemRequest;
import com.example.ecombe.repository.CartRepository;
import com.example.ecombe.repository.ProductRepository;
import com.example.ecombe.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartService {
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private CartRepository cartRepo;
    @Autowired
    private ModelMapper modelMapper;
    public CartDto addItem(ItemRequest item, String username){
        int productId=item.getProductId();
        int quantity=item.getQuantity();
        //fetch user
        User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User not found"));
        //fetch Product
        Product product=this.productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product Not Found"));

        //here we are checking product stock
        if(!product.isStock()){

            new ResourceNotFoundException("Product Out of Stock");
        }

        // create cartItem with productId and Qnt

        CartItem cartItem=new CartItem();
        cartItem.setProduct(product);
        cartItem.setQuantity(quantity);
        double totaleprice=product.getProductPrice()*quantity;
        cartItem.setTotalprice(totaleprice);

        //getting cart from user
        Cart cart=user.getCart();

        if(cart==null) {
            cart=new Cart();
            //
            cart.setUser(user);
        }

        cartItem.setCart(cart);

        Set<CartItem> items = cart.getItems();

        // here we check item is available in CartItem or not
        // if CartItem is availabe then we inc Qnt only else
        // add new product in cartItem
        //
        // by default false
        AtomicReference<Boolean> flag=new AtomicReference<>(false);

        Set<CartItem> newproduct = items.stream().map((i)->{
            if(i.getProduct().getProductId()==product.getProductId()) {
                i.setQuantity(quantity);
                i.setTotalprice(totaleprice);
                flag.set(true);
            }
            return i;

        }).collect(Collectors.toSet());

        if(flag.get()){
            items.clear();
            items.addAll(newproduct);

        }else {
            cartItem.setCart(cart);
            items.add(cartItem);
        }

        Cart saveCart = this.cartRepo.save(cart);



        return  this.modelMapper.map(saveCart,CartDto.class);
    }
    public CartDto getcartAll(String email){
        //find user
        User user = this.userRepo.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User Not Found"));
        //find cart
        Cart cart= this.cartRepo.findByUser(user).orElseThrow(()->new ResourceNotFoundException("There is no cart"));

        return this.modelMapper.map(cart,CartDto.class);

    }
    public CartDto getCartByID(int cartId){

        //User user=this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User Not found"));

        Cart findByUserAndcartId = this.cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart not Found"));

        return this.modelMapper.map(findByUserAndcartId,CartDto.class);
    }

    public CartDto removeCartItemFromCart(String userName, int ProductId){
        User user=this.userRepo.findByEmail(userName).orElseThrow(()->new ResourceNotFoundException("User Not found"));

        Cart cart=user.getCart();
        Set<CartItem> items = cart.getItems();

        boolean removeIf = items.removeIf((i)->i.getProduct().getProductId()==ProductId);
        Cart save = this.cartRepo.save(cart);
        System.out.println(removeIf);
        return this.modelMapper.map(save,CartDto.class);
    }
}
