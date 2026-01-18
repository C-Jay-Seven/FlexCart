package com.potragis.FlexCart.service.cart;

import com.potragis.FlexCart.dto.cart.CartResponse;
import com.potragis.FlexCart.dto.cartItems.CartItemsRequest;
import com.potragis.FlexCart.mapper.cart.CartDTOMapper;
import com.potragis.FlexCart.model.entity.Cart;
import com.potragis.FlexCart.model.entity.CartItems;
import com.potragis.FlexCart.model.entity.Product;
import com.potragis.FlexCart.repository.ICartRepository;
import com.potragis.FlexCart.repository.IClientRepository;
import com.potragis.FlexCart.repository.IProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class                                                                                                                                                                    CartItemService implements ICartService{

    private final ICartRepository cartRepository;
    private final IClientRepository clientRepository;
    private final IProductRepository productRepository;
    private final CartDTOMapper mapper;

    @Override
    public CartResponse getCart(Long clientId) {
        Cart cart = cartRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return mapper.toDTO(cart);
    }

    @Override
    public CartResponse addToCart(Long clientId, CartItemsRequest cartItemsRequest) {

        Cart cart = cartRepository.findByClientId(clientId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setClient(clientRepository.findById(clientId)
                    .orElseThrow(() -> new RuntimeException("Client not found")));
            newCart.setTotalPrice(BigDecimal.ZERO);
            newCart.setCartItems(new java.util.ArrayList<>());
            return cartRepository.save(newCart);
        });

        Product product = productRepository.findById(cartItemsRequest.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItems> existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();

        CartItems items;

        if(existingItem.isPresent()) {
            items = existingItem.get();
            int newQuantity = items.getQuantity() + cartItemsRequest.getQuantity();
            items.setQuantity(newQuantity);
            items.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(newQuantity)));
        } else {
            items = new CartItems();
            items.setCart(cart);
            items.setProduct(product);
            items.setQuantity(cartItemsRequest.getQuantity());
            items.setUnitPrice(product.getPrice());
            items.setSubtotal(product.getPrice().multiply(BigDecimal.valueOf(cartItemsRequest.getQuantity())));
            cart.getCartItems().add(items);
        }

        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(CartItems::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);

        return mapper.toDTO(cart);
    }
    
    @Override
    public CartResponse updateCart(Long clientId, Long cartItemId, CartItemsRequest cartItemsRequest) {
        Cart cart = cartRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItems cartItems = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItems.setQuantity(cartItemsRequest.getQuantity());

        BigDecimal newSubtotal = BigDecimal.valueOf(cartItemsRequest.getQuantity())
                .multiply(cartItems.getUnitPrice());

        cartItems.setSubtotal(newSubtotal);

        BigDecimal newTotalPrice = cart.getCartItems().stream()
                .map(CartItems::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(newTotalPrice);

        cartRepository.save(cart);

        return mapper.toDTO(cart);
    }

    @Override
    public CartResponse deleteCartItem(Long clientId, Long cartItemId) {
        Cart cart = cartRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItems cartItems = cart.getCartItems().stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cart.getCartItems().remove(cartItems);

        BigDecimal totalPrice = cart.getCartItems().stream()
                .map(CartItems::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);

        return mapper.toDTO(cart);
    }
}
