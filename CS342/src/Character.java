import java.util.*;

public class Character {
	private int id;
	private String name;
	private String description=" ";
	Random rand=new Random();
	private static String line;
	private Place currentRoom;
	private Scanner lineScanner;
	private static Map<Integer,Character> characters= new HashMap<Integer,Character>();
	private static Map<String, Artifact> artifacts=new HashMap<String, Artifact>();
	DecisionMaker decision;
	
	static private String getCleanLine(String scanner) {
		int findIndex= line.indexOf("//");
		if(line.length()>=0 && findIndex==-1) {
			return line;
		}
		if(line.length()<0) {
			return line;
		}
		line=line.substring(0,findIndex);
		line= line.trim();
		return line;
	}
	
	
	Character(Scanner infile){
		Place p = new Place();
		line = infile.nextLine();
		lineScanner = new Scanner(line);
		int roomID=lineScanner.nextInt();
		if(roomID>0) {
			currentRoom=p.getPlaceById(roomID);
		}
		else {
			currentRoom=p.randomPlace();
		}
		
		
		line = infile.nextLine();
		line=getCleanLine(line);
		lineScanner = new Scanner(line);
		id=lineScanner.nextInt();
		name=lineScanner.nextLine();
		name=name.trim();
		
		line=infile.nextLine();
		line=getCleanLine(line);
		lineScanner = new Scanner(line);
		int numLines=lineScanner.nextInt();
		for(int i=0;i<numLines;i++) {
			description+=infile.nextLine();
		}
		characters.put(id,this);
	}


	Character(int ID, String Name, String Desc, Place room){
		id=ID;
		name=Name;
		description=Desc;
		currentRoom=room;
	}
	
	//getters
	int id() {
		return id;
	}
	String name() {
		return name;
	}
	public static Character getCharacterbyId(int id) {
		
		return characters.get(id);
	}
	Place currPlace() {
		return currentRoom;
	}
	
	//adder and remover calls functions for place and artifact as well
	void addArtifact(Artifact a, String name) {
		if(a.mobility()>0) {
			artifacts.put(name, a);
		}
		else {
			System.out.println("This artifact is to heavy to carry.");
		}
		
	}
	void removeArtifact(Artifact a , String name) {
		if(artifacts.get(name)!=null) {
			currentRoom.addArtifact(a, name);
			artifacts.remove(name);
			a.setCurrentCharacter(null);
			a.setCurrentPlace(currentRoom);
		}
	}
	
	
	void printInventory() {
		for (Artifact a: artifacts.values()) {
			System.out.println("Name:" + a.name() + "Description:" + a.description() + "Value:"+ a.value() );
		}
	}
	
	
	
	void makeMove() {
		Move m = new Move();
		
		System.out.println("What move would you like to do for "+ name + ":");
		Scanner input = new Scanner(System.in);
		String command  = input.next();
		
		m=decision.getMove(this, this.currPlace(), command);
		//switch case for evuluating move type 
		switch(m.type) {
			
		case GO :
			String object= input.next();
			currentRoom.removeCharacter(id, this);
			currentRoom=currentRoom.followDirection(object);
			break;
			
		case LOOK:
			currentRoom.printDirections();
			makeMove();
			break;
			
		case GET:
			object= input.next();
			if(currentRoom.conatinsArtifact(object) ) {
				addArtifact(currentRoom.returnArtifact(object), object);
				currentRoom.removeArtifact(object);
				artifacts.get(object).setCurrentCharacter(this);
				artifacts.get(object).setCurrentCharacter(null);
				System.out.println(name + " have picked up " + object);
			}
			else {
				System.out.println("This artifact is not " + name + " this room");
				makeMove();
			}
			break;
			
		case DROP:
			object= input.next();
			if(currentRoom.conatinsArtifact(object)) {
				removeArtifact(currentRoom.returnArtifact(object), object);
				System.out.println(name + " have dropped" + object);
			}
			else {
				System.out.println("This artifact is not in" + name +"inventory");
				makeMove();
			}
			break;
			
		case USE:
			object= input.next();
			currentRoom.useKey(artifacts.get(object));				
			System.out.println("All doors unlocked by key have been unlocked");
			break;
			
		case INVENTORY:
			System.out.println( name + " Inventory: ");
			printInventory();
			makeMove();
			break;
		case NONE:
			break;
		default:
			System.out.println("Ending game");
			System.exit(-3);
			break;
		}
		
	}

	
	
}

class NPC extends Character{
	
	NPC(Scanner infile){
		super(infile);
		decision = new UI();
		
	}
	
	
	
}


class Player extends Character{
	
	Player(Scanner infile){
		super(infile);
		decision = new UI();
	}
	Player(int ID, String Name, String Desc, Place room){
		super(ID,Name,Desc,room);
		decision = new UI();
	}
	
	
}

class UI implements DecisionMaker{
	public Move getMove(Character c, Place p, String argument) {
		Move m = new Move();
		m.getMoveType(argument);
		return m;
		
	}
}

class AI implements DecisionMaker{
	Random rand=new Random();
	
	public Move getMove(Character c, Place p, String argument) {
		Move m =new Move();
		int randomNum=rand.nextInt(4);
		if(randomNum==0) {
			m.getMoveType("Go");
			randomNum=rand.nextInt(18);
			String directions=" ";
			switch(randomNum) {
				case 0:
					directions="N";
				case 1:
					directions="S";
				case 2:
					directions="E";
				case 3:
					directions="W";	
				case 4:
					directions="U";
				case 5:
					directions="D";
				case 6:
					directions="NE";
				case 7:
					directions="NW";
				case 8:
					directions="SE";
				case 9:
					directions="NNE";
				case 10:
					directions="NNW";
				case 11:
					directions="ENE";
				case 12:
					directions="WNW";
				case 13:
					directions="ESE";
				case 14:
					directions="WSW";
				case 15:
					directions="SSE";
				case 16:
					directions="SSW";
				case 17:
					directions="SW";
			}
			
			p.removeCharacter(c.id(), c);
			p=p.followDirection(directions);
		}
		if(randomNum==1) {
			m.getMoveType("Get");
			
		}
		if(randomNum==0) {
			m.getMoveType("Drop");
		}
		if(randomNum==0) {
			m.getMoveType("Use");
		}
		m.getMoveType("None");
		return m;
	}
}