import java.util.*;
 
public class Merchant{
 
    private Place place; // each merchant has a place associated with it that will be its locations
    private Map<String, Artifact> artifactInventory = new HashMap<String, Artifact>();
 
    public Merchant() {
         
    }
    public Merchant(Place p) 
    {
        place = p; // put the merchant in said place
    }
     
    public void addArtifact(Artifact a) {
        if ( a != null ) {
            Match m = new Match();
            Artifact result = m.contains(a.name(), artifactInventory); // returns the artifact in inventory which contained was true for
            if ( result != null ) { // if 
                result.addCopy(); // increase the number of copies of specified item since it was already contained in the list
            }
            else {
                artifactInventory.put(a.name(), a);
            }
        }
         
    }
     
    public void stock(Artifact a) { // for all places, if there's a merchant, stock with
        // the weapon given in the argument
        Place p = new Place();
        Map<Integer, Place> placeMap = p.placesMap;
         
        for ( Map.Entry<Integer, Place> entry:  placeMap.entrySet() ) {
            Merchant m = entry.getValue().hasMerchant();
            if ( m != null ) {
                Artifact newArtifact = new Artifact(a);
                m.addArtifact(newArtifact);
            }
        }
    }
     
    public void greet(Character  c) {
        if ( c instanceof Player == false ){
            return;
        }
         
        //1. greet player with a message and produce choices; sell, buy, look
        boolean inputProcessed = false;
        while ( inputProcessed == false ) {
            System.out.println("Welcome to the merchant shop! These are our items we have to offer");
            inventory();
            System.out.println("What would you like to do, buy, sell, or look?");
            System.out.println("(Enter leave to exit)");
            System.out.println("Your balance: $" + c.showBalance() );
             
            Scanner userInput = new Scanner(System.in);
             
            String choice = userInput.nextLine();
             
            if ( choice.equalsIgnoreCase("sell") ) {
                sell(c);
                inputProcessed = true;
            }
            else if ( choice.equalsIgnoreCase("buy") ) {
                buy(c);
                inputProcessed = true;
            }
            else if ( choice.equalsIgnoreCase("look") ) {
                inventory();
                System.out.println("See anything you like?");
            }
            else if (choice.equalsIgnoreCase("leave" ) ) {
                System.out.println("Thanks for stopping by!");
                inputProcessed = true;
                return;
            }
            else {
                System.out.println("I'm sorry. I didn't hear you..");
            }
        }
 
    }
     
    public void artifactInformation(Artifact a) {
            // prints artifact info that the merchant has 
            System.out.println("Artifact Name: " + a.name());
            System.out.println("Desc:" + a.description() );
            System.out.println("Price: $" + a.value() );
            System.out.println("Mobility: " + a.mobility() );       
    }
     
    public void inventory() {
        for ( Map.Entry<String, Artifact> entry: artifactInventory.entrySet() ) {
            if ( entry.getValue()!= null ) {
                artifactInformation(entry.getValue() );
            }
        }
    }
     
    public void buy(Character c) {
        if ( c instanceof Player == false) {
            return;
        }
        System.out.println("Please enter the item that you would like to purchase");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
         
        boolean hasRequestedItem = false;
        // first confirm that the requested item is in the merchant's inventory list
        // then if it is exchange artifacts, but copy the artifact before exchanging it
        // to avoid copying numCopies and other fields, also exchange money(deduct money from 
        // the character)
         
        for ( Map.Entry<String, Artifact> entry: artifactInventory.entrySet() ) {
            if ( line.equalsIgnoreCase(entry.getValue().name() ) ) { // if the merchant has the item
                hasRequestedItem = true;
                if ( c.balanceSufficient(entry.getValue().value() ) ) { // if character has enough money
                    c.pay(entry.getValue().value() );
                    //1. if character has item already, then increase the number of copies, which addArtifact handles.
                    // 2. if character does not have item, make a copy of the one the merchant has and add the copy and delete the one
                    // the merchant has from the map
                    Artifact newArtifact = new Artifact(entry.getValue() );
                     
                    c.addArtifact(newArtifact); // add the copy
                    if ( entry.getValue().count() == 0 ) { // if merchant has no more copies of said item, remove from merchant map
                        artifactInventory.remove(entry.getKey() ); 
                    }
                    else { // if merchant has a copy of the item, decrease the number of copies by one
                        entry.getValue().loseCopy();
                    }
                    System.out.println(entry.getKey() + " has been added to " + c.name() + "'s bag of artifacts");
                    System.out.println("Remaining balance: $" + c.showBalance() );
                    return;
                     
                }
                else { // not enough money to buy item
                    System.out.println("You do not have enough money to buy that item");        
                }
                 
            }   
        }
        if ( hasRequestedItem == false ) {
            System.out.println("We're sorry, we don't have that item right now.");
        }
        return;
    }
     
    public void sell(Character c) {
        // function sells an item that the merchant has to the buyer and they get money
        // equal to the artifact's value 
        if ( c instanceof Player == false ) {
            return;
        }
        System.out.println("Which item of yours may I buy?");
        System.out.println("Your balance: $" + c.showBalance() );
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
         
        Artifact artifact = c.hasArtifact(line); // function returns the artifact that the person has
 
        if ( artifact == null ) {
            System.out.println("But you do not have that item");
        }
        else {
            // player gets money for item sold, their item count decreases by one, and the merchant's inventory goes up by one for that item
            //however the merchant's money is not stated there is no need
            c.addMoney(artifact.value() );
            if ( artifact.count() == 0 ) { // if player has no more copies of said item, remove from player map
                c.discardArtifactCopies(line);
                Artifact newArtifact = new Artifact(artifact);
                addArtifact(newArtifact); // add copy of the artifact so as to avoid copying number of copies of the artifact
            }
            else { // if player has a copy of the item, decrease the number of copies by one
                 
                artifact.loseCopy();
                if ( artifact != null ) {
                    Artifact newArtifact = new Artifact(artifact);
                    addArtifact( newArtifact); // add the copy artifact to the merchant inventory
                }
            }
            System.out.println("Item has been purchased by the merchant!");
            System.out.println("Remaining balance:  $" + c.showBalance());
        }   
    }
}