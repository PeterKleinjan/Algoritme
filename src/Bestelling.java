import java.util.Date;
import java.util.Random;
import java.util.TreeSet;

/**
 * Created by Peter on 25-1-2015.
 */
public class Bestelling {
    private static TreeSet ids = new TreeSet();
    private int klantID;
    private int bestellingID;
    private boolean verwerking;
    private long starttijd;
    private long duur;
    private boolean compleet;
    private boolean dadelijk;

    public void Bestelling(int klantID, int duur, boolean dadelijk ){
        this.klantID = klantID;
        this.bestellingID = generateID();
        this.dadelijk = dadelijk;
        this.duur = duur;
        this.verwerking = false;
        this.compleet = false;
    }

    public void startVerwerking(){
        System.out.println("Verwerking gestart");
        this.verwerking = true;
        this.starttijd = System.currentTimeMillis();
    }

    public boolean getStatusGereed(){
        System.out.println(duur >= System.currentTimeMillis()-starttijd);
        if (duur >= System.currentTimeMillis()-starttijd){
            return true;
        }else{
            return false;
        }
    }

    public void eindVerwerking(){

        this.compleet = true;
    }

    public int generateID(){
        Random randGen = new Random();
        int randomInt = randGen.nextInt((999999 - 100000) + 1) + 100000;
        while(ids.contains(randomInt)) {
            randomInt = randGen.nextInt((999999 - 100000) + 1) + 100000;
        }
        ids.add(randomInt);

        return randomInt;
    }

    public int getBestellingID(){
        return this.bestellingID;
    }

    public boolean isVerwerking() {
        return verwerking;
    }

    public boolean isCompleet() {
        return compleet;
    }

    public void printStatus(){
        if(compleet) {
            System.out.println("Bestelling is gereed");
        }else if(verwerking){
            System.out.println("Bestelling is in verwerking");
        }else{
            System.out.println("Bestelling is nog niet opgepakt");
        }
    }
}
