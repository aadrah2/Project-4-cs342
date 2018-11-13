import java.util.*;


enum dirType{ N("North", "N"), S("South","S"), E("East", "E"), W("West","W"), U("Up","U"), D("Down","D"), NE("Northeast", "NE"), 
			  NW("Northwest","NW"), SE("Southeast", "SE"), SW("Southwest","SW"), NNE("North-Northeast","NNE"), NNW("North-Northwest","NNW"), 
			  ENE("East-Northeast","ENE"), WNW("West-Northwest","WNW"), ESE("East-Southwest","ESE"), WSW("West-Southwest","WSW"), 
			  SSE("South-Souteast","SSE"), SSW("South-Southwest","SSW");
			  
			  String arg1;
			  String arg2;
			  
			  private dirType(String a, String b) {
				  arg1=a;
				  arg2=b;
			  }
			  public boolean match(String command) {
					if(arg1.matches(command) ||arg2.matches(command) ) {
						return true;
					}
					return false;
				}		  
}

enum status{
	UNLOCKED,LOCKED;
}

public class Direction {
	private int id;//direction id
	private final Place from;//room from
	private final Place to;//room to
	private final dirType dir;// direction
	private status access;//accessibility
	private static String line;
	private Scanner lineScanner;
	private int artifactKey;
	
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
	
	
	
	//constructor for initalizing variables 
	public Direction (Scanner infile) {
		
		Place p = new Place();
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
		from = p.getPlaceById( lineScanner.nextInt() );
		dir=getDirType(lineScanner.next());
		int toValue = lineScanner.nextInt();
		if(toValue<0) {
			access=status.LOCKED;
		}
		else {
			access=status.UNLOCKED;
		}
		to = p.getPlaceById( Math.abs(toValue) );
		artifactKey=lineScanner.nextInt();
		from.addDirection(this);
		
	}
	//checks if user input matches direction
	

	//boolen function for checking if string matches dirType
	boolean Match(String direction) {
			if(dir.match(direction)) {
				return true;
			}
		
		return false;
	}
	
	
	//getters
	int id() {
		return id;
	}
	dirType returnDir() {
		return dir;
	}

	
	
	
	//unlocking door
	void useKey(Artifact a) {
		if(a.keyPattern()==artifactKey) {
			access=status.UNLOCKED;
		}
	}
	

	
	//returns to room if door unlocked
	Place follow() {
		if(access==status.UNLOCKED ) {
			System.out.println("You are now in room " + to.name());
			return to;
		}
		else {
			System.out.println("Door is locked");
			return from;
		}
	}
	
	
	//retrieving dirType that matches input
	dirType getDirType(String input) {
		for(dirType d: dirType.values()) {
			if(d.match(input)) {
				return d;
			}
			
	}
		return null;
	}
	
	
	
	//prints information of direction
	void print() {
		if(to==null) {
			System.out.println(" id: " + id+ " from: " + from.name() + " to: Exit" + " direction: " + dir + " access: " + access + "\n");
		}
		else {
		System.out.println(" id: " + id+ " from: " + from.name() + " to: " + to.name()+ " direction: " + dir + " access: " + access + "\n");
		}
	}
	
	
	
		
}
