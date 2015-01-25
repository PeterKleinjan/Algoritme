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
    private Date starttijd;
    private int duur;
    private boolean compleet;
    private boolean dadelijk;

    public void Bestelling(int klantID, boolean dadelijk ){
        this.klantID = klantID;
        this.bestellingID = generateID();
        this.dadelijk = dadelijk;
        this.verwerking = false;
        this.compleet = false;
    }

    public void startVerwerking(){
        this.verwerking = true;
        this.starttijd = new Date();
    }

    public void eindVerwerking(){

        //Weet niet zeker of deze goed werkt:
        this.duur = new Date().compareTo(starttijd);

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
}
