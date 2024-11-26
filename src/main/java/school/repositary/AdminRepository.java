package school.repositary;

import org.springframework.data.jpa.repository.JpaRepository;

import school.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsernameAndPassword(String username, String password);
    Admin findByUsername(String username);  // Method definition

}
