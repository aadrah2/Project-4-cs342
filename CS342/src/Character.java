import java.util.*;

public class Character {
	protected int id;
	private String name;
	private String description=" ";
	Random rand=new Random();
	private static String line;
	protected Place currentRoom;
	private Scanner lineScanner;
	private static Map<Integer,Character> characters= new HashMap<Integer,Character>();
	protected Map<String, Artifact> artifacts=new HashMap<String, Artifact>();
	public DecisionMaker decision;
	
	//variable stats for battle mode
	protected int health;
	private boolean alive;
	private int defense;
	private int stamina;
	protected int baseAttack;
	
	
	
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
		alive=true;
		defense=0;
		stamina=100;
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
	Artifact getArtifact(String a) {
		if(artifacts.containsKey(a)) {
			return artifacts.get(a);
		}
		return null;
	}
	Artifact returnArtifact() {
		int num = rand.nextInt(artifacts.size());
		int i = 0;
		for(Artifact a : artifacts.values()) {
			if(num==i) {
				return a;
			}
			i++;
		}
		return null;
	}
	boolean alive() {
		return alive;
	}
	
	
	
	void printInventory() {
		for (Artifact a: artifacts.values()) {
			System.out.println("Name:" + a.name() + "Description:" + a.description() + "Value:"+ a.value() );
		}
	}
	
	
	void addHealth(int num) {
		health+=num;
		if(health>100) {
			health=100;
		}
	}
	void removeHealth(int num) {
		health-=num;
		if(health<=0) {
			health=0;
			alive=false;
		}
	}
	void addDefense(int num) {
		defense+=num;
	}
	void removeDefense(int num) {
		if(defense<=0) {
			defense=0;
			removeHealth(num);
		}
		else {
			defense-=num;
		}
	}
	void addStamina(int num) {
		stamina+=num;
		if(stamina>100) {
			stamina=100;
		}
	}
	
	void removeStamina(int num) {
		if(stamina>0) {
			stamina-=num;
		}
	}
	int returnStamina() {
		return stamina;
	}
	int returnBaseAttack() {
		return baseAttack;
	}
	
	
	void printStats() {
		System.out.println("Health: " + health);
		System.out.println("Defense: " + defense);
		System.out.println("Stamina: " + stamina);
	}
	void makeMove() {
		Move m = new Move();
		
		System.out.println("What move would you like to do for "+ name + ":");
		Scanner input = new Scanner(System.in);
		String command  = input.next();
		
		m=decision.getMove(this, this.currPlace(), command);
		//switch case for evaluating move type 
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
			object = input.next();
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
		decision = new AI();
		health=200;
		baseAttack=30;
		
	}
	void makeMove() {
		Move m = decision.getMove(this, this.currentRoom, "Random");
		
		switch(m.type) {
		case GET:
			Artifact a = currentRoom.returnArtifact();
			addArtifact( a, a.name() );
			currentRoom.removeArtifact(a.name());
			artifacts.get(a.name()).setCurrentCharacter(this);
			artifacts.get(a.name()).setCurrentCharacter(null);
			System.out.println(this.name() + " have picked up " + a.name());
		case DROP:
			Artifact A = returnArtifact();
			removeArtifact(A, A.name());
			System.out.println(this.name() + " have dropped" + A.name());
		case USE:
			Artifact b = returnArtifact();
			currentRoom.useKey(b);				
			System.out.println("All doors unlocked by key have been unlocked");
			break;
		default:
			currentRoom.removeCharacter(id, this);
			currentRoom=currentRoom.returnDirection().follow();
		}
		
		}
	
	
	
}


class Player extends Character{
	
	Player(Scanner infile){
		super(infile);
		decision = new UI();
		health=200;
		baseAttack=30;
	}
	Player(int ID, String Name, String Desc, Place room){
		super(ID,Name,Desc,room);
		decision = new UI();
		health=200;
		baseAttack=30;
	}
	
	
}

class UI implements DecisionMaker{
	public Move getMove(Character c, Place p, String argument) {
		Move m = new Move();
		m.getMoveType(argument);
		return m;
		
	}
	public battleType getAttack(Character c) {
		battleType m = null;
		while(true) {
			  if(c.returnStamina()==0) {
				  System.out.println(c.name() + " You have run out of stamina you cannot make a move.");
				  m=null;
				  break;
			  }
			  System.out.println(c.name() + " what would you like to do.");
			  c.printStats();
			  Scanner input = new Scanner(System.in);
			  String w = input.next();
			  if(!Battle.getBattle().getBattletype(w, m)) {
				System.out.println("Invalid battle move");
				continue;
			  }
			  break;
			}
		return m;
	}
}



class AI implements DecisionMaker{
	Random rand=new Random();
	
	public Move getMove(Character c, Place p, String argument) {
		int randomNumber = rand.nextInt(4);
		Move move = new Move();
		
		switch(randomNumber) {
		case 0:
			move.getMoveType("Go");
			break;
		case 1:
			move.getMoveType("Get");
			break;
		case 2:
			move.getMoveType("Drop");
			break;
		case 3:
			move.getMoveType("Use");
			break;
	}
		return move;
	}
	public battleType getAttack(Character c) {
		int randomNumber = rand.nextInt(4);
		battleType b = null;
		
		switch(randomNumber) {
		case 0:
			b=battleType.MediumAttack;
			break;
		case 1:
			b=battleType.StrongAttack;
			break;
		case 2:
			b=battleType.WeakAttack;
			break;
		case 3:
			b=battleType.Defend;
			break;
	}
	return b;
}
	
}