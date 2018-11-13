import java.util.Scanner;

public class Artifact {
	private String name;
	private String description=" ";
	private int value;
	private int id;
	private int mobility;
	private int roomID;
	private int keyPattern;
	private Scanner lineScanner;
	private static String line;
	private Place currentPlace;
	private Character currentCharacter;
	private int attack; 
	private int defense; 
	
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
	
	Artifact(Scanner infile){
		while(true) {
			line = infile.nextLine();
			line=getCleanLine(line);
			if(line.length()<1) {
				 continue;
			 }
			break;
		}
		lineScanner = new Scanner(line);
		roomID = lineScanner.nextInt();
		
		line = infile.nextLine();
		line = getCleanLine(line);
		lineScanner = new Scanner(line);
		id=lineScanner.nextInt();
		value =  lineScanner.nextInt();
		//assigning values depending on weapon or artifact
		if(this instanceof Weapon) {
			attack = lineScanner.nextInt();
		}
		else {
			mobility= lineScanner.nextInt();
		}
		if(this instanceof Weapon) {
			defense = lineScanner.nextInt();
		}
		else {
			keyPattern= lineScanner.nextInt();
		}
		name = lineScanner.nextLine();
		name=name.trim();
		
		line = infile.nextLine();
		line=getCleanLine(line);
		lineScanner = new Scanner(line);
		int num= lineScanner.nextInt();
		if(this instanceof Weapon) {
			for(int i = 0; i < num; i++) {
				description += infile.nextLine();
			}
			return; 
		}
		for(int i=0;i<num; i++) {
			description+=infile.nextLine();
		}
		
		Place p = new Place();
		if(roomID>0) {
			p.getPlaceById(roomID).addArtifact(this,name);
			currentPlace=p.getPlaceById(roomID);
		}
		else if(roomID==0) {
			currentPlace=p.randomPlace();
		}
		else {
			currentCharacter = Character.getCharacterbyId(Math.abs(roomID));
			currentCharacter.addArtifact(this,name);
			
		}
		
	}
	
	
	//getters
	int value() {
		return value;
	}
	int mobility() {
		return mobility;
	}
	int roomID() {
		return roomID;
	}
	String name() {
		return name;
	}
	String description() {
		return description;
	}
	int id() {
		return id;
	}
	int keyPattern() {
		return keyPattern;
	}
	Place currentPlace() {
		return currentPlace;
	}
	Character currentCharacter() {
		return currentCharacter;
	}
	
	
	//setters
	void setCurrentPlace(Place p) {
		currentPlace=p;
		
	}
	void setCurrentCharacter(Character c) {
		currentCharacter=c;
	}
	void unlockDirection(Direction d) {
		d.useKey(this);
	}
}

class Weapon extends Artifact{
	Weapon(Scanner infile){
		super(infile);
	}
	
	public Weapon getWeapon(String s, Character c) {
		return c.artifacts.get(s); 
	}
	
	public int getAttack(String s, Character c) {
		return c.artifacts.gets(s).attack; 
	}
}
