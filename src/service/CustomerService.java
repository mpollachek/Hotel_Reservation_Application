package service;

import model.Customer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomerService {

    // static reference
    private static CustomerService Singleton = new CustomerService();

    private CustomerService() {}

    public static CustomerService getSingleton() {
        return Singleton;
    }

    private Map<String, Customer> customers = new HashMap<>();

    public void addCustomer(String firstName, String lastName, String email) {
        customers.put(email, new Customer(firstName, lastName, email));
    }

    public Customer getCustomer(String customerEmail){
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers() {
        return customers.values();
    }

    public Set<String> getAllCustomersEmails() { return customers.keySet(); }

}
