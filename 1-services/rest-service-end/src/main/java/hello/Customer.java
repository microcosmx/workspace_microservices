package hello;

import org.springframework.data.annotation.Id;


public class Customer {

    @Id
    public String id;

    public String state;
    public String lastName;

    public Customer() {}

    public Customer(String state, String lastName) {
        this.state = state;
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, state='%s', lastName='%s']",
                id, state, lastName);
    }

}

