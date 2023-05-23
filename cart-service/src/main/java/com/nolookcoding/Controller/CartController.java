import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<Cart> createCart(@PathVariable int userId, @RequestBody Cart cart) {
        cart.setUserId(userId);
        Cart createdCart = cartService.createCart(cart);
        return ResponseEntity.ok(createdCart);
    }

    @PutMapping("/{cartId}/quantity")
    public ResponseEntity<Cart> updateCart(@PathVariable int cartId, @RequestBody int quantity) {
        cartService.updateCart(cartId, quantity);
        Cart updatedCart = cartService.getCartById(cartId); // You need to add this method to your service
        return ResponseEntity.ok(updatedCart);
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<Void> deleteCart(@PathVariable int cartId) {
        cartService.deleteCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Cart>> getCartsByUserId(@PathVariable int userId) {
        List<Cart> carts = cartService.getCartsByUserId(userId);
        return ResponseEntity.ok(carts);
    }
}
