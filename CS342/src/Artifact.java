import java.util.*;


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
	private int numCopies; // number of copies of the named artifact that someone will have 
	private int attack; 
	private int defense;
	public static Map<String,Weapon> weapons = new HashMap<String,Weapon>(); 
	private Character currentCharacter;
	
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
	Artifact(){
		
	}
	public Artifact(Artifact b){
		name = b.name;
		description = b.description;
		value = b.value;
		id = b.id;
		mobility = b.mobility;
		roomID = b.roomID;
		keyPattern = b.keyPattern;
		currentPlace = b.currentPlace; // ?
		attack = b.attack;
		defense = b.defense;
		
		// don't copy numcopies because we just want to make one
		
	}
	
	Artifact(String Name, int Attack, int Defense){
		name = Name;
		attack=Attack;
		defense=Defense;
	
}
	
	Artifact(String Name, int Attack, int Defense, String description){
		name = Name;
		attack=Attack;
		defense=Defense;
		this.description = description;
		
	
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
		numCopies = 0; // excluding the original. when artifact is first made there is that and then whenever someone will do add artifact 
		// it will call the addCopy of their artifact 
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
				if ( i < num - 1 ) {
					description += "\n";
				}
			}
			return; //not placing weapons around the map, so return
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
			currentCharacter.addArtifact(this);
			
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
	void addCopy() {
		numCopies++;
	}
	void loseCopy() {
		numCopies--;
	}
	int keyPattern() {
		return keyPattern;
	}
	int count() {
		return numCopies; // returns the number of artifacts that a character has
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
	public void inventoryPrint() { // same as display but doesn't print out pattern
		System.out.println("Artifact Name: " + name);
		System.out.println("Desc:" + description);
		System.out.println("Value: " + value);
		System.out.println("Mobility: " + mobility);		
	}
	
	public int getAttack() {
		return attack; 
	}
	public int getDefense() {
		return defense;
	}
}

class Weapon extends Artifact{
	// the standard set of weapons for the entire game, should be public because all characters
	// should know attainable weapons
	
    public Set<Weapon> hash_Set = new HashSet<Weapon>(); 

	Weapon(){
		
	}
	Weapon(Scanner infile){
		super(infile);
	}
	
	
	//public Weapon getWeapon(String s, Character c) {
		//return c.artifacts.get(s); 
	//}
	

	
	//public int getAttack(String s, Character c) {
		//return c.artifacts.gets(s).attack; 
	//}
	
	Weapon(String Name, int Attack, int Defense){
		super(Name,Attack,Defense);
	}
	
	Weapon(String Name, int Attack, int Defense, String description){
		super(Name,Attack,Defense, description);
	}
	
	public Weapon isStandardWeapon(String source) { // returns the artifact for which contains yielded true
		Artifact a = new Artifact();
		for ( Map.Entry<String, Weapon> entry: a.weapons.entrySet() ) { // get the standard set of weapons 
			if ( source.equalsIgnoreCase(entry.getValue().name() ) ) {
				return entry.getValue();
			}
		}
		return null;
	}
}//end of weapon
