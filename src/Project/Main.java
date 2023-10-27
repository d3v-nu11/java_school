package Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main {

    static Menu menu = new Menu();
    static Restaurant restaurant = new Restaurant(menu);


    public static void main(String[] args) throws InterruptedException {
        String username = System.getProperty("user.name");
        try {
            File file = new File("C:\\Users\\"+username+"\\menu.txt");
            if (file.createNewFile()) {
                System.out.println("New file created 'C:\\Users\\"+username+"\\menu.txt'");
            } else {
                Scanner myReader = new Scanner(file);
                for(Item i : loading(myReader)){
                    menu.add(i);
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading a menu a file");
            e.printStackTrace();
        }
        try {
            File file = new File("C:\\Users\\"+username+"\\employees.txt");
            if (file.createNewFile()) {
                System.out.println("New file created 'C:\\Users\\"+username+"\\employees.txt'");
            } else {
                Scanner myReader = new Scanner(file);
                for(Employee e : loadingEmployees(myReader)){
                    restaurant.addEmployee(e);
                }
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading an employees file");
            e.printStackTrace();
        }

        while(true) {
            startingScreen();
            Scanner in = new Scanner(System.in);
            int choice = in.nextInt();
            switch (choice){
                case 1: {
                    modifyMenu();
                    in = new Scanner(System.in);
                    choice = in.nextInt();
                    switch (choice) {
                        case 1:
                            menu.add(addItem());
                            System.out.println("Item added");
                            break;
                        case 2:
                            menu.delete(deleteItem());
                            System.out.println("Item deleted");
                            break;
                        case 3:
                            menu.changeAvailability(modifyAvailability());
                            System.out.println("Availability changed");
                            break;
                        case 4:
                            menu.show();
                            break;
                        default:
                            System.out.println("Invalid input");
                            break;
                    }
                }
                break;
                case 2: menu.show();
                    break;
                case 3: System.out.println("Provide full path of the file to be loaded e.g.'C:\\Users\\User\\file.txt'\n"+
                        "Remember to separate name, description and price using '|'. Each item in file should be written\n"+
                        "in new line and follow this syntax: 'item num | name | description | price'");
                    Scanner in1 = new Scanner(System.in);
                    String pathToRead = in1.nextLine();
                    try {
                        File file = new File(pathToRead);
                        if (file.exists()){
                            Scanner myReader = new Scanner(file);
                            for(Item i : loading(myReader)){
                                menu.add(i);
                            }
                        }else{
                            System.out.println("File with provided path doesnt exists");
                        }
                    }catch (IOException e) {
                        System.out.println("Error occurred while loading a file");
                        e.printStackTrace();
                    }
                    System.out.println("File "+pathToRead+" loaded successfully");
                    break;
                case 4: System.out.println("Provide full path to file where menu will be stored ('C:\\...\\filename.txt");
                    Scanner input = new Scanner(System.in);
                    String pathToWrite = input.nextLine();
                    try {
                        FileWriter myWriter = new FileWriter(pathToWrite);
                        myWriter.write(menu.toString());
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.println("Error occurred while writing to file");
                        e.printStackTrace();
                    }
                    System.out.println("Menu saved in "+pathToWrite);
                    break;
                case 5: placeAnOrder();
                    break;
                case 6: showOrders();
                    break;
                case 7: viewEmployees();
                    break;
                case 8: restaurant.start();
                    System.out.println("Restaurant is open");
                    break;
                case 9: if(restaurant.getPendingOrders().size()==0){
                    try{
                        restaurant.interrupt();
                        System.out.println("Restaurant is closed");
                    }catch (Exception e){
                        return;
                    }
                }else{
                    System.out.println("Restaurant hasnt made all of the orders yet. Try again later");
                }
                    break;
                case 0: try {
                    FileWriter myWriter = new FileWriter("C:\\Users\\"+username+"\\menu.txt");
                    myWriter.write(menu.toString());
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("Error occurred while saving menu");
                    e.printStackTrace();
                }
                    System.out.println("Menu saved in 'C:\\Users\\"+username+"\\menu.txt'");
                    try {
                        FileWriter myWriter = new FileWriter("C:\\Users\\"+username+"\\employees.txt");
                        myWriter.write(restaurant.employeesToString());
                        myWriter.close();
                    } catch (IOException e) {
                        System.out.println("Error occurred while saving employees");
                        e.printStackTrace();
                    }
                    System.out.println("Employees saved in 'C:\\Users\\"+username+"\\employees.txt'");
                    System.exit(0);
                default: System.out.println("Invalid input");
                    break;
            }
        }

    }
    public static void startingScreen(){
        System.out.println("\nTo modify menu press 1 \nTo show menu press 2 \n" +
                "To load menu from file press 3 (supports only .txt files)\nTo save menu to a file press 4\nTo place an order press 5\n" +
                "To show orders press 6\nTo go to 'employees' press 7\n" +
                "To open the restaurant press 8\nTo close the restaurant press 9\nTo exit press 0");
    }
    public static void modifyMenu(){
        System.out.println("To add an item press 1 \nTo delete an item press 2 \n" +
                "To change the availability of an item press 3 \nTo show the menu press 4 \n" +
                "To exit press 0");
    }
    public static Item addItem(){
        System.out.println("item's name: ");
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        System.out.println("item's description: ");
        Scanner in1 = new Scanner(System.in);
        String description = in1.nextLine();
        System.out.println("item's price: ");
        Scanner in2 = new Scanner(System.in);
        double price = in2.nextDouble();
        Item item = new Item(name, description, price);
        return item;
    }
    public static int deleteItem() {
        System.out.println("item's number: ");
        Scanner in = new Scanner(System.in);
        int number = in.nextInt();
        while(number > Item.number) {
            System.out.println("There is no item with this number");
            System.out.println("item's number: ");
            in = new Scanner(System.in);
            number = in.nextInt();
        }
        return number;
    }
    public static ArrayList<Integer> modifyAvailability(){
        ArrayList<Integer> numbers = new ArrayList<>();
        System.out.println("item's number: ");
        Scanner in = new Scanner(System.in);
        int number = in.nextInt();
        while(number > Item.number) {
            System.out.println("There is no item with this number");
            System.out.println("item's number: ");
            in = new Scanner(System.in);
            number = in.nextInt();
        }
        numbers.add(number);
        System.out.println("To set an item as unavailable press 0 \n" +
                "To set an item as available press 1");
        in = new Scanner(System.in);
        number = in.nextInt();
        numbers.add(number);
        return numbers;
    }
    public static ArrayList<Item> loading(Scanner myReader){
        ArrayList<Item> items = new ArrayList<>();
        while(myReader.hasNextLine()) {
            String data = myReader.nextLine();
            ArrayList<Integer> numberOfSeparators = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == '|') {
                    numberOfSeparators.add(i);
                }
            }
            List<String> words = Arrays.stream(data.split(" \\| ",numberOfSeparators.size()+1)).toList();
            Item item = new Item(words.get(1), words.get(2), Double.parseDouble(words.get(3)));
            if (words.get(words.size()-1).equals("unavailable")) {
                item.setIsAvailable(false);
            }
            items.add(item);
        }
        return items;
    }
    public static ArrayList<Employee> loadingEmployees(Scanner myReader){
        ArrayList<Employee> employees = new ArrayList<>();
        while(myReader.hasNextLine()) {
            String data = myReader.nextLine();
            ArrayList<Integer> numberOfSeparators = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                if (data.charAt(i) == '|') {
                    numberOfSeparators.add(i);
                }
            }
            List<String> words = Arrays.stream(data.split(" \\| ",numberOfSeparators.size()+1)).toList();
            if(words.get(words.size()-1).equals("cook")){
                Cook cook = new Cook(Integer.parseInt(words.get(0)),words.get(1),words.get(2),words.get(3));
                employees.add(cook);
            }else if(words.get(words.size()-1).equals("waiter")){
                Waiter waiter = new Waiter(Integer.parseInt(words.get(0)),words.get(1),words.get(2),words.get(3));
                employees.add(waiter);
            }else if(words.get(words.size()-1).equals("deliveryMan")){
                DeliveryMan deliveryMan = new DeliveryMan(Integer.parseInt(words.get(0)),words.get(1),words.get(2),words.get(3));
                employees.add(deliveryMan);
            }
        }
        ArrayList<Integer> ids = new ArrayList<>();
        employees.forEach(x -> ids.add(x.getId()));
        Employee.idNumber = Collections.max(ids);
        return employees;
    }
    public static void placeAnOrder(){
        System.out.println("To place an order to stay press 1\nTo place a takeaway order press 2\n"+
                "To place a random order press 3\nTo go back press 0");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice){
            case 1: {
                System.out.println("Type the number of items you want to order separated by space");
                Scanner in1 = new Scanner(System.in);
                String numbersInput = in1.nextLine();
                List<Integer> numbers = Arrays.stream(numbersInput.split("\\s+")).map(x -> Integer.parseInt(x)).toList();
                for (int n : numbers){
                    for (Item i : menu.getMenu()){
                        if(n==i.getItemNumber() && !i.getIsAvailable()){
                            System.out.println("Item number "+n+" is unavailable\nTo order additional item press 1\n" +
                                    "To continue without this item press 0");
                            Scanner in2 = new Scanner(System.in);
                            int choice2 = in2.nextInt();
                            switch (choice2) {
                                case 1:
                                    System.out.println("Type item's number you want to add to your order");
                                    Scanner in3 = new Scanner(System.in);
                                    String input = in3.nextLine();
                                    if (input.length() == 1) {
                                        numbers.add(Integer.parseInt(input));
                                    }else {
                                        List<Integer> numbersToAdd = Arrays.stream(input.split("\\s+")).map(x -> Integer.parseInt(x)).toList();
                                        numbers.addAll(numbersToAdd);
                                    }
                                case 0:
                                    numbers.remove(n);
                                    break;
                                default:
                                    numbers.remove(n);
                                    System.out.println("Invalid input");
                                    break;
                            }
                        }
                    }
                }
                placeAnOrderToStay(numbers);
            }
            break;
            case 2: {
                System.out.println("Type the number of items you want to order separated by space");
                Scanner in1 = new Scanner(System.in);
                String numbersInput = in1.nextLine();
                List<Integer> numbers = Arrays.stream(numbersInput.split("\\s+")).map(x -> Integer.parseInt(x)).toList();
                for (int n : numbers){
                    for (Item i : menu.getMenu()){
                        if(n==i.getItemNumber() && !i.getIsAvailable()){
                            System.out.println("Item number "+n+" is unavailable\nTo order additional item press 1\n" +
                                    "To continue without this item press 0");
                            Scanner in2 = new Scanner(System.in);
                            int choice2 = in2.nextInt();
                            switch (choice2) {
                                case 1:
                                    System.out.println("Type item's number you want to add to your order");
                                    Scanner in3 = new Scanner(System.in);
                                    String input = in3.nextLine();
                                    if (input.length() == 1) {
                                        numbers.add(Integer.parseInt(input));
                                    }else {
                                        List<Integer> numbersToAdd = Arrays.stream(input.split("\\s+")).map(x -> Integer.parseInt(x)).toList();
                                        numbers.addAll(numbersToAdd);
                                    }
                                case 0:
                                    numbers.remove(n);
                                    break;
                                default:
                                    System.out.println("Invalid input");
                                    numbers.remove(n);
                                    break;
                            }
                        }
                    }
                }
                placeAnOrderTakeaway(numbers);
            }
            break;
            case 3: {
                ArrayList<Integer> numbers = new ArrayList<>();
                int random = (int)(Math.random()*2);
                int numberOfItems = (int)(Math.random()*15)+1;
                if(random == 0){
                    for (int i = 1; i <= numberOfItems; i++) {
                        int itemsNumber = (int)(Math.random()* Item.number)+1;
                        for(Item item : menu.getMenu()){
                            if(itemsNumber == item.getItemNumber() && item.getIsAvailable()){
                                numbers.add(itemsNumber);
                            }
                        }
                    }
                    if(numbers.isEmpty()) {
                        for (Item i : menu.getMenu()) {
                            if (i.getIsAvailable()) {
                                numbers.add(i.getItemNumber());
                            }
                        }
                    }
                    System.out.println("Order to stay was drawn containing "+ numbers.size()+" items");
                    placeAnOrderToStay(numbers);
                }else{
                    for (int i = 1; i <= numberOfItems; i++) {
                        int itemsNumber = (int)(Math.random()* Item.number)+1;
                        for(Item item : menu.getMenu()){
                            if(itemsNumber == item.getItemNumber() && item.getIsAvailable()){
                                numbers.add(itemsNumber);
                            }
                        }
                    }
                    if(numbers.isEmpty()) {
                        for (Item i : menu.getMenu()) {
                            if (i.getIsAvailable()) {
                                numbers.add(i.getItemNumber());
                            }
                        }
                    }
                    System.out.println("Takeaway order was drawn containing "+numbers.size()+" items");
                    placeAnOrderTakeaway(numbers);
                }
            }
            break;
            case 0: break;
            default: System.out.println("Invalid input");
                break;

        }
    }
    public static void placeAnOrderToStay(List<Integer> numbers){
        System.out.println("Provide table's number");
        Scanner in = new Scanner(System.in);
        int tablesNumber = in.nextInt();
        ArrayList<Item> items = new ArrayList<>();
        for (Item i : menu.getMenu()){
            for (int n : numbers){
                if(i.getItemNumber()==n){
                    items.add(i);
                }
            }
        }
        OrderToStay orderToStay = new OrderToStay(items,tablesNumber);
        restaurant.makeAnOrder(orderToStay);
        System.out.println("Order was successfully placed");
    }
    public static void placeAnOrderTakeaway(List<Integer> numbers){
        System.out.println("Provide delivery address");
        Scanner in = new Scanner(System.in);
        String address = in.nextLine();
        System.out.println("Provide phone number");
        in = new Scanner(System.in);
        String phoneNumber = in.nextLine();
        ArrayList<Item> items = new ArrayList<>();
        for (Item i : menu.getMenu()){
            for (int n : numbers){
                if(i.getItemNumber()==n){
                    items.add(i);
                }
            }
        }
        OrderTakeaway orderTakeaway = new OrderTakeaway(items,address,phoneNumber);
        restaurant.makeAnOrder(orderTakeaway);
        System.out.println("Order was successfully placed");
    }
    public static void showOrders(){
        System.out.println("To show pending orders press 1\nTo show order already " +
                "served press 2\nTo show todays earnings press 3\n" +
                "To go back press 0");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice){
            case 1: restaurant.getPendingOrders().forEach(o -> {
                System.out.println("Order number "+o.getNumber()+" :");
                o.show();
            });
                break;
            case 2: Restaurant.ordersMade.forEach(o -> {
                System.out.println("Order number "+o.getNumber()+" :");
                o.show();
            });
                System.out.println("\nNumber of cancelled outdated orders: "+ restaurant.getNumberOfCancelled());
                break;
            case 3: ArrayList<Double> prices = new ArrayList<>();
                restaurant.getOrdersMade().forEach(o -> {
                    for(Item i : o.getItems()){
                        prices.add(i.getPrice());
                    }
                });
                double sum = 0;
                for (int i = 0; i < prices.size(); i++)
                    sum += prices.get(i);
                String sumFormatted = String.format("%.2f",sum);
                System.out.println("Earnings so far: "+sumFormatted);
                break;
            case 0: break;
            default: System.out.println("Invalid input");
                break;
        }
    }
    public static void viewEmployees(){
        System.out.println("To add an employee press 1\nTo delete an employee press 2\n" +
                "To show info about an employee press 3\nTo show all employees press 4\n"+
                "To go back press 0");
        Scanner in = new Scanner(System.in);
        int choice = in.nextInt();
        switch (choice){
            case 1: {
                System.out.println("Name: ");
                Scanner in1 = new Scanner(System.in);
                String name = in1.nextLine();
                System.out.println("Surname: ");
                in1 = new Scanner(System.in);
                String surname = in1.nextLine();
                System.out.println("Phone Number: ");
                in1 = new Scanner(System.in);
                String phoneNumber = in1.nextLine();
                System.out.println("Job (cook,waiter,deliveryMan): ");
                in1 = new Scanner(System.in);
                String job = in1.nextLine();
                job = job.toLowerCase();
                while(!job.equals("cook") && !job.equals("waiter") && !job.equals("deliveryMan")){
                    System.out.println("Invalid job. Try again (cook,waiter,deliveryMan): ");
                    in1 = new Scanner(System.in);
                    job = in1.nextLine();
                }
                if(job.equals("cook")){
                    Cook cook = new Cook(name,surname,phoneNumber);
                    restaurant.addEmployee(cook);
                    System.out.println("Added: "+ cook);
                }else if(job.equals("waiter")){
                    Waiter waiter = new Waiter(name,surname,phoneNumber);
                    restaurant.addEmployee(waiter);
                    System.out.println("Added: "+ waiter);
                }else{
                    DeliveryMan deliveryMan = new DeliveryMan(name,surname,phoneNumber);
                    restaurant.addEmployee(deliveryMan);
                    System.out.println("Added: "+ deliveryMan);
                }
            }
            break;
            case 2: {
                System.out.println("Provide an id of an employee you want to delete");
                Scanner in1 = new Scanner(System.in);
                int idNumber = in1.nextInt();
                findEmployee :
                {
                    for (Employee e : restaurant.getEmployees()) {
                        if (e.getId() == idNumber) {
                            if (restaurant.getEmployees().size() == 1) {
                                System.out.println("You are deleting the only employee\n" +
                                        "To stop press 0\nTo continue press 1");
                                in1 = new Scanner(System.in);
                                int choice2 = in1.nextInt();
                                if (choice2 == 1) {
                                    restaurant.deleteEmployee(e);
                                }
                                break findEmployee;
                            }
                            if (e.getClass()== Cook.class && Cook.numberOfCooks ==1){
                                System.out.println("You are deleting the only cook\n" +
                                        "To stop press 0\nTo continue press 1");
                                in1 = new Scanner(System.in);
                                int choice2 = in1.nextInt();
                                if (choice2 == 1) {
                                    restaurant.deleteEmployee(e);
                                }
                                break findEmployee;
                            }else if (e.getClass()== Waiter.class && Waiter.numberOfWaiters==1){
                                System.out.println("You are deleting the only waiter\n" +
                                        "To stop press 0\nTo continue press 1");
                                in1 = new Scanner(System.in);
                                int choice2 = in1.nextInt();
                                if (choice2 == 1) {
                                    restaurant.deleteEmployee(e);
                                }
                                break findEmployee;
                            }else if (e.getClass()== DeliveryMan.class && DeliveryMan.numberOfDeliveryMen==1){
                                System.out.println("You are deleting the only delivery man\n" +
                                        "To stop press 0\nTo continue press 1");
                                in1 = new Scanner(System.in);
                                int choice2 = in1.nextInt();
                                if (choice2 == 1) {
                                    restaurant.deleteEmployee(e);
                                }
                                break findEmployee;
                            }

                        }
                    }
                }
            }
            break;
            case 3: {
                System.out.println("Provide an id of an employee you want to get info about");
                Scanner in1 = new Scanner(System.in);
                int idNumber = in1.nextInt();
                restaurant.getEmployees().stream().filter(x -> x.getId() == idNumber).forEach(x -> x.show());
            }
            break;
            case 4: {
                restaurant.getEmployees().forEach(x -> x.show());
            }
            break;
            case 0: break;
            default:
                System.out.println("Invalid input");
                break;
        }
    }
}
