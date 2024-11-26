package school.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import school.entity.CartItem;
import school.entity.Dish;
import school.repositary.DishRepository;

@Service
public class CartService {

	    // Store the cart in session (in-memory)
	    public List<CartItem> getCart(HttpSession session) {
	        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
	        if (cart == null) {
	            cart = new ArrayList<>();
	            session.setAttribute("cart", cart);
	        }
	        return cart;
	    }

	    public void addToCart(Long dishId, int quantity, HttpSession session, DishRepository dishRepository) {
	        Dish dish = dishRepository.findById(dishId).orElse(null);
	        if (dish != null) {
	            List<CartItem> cart = getCart(session);
	            Optional<CartItem> existingItem = cart.stream()
	                    .filter(item -> item.getDish().getId().equals(dishId))
	                    .findFirst();

	            if (existingItem.isPresent()) {
	                existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
	            } else {
	                cart.add(new CartItem(dish, quantity));
	            }
	        }
	    }

	    public double calculateTotalPrice(List<CartItem> cart) {
	        return cart.stream().mapToDouble(item -> item.getDish().getPrice() * item.getQuantity()).sum();
	    }

	    public void clearCart(HttpSession session) {
	        session.removeAttribute("cart");
	    }
	}


