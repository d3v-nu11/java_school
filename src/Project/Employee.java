package Project;

public abstract class Employee {
    private String name;
    private String surname;
    private String phoneNumber;
    private String job;
    public static int idNumber = 0;
    private boolean isAvailable;
    private int numberOfOrdersMade;
    private double tips;
    private long deliveryDeparture;
    public Employee(){}
    public Employee(String name, String surname, String phoneNumber){
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
    }
    public String getName(){
        return this.name;
    }
    public String getSurname(){
        return this.surname;
    }
    public String getPhoneNumber(){
        return this.phoneNumber;
    }
    public String getJob(){
        return this.job;
    }
    public void setJob(String job){
        this.job = job;
    }
    public boolean getIsAvailable(){
        return this.isAvailable;
    }
    public void setIsAvailable(boolean isAvailable){
        this.isAvailable = isAvailable;
    }
    public abstract String toString();
    public abstract void show();
    public abstract int getId();
    public int getNumberOfOrdersMade() {
        return numberOfOrdersMade;
    }
    public void setTips(double tips) {
        this.tips = tips;
    }
    public void setNumberOfOrdersMade(int numberOfOrdersMade) {
        this.numberOfOrdersMade = numberOfOrdersMade;
    }
    public void addOrderMade() {
        numberOfOrdersMade++;
    }
    public double getTips() {
        return tips;
    }
    public void addTip(double tip) {
        this.tips = this.tips + tip;
    }
    public long getDeliveryDeparture() {
        return deliveryDeparture;
    }
    public void setDeliveryDeparture(long deliveryDeparture) {
        this.deliveryDeparture = deliveryDeparture;
    }
}
