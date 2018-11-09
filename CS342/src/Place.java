import java.util.*;


public class Place {

	private int id;//name id
	private int numOfLines;
	private int dirCount;//number of directions
	Random rand=new Random();
	private String name;//name of room
	private String desc=" ";//description of room
	private static Vector<Place> placesVector=new Vector<Place>();
	private ArrayList<Direction> directions;//array of directions leaving room
	private Map<String,Artifact> artifacts;
	public static Map<Integer,Place> placesMap = new HashMap<Integer,Place>();
	public Map<Integer, Character> characters = new HashMap<Integer,Character>();
	
	private static String line;
	private Scanner lineScanner;
	

	
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
	
	//constructor for initializing variables
	Place(Scanner infile){
		 directions = new ArrayList<Direction>();
		 artifacts = new HashMap<String,Artifact>();
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
	}


	


	void useKey(Artifact a) {
		for(Direction d : directions) {
			d.useKey(a);
		}
	}

	Place followDirection(String direction) {
		
		//for leap iterating through ever direction looking for a match
		for(int i=0;i<dirCount;i++) {
			if(directions.get(i).Match(direction)) 
				 return directions.get(i).follow();
			}
		System.out.println("You did not move invalid direction");
		return this;
	}
		
	
	
	
	
	
	//returns random place for artifacts/players to go
	Place randomPlace() {
		int randomNum=rand.nextInt(placesVector.size());
		return placesVector.get(randomNum);
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
}
