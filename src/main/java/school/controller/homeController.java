package school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import school.entity.Admin;
import school.entity.Contact;
import school.entity.Customer;
import school.entity.Employee;
import school.repositary.AdminRepository;
import school.repositary.ContactRepository;
import school.repositary.CustomerRepository;
import school.repositary.EmployeeRepository;


@Controller
public class homeController {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private ContactRepository contactRepository;


    // Home Page
    @GetMapping("/")
    public String homePage() {
        return "Home";
    }
    // about-us page
    @RequestMapping("/aboutus")
    public String aboutus() {
    	return "aboutus";
    }
    // contact-us page
    @RequestMapping("/contactus")
    	public String contactus() {
    		return "contactus";
    }
    
    @PostMapping("/contact-us")
    public String saveContactDetails(
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam String message,
            Model model) {

        // Create and save the contact object
        Contact contact = new Contact();
        contact.setName(name);
        contact.setEmail(email);
        contact.setMessage(message);

        contactRepository.save(contact);

        // Pass success message
        model.addAttribute("success", "Your message has been sent successfully!");

        // Render the contact form with success message
        return "contactus";
    }


    // Login Type Selection
    @GetMapping("/login-type")
    public String loginType() {
        return "logintype";
    }

    // Customer Login
    @GetMapping("/customer-login")
    public String customerLogin() {
        return "customer-login";
    }

    @PostMapping("/customer-login")
    public String customerLogin(@RequestParam String email, @RequestParam String password, Model model) {
        Customer customer = customerRepository.findByEmailAndPassword(email, password);

        if (customer != null) {
            // Redirect to the customer dashboard if the credentials are correct
            return "redirect:/customer-dashboard";
        } else {
            // Add error message and return the login page if credentials are invalid
            model.addAttribute("error", "Invalid credentials");
            return "customer-login"; // This will return the customer-login.html page with the error message
        }
    }



    // Employee Login
    @GetMapping("/employee-login")
    public String employeeLogin() {
        return "employee-login";
    }

    @PostMapping("/employee-login")
    public String employeeLogin(@RequestParam String username, @RequestParam String password, Model model) {
        Employee employee = employeeRepository.findByUsername(username);
        if (employee != null && employee.getPassword().equals(password)) {
            return "redirect:/employee-dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "employee-login";
        }
    }

    // Admin Login
    @GetMapping("/admin-login")
    public String adminLogin() {
        return "admin-login";
    }

    @PostMapping("/admin-login")
    public String adminLogin(@RequestParam String username, @RequestParam String password, Model model) {
        Admin admin = adminRepository.findByUsername(username); // Fetch by username
        if (admin != null && admin.getPassword().equals(password)) { // Check password
            return "redirect:/admin-dashboard";
        } else {
            model.addAttribute("error", "Invalid credentials");
            return "admin-login";
        }
    }


    // Customer Registration
    @GetMapping("/customer-registration")
    public String customerRegistration() {
        return "customeregistration";
    }

    @PostMapping("/customer-registration")
    public String registerCustomer(@ModelAttribute Customer customer,
                                   @RequestParam String confirmPassword, RedirectAttributes redirectAttributes) {
        if (!customer.getPassword().equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("error", "Passwords do not match");
            return "redirect:/customer-registration";
        }

        // Hash password here if needed, using PasswordEncoder
        customerRepository.save(customer);
        redirectAttributes.addFlashAttribute("success", "Registration successful! Please login.");
        return "redirect:/customer-login";
    }


//    // Dashboard for customer
//    @GetMapping("/customer-dashboard")
//    public String customerDashboard() {
//        return "customer-dashboard";
//    }

    // Dashboard for employee
    @GetMapping("/employee-dashboard")
    public String employeeDashboard() {
        return "employee-dashboard";
    }

    // Dashboard for admin
    @GetMapping("/admin-dashboard")
    public String adminDashboard() {
        return "admin-dashboard";
    }
}
