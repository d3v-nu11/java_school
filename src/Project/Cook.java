package Project;

public class Cook extends Employee {
    public static int numberOfCooks = 0;
    private int id;
    public Cook(String name, String surname, String phoneNumber){
        super(name,surname,phoneNumber);
        super.setJob("cook");
        this.id = Employee.idNumber+1;
        Employee.idNumber++;
        numberOfCooks++;
    }
    public Cook(int id, String name, String surname, String phoneNumber){
        super(name,surname,phoneNumber);
        super.setJob("cook");
        this.id = id;
        numberOfCooks++;
    }

    @Override
    public String toString() {
        return this.id+" | "+this.getName()+" | "+this.getSurname()+" | "+this.getPhoneNumber()+" | "+this.getJob();
    }

    @Override
    public void show() {
        System.out.println("Id: "+this.id+" "+this.getName()+" "+this.getSurname()+" phone: "+this.getPhoneNumber()+" "+this.getJob());
    }

    public int getId(){
        return this.id;
    }
}
