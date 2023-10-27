package Project;

import java.util.ArrayList;

public class Menu {
    private ArrayList<Item> menu = new ArrayList<>();
    public Menu(){}
    public void add(Item item){
        menu.add(item);
    }
    public void delete(int itemNumber){
        for(Item p : menu){
            if(p.getItemNumber() == itemNumber){
                menu.remove(p);
                break;
            }
        }
        Item.number = Item.number-1;
        for(Item p : menu) {
            if (p.getItemNumber() > itemNumber) {
                p.setItemNumber(p.getItemNumber() - 1);
            }
        }
    }
    public void show(){
        for(Item e : menu){
            System.out.println(e.getItemNumber()+" | "+e.getName()+" | "+e.getDescription()+" | "+e.getPrice()+" "+e.getAvailability());
        }
    }
    public void changeAvailability(ArrayList<Integer> itemNumbers){
        for(Item p : menu){
            if(p.getItemNumber() == itemNumbers.get(0)){
                if(itemNumbers.get(1) == 0)
                    p.setIsAvailable(false);
                else
                    p.setIsAvailable(true);
            }
        }
    }
    public String toString() {
        String str = "";
        for (Item e : menu) {
            str = str + e.getItemNumber() + " | " + e.getName() + " | " + e.getDescription() + " | " + e.getPrice() +" | "+ e.getAvailability() + "\n";
        }
        return str;
    }
    public ArrayList<Item> getMenu(){
        return this.menu;
    }
}

