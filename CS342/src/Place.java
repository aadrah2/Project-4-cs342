import java.util.*;




public class Place {

	private int id;//name id
	private int numOfLines;
	private int dirCount;//number of directions
	Random rand=new Random();
	private String name;//name of room
	private String desc=" ";//description of room
	private static Vector<Place> placesVector=new Vector<Place>();
	private ArrayList<Direction> directions = new ArrayList<Direction>();;//array of directions leaving room
	private Map<String,Artifact> artifacts= new HashMap<String,Artifact>();;
	public static Map<Integer,Place> placesMap = new HashMap<Integer,Place>();
	public Map<Integer, Character> characters = new HashMap<Integer,Character>();
	private Vector<Character> characterVector = new Vector<Character>();

	Merchant merchant; // merchant reference as to whether there is a merchant in the place or not

	private static String line;
	private Scanner lineScanner;
	Battle battle;

	
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
	Place(){
		
	}
	
	Place(String s, int ID){
		name = s;
		id=ID;
		placesMap.put(id, this);
		placesVector.add(this);
	}
	
	//constructor for initializing variables
	Place(Scanner infile){
		 int merchantExists; // when we find out whether the place has a merchant or not we still need to initialize all the other place information
		 // before passing merchant the place information. The place gets a merchant but also each merchant object gets a place object for which it resides
		 battle = Battle.getBattle();

		 while(true) {
				line = infile.nextLine();
				line=getCleanLine(line);
				if(line.length()<1) {
					 continue;
				 }
				break;
			}
		lineScanner = new Scanner(line);
		id=lineScanner.nextInt();
		//4.1 code
		merchantExists = lineScanner.nextInt();

		//end 4.1 code
		name=lineScanner.nextLine();
		name=name.trim();
		line = infile.nextLine();
		line=getCleanLine(line);
		lineScanner=new Scanner(line);
		numOfLines=lineScanner.nextInt();
		
		line = infile.nextLine();
		line=getCleanLine(line);
		lineScanner = new Scanner(line);
		for(int i=0;i<numOfLines;i++) {
			desc+=infile.nextLine();
		}
		placesMap.put(id, this);
		placesVector.add(this);
		if ( merchantExists == 1) {
			merchant = new Merchant(this);
			
		}
		else {
			merchant = null;
		}
	}
	
	//Getters for each variable
	String name() {
		return name;
	}
	int id() {
		return id;
	}
	Place  getPlaceById(int id) {
		return placesMap.get(id);
	}
	Place returnEntrance() {
		return placesVector.get(0);
	}
	Artifact returnArtifact(String name) {
		return artifacts.get(name);
	}
	boolean conatinsArtifact(String artifact) {
		if(artifacts.containsKey(artifact)) {
			return true;
		}
		return false;
	}
	
	
	
	//adders
	void addDirection(Direction d) {
			directions.add(d);
			dirCount++;
	}
	void addArtifact(Artifact a,String name) {
		artifacts.put(name, a);
	}
	void addCharacter(int id, Character c) {
		characters.put(id, c);
	}

	
	//removers
	void removeArtifact(String name) {
		artifacts.remove(name);
	}
	void removeCharacter(int id, Character c) {
		characters.remove(id, c);
		characterVector.remove(c);

	}
	
	void changeMerchant(Merchant m) {
		merchant=m;
	}

	void useKey(Artifact a) {
		for(Direction d : directions) {
			d.useKey(a);
		}
	}
	void battle() { //  more of a testing function for battle 
		battle.battle(characterVector.get(0), characterVector.get(1));
	}

	Place followDirection(String direction) {
		
		//for leap iterating through ever direction looking for a match
		for(Direction d : directions ) {
			if(d.Match(direction)) 
				 return d.follow();
			}
		System.out.println("You did not move in a valid direction");
		return this;
	}
		
	public boolean notifyMerchant(Character c){
		//If place has a mechant, return false to indicate so. Otherwise, it has a merchant
		// and return true after calling the menu options for that merchant
		if ( merchant == null ) {
			return false;
		}
		if ( c instanceof Player ) {
			merchant.greet(c);
			return true;
		}
		return false;
	}
	
	public void notifyMerchant_stock(Artifact a) {
					Merchant m = new Merchant();
					
					if ( m != null ) {
						m.stock(a);
					}
	
	}
	// can be used as debug function
	
	public void merchant_inventory() {
		if ( merchant != null ) {
				merchant.inventory();
		}
	}
	Merchant hasMerchant() {
		if ( merchant == null ) {
			return null;
		}
		else {
			return merchant;
		}
	}
	
	public void merchantLocations() {
		for ( Map.Entry<Integer, Place> entry: placesMap.entrySet() ) {
			System.out.println(entry.getValue().hasMerchant() + " :  " + name );
		}
	}
	
	//returns random place for artifacts/players to go
	Place randomPlace() {
		int randomNum=rand.nextInt(placesVector.size());
		return placesVector.get(randomNum);
	}
	
	boolean emptyRoom() {
		if(artifacts.isEmpty()) {
			return false;
		}
		return true;
	}
	void print() {
		System.out.println("id: " + id);
		System.out.println("name: " + name);
		System.out.println("description: " + desc);
		System.out.println("directions:");
		printDirections();
	}
	
	//prints directions leaving room
	void printDirections() {
		for(int i=0;i<dirCount;i++) {
			directions.get(i).print();
		}
	}
	
	Direction returnDirection() {
		int num = rand.nextInt(directions.size());
		return directions.get(num); 
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
}
