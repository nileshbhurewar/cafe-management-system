package school.services;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    public boolean processPayment(String cardNumber, String expiryDate, String cvv) {
        // Simulate a payment process (dummy logic)
        return cardNumber != null && expiryDate != null && cvv != null;
    }
}

