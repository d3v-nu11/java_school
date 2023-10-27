package Project;

import java.util.ArrayList;

public class OrderToStay extends Order implements Comparable<OrderToStay> {

    private int tableNumber;
    public OrderToStay(ArrayList<Item> items, int tableNumber){
        super(items);
        this.tableNumber = tableNumber;
    }
    public int getTableNumber(){
        return this.tableNumber;
    }

    @Override
    public int compareTo(OrderToStay z) {
        return this.getDate().compareTo(z.getDate());
    }
}
