import java.util.Date;
import java.util.Scanner;
/**
 * Created by Peter on 25-1-2015.
 */
public class Main {


    public static void main(String[] args){
        new Main().menu();
    }

    public void menu(){
        Scanner user_input = new Scanner(System.in);
        System.out.println("Wat wilt u doen? \n 1) Nieuwe bestelling \n 2) Nieuwe klant");
        switch(user_input.nextInt()){
            case 1: nieuweBestelling(); break;
            case 2: nieuweKlant(); break;
            default: System.out.println("No valid input found"); menu();
        }
    }

    public void nieuweBestelling(){
        Scanner user_input = new Scanner(System.in);

        int klantID;
        System.out.println("Klant-ID: ");
        klantID = user_input.nextInt();

        boolean dadelijk;
        System.out.println("Dadelijk (y/n): ");
        String dadelijkStr = user_input.next();
        if(dadelijkStr.equals("y")||dadelijkStr.equals("Y"))dadelijk = true;
        else dadelijk = false;

        Bestelling bestelling = new Bestelling();
        bestelling.Bestelling(klantID,dadelijk);

        System.out.println("Bestelling aangemaakt met id: "+bestelling.getBestellingID());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        menu();
    }

    public void nieuweKlant(){
        Scanner user_input = new Scanner(System.in);

        String achternaam;
        System.out.println("Achternaam: ");
        achternaam = user_input.next();

        String tussenvoegsel;
        System.out.println("Tussenvoegsel: ");
        tussenvoegsel = user_input.next();

        String voornaam;
        System.out.println("Voornaam: ");
        voornaam = user_input.next();

        int leeftijd;
        System.out.println("Leeftijd: ");
        leeftijd = user_input.nextInt();

        char geslacht;
        System.out.println("Geslacht (m/v): ");
        String geslachtStr = user_input.next();
        if(geslachtStr.equals("m")||geslachtStr.equals("M"))geslacht = 'm';
        else geslacht = 'v';

        String plaats;
        System.out.println("Plaats: ");
        plaats = user_input.next();

        String email;
        System.out.println("E-mail: ");
        email = user_input.next();

        Klant klant = new Klant();
        klant.Klant(achternaam,tussenvoegsel,voornaam,leeftijd,geslacht,plaats,email);
        System.out.println("Klant aangemaakt met id: "+klant.getKlantID());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        menu();
    }

}
