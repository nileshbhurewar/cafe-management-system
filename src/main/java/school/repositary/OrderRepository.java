package school.repositary;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import school.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    // Custom queries to fetch orders by date or date range
    List<Order> findByDate(LocalDate date);  // Corrected to match the 'date' field

    List<Order> findByDateBetween(LocalDate startDate, LocalDate endDate);  // Corrected to match the 'date' field

    List<Order> findAllByOrderByDateDesc(); // To get the latest orders first (sorting by date)
}
