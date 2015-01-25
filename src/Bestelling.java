import java.util.Date;

/**
 * Created by Peter on 25-1-2015.
 */
public class Bestelling {
    private int klantID;
    private int bestellingID;
    private boolean verwerking;
    private Date starttijd;
    private int duur;
    private boolean compleet;
    private boolean dadelijk;

    public void Bestelling(int klantID, int bestellingID, boolean dadelijk ){
        this.klantID = klantID;
        this.bestellingID = bestellingID;
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
}
