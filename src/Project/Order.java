package Project;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Order {
    private ArrayList<Item> items;
    private int number;
    public static int numberOfOrders = 0;
    private LocalDateTime date;
    private long orderStart;
    private long processingStart;
    private double price;
    private int serviceId;
    public Order(){}
    public Order(ArrayList<Item> items){
        this.items=items;
        this.number = numberOfOrders+1;
        numberOfOrders++;
        this.date = LocalDateTime.now();
        for(Item i : items)
            price = price + i.getPrice();
    }
    public ArrayList<Item> getItems(){
        return this.items;
    }
    public LocalDateTime getDate(){
        return this.date;
    }
    public int getNumber(){
        return this.number;
    }
    public long getoOrderStart() {
        return orderStart;
    }
    public void setOrderStart(long orderStart){
        this.orderStart = orderStart;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price){
        this.price = price;
    }
    public boolean isExpired(){
        return System.currentTimeMillis()-orderStart > 900000L;
    }
    public int getServiceId() {
        return serviceId;
    }
    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }
    public long getProcessingTime() {
        return System.currentTimeMillis()-processingStart;
    }
    public void setProcessingStart(long processingStart) {
        this.processingStart = processingStart;
    }
    public void show(){
        items.forEach(i -> System.out.println("Item number: "+i.getItemNumber()+" name: "+i.getName()+" "));
    }

}
