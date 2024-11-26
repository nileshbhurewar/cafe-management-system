package school.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import school.entity.Admin;
import school.entity.Customer;
import school.entity.Employee;
import school.repositary.AdminRepository;
import school.repositary.CustomerRepository;
import school.repositary.EmployeeRepository;

@Service
public class AuthService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AdminRepository adminRepository;

    public String registerCustomer(Customer customer) {
        // Check if email already exists
        if (customerRepository.findByEmailAndPassword(customer.getEmail(), customer.getPassword()) != null) {
            return "Customer already exists!";
        }
        customerRepository.save(customer);
        return "Registration successful!";
    }

    public String loginCustomer(String email, String password) {
        Customer customer = customerRepository.findByEmailAndPassword(email, password);
        return (customer != null) ? "/customer-dashboard.html" : "Invalid credentials";
    }

    public String loginEmployee(String username, String password) {
        Employee employee = employeeRepository.findByUsernameAndPassword(username, password);
        return (employee != null) ? "/employee-dashboard.html" : "Invalid credentials";
    }

    public String loginAdmin(String username, String password) {
        Admin admin = adminRepository.findByUsernameAndPassword(username, password);
        return (admin != null) ? "/admin-dashboard.html" : "Invalid credentials";
    }


    public String addEmployee(Employee employee) {
        employeeRepository.save(employee);
        return "Employee added successfully!";
    }
}
