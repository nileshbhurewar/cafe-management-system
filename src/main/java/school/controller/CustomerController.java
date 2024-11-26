package school.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import school.entity.CartItem;
import school.repositary.DishRepository;
import school.services.CartService;
import school.services.DishService;
import school.services.PaymentService;

@Controller
public class CustomerController {

    @Autowired
    private DishService dishService;

    @Autowired
    private CartService cartService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DishRepository dishRepository;

    // Display all dishes on the customer dashboard
    @GetMapping("/customer-dashboard")
    public String customerDashboard(Model model, HttpSession session) {
        model.addAttribute("dishes", dishService.getAllDishes());
        return "customer-dashboard";
    }

    // Add dish to cart
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long dishId, @RequestParam int quantity, HttpSession session) {
        cartService.addToCart(dishId, quantity, session, dishRepository);
        return "redirect:/add-to-cart";
    }

    // View items in the cart
    @GetMapping("/add-to-cart")
    public String viewCart(Model model, HttpSession session) {
        List<CartItem> cartItems = cartService.getCart(session);
        double totalPrice = cartService.calculateTotalPrice(cartItems);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("totalPrice", totalPrice);
        return "add-to-cart";
    }

    // Payment page
    @GetMapping("/payment")
    public String paymentPage() {
        return "payment";
    }

    // Process payment
    @PostMapping("/payment-success")
    public String paymentSuccess(@RequestParam String cardNumber, @RequestParam String expiryDate,
                                  @RequestParam String cvv, HttpSession session, Model model) {
        boolean paymentSuccessful = paymentService.processPayment(cardNumber, expiryDate, cvv);

        if (paymentSuccessful) {
            // Clear the cart after successful payment
            cartService.clearCart(session);
            model.addAttribute("paymentSuccess", true);
        } else {
            model.addAttribute("paymentSuccess", false);
        }

        return "payment";
    }
}
