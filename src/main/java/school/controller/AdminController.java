package school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import school.entity.Employee;
import school.repositary.EmployeeRepository;

@Controller
public class AdminController {

	@Autowired
    private EmployeeRepository employeeRepository;
	
	// Page to create an employee
    @GetMapping("/employee-create")
    public String createEmployeePage() {
        return "employee-create";
    }
    
    // Handle employee creation
    @PostMapping("/employee-create")
    public String createEmployee(@RequestParam String username, @RequestParam String password, Model model) {
        Employee employee = new Employee(null, username, password);
        employeeRepository.save(employee);
        model.addAttribute("message", "Employee created successfully");
        model.addAttribute("message", "Welcome aboard, " + employee.getUsername() + "! We're excited to have you at Cafe Management.");
        return "employee-create";
    }
    
 // Page to update employee
    @GetMapping("/employee-update")
    public String updateEmployeePage(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee-update";
    }

 // Update Employee (using the ID)
    @PostMapping("/employee-update")
    public String updateEmployee(@RequestParam Long id, @RequestParam String newUsername, @RequestParam String newPassword, Model model) {
        Employee employee = employeeRepository.findById(id).orElse(null);
        if (employee != null) {
            employee.setUsername(newUsername);
            employee.setPassword(newPassword);
            employeeRepository.save(employee);
            model.addAttribute("message", "Employee updated successfully");
        } else {
            model.addAttribute("message", "Employee not found");
        }
        return "employee-update";  // Return a page with the update result
    }
    
 // Page to delete employee
    @GetMapping("/employee-delete")
    public String deleteEmployeePage(Model model) {
        model.addAttribute("employees", employeeRepository.findAll());
        return "employee-delete";
    }
    
 // Delete Employee (by ID)
    @PostMapping("/employee-delete")
    public String deleteEmployee(@RequestParam Long id, Model model) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            model.addAttribute("message", "Employee deleted successfully");
        } else {
            model.addAttribute("message", "Employee not found");
        }
        return "employee-delete";  // Return a page with the delete result
    }
    
    // View all Employees
    @GetMapping("/employee-view-all")
    public String viewAllEmployees(Model model) {
        Iterable<Employee> employees = employeeRepository.findAll();
        model.addAttribute("employees", employees);
        return "employee-view-all";  // Return a page displaying the list of employees
    }
    
}
