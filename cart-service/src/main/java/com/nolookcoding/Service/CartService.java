import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart createCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public void updateCart(int cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found")); // Replace with a custom exception in real code
        cart.setQuantity(quantity);
        cartRepository.save(cart);
    }

    public void deleteCart(int cartId) {
        cartRepository.deleteById(cartId);
    }

    public List<Cart> getCartsByUserId(int userId) {
        return cartRepository.findByUserId(userId);
    }
}
