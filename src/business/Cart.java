package business;

import java.util.HashMap;

public class Cart {

    // Create an cart hashmap to store products    
    public HashMap cartItems = new HashMap();

    public String ID, name;
    public int qty;

    public Cart() {

    }

    public Cart(String ID, String name, int qty) {
        this.ID = ID;
        this.name = name;
        this.qty = qty;
    }
}
