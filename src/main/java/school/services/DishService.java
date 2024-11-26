package school.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import school.entity.Dish;
import school.repositary.DishRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    private final DishRepository dishRepository;

    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    // Save or update a dish
    public void saveDish(Dish dish) {
        dishRepository.save(dish);
    }

    // Get all dishes
    public List<Dish> getAllDishes() {
        return dishRepository.findAll();
    }

    // Get a dish by its ID
    public Dish getDishById(Long id) {
        return dishRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid dish id: " + id));
    }

    // Delete a dish by its ID
    public void deleteDish(Long id) {
        dishRepository.deleteById(id);
    }
}

