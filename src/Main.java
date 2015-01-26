import javax.swing.text.html.parser.Parser;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Created by Peter on 25-1-2015.
 */
public class Main implements Runnable {

    Node root;

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
                printKlant((Klant)klanten[i]);

            }

        }
        returnToMenu();
    }

    private void printKlant(Klant klant){
        String print = "--------------------------------------------\n"+klant.getVoornaam() + " " + klant.getTussenvoegsel()+ " "+
                klant.getAchternaam()+"\nLeeftijd: "+klant.getLeeftijd()+"\n"+klant.getKlantID()+
                "\n--------------------------------------------";
        System.out.println(print);
        returnToMenu();
    }

    private static void insertionSort(Klant[] klanten){
        for(int i = 1; i < klantenSize; i++){
            Klant klant = klanten[i];
            int j = i;

            while (j > 0 && klanten[i-1].getAchternaam().compareTo(klant.getAchternaam()) > 0){
                klanten[j] = klanten[j - 1];
                j--;
            }
            klanten[j] = klant;
        }

    }

    private void binarySearch(){
        System.out.println("Zoek op achternaam: ");
        String search = user_input.next();

        int first, last, middle;

        first = 0;
        last = klantenSize-1;
        middle = (first+last)/2;

        while(first <= last){
            if(klanten[middle].getAchternaam().compareTo(search) < 0){
                first = middle + 1;
            }else if (klanten[middle].getAchternaam().equals(search)){
                printKlant(klanten[middle]);
                break;
            }
            else
                last = middle -1;
            middle = (first + last)/2;
        }
        if ( first > last){
            System.out.println(search+" is not found!");
            returnToMenu();
        }
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

        Klant klant = new Klant();
        klant.Klant(achternaam,tussenvoegsel,voornaam,leeftijd,geslacht,plaats,email);
        klanten[klantenSize] = klant;
        klantenSize++;

        System.out.println("Klant aangemaakt met id: "+klant.getKlantID());

        //Add node to binary-tree
        addNode(klant.getKlantID(),klant);

        returnToMenu();
    }

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


    //Binary tree


    public void addNode(int key, Klant klant){

        Node newNode = new Node(key,klant);

        //Als de tree nog leeg is, zet deze node als root
        if(root == null){
            root = newNode;
        }else{

            Node focusNode = root;

            Node parent;

            while(true){
                parent = focusNode;
                if(key < focusNode.key){
                    focusNode = focusNode.leftChild;
                    if(focusNode == null){
                        parent.leftChild = newNode;
                        return;
                    }
                }else{
                    focusNode = focusNode.rightChild;
                    if(focusNode == null){
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

            inOrderTraverseTree(focusNode.leftChild);

            printKlant(focusNode.klant);
           // System.out.println(focusNode.key+" naam: "+focusNode.klant.getAchternaam());

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



        Node focusNode = root;
        Node parent = root;

        boolean isItALeftChild = true;

        while(focusNode.key != key){

            parent = focusNode;

            if(key < focusNode.key){

                isItALeftChild = true;

                focusNode = focusNode.leftChild;

            }else{

                isItALeftChild = false;

                focusNode= focusNode.rightChild;
            }

            if(focusNode == null){
                return false;
            }
        }

        if(focusNode.leftChild == null && focusNode.rightChild == null){
            if(focusNode == root){
                root = null;
            }else if(isItALeftChild){
                parent.leftChild = null;
            }else{
                parent.rightChild =null;
            }
        }else if(focusNode.rightChild == null){

            if(focusNode == root){
                root = focusNode.leftChild;
            }else if(isItALeftChild){
                parent.leftChild = focusNode.leftChild;
            }else parent.rightChild = focusNode.leftChild;
        }
        else if (focusNode.leftChild == null){

            if(focusNode == root){
                root = focusNode.rightChild;
            }else if(isItALeftChild) {
                parent.leftChild = focusNode.rightChild;
            }else
                parent.rightChild = focusNode.leftChild;
        }
        else{
            Node replacement = getReplacementNode(focusNode);

            if(focusNode == root){
                root = replacement;
            }else if(isItALeftChild)
                parent.leftChild = replacement;
            else
                parent.rightChild = replacement;

            replacement.leftChild = focusNode.leftChild;
        }
        return true;
    }

public Node getReplacementNode(Node replaceNode){

    Node replacementParent = replaceNode;
    Node replacement = replaceNode;

    Node focusNode = replaceNode.rightChild;

    while(focusNode != null){
        replacementParent = replacement;
        replacement = focusNode;
        focusNode = focusNode.leftChild;
    }
    if(replacement != replacement.rightChild){
        replacementParent.leftChild = replacement.rightChild;
        replacement.rightChild = replaceNode.rightChild;
    }
    return replacement;
}


private void removeFromArray(int key){
    for(int i = 0; i < klantenSize; i++){
        if(klanten[i].getKlantID() == key)klanten[i]=null;
    }
    //returnToMenu();
}




}
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