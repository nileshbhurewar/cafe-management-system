package school.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import school.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmailAndPassword(String email, String password);
}

