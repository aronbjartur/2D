package model;

import java.util.Objects;

// þetta er ekki alvöru og er bara mjög basic, ef við erum að gera þetta rosa flókið þá myndi þurfa helling af secruity dæmum og error höndlurum
// allt geymt raw líka eins og er


// tengt CheckoutController
public class Checkout {
    private final String customerName; 
    private final String email;        // Contact email
    private final String phoneNumber;  // veit ekki með þetta
    private final String creditCardNumber; 
    private final String creditCardExpiry; // MM/YY
    private final String creditCardCVV;    // 3 tölur að aftan

    public Checkout(String customerName, String email, String phoneNumber, String creditCardNumber, String creditCardExpiry, String creditCardCVV) {
        this.customerName = Objects.requireNonNull(customerName, "Customer name required");
        this.email = Objects.requireNonNull(email, "Email required");
        this.phoneNumber = phoneNumber; // Optional?
        this.creditCardNumber = Objects.requireNonNull(creditCardNumber, "Credit card number required");
        this.creditCardExpiry = Objects.requireNonNull(creditCardExpiry, "Credit card expiry required");
        this.creditCardCVV = Objects.requireNonNull(creditCardCVV, "Credit card CVV required");
    }

    // Getters 
    public String getCustomerName() { return customerName; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getCreditCardNumber() { return creditCardNumber; }
    public String getCreditCardExpiry() { return creditCardExpiry; }
    public String getCreditCardCVV() { return creditCardCVV; }

    @Override
    public String toString() {
        return "Checkout{" +
               "customerName='" + customerName + '\'' +
               ", email='" + email + '\'' +
               ", phoneNumber='" + phoneNumber + '\'' +
               ", creditCardNumber='**** **** **** " + (creditCardNumber.length() > 4 ? creditCardNumber.substring(creditCardNumber.length() - 4) : "****") + '\'' +
               '}'; //má kannski breyta credit card
    }
}