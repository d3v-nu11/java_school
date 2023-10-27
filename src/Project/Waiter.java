package Project;

public class Waiter extends Employee {
    public static int numberOfWaiters = 0;
    private int id;
    private int numberOfOrdersMade;
    private double tips;
    public Waiter(String name, String surname, String phoneNumber){
        super(name,surname,phoneNumber);
        super.setJob("waiter");
        super.setIsAvailable(true);
        super.setNumberOfOrdersMade(0);
        super.setTips(0);
        this.id = Employee.idNumber+1;
        Employee.idNumber++;
        numberOfWaiters++;

    }
    public Waiter(int id, String name, String surname, String phoneNumber){
        super(name,surname,phoneNumber);
        super.setJob("waiter");
        super.setIsAvailable(true);
        super.setNumberOfOrdersMade(0);
        super.setTips(0);
        this.id = id;
        numberOfWaiters++;

    }

    @Override
    public String toString() {
        return this.id+" | "+this.getName()+" | "+this.getSurname()+" | "+this.getPhoneNumber()+" | "+this.getJob();
    }

    @Override
    public void show() {
        String tipsFormatted = String.format("%.2f",super.getTips());
        System.out.println("Id: "+this.id+" "+this.getName()+" "+this.getSurname()+" phone: "+this.getPhoneNumber()+" "+this.getJob()
                +" orders made: "+super.getNumberOfOrdersMade()+" total tips: "+tipsFormatted);
    }

    public int getId(){
        return this.id;
    }

}
