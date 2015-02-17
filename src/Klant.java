import java.util.Random;
import java.util.TreeSet;

/**
 * Created by Peter on 25-1-2015.
 */

//Class klant met de benodigde opslagvelden
public class Klant {

    //TreeSet is om reeds uitgegeven id's op te slaan
    private static TreeSet ids = new TreeSet( ) ;

    //Klantgegevens
    private int klantID;
    private String achternaam;
    private String tussenvoegsel;
    private String voornaam;
    private int leeftijd;
    private char geslacht;
    private String plaats;
    private String email;


    //Klant CONSTRUCTOR
    public Klant(String achternaam, String tussenvoegsel, String voornaam, int leeftijd, char geslacht, String plaats, String email){
        this.klantID = generateID((achternaam + tussenvoegsel + voornaam + leeftijd + geslacht + plaats + email).hashCode());
        this.achternaam = achternaam;
        this.tussenvoegsel = tussenvoegsel;
        this.voornaam = voornaam;
        this.leeftijd = leeftijd;
        this.geslacht = geslacht;
        this.plaats = plaats;
        this.email = email;
    }

    //Functie krijgt een hashcode door die opgemaakt wordt van de strings van alle klantgegevens
    public int generateID(int hashCode){
        //Met deze hashcode wordt een randomgetal gemaakt
        Random randGen = new Random(hashCode);
        int randomInt = randGen.nextInt((999999 - 100000) + 1) + 100000;

        //zolang de id al is uitgegeven, blijf nieuwe genereren
        while(ids.contains(randomInt)) {
            randomInt = randGen.nextInt((999999 - 100000) + 1) + 100000;
        }
        //Registreer id die uitgegeven gaat worden
        ids.add(randomInt);

        return randomInt;
    }

    //Getters en setters voor de gegevens
    public int getKlantID() {
        return klantID;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public int getLeeftijd() {
        return leeftijd;
    }

    public void setLeeftijd(int leeftijd) {
        this.leeftijd = leeftijd;
    }

    public char getGeslacht() {
        return geslacht;
    }

    public void setGeslacht(char geslacht) {
        this.geslacht = geslacht;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
