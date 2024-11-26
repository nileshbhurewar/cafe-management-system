package school.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import school.entity.Dish;

public interface DishRepository extends JpaRepository<Dish, Long> {
    // JpaRepository provides CRUD methods like save() automatically
}
