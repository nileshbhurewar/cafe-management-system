package school.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.entity.Dish;
import school.entity.DishOrder;
import school.entity.Order;
import school.repositary.DishRepository;
import school.repositary.OrderRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final DishRepository dishRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository, DishRepository dishRepository) {
        this.orderRepository = orderRepository;
        this.dishRepository = dishRepository;
    }

    public Order placeOrder(String customerName, List<Long> dishIds, List<Integer> quantities) {
        // Create new Order
        Order order = new Order();
        order.setCustomerName(customerName);  // Set the customer's name
        order.setDate(LocalDate.now());       // Set the current date as the order date
        order.setStatus("Pending");           // Set default order status (e.g., "Pending")
        
        List<DishOrder> dishOrders = new ArrayList<>();
        double totalAmount = 0;

        // Create DishOrder entities for each selected dish
        for (int i = 0; i < dishIds.size(); i++) {
            Dish dish = dishRepository.findById(dishIds.get(i)).orElse(null);
            Integer quantity = quantities.get(i);

            if (dish != null) {
                DishOrder dishOrder = new DishOrder();
                dishOrder.setDish(dish);
                dishOrder.setQuantity(quantity);
                dishOrder.setPrice(dish.getPrice() * quantity);
                dishOrder.setOrder(order);

                dishOrders.add(dishOrder);
                totalAmount += dishOrder.getPrice();
            }
        }

        order.setDishOrders(dishOrders);
        order.setTotalAmount(totalAmount);

        // Save the order to the database
        return orderRepository.save(order);  // Saving and returning the saved order
    }
    
 // Method to get all orders
    public List<Order> getAllOrders() {
        return orderRepository.findAll(); // Fetch all orders from the repository
    }


    // Method to get orders by date
    public List<Order> getOrdersByDate(LocalDate date) {
        return orderRepository.findByDate(date);  // Corrected to match the field name 'date'
    }

    // Method to get orders between two dates
    public List<Order> getOrdersBetweenDates(LocalDate startDate, LocalDate endDate) {
        return orderRepository.findByDateBetween(startDate, endDate);  // Corrected to match the field name 'date'
    }
}
