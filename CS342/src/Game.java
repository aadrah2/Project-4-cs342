//manuel torres
import java.io.*;
import java.util.*;

public class Game {
	private String name;//name of game
	private int numPlaces;//number of places
	private int numDirections;
	private double fileVersion; 
	private static String line;
	private Scanner lineScanner;
	private int numArtifacts;
	private int numWeapons;
	Battle battle;
	private static Map<Integer,Character> characters= new HashMap<Integer,Character>();
	//constructor for initialzing variables
	private static TreeMap<Integer,Currency> currencies= new TreeMap<Integer,Currency>();

	//Clean line function
	static private String getCleanLine(String scanner) {
		int findIndex= line.indexOf("//");
		if(line.length()>0 && findIndex==-1) {
			return line;
		}
		
		line=line.substring(0,findIndex);
		line= line.trim();
		return line;
	}
	
	Game(Scanner infile) {
		 battle=Battle.getBattle();
		 while(infile.hasNext()) {
			 //checking if line is just spaces
			 line = infile.nextLine();
			 if(line.length()<1) {
				 continue;
			 }
			 
			 line=getCleanLine(line);
			 if(line.length()<1) {
				 continue;
			 }
			 
			 lineScanner = new Scanner(line);
			 
			 //getting file version
			 if(lineScanner.next().equals("GDF")) {
				 fileVersion=lineScanner.nextDouble();
				 lineScanner.skip(" ");
				 name= lineScanner.nextLine();
				 continue;
			 }
			 
			 lineScanner = new Scanner(line);
			 
			 //reading places
			 if(lineScanner.next().equals("PLACES")) {
				 numPlaces= lineScanner.nextInt();
				 for(int i =0; i < numPlaces; i++) {
					 Place n = new Place(infile); 
				 }
				 continue;
			 }
			 Place n = new Place("Exit",1);
			 //Place newPlace = new Place();
			 //newPlace.merchantLocations();
			 
			 lineScanner = new Scanner(line);
			 //reading directions
			 if(lineScanner.next().equals("DIRECTIONS")) {
				 numDirections = lineScanner.nextInt();
				 for(int i = 0 ; i < numDirections; i++) {
					 new Direction(infile);
				 }
				 continue;
			 }
			 
			 lineScanner = new Scanner(line);
			 //if file version equal to 4.0 then read in characters
			 if(fileVersion ==4.0) {
				 if(lineScanner.next().equals("CHARACTERS")) {
					 int numCharacters = lineScanner.nextInt();
					 String characterType;
					 //for loop evaluating character types and initialzing them
					 for(int i = 0 ; i < numCharacters; i++) {
						 line=infile.next();
						 lineScanner=new Scanner(line);
						 characterType = lineScanner.next();
					 
						 if(characterType.equals("NPC")) {
							 Character c =  new NPC(infile);
							 characters.put(c.id(), c);
						 }
						 else {
							 Character c = new Player(infile);
							 characters.put(c.id(), c);	 
						 }
					 }
					 continue;
				 }
			 }
			 //for if file does not contain defualt characters
			 else {
				 System.out.println("No default Characters in file how many woud you like to add?");
				 Scanner input = new Scanner(System.in);
				 int numOfCharacters=input.nextInt();
				 Place p=new Place();
				 for(int i=0;i<numOfCharacters;i++) {
					 Character c =  new Player(i+1,"Player"+i+1, " ", p.returnEntrance());
					 characters.put(c.id(), c);
				 }
			 } 
			 
			 lineScanner = new Scanner(line);
			 
			 //reading in Artifacts
			 if(lineScanner.next().equals("ARTIFACTS")) {
				 numArtifacts= lineScanner.nextInt();
				 for(int i = 0 ; i < numArtifacts; i++) {
					 new Artifact(infile);
				 }
				 continue;
			 }
			 
			 Currency c1 = new Currency("dollar", 100);
			 int newSize = currencies.size();
			 currencies.put(newSize, c1 );
			 
			 lineScanner = new Scanner(line);
			 if(fileVersion >= 4.0) {
				 if(lineScanner.next().equals("WEAPONS")) {
					 numWeapons = lineScanner.nextInt(); 
					 for(int i = 0; i < numWeapons; i++) {
						 new Weapon(infile);
					 }//end of for
				 }//end of if
			 }//end of if
		 }//end of while
	}//end of game constructor
	
	
	//prints all places and their ID's
	public TreeMap<Integer, Currency> getCurrencies(){
		return currencies;
	}
	
	void print() {
		int i=0;
		Place p=new Place();
		while(i<numPlaces) {
			 p.print();
			i++;
		}	
	}
	
	//prints all directions
	void printDirections() {
		int i=0;
		Place p=new Place();
		while(i<numPlaces) {
			 p.printDirections();
			i++;
		}
	}
	void createMerchants() {
		for (int i=0;i<8;i++) {
			Place p = Place.randomPlace();
			Merchant m = new Merchant(p);
			p.changeMerchant(m);
		}
	}
	void checkForBattle() {
		for(Place p : Place.placesMap.values()) {
			if(p.characters.size()>1) {
				p.battle();
			}
		}
	}
	//plays game
	void play() {
		System.out.println("Welcome to " + name + " Dungeon");
		createMerchants();
		
		while(true) {
			for(Character c : characters.values()) {
				c.makeMove();
			}
			checkForBattle();
		}
	}			
}//end of game