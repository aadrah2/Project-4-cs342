//manuel torres
import java.util.*;

public class Merchant{
	private Place place; // each merchant has a place associated with it that will be its locations
	private Map<String, Artifact> artifactInventory = new HashMap<String, Artifact>();
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
				m.addArtifact(a);
			}
		}
	}
	
	public void greet(Character  c) {
		if ( c instanceof Player == false ){
			return;
		}
		System.out.println("Welcome to the merchant shop! These are our items we have to offer");
		inventory();
		System.out.println("What would you like to do, buy, sell, or look?");
		Scanner userInput = new Scanner(System.in);
		String choice = userInput.nextLine();
		
		if ( choice.equalsIgnoreCase("sell") ) {
			sell(c);
			
		}
		else if ( choice.equalsIgnoreCase("buy") ) {
			buy(c);
		}
	}
	
	public void inventory() {
		for ( Map.Entry<String, Artifact> entry: artifactInventory.entrySet() ) {
			entry.getValue().inventoryPrint();
		}
	}
	
	public void sell(Character c) {
		if ( c instanceof Player == false) {
			return;
		}
		System.out.println("Please enter the item that you would like to purchase");
		Scanner scanner = new Scanner(System.in);
		String line = scanner.nextLine();
		
		for ( Map.Entry<String, Artifact> entry: artifactInventory.entrySet() ) {
			if ( line.equalsIgnoreCase(entry.getValue().name() ) ) { // if the merchant has the item
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
					System.out.println(entry.getKey() + "has been added to " + c.name() + "'s bag of artifacts");
					return;
					
				}
				else { // not enough money to buy item
					System.out.println("You do not have enough money to buy that item");		
				}
				
			}
		
		}
		return;
	}
	
	public void buy(Character c) {
		if ( c instanceof Player == false ) {
			return;
		}
		System.out.println("Which item of yours may I buy?");
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
			}
			else { // if player has a copy of the item, decrease the number of copies by one
				
				artifact.loseCopy();
				if ( artifact != null ) {
					Artifact newArtifact = new Artifact(artifact);
					addArtifact( newArtifact); // add the copy artifact to the merchant inventory
				}
			}
			System.out.println("Item has been purchased by the merchant!");
		}	
	}
}