package Project;

public class DeliveryMan extends Employee implements Runnable{
    public static int numberOfDeliveryMen = 0;
    private int id;
    private int numberOfOrdersMade;
    private double tips;
    public DeliveryMan(String name, String surname, String phoneNumber){
        super(name,surname,phoneNumber);
        super.setJob("deliveryMan");
        super.setIsAvailable(true);
        super.setNumberOfOrdersMade(0);
        super.setTips(0);
        this.id = Employee.idNumber+1;
        Employee.idNumber++;
        numberOfDeliveryMen++;

    }
    public DeliveryMan(int id, String name, String surname, String phoneNumber){
        super(name,surname,phoneNumber);
        super.setJob("deliveryMan");
        super.setIsAvailable(true);
        super.setNumberOfOrdersMade(0);
        super.setTips(0);
        this.id = id;
        numberOfDeliveryMen++;
    }


    @Override
    public String toString() {
        return this.id+" | "+this.getName()+" | "+this.getSurname()+" | "+this.getPhoneNumber()+" | "+this.getJob();
    }

    @Override
    public void show() {
        String tipsFormatted = String.format("%.2f",super.getTips());
        System.out.println("Id: "+this.id+" "+this.getName()+" "+this.getSurname()+" phone: "+this.getPhoneNumber()+" "+
                this.getJob()+" orders made: "+super.getNumberOfOrdersMade()+" total tips: "+tipsFormatted);
    }

    public int getId(){
        return this.id;
    }

    @Override
    public void run() {
        while(!getIsAvailable()) {
            try {
                Thread.sleep(120000);
                super.setIsAvailable(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
