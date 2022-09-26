package model;

import java.util.regex.Pattern;

public class Customer {

    public final String firstName;
    public final String lastName;
    public final String email;

    public final String emailRegex = "^(.+)@(.+).(.+)$";
    public final Pattern pattern = Pattern.compile(emailRegex);

    public Customer(String firstName, String lastName, String email) {
            if(!pattern.matcher(email).matches()) {
                throw new IllegalArgumentException("Error, Invalid email");
            }

            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public String getEmail() { return this.email; }
        public String getFirstName() {return this.firstName;}
        public String getLastName() {return this.lastName;}

        @Override
        public String toString() {
            return "first name: " + firstName + "\nlast name: " + lastName + "\nemail: " + email +"\n";
    }
}
