package school.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import school.entity.Dish;
import school.services.DishService;

@Controller
@RequestMapping("/employee-dashboard/dishes")
public class DishController {

    private final DishService dishService;

    @Autowired
    public DishController(DishService dishService) {
        this.dishService = dishService;
    }

    // Show all dishes
    @GetMapping("/list")
    public String listDishes(Model model) {
        model.addAttribute("dishes", dishService.getAllDishes());
        return "dish_list"; // Returns a Thymeleaf view for listing dishes
    }

    // Show form to add a new dish
    @GetMapping("/add")
    public String showAddDishForm(Model model) {
        model.addAttribute("dish", new Dish());
        return "add_dish"; // Form for adding a new dish
    }

    // Handle the form submission to add a new dish
    @PostMapping("/add")
    public String addDish(@ModelAttribute Dish dish) {
        dishService.saveDish(dish);
        return "redirect:/employee-dashboard/dishes/list"; // Redirect to dish list
    }

    // Show form to update an existing dish
    @GetMapping("/update/{id}")
    public String showUpdateDishForm(@PathVariable Long id, Model model) {
        Dish dish = dishService.getDishById(id);
        model.addAttribute("dish", dish);
        return "update_dish"; // Form for updating an existing dish
    }

    // Handle the form submission to update a dish
    @PostMapping("/update/{id}")
    public String updateDish(@PathVariable Long id, @ModelAttribute Dish dish) {
        dish.setId(id);  // Ensure the ID is set for the existing dish
        dishService.saveDish(dish);
        return "redirect:/employee-dashboard/dishes/list"; // Redirect to dish list
    }

    // Handle the deletion of a dish
    @GetMapping("/delete/{id}")
    public String deleteDish(@PathVariable Long id) {
        dishService.deleteDish(id);
        return "redirect:/employee-dashboard/dishes/list"; // Redirect to dish list
    }
}

