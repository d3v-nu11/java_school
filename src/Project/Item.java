package Project;

public class Item {
    private String name;
    private String description;
    private double price;
    private int itemNumber;
    public static int number = 0;
    private boolean isAvailable;
    private String availability;
    public Item(String name, String description, double price){
        this.name = name;
        this.description = description;
        this.price = price;
        this.itemNumber = number+1;
        this.isAvailable=true;
        this.availability="";
        number++;
    }
    public String getName(){
        return this.name;
    }
    public String getDescription(){
        return this.description;
    }
    public double getPrice(){
        return this.price;
    }
    public int getItemNumber(){
        return this.itemNumber;
    }
    public void setItemNumber(int nr){
        this.itemNumber=nr;
    }
    public String getAvailability(){
        return this.availability;
    }
    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
        if(isAvailable)
            this.availability = "";
        else
            this.availability = "not available";
    }
    public boolean getIsAvailable(){
        return this.isAvailable;
    }
}
