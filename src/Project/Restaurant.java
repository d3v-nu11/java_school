package Project;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Restaurant extends Thread{
    private Menu menu;
    public static ArrayList<Order> ordersMade;
    private ArrayList<Order> pendingOrders;
    private PriorityQueue<OrderToStay> pendingOrdersToStay;
    private PriorityQueue<OrderTakeaway> pendingOrdersTakeaway;
    public static ArrayList<Employee> employees;
    private ArrayList<Employee> waiters;
    private ArrayList<Employee> deliveryMen;
    private ArrayList<Employee> cooks;
    private int numberOfCancelled;
    public Restaurant(Menu menu){
        this.menu = menu;
        ordersMade = new ArrayList<>();
        pendingOrders = new ArrayList<>();
        employees = new ArrayList<>();
        waiters = new ArrayList<>();
        deliveryMen = new ArrayList<>();
        cooks = new ArrayList<>();
        pendingOrdersToStay = new PriorityQueue<>();
        pendingOrdersTakeaway = new PriorityQueue<>();
        numberOfCancelled = 0;
    }
    public void makeAnOrder(Order order){
        pendingOrders.add(order);
        order.setOrderStart(System.currentTimeMillis());
        if(order.getClass()== OrderToStay.class)
            pendingOrdersToStay.add((OrderToStay) order);
        else
            pendingOrdersTakeaway.add((OrderTakeaway) order);
    }
    public ArrayList<Order> getOrdersMade(){
        return ordersMade;
    }
    public ArrayList<Order> getPendingOrders(){
        return this.pendingOrders;
    }
    public ArrayList<Employee> getEmployees(){
        return employees;
    }
    public int getNumberOfCancelled(){
        return numberOfCancelled;
    }

    public void addEmployee(Employee employee){
        if(employee.getClass()== Cook.class) {
            cooks.add(employee);
        }else if(employee.getClass()== Waiter.class){
            waiters.add(employee);
        }else{
            deliveryMen.add(employee);
        }
        employees.add(employee);
    }
    public void deleteEmployee(Employee employee){
        employees.remove(employee);
    }
    public String employeesToString(){
        String str = "";
        for (Employee e : employees) {
            str = str + e.toString() + "\n";
        }
        return str;
    }
    public void run(){
        while(!Thread.currentThread().isInterrupted()) {
            processingAnOrder : {
                if (!pendingOrdersToStay.isEmpty()) {
                    Order currentOrder = pendingOrdersToStay.poll();
                    if (currentOrder.isExpired()) {
                        int clientsChoice = (int) (Math.random() * 2);
                        if (clientsChoice == 0) {
                            pendingOrders.remove(currentOrder);
                            numberOfCancelled++;
                            break processingAnOrder;
                        }
                        currentOrder.setPrice(currentOrder.getPrice() - 0.2 * currentOrder.getPrice());
                    }
                    findWaiter:
                    {
                        for (Employee w : waiters) {
                            if (w.getIsAvailable()) {
                                currentOrder.setServiceId(w.getId());
                                waiters.remove(w);
                                waiters.add(w);
                                break findWaiter;
                            }
                        }

                    }
                    long workTime = currentOrder.getItems().size()*30500L - cooks.size()*500L;
                    currentOrder.setProcessingStart(System.currentTimeMillis());
                    try {
                        Thread.sleep(workTime);
                    } catch (InterruptedException e) {
                        return;
                    }
                    ordersMade.add(currentOrder);
                    pendingOrders.remove(currentOrder);
                    long processingTime = currentOrder.getProcessingTime();
                    if (processingTime < 900000L) {
                        long numOfMinutesBelow = 15 - (processingTime / 60000L);
                        for (Employee e : employees) {
                            if (e.getId() == currentOrder.getServiceId()) {
                                e.addOrderMade();
                                double tip = numOfMinutesBelow * (currentOrder.getPrice() * 0.007);
                                if(tip > (currentOrder.getPrice())*0.1)
                                    tip = currentOrder.getPrice()*0.1;
                                e.addTip(tip);
                            }
                        }
                    }else{
                        for (Employee e : employees) {
                            if (e.getId() == currentOrder.getServiceId()) {
                                e.addOrderMade();
                            }
                        }
                    }
                } else if (!pendingOrdersTakeaway.isEmpty()) {
                    Order currentOrder = pendingOrdersTakeaway.poll();
                    if (currentOrder.isExpired()) {
                        int clientsChoice = (int) (Math.random() * 2);
                        if (clientsChoice == 0) {
                            pendingOrders.remove(currentOrder);
                            numberOfCancelled++;
                            break processingAnOrder;
                        }
                        currentOrder.setPrice(currentOrder.getPrice() - 0.2 * currentOrder.getPrice());
                    }

                    findDeliveryMan:
                    {
                        do {
                            for (Employee d : deliveryMen) {
                                if (d.getIsAvailable()) {
                                    currentOrder.setServiceId(d.getId());
                                    break findDeliveryMan;
                                }
                            }
                        }while(true);
                    }
                    long workTime = currentOrder.getItems().size()*30500L - cooks.size()*500L;
                    currentOrder.setProcessingStart(System.currentTimeMillis());
                    try {
                        Thread.sleep(workTime);
                    } catch (InterruptedException e) {
                        return;
                    }
                    ordersMade.add(currentOrder);
                    pendingOrders.remove(currentOrder);
                    long processingTime = currentOrder.getProcessingTime();
                    if (processingTime < 780000L) {
                        long numOfMinutesBelow = 15 - (processingTime / 60000L);
                        for (Employee d : employees) {
                            if (d.getId() == currentOrder.getServiceId()) {
                                d.addOrderMade();
                                double tip = numOfMinutesBelow * (currentOrder.getPrice() * 0.007);
                                if(tip > (currentOrder.getPrice())*0.1)
                                    tip = currentOrder.getPrice()*0.1;
                                d.addTip(tip);
                                d.setDeliveryDeparture(System.currentTimeMillis());
                                d.setIsAvailable(false);
                                Thread delivery = new Thread((DeliveryMan)d);
                                delivery.start();
                            }
                        }
                    }else{
                        for (Employee e : employees) {
                            if (e.getId() == currentOrder.getServiceId()) {
                                e.addOrderMade();
                            }
                        }
                    }
                }
            }

        }
    }

}
