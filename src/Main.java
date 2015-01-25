import javax.swing.text.html.parser.Parser;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by Peter on 25-1-2015.
 */
public class Main implements Runnable {

    private static int klantenSize = 0;
    ArrayList bestellingenSpoed = new ArrayList();
    ArrayList bestellingenNormaal = new ArrayList();
    Klant[]klanten = new Klant[1000];
    Scanner user_input = new Scanner(System.in);

    public static void main(String[] args){
        new Main().menu();
    }

    public void menu(){

        System.out.println("Wat wilt u doen? \n 1) Nieuwe bestelling \n 2) Nieuwe klant \n 4) Printklanten \n" +
                " 5) Mergesort \n 6) Linear search\n 7) update");
        switch(user_input.nextInt()){
            case 1: nieuweBestelling(); break;
            case 2: nieuweKlant(); break;
            case 3: getBestellingStatus(); break;
            case 4: printKlanten(); break;
            case 5: mergeSort(klanten); printKlanten(); break;
            case 6: linSearch(); break;
            case 7: updateBestelling(); break;
            default: System.out.println("No valid input found"); menu();
        }
    }

    private void linSearch(){
        System.out.println("Voer uw zoekterm in: ");
        String search = user_input.next();

        for(int i =0; i< klantenSize; i++){
            boolean hit = false;
            if(search.equalsIgnoreCase(klanten[i].getAchternaam()))hit = true;
            if(search.equalsIgnoreCase(klanten[i].getEmail()))hit = true;
            if(search.equalsIgnoreCase(klanten[i].getPlaats()))hit = true;
            if(search.equalsIgnoreCase(klanten[i].getTussenvoegsel()))hit = true;
            if(search.equalsIgnoreCase(klanten[i].getVoornaam()))hit = true;
            if(search.equalsIgnoreCase(String.valueOf(klanten[i].getGeslacht())))hit = true;
            if(search.equalsIgnoreCase(String.valueOf(klanten[i].getLeeftijd())))hit = true;
            if(search.equalsIgnoreCase(String.valueOf(klanten[i].getKlantID())))hit = true;
            if(hit){
                Klant klant = (Klant)klanten[i];
                String print = "--------------------------------------------\n"+klant.getVoornaam() + " " + klant.getTussenvoegsel()+ " "+
                        klant.getAchternaam()+"\nLeeftijd: "+klant.getLeeftijd()+"\n"+klant.getKlantID()+
                        "\n--------------------------------------------";
                System.out.println(print);
            }

        }
        returnToMenu();
    }

    //Klanten
    private void printKlantTotaal(){
        System.out.println("Totaal zijn er "+klanten.length+" klanten opgeslagen");
        returnToMenu();
    }

    private void nieuweKlant(){

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
        klanten[klantenSize] = klant;
        klantenSize++;

        System.out.println("Klant aangemaakt met id: "+klant.getKlantID());

        returnToMenu();
    }

    private void printKlanten(){
        for(int i =0; i < klantenSize; i++){
            Klant klant = (Klant)klanten[i];
            String print = "--------------------------------------------\n"+klant.getVoornaam() + " " + klant.getTussenvoegsel()+ " "+
                    klant.getAchternaam()+"\nLeeftijd: "+klant.getLeeftijd()+"\n"+klant.getKlantID()+
                    "\n--------------------------------------------";
            System.out.println(print);
        }
        returnToMenu();
    }

    public static Klant[]mergeSort(Klant[]klanten){
        if(klanten.length <= 1){
            return klanten;
        }

        Klant[]first = new Klant[klantenSize/2];
        Klant[]second = new Klant[klantenSize-first.length];
        System.arraycopy(klanten, 0, first, 0, first.length);
        System.arraycopy(klanten, first.length, second, 0, second.length);

        mergeSort(first);
        mergeSort(second);

        merge(first, second, klanten);
        return klanten;
    }
    private static void merge(Klant[] first, Klant[] second, Klant[] klanten){
        int iFirst = 0;
        int iSecond = 0;

        int j = 0;

        while (iFirst < first.length && iSecond < second.length){
            if(first[iFirst].getLeeftijd() < second[iSecond].getLeeftijd()){
                klanten[j] = first[iFirst];
                iFirst++;
            }else{
                klanten[j] = second[iSecond];
                iSecond++;
            }
        }
    }

    //Bestellingen
    private void nieuweBestelling(){

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
        updateBestelling();
    }
}
