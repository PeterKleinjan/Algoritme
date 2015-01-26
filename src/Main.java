import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by Peter on 25-1-2015.
 */

//Class implementeerd runnable om de updatefunctie automatisch te laten werken, dit werkt echter nog niet
    //De mainclass maakt bevat het aanmaken van klanten en bestellingen, er zijn de benodigde zoekfuncties
public class Main implements Runnable {

    //De rootnode wordt gebruikt als referentiepunt om de tree te doorkruisen
    Node root;

    //De klantenarray
    Klant[]klanten = new Klant[1000];

    //Houd de grootte van het klantenbestand bij
    private static int klantenSize = 0;

    //Arraylisten voor de bestellingen, dadelijk/niet-dadelijk
    ArrayList bestellingenSpoed = new ArrayList();
    ArrayList bestellingenNormaal = new ArrayList();

    //Scanner voor de user input
    Scanner user_input = new Scanner(System.in);

    public static void main(String[] args){
        new Main().menu();
    }

    //Startmenu vanwaar de diverse functies opgestart kunnen worden
    public void menu(){

        System.out.println("Wat wilt u doen? \n 1) Nieuwe bestelling \n 2) Nieuwe klant \n 4) Printklanten \n" +
                " 5) Mergesort \n 6) Linear search\n 7) Insertion sort\n 8) Binary search\n 9) In order traversal\n 10) Verwijder klant");
        switch(user_input.nextInt()){
            case 1: nieuweBestelling(); break;
            case 2: nieuweKlant(); break;
            case 3: getBestellingStatus(); break;
            case 4: printKlanten(); break;
            case 5: mergeSort(klanten); printKlanten(); break;
            case 6: linSearch(); break;
            case 7: insertionSort(klanten); printKlanten(); break;
            case 8: binarySearch(); break;
            case 9: inOrderTraverseTree(root); returnToMenu();break;
            case 10: removeInBoth(); break;
            default: System.out.println("No valid input found"); menu();
        }
    }

    //Linear search, zoekt met de zoekterm tussen alle klantengegevens
    private void linSearch(){
        //vraag om input
        System.out.println("Voer uw zoekterm in: ");
        String search = user_input.next();

        //Vergelijkt de gegevens van elke klant met de zoekterm
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
                //Als er iets gevonden is wat overeen komt: print
                printKlant((Klant)klanten[i]);

            }

        }
        //Stuurt user terug naar het menu
        returnToMenu();
    }

    //Ontvangt een klant en print hier gegevens van
    private void printKlant(Klant klant){
        String print = "--------------------------------------------\n"+klant.getVoornaam() + " " + klant.getTussenvoegsel()+ " "+
                klant.getAchternaam()+"\nLeeftijd: "+klant.getLeeftijd()+"\n"+klant.getKlantID()+
                "\n--------------------------------------------";
        System.out.println(print);
        returnToMenu();
    }

    //Sorteer op achternaam met insertionsort
    private static void insertionSort(Klant[] klanten){
        for(int i = 1; i < klantenSize; i++){
            Klant klant = klanten[i];
            int j = i;

            //Vergelijk de achternamen van opeenvolgende klanten en zet dit per duo op volgorde
            while (j > 0 && klanten[i-1].getAchternaam().compareTo(klant.getAchternaam()) > 0){
                klanten[j] = klanten[j - 1];
                j--;
            }
            klanten[j] = klant;
        }

    }

    //Binary search op achternaam
    private void binarySearch(){

        //vraagt input
        System.out.println("Zoek op achternaam: ");
        String search = user_input.next();

        //Initialiseer: first, last en middle
        int first, last, middle;

        //Zet de waardes hiervan goed afhankelijk vh klantenbestand
        first = 0;
        last = klantenSize-1;
        middle = (first+last)/2;

        //Zolang first de last niet voorbij is, of gelijk aan
        while(first <= last){

            //Als de middelste naam kleiner is dan de gezochte naam
            //Zet dan first gelijk aan middle + 1, want daar hoeven we niet naar te kijken
            if(klanten[middle].getAchternaam().compareTo(search) < 0){
                first = middle + 1;

                //Als de naam gelijk is aan de gezochte naam -> gevonden
            }else if (klanten[middle].getAchternaam().equals(search)){
                printKlant(klanten[middle]);
                break;
            }
            //Anders zit de gezochte waarde in de onderste helft
            //Dus zet laatste gelijk aan middle -1
            else
                last = middle -1;
            //Midden resetten naar het nieuwe midden
            middle = (first + last)/2;
        }
        //Als first voorbij de last gaat is de zoekterm niet gevonden
        if ( first > last){
            System.out.println(search+" is not found!");
            returnToMenu();
        }
    }


    //Invoer nieuwe klant
    private void nieuweKlant(){

        String achternaam;
        System.out.println("Achternaam: ");
        achternaam = user_input.next();

        String tussenvoegsel;
        System.out.println("Tussenvoegsel: ");
        if(user_input.next().equals("geen"))tussenvoegsel = "";
        else tussenvoegsel = user_input.next();

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

        //Maakt een nieuwe klant aan met de opgegeven gegevens
        Klant klant = new Klant();
        klant.Klant(achternaam,tussenvoegsel,voornaam,leeftijd,geslacht,plaats,email);
        klanten[klantenSize] = klant;
        klantenSize++;

        System.out.println("Klant aangemaakt met id: "+klant.getKlantID());

        //Add node to binary-tree
        addNode(klant.getKlantID(),klant);

        returnToMenu();
    }

    //Print een overzicht van de klanten in de array
    private void printKlanten(){
        for(int i =0; i < klantenSize; i++){
            Klant klant = (Klant)klanten[i];
            if(klant != null) {
                String print = "--------------------------------------------\n" + klant.getVoornaam() + " " + klant.getTussenvoegsel() + " " +
                        klant.getAchternaam() + "\nLeeftijd: " + klant.getLeeftijd() + "\n" + klant.getKlantID() +
                        "\n--------------------------------------------";
                System.out.println(print);
            }
        }
        returnToMenu();
    }

    //Mergesort van de klanten op leeftijd
    public static Klant[]mergeSort(Klant[]klanten){

        //Als de klantenarray leeg is hoeft er niet gesorteerd te worden
        if(klanten.length <= 1){
            return klanten;
        }

        //Twee nieuwe array's aanmaken om de array op te splitsen
        Klant[]first = new Klant[klantenSize/2];
        Klant[]second = new Klant[klantenSize-first.length];
        System.arraycopy(klanten, 0, first, 0, first.length);
        System.arraycopy(klanten, first.length, second, 0, second.length);

        //Recursief, blijft hierdoor opdelen
        mergeSort(first);
        mergeSort(second);

        //Vervolgens in de mergemethode op juiste volgorde weer neerzetten
        merge(first, second, klanten);
        return klanten;
    }
    private static void merge(Klant[] first, Klant[] second, Klant[] klanten){

        //Volgende object om naar te kijken in eerste en tweede helft
        int iFirst = 0;
        int iSecond = 0;

        //Plek om in resultatenarray op te slaan
        int j = 0;

        while (iFirst < first.length && iSecond < second.length){
            //Als de eerste leeftijd minder is dan de tweede, sla dan de eerste waarde op
            if(first[iFirst].getLeeftijd() < second[iSecond].getLeeftijd()){
                klanten[j] = first[iFirst];
                iFirst++;
            }else{
                //Anders de tweede waarde
                klanten[j] = second[iSecond];
                iSecond++;
            }
            j++;
        }
    }

    //Nieuwe bestelling
    private void nieuweBestelling(){

        //vraagt input
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

        //maakt nieuwe bestelling met de opgegeven gegevens
        Bestelling bestelling = new Bestelling();
        bestelling.Bestelling(klantID, duur, dadelijk);

        //Dadelijk bepaald in welke arraylist de bestelling komt
        if(dadelijk)bestellingenSpoed.add(bestelling);
        else bestellingenNormaal.add(bestelling);

        System.out.println("Bestelling aangemaakt met id: "+bestelling.getBestellingID());

        returnToMenu();
    }

    //Update bestelling
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

    //Wordt door update aangeroepen, verwijderd bestellingen met status compleet
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


    //Methode om de bestelstatus op de vragen van een bestelling
    private void getBestellingStatus(){

        int ID;
        System.out.println("Bestelling-ID: ");
        ID = user_input.nextInt();

        boolean spoed;
        System.out.println("Spoed (y/n): ");
        String dadelijkStr = user_input.next();
        if(dadelijkStr.equals("y")||dadelijkStr.equals("Y"))spoed = true;
        else spoed = false;

        //Gebruikt een andere functie om de bestelling op te zoeken
        Bestelling bestelling = getBestellingByID(ID, spoed);
        if(bestelling!=null){
            bestelling.printStatus();
        }else{
            System.out.println("Bestelling niet gevonden");
        }
        returnToMenu();
    }
    //Doorzoekt bestellingen op id en dadelijk/niet en retourneert indien gevonden
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


    //Algemene functie delay terug naar menu
    private void returnToMenu(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        menu();
    }


    //Werkt niet maar zou het updateproces moeten automatiseren
    @Override
    public void run() {
        updateBestelling();
    }


    //Binary tree

    //Add node methode
    public void addNode(int key, Klant klant){

        //Maak een nieuwe node aan voor de klant
        Node newNode = new Node(key,klant);

        //Als de tree nog leeg is, zet deze node als root
        if(root == null){
            root = newNode;
        }else{
            //Zet root als focuspunt om de tree te doorkruisen
            Node focusNode = root;

            //Toekomstige parent van de nieuwe node
            Node parent;

            while(true){
                //Root is de start
                parent = focusNode;

                //Moet node naar links?
                if(key < focusNode.key){
                    //focus op linker-child
                    focusNode = focusNode.leftChild;
                    //als deze geen kinderen heeft
                    if(focusNode == null){
                        //wordt de nieuwe node de linker child
                        parent.leftChild = newNode;
                        return;
                    }
                }else{ //De node moet dus naar rechts
                    focusNode = focusNode.rightChild;

                    //geen kinderen?
                    if(focusNode == null){
                        //Dan nieuwe node als rightchild
                        parent.rightChild = newNode;
                        return;
                    }
                }
            }
        }
    }

    //in-order traversal
    public void inOrderTraverseTree(Node focusNode){

        if(focusNode != null){

            //Ga via links
            inOrderTraverseTree(focusNode.leftChild);

            //Print huidige klant
            printKlant(focusNode.klant);

            //ga naar recht
            inOrderTraverseTree(focusNode.rightChild);
        }

    }

    //find node
    public Node findNode(int key){

        Node focusNode = root;

        while(focusNode.key != key){
            if (key < focusNode.key){

                focusNode = focusNode.leftChild;

            }else{
                focusNode = focusNode.rightChild;
            }
            if(focusNode == null)
                return null;

        }

        return focusNode;
    }

    //Functie die ervoor zorgt dat de klant zowel in de array als in de tree verwijderd worden
    private void removeInBoth(){
        int key;
        System.out.println("Voer de klant-ID in om te verwijderen: ");
        key = user_input.nextInt();

        removeFromArray(key);
        remove(key);
        returnToMenu();
    }


    //delete node
    public boolean remove(int key){

        //Start aan de top
        Node focusNode = root;
        Node parent = root;

        //boolean die aangeeft welke kant op gezocht moet worden
        boolean isItALeftChild = true;

        //Zolang niet gevonden, blijf zoeken
        while(focusNode.key != key){

            parent = focusNode;

            //Moet er naar links gezocht worden?
            if(key < focusNode.key){

                isItALeftChild = true;

                //Focus op linker-child
                focusNode = focusNode.leftChild;

            }else{
                //Groter dan focus node dus naar rechts

                isItALeftChild = false;

                //Focus op rechter-child
                focusNode= focusNode.rightChild;
            }
            //Als de node niet gevonden is
            if(focusNode == null){
                return false;
            }
        }

        //Als node geen kinderen heeft, verwijder het
        if(focusNode.leftChild == null && focusNode.rightChild == null){
            //Als het de root is -> verwijder
            if(focusNode == root){
                root = null;
            }else if(isItALeftChild){
                //Als het een linker-child was van de parent
                //Verwijder dit bij parent
                parent.leftChild = null;
            }else{
                //Anders bij het rechter-child
                parent.rightChild =null;
            }

        //Als er geen rechter-child is
        }else if(focusNode.rightChild == null){

            if(focusNode == root){
                root = focusNode.leftChild;

                //Als de focus op de linkerkant van de parent was
                //Verplaats de focus nodes linker-child naar de parent
            }else if(isItALeftChild){
                parent.leftChild = focusNode.leftChild;
                //Anders hetzelfde maar dan voor rechts
            }else parent.rightChild = focusNode.leftChild;
        }

        //Als er geen linker child is
        else if (focusNode.leftChild == null){

            if(focusNode == root){
                root = focusNode.rightChild;

                //Als de focus op de linkerkant van de parent was
                //verplaats de focus nodes linker-child naar de parent
            }else if(isItALeftChild) {
                parent.leftChild = focusNode.rightChild;

                //Anders naar de rechter-child
            }else
                parent.rightChild = focusNode.leftChild;
        }
        //Twee child's dus er moet vervanging gezocht worden
        else{
            Node replacement = getReplacementNode(focusNode);

            //Als de focusnode de root is verplaats het dan met de vervangende node
            if(focusNode == root){
                root = replacement;
                //Als de verwijderde node een linker-child was
                //maak de vervanging dan de linker-child
            }else if(isItALeftChild)
                parent.leftChild = replacement;
            else
                //anders voor de rechter-child
                parent.rightChild = replacement;

            replacement.leftChild = focusNode.leftChild;
        }
        return true;
    }

public Node getReplacementNode(Node replaceNode){

    Node replacementParent = replaceNode;
    Node replacement = replaceNode;

    Node focusNode = replaceNode.rightChild;

    //zolang er geen linker-childs zijn
    while(focusNode != null){
        replacementParent = replacement;
        replacement = focusNode;
        focusNode = focusNode.leftChild;
    }

    //Als de vervangende node niet het rechter-child is
    //verplaats de vervanging tussen de parent en child
    if(replacement != replacement.rightChild){
        replacementParent.leftChild = replacement.rightChild;
        replacement.rightChild = replaceNode.rightChild;
    }
    return replacement;
}

//Verwijderd van klanten-array
private void removeFromArray(int key){
    for(int i = 0; i < klantenSize; i++){
        if(klanten[i].getKlantID() == key)klanten[i]=null;
    }
    //returnToMenu();
}




}
//Class node
class Node{

    int key;
    Klant klant;

    Node leftChild;
    Node rightChild;

    Node(int key, Klant klant){
        this.key = key;
        this.klant = klant;
    }

    public Klant getKlant(){
        return klant;
    }
}