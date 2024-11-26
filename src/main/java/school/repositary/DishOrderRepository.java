package school.repositary;

import org.springframework.data.jpa.repository.JpaRepository;
import school.entity.DishOrder;

public interface DishOrderRepository extends JpaRepository<DishOrder, Long> {}
