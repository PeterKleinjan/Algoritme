import java.util.Scanner;

/**
 * Created by Peter on 17-2-2015.
 */
public class BinaryTree {

    private KlantNode root;
    private KlantNode parent;
    private KlantNode replaceNode;
    private boolean deleteLeft = true;
    private Scanner user_input = new Scanner(System.in);

    public static void main(String[]args){
        new BinaryTree();
    }

    public BinaryTree(){
        root = new KlantNode(new Klant("Kleinjan", "", "Peter", 26, 'm', "Dordrecht", "peterkleinjan@gmail.com"));
        menu();
    }
    private void menu(){
        System.out.println("Wat wilt u doen? \n 1) Nieuwe klant \n 2) Zoek klant \n 3) In-order-Traverse \n" +
                " 4) Verwijder klant \n");
        switch(user_input.nextInt()){
            case 1: nieuweKlant(); break;
            case 2: zoekKlant(); break;
            case 3: inOrderTraverse(root); returnToMenu(); break;
            case 4: verwijderKlant(); System.out.println("Klant is verwijderd "); returnToMenu(); break;
            default: System.out.println("No valid input found"); returnToMenu();
        }
    }

    //Invoer nieuwe klant
    private void nieuweKlant(){

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

        //Maakt een nieuwe klant aan met de opgegeven gegevens
        Klant klant = new Klant(achternaam,tussenvoegsel,voornaam,leeftijd,geslacht,plaats,email);

        System.out.println("Klant aangemaakt met id: "+klant.getKlantID());

        //Add node to binary-tree
        addKlantNode(root, klant);

        returnToMenu();
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

    //Add klantnode methode
    private void addKlantNode(KlantNode focusNode, Klant klant){

        //Maak een nieuwe node aan voor de klant
        KlantNode newKlantNode = new KlantNode(klant);

        //Als de tree nog leeg is, zet deze node als root
        if(root == null){
            root = newKlantNode;
        }else{
            //Anders als de nieuwe klantID kleiner is dan de focusKlantID, zoek naar links
            if(klant.getKlantID() < focusNode.key ){
                if(focusNode.leftChild != null) {
                    //Zolang er leftChilds zijn met een hoger ID dan de nieuwe klant
                    //Recursive call om verder naar links te navigeren
                    addKlantNode(focusNode.leftChild, klant);
                }else{
                    //Als de focusnode geen leftchild heeft, maak de nieuwe klant dan de child
                    focusNode.leftChild = newKlantNode;
                }
            }else if(klant.getKlantID() > focusNode.key ) {
                if (focusNode.rightChild != null) {
                    //Zolang er rightChilds zijn met een hoger ID dan de nieuwe klant
                    //Recursive call om verder naar rechts te navigeren
                    addKlantNode(focusNode.rightChild, klant);
                } else {
                    //Als de focusnode geen rightchild heeft, maak de nieuwe klant dan de child
                    focusNode.rightChild = newKlantNode;
                }

            }
        }
    }

    //Searchdialoge
    private void zoekKlant(){
        System.out.println("Zoek op ID: ");
        int search = user_input.nextInt();
        findKlantNode(root, search);
    }

    //find node
    private void findKlantNode(KlantNode focusNode, int ID){

        //Als focusNode null is, is de klant niet gevonden
        //of de methode is met null opgeroepen
        if(focusNode != null) {

            //Als de focusNodeID niet gelijk is aan de gezochte ID..
            if (focusNode.key != ID) {

                //..zoek naar links als de ID lager is
                if (ID < focusNode.key) {

                    //Recursive call om verder te zoeken
                    findKlantNode(focusNode.leftChild, ID);

                //..of naar rechts als de ID hoger is
                }else if(ID > focusNode.key){

                    //Recursive call om verder te zoeken
                    findKlantNode(focusNode.rightChild, ID);
                }
            }else{
                //ID gelijk aan de gezochte ID, dus gevonden
                System.out.println("Klant gevonden:\n" + focusNode.getKlant().getEmail());
                returnToMenu();
            }
        }else{
            //focusNode is null, klant niet gevonden
            System.out.println("Klant niet gevonden");
            returnToMenu();
        }

    }

    //in-order traversal
    private void inOrderTraverse(KlantNode focusNode){

        if(focusNode != null){

            //Ga via links
            inOrderTraverse(focusNode.leftChild);

            //Print huidige klant
            System.out.println(focusNode.key+" : " +focusNode.getKlant().getEmail());

            //ga naar rechts
            inOrderTraverse(focusNode.rightChild);
        }

    }

    private void verwijderKlant(){
        System.out.println("Verwijder met ID: ");
        int search = user_input.nextInt();
        deleteKlantNode(root, search);
    }

    private void deleteKlantNode(KlantNode focusNode, int ID){

        if(focusNode != null){
            if(focusNode.key != ID){
                if(ID < focusNode.key){
                    parent = focusNode;
                    deleteLeft = true;
                    deleteKlantNode(focusNode.leftChild, ID);
                }else if(ID > focusNode.key){
                    parent = focusNode;
                    deleteLeft = false;
                    deleteKlantNode(focusNode.rightChild, ID);

                }
            }else{

                //Verwijder een leaf
                if(focusNode.leftChild == null && focusNode.rightChild == null){
                    //Als het de root is -> verwijder
                    if(focusNode == root){
                        root = null;
                    }else if(deleteLeft){
                        //Als het een linker-child was van de parent
                        //Verwijder dit bij parent
                        parent.leftChild = null;
                    }else{
                        //Anders bij het rechter-child
                        parent.rightChild =null;
                    }
                }
                //Verwijder een node met enkel een leftChild
                else if(focusNode.rightChild == null){
                    if(focusNode == root) {
                        root = focusNode.leftChild;
                    }else if(deleteLeft){
                        parent.leftChild = focusNode.leftChild;
                    }else{
                        parent.rightChild = focusNode.leftChild;
                    }
                }
                //Verwijder een node met enkel een rightChild
                else if(focusNode.leftChild == null){
                    if(focusNode == root) {
                        root = focusNode.rightChild;
                    }else if(deleteLeft){
                        parent.leftChild = focusNode.rightChild;
                    }else{
                        parent.rightChild = focusNode.rightChild;
                    }
                }
                //Verwijder een node met beiden childs
                else{
                    replaceNode = focusNode;
                    KlantNode replacement = getReplacement();

                    //Als de focusnode de root is verplaats het dan met de vervangende node
                    if(focusNode == root){
                        root = replacement;
                        //Als de verwijderde node een linker-child was
                        //maak de vervanging dan de linker-child
                    }else if(deleteLeft)
                        parent.leftChild = replacement;
                    else
                        //anders voor de rechter-child
                        parent.rightChild = replacement;

                    replacement.leftChild = focusNode.leftChild;


                }


            }
            //focusNode is null, klant niet gevonden
        }else{
            System.out.println("Klant niet verwijderd, klant niet gevonden");
        }
    }


    private KlantNode getReplacement() {

        KlantNode replacementParent = replaceNode;
        KlantNode replacement = replaceNode;

        KlantNode focusNode = replaceNode.rightChild;

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


}


//Class node
class KlantNode{

    int key;
    private Klant klant;

    KlantNode leftChild;
    KlantNode rightChild;

    KlantNode(Klant klant){
        this.key = klant.getKlantID();
        this.klant = klant;
    }

    public Klant getKlant(){
        return klant;
    }
}
