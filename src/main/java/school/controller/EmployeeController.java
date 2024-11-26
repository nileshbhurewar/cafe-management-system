package school.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import school.entity.Dish;
import school.entity.Order;
import school.services.OrderService;
import school.repositary.DishRepository;
import school.repositary.OrderRepository;

@Controller
@RequestMapping("/employee-dashboard")
public class EmployeeController {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;
     // here employee can order dishes generate a bill
    
    @GetMapping("/profile")
    public String employeeprofile() {
    	return "employee-profile";
    }
    
    @GetMapping("/orders")
    public String orderPage(Model model) {
        List<Dish> dishes = dishRepository.findAll();
        model.addAttribute("dishes", dishes);
        return "order-page";  // Thymeleaf view
    }

    @PostMapping("/place-order")
    public String placeOrder(@RequestParam String customerName, 
                             @RequestParam List<Long> dishIds, 
                             @RequestParam List<Integer> quantities, 
                             Model model) {
        // Call service to handle order placement with the customer name
        Order order = orderService.placeOrder(customerName, dishIds, quantities);
        model.addAttribute("orderSuccess", "Order placed successfully! Total: $" + order.getTotalAmount());
        return "redirect:/employee-dashboard/orders"; // Redirect to orders page
    }


    @GetMapping("/view-orders")
    public String viewOrders(Model model) {
        List<Order> orders = orderService.getAllOrders(); // Fetch latest orders
        model.addAttribute("orders", orders);
        model.addAttribute("today", LocalDate.now());
        return "order_list"; // Thymeleaf view for displaying orders
    }

    @PostMapping("/filter-orders")
    public String filterOrders(@RequestParam("date") String date, Model model) {
        LocalDate selectedDate = LocalDate.parse(date);
        List<Order> orders = orderService.getOrdersByDate(selectedDate);
        model.addAttribute("orders", orders);
        model.addAttribute("today", selectedDate);
        return "order_list"; // Thymeleaf view for filtered orders
    }

    @PostMapping("/filter-date-range")
    public String filterDateRange(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, Model model) {
        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);
        List<Order> orders = orderService.getOrdersBetweenDates(start, end);
        model.addAttribute("orders", orders);
        return "order_list"; // Thymeleaf view for date range filtered orders
    }
    
    // here user can view details about a order by orderId
    @GetMapping("/order/{id}")
    public String viewOrderDetails(@PathVariable Long id, Model model) {
        // Fetch the order by its ID from the repository
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid order id: " + id));
        
        // Add the order to the model
        model.addAttribute("order", order);
        
        // Return the view name (you can create a view like 'order_details.html')
        return "order_details";
    }


}
