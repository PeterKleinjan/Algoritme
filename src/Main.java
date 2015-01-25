import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by Peter on 25-1-2015.
 */
public class Main implements Runnable {

    ArrayList bestellingenSpoed = new ArrayList();
    ArrayList bestellingenNormaal = new ArrayList();
    Klant[]klanten = new Klant[1000];

    public static void main(String[] args){
        new Main().menu();
    }

    public void menu(){
        Scanner user_input = new Scanner(System.in);
        System.out.println("Wat wilt u doen? \n 1) Nieuwe bestelling \n 2) Nieuwe klant \n 3) Bestelstatus");
        switch(user_input.nextInt()){
            case 1: nieuweBestelling(); break;
            case 2: nieuweKlant(); break;
            case 3: getBestellingStatus(); break;
            case 4: run(); break;
            default: System.out.println("No valid input found"); menu();
        }
    }

    //Klanten
    private void printKlantTotaal(){
        System.out.println("Totaal zijn er "+klanten.size()+" klanten opgeslagen");
        returnToMenu();
    }

    private void nieuweKlant(){
        Scanner user_input = new Scanner(System.in);

        String achternaam;
        System.out.println("Achternaam: ");
        achternaam = user_input.next();

        String tussenvoegsel;
        System.out.println("Tussenvoegsel: ");
        if(!user_input.next().equals("geen"))tussenvoegsel = user_input.next();
        else tussenvoegsel = "";

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
        klanten.add(klant);

        System.out.println("Klant aangemaakt met id: "+klant.getKlantID());

        returnToMenu();
    }

    //Bestellingen
    private void nieuweBestelling(){
        Scanner user_input = new Scanner(System.in);

        int klantID;
        System.out.println("Klant-ID: ");
        klantID = user_input.nextInt();

        boolean dadelijk;
        System.out.println("Dadelijk (y/n): ");
        String dadelijkStr = user_input.next();
        if(dadelijkStr.equals("y")||dadelijkStr.equals("Y"))dadelijk = true;
        else dadelijk = false;

        int duur;
        System.out.println("Duur: ");
        duur = user_input.nextInt();

        Bestelling bestelling = new Bestelling();
        bestelling.Bestelling(klantID, duur, dadelijk);

        if(dadelijk)bestellingenSpoed.add(bestelling);
        else bestellingenNormaal.add(bestelling);

        System.out.println("Bestelling aangemaakt met id: "+bestelling.getBestellingID());

        returnToMenu();
    }

    private void verwijderBestelling(){
        if(bestellingenNormaal.size()>0){
            Bestelling normaal = (Bestelling)bestellingenNormaal.get(0);
            if(normaal.isCompleet())bestellingenNormaal.remove(0);
        }
        if(bestellingenSpoed.size()>0){
            Bestelling spoed = (Bestelling)bestellingenSpoed.get(0);

            if(spoed.isCompleet())bestellingenSpoed.remove(0);
        }
    }

    private void updateBestelling(){
        if(bestellingenNormaal.size()>0){
            Bestelling normaal = (Bestelling)bestellingenNormaal.get(0);
            if(!normaal.isVerwerking())normaal.startVerwerking();
            if(normaal.getStatusGereed())normaal.eindVerwerking();
        }
        if(bestellingenSpoed.size()>0){
            Bestelling spoed = (Bestelling)bestellingenSpoed.get(0);
            if(!spoed.isVerwerking())spoed.startVerwerking();
            if(spoed.getStatusGereed())spoed.eindVerwerking();
        }
        verwijderBestelling();

        returnToMenu();
    }

    private void getBestellingStatus(){
        Scanner user_input = new Scanner(System.in);

        int ID;
        System.out.println("Bestelling-ID: ");
        ID = user_input.nextInt();

        boolean spoed;
        System.out.println("Spoed (y/n): ");
        String dadelijkStr = user_input.next();
        if(dadelijkStr.equals("y")||dadelijkStr.equals("Y"))spoed = true;
        else spoed = false;

        Bestelling bestelling = getBestellingByID(ID, spoed);
        if(bestelling!=null){
            bestelling.printStatus();
        }else{
            System.out.println("Bestelling niet gevonden");
        }
        returnToMenu();
    }

    private Bestelling getBestellingByID(int ID, boolean spoed){
        if(spoed){
            for (int i = 0; i < bestellingenSpoed.size(); i++) {
                if(ID == ((Bestelling)bestellingenSpoed.get(i)).getBestellingID())return (Bestelling)bestellingenSpoed.get(i);
            }
        }else{
            for (int i = 0; i < bestellingenNormaal.size(); i++) {
                if(ID == ((Bestelling)bestellingenNormaal.get(i)).getBestellingID())return (Bestelling)bestellingenNormaal.get(i);
            }
        }
        return null;
    }


    //Algemene functies
    private void returnToMenu(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        menu();
    }



    @Override
    public void run() {
        System.out.println("runcall");
        updateBestelling();
    }
}
