package Project;

import java.util.ArrayList;

public class OrderTakeaway extends Order implements Comparable<OrderTakeaway> {
    private String address;
    private String phoneNumber;
    public OrderTakeaway(ArrayList<Item> items, String address, String phoneNumber){
        super(items);
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    public String getAddress(){
        return this.address;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    @Override
    public int compareTo(OrderTakeaway z) {
        return this.getDate().compareTo(z.getDate());
    }
}
