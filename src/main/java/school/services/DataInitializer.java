package school.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import school.entity.Admin;
import school.entity.Employee;
import school.entity.Dish;
import school.repositary.AdminRepository;
import school.repositary.EmployeeRepository;
import school.repositary.DishRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final DishRepository dishRepository;

    @Autowired
    public DataInitializer(AdminRepository adminRepository, EmployeeRepository employeeRepository, DishRepository dishRepository) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.dishRepository = dishRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Insert default Admin if not already present
        if (adminRepository.count() == 0) {
            Admin defaultAdmin = new Admin(null, "admin", "admin123");
            adminRepository.save(defaultAdmin);
            System.out.println("Default admin user created");
        }

        // Insert default Employee if not already present
        if (employeeRepository.count() == 0) {
            Employee defaultEmployee = new Employee(null, "employee", "employee123");
            employeeRepository.save(defaultEmployee);
            System.out.println("Default employee user created");
        }

        // Insert default dishes if not already present
        if (dishRepository.count() == 0) {
            dishRepository.save(new Dish(null, "Margherita Pizza", "Classic cheese pizza", 8.99, "images/margherita.jpg", null));
            dishRepository.save(new Dish(null, "Pasta Alfredo", "Creamy white sauce pasta", 7.99, "images/alfredo.jpg", null));
            dishRepository.save(new Dish(null, "Veggie Burger", "Loaded with fresh veggies", 6.49, "images/veggie_burger.jpg", null));
            System.out.println("Default dishes added");
        }
    }
}
