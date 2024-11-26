package school.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import school.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByUsernameAndPassword(String username, String password);
    Employee findByUsername(String username);  // Method definition
}

