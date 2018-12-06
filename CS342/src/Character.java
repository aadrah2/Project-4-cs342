import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    Money money;
    private Map<String,Weapon> weapons = new HashMap<String,Weapon>(); 
    //variable stats for battle mode
    protected int health;
    private boolean alive;
    private int defense;
    private int stamina;
    protected int baseAttack;
    IO characterOption = new IO();
    
    private Weapon fists; // when starting the game this is the weapon that all characters have
 
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
        fists=new Weapon("fists",baseAttack,defense, "Your bare hands");
        weapons.put("fists", fists);
    }
 
    Character(int ID, String Name, String Desc, Place room){
        id=ID;
        name=Name;
        description=Desc;
        currentRoom=room;
        money = new Money();
 
    }
     
    Character(String name){ // primarily used for battle from the menu 
        this.name = name;
        alive = true;
        defense=0;
        stamina=100;
        fists=new Weapon("fists",baseAttack,defense, "Your bare hands");
        weapons.put("fists", fists);
    }
    String description() {
    	return description;
    }
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
    public void addArtifact(Artifact a) {
        if ( a != null ) {
            Match m = new Match();
            Artifact result = m.contains(a.name(), artifacts); // returns the artifact in inventory which contained was true for
            if ( result != null ) { // if 
                result.addCopy(); // increase the number of copies of specified item since it was already contained in the list
            }
            else if ( a.mobility() < 0 ) {
                System.out.println("This artifact is too heavy to carry");
            }
            else {
                artifacts.put(a.name(), a);
            }
        }
         
    }
    void removeArtifact(Artifact a , String name) {
        if(artifacts.get(name)!=null) {
            currentRoom.addArtifact(a, name);
            artifacts.remove(name);
            weapons.remove(name);
            a.setCurrentCharacter(null);
            a.setCurrentPlace(currentRoom);
        }
    }
     
    boolean discardArtifactCopies( String name) { // returns false if the artifact is not there, true if it was and got deleted. it also deletes all copies of the artifact
        if(name!=null) {
            Artifact res = artifacts.remove(name);
            if ( res == null ) {
                return false;
            }
            else {
                return true;
            }
        }
        else {
            return false;
        }
         
         
    }
 
    public void addMoney(int value){
        money.addMoney(value);
    }
     
    public boolean pay(int value) {
        if ( value > money.balance() ) {
            return false;
        }
        money.spendMoney(value);
        return true;
    }
     
    public boolean balanceSufficient(int value ) { // if value is more than the balance of the player's money, return false. else return true
        if ( money.balance() < value ) { // if player's balance less than value's they don't have enough moneyh
            return false;
        }
        else {
            return true;
        }
    }
     
    public Artifact hasArtifact(String source) { // returns the artifact for which contains yielded true
        for ( Map.Entry<String, Artifact> entry: artifacts.entrySet() ) {
            if ( source.equalsIgnoreCase(entry.getValue().name() ) ) {
                return entry.getValue();
            }
        }
        return null;
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
     
 
    void death() {
        System.out.println("Characeter " + name + " has died and been removed from game");
        alive=false;
        currentRoom.removeCharacter(id, this);
        dropAllArtifacts();
    }
    void exit() {
        alive=false;
        scatterArtifacts();
    }
    void dropAllArtifacts() {
        for(Artifact a: artifacts.values()) {
            removeArtifact(a,a.name());
        }
    }
    void scatterArtifacts() {
        for(Artifact a: artifacts.values()) {
            a.setCurrentCharacter(null);
            a.setCurrentPlace(Place.placesMap.get(1).randomPlace());
        }
    }
 
    void printArtifactInventory() {
    	if(artifacts.size() == 0) {
    		System.out.println("You have no artifacts.");
    	}
    	System.out.println("Your artifacts");
        for (Artifact a: artifacts.values()) {
            System.out.println("Name: " + a.name() + " \nDescription:" + a.description());
    
        }
    }
    String[][] weapons() {
    	String w[][] = {weapons.keySet().toArray(new String[0])};
    	return w;
    }
    void printWeaponInventory() {
    	System.out.println("Your weapons");
        for (Artifact w: weapons.values()) {
            System.out.println("Name: " + w.name() + "\nDescription : " + w.description() + "\nValue:"+ w.value() +"\nBase Attack: " + w.getAttack() + "\nBase Defense: " +w.getDefense());
        }
    }
     
    void addHealth(int num) {
        health+=num;
        if(health>100) { // should this be 200
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
    int defense() {
    	return defense;
    }
    int health() {
    	return health;
    }
    int returnStamina() {
        return stamina;
    }
    int returnBaseAttack() {
        return baseAttack;
    }
    boolean emptyBag() {
        if(artifacts.isEmpty()) {
            return false;
        }
        return true;
    }
     
    void addWeapon(String s, Weapon w) {
        weapons.put(s,w);
        addArtifact(weapons.get(s));
    }
 
    Weapon getWeapon(String s) {
        if(weapons.containsKey(s)) {
            return weapons.get(s);
        }
        return weapons.get("fists");
    }
     
    int showBalance() { // returns the amount of money the character has
        return money.balance();
    }
     
    void printStats() {
        System.out.println("Health: " + health);
        System.out.println("Defense: " + defense);
        System.out.println("Stamina: " + stamina);
    }
    void takeTurn() {
    	characterOption.display(3);
    	characterOption.GUI4.addListeners(new MoveChooser() );
    	
    }
    void makeMove(String command) {
        /*
         * function that gets the inut from the user as to what move they want to do
         * and does that move. Each case below corresonds with a move from the menu
         * and is documented in the move class
         */
        if(!alive) { // if character is dead cannot make amove
            return;
        }
        Move m = new Move();
        while ( m.type == null ) 
        { 
            m=decision.getMove(this, this.currPlace(), command);
            //switch case for evuluating move type 
            if ( m.type!= null )
            {
                switch(m.type) {
                 
                case GO :
                    currentRoom.removeCharacter(id, this); // msg to group: come back to this
                    currentRoom=currentRoom.followDirection(command);
                    if(currentRoom==null) {
                        exit();
                        break;
                    }
                    currentRoom.addCharacter(id, this);
                    break;
                     
                case LOOK:
                    currentRoom.printDirections();
                    if(currentRoom.hasMerchant()!= null) {
                    	System.out.println("There is a merchant in this room!");
                    }
                    if ( currentRoom.hasArtifact() ) {
                    	System.out.println("There are also some artifacts in this room!!");
                    	for ( Map.Entry<String, Artifact> entry: currentRoom.showVisibleArtifacts().entrySet() ) {
	                    	entry.getValue().inventoryPrint();
	                    }
                    }

                    takeTurn();
                    break;
                     
                case GET:
                    if(currentRoom.conatinsArtifact(command) ) {
                        addArtifact(currentRoom.returnArtifact(command));
                        currentRoom.removeArtifact(command);
                        artifacts.get(command).setCurrentCharacter(this);
                        artifacts.get(command).setCurrentCharacter(null);
                        System.out.println(name + " has picked up " + command);
                    }
                    else {
                        System.out.println("That artifact" + command +" is not in the room");
                        takeTurn();
                    }
                    break;
                     
                case DROP:
                    if(currentRoom.conatinsArtifact(command)) {
                        removeArtifact(currentRoom.returnArtifact(object), object);
                        System.out.println(name + " has dropped" + object);
                    }
                    else {
                        System.out.println("This artifact is not in" + name +"s inventory");
                        TakeTurn();
                    }
                    break;
                     
                case USE:
                    object= input.next();
                    currentRoom.useKey(artifacts.get(object));              
                    System.out.println("All doors unlocked by key have been unlocked");
                    break;
                     
                case INVENTORY:
                    System.out.println( name + " Inventory: ");
                    printArtifactInventory();
                    makeMove();
                    break;
                case MERCHANT:
                    boolean answer = currentRoom.notifyMerchant(this);
                    if ( answer == false ) {
                        System.out.println("There is no merchant currently present in this room");
                    }
                    break;
                case BATTLE:
                    Battle b = Battle.getBattle();
                    b.battlePractice();
                    break;
                     
                case NONE:
                    break;
                default:
                    System.out.println("Not a correct command..");
                    break;
                }   
            }
             
        }
         
         
    }
     
    public Weapon getLargestWeapon() {
        int size = weapons.size();
        int index = 0; 
        int largestValue = 0; 
        Weapon w = null;
        while (size > 0) { 
            if(weapons.get(index).getAttack() > largestValue) {
                largestValue = weapons.get(index).getAttack();
                w = weapons.get(index); 
            }
            size--; 
            index++;
        }
        return w; 
    }
    private class MoveChooser implements ActionListener{
      	 public void actionPerformed(ActionEvent e) {
      		 //fileChooser.close(1);
      		 String command = e.getActionCommand();
      		 makeMove(command);
      	 }
       }
}
 
class NPC extends Character{
     
    NPC(Scanner infile){
        super(infile);
        decision = new AI();
        health=100;
        baseAttack=30;
        money = new Money(30);
 
         
    }
    void makeMove() {
        if(!this.alive()) {
            return;
        }
        Move m = decision.getMove(this, this.currentRoom, "Random");
        System.out.println(super.name() + "'s turn: " );
        if ( currentRoom != null ) {
        	
        switch(m.type) {
        
        case GET:
            if(currentRoom.emptyRoom()) {
                Artifact a = currentRoom.returnArtifact();
                addArtifact( a);
                currentRoom.removeArtifact(a.name());
                artifacts.get(a.name()).setCurrentCharacter(this);
                artifacts.get(a.name()).setCurrentCharacter(null);
                System.out.println(this.name() + " has picked up " + a.name());
                break;
            }
            makeMove();
            break;  
        case DROP:
            if(emptyBag()) {
                Artifact A = returnArtifact();
                removeArtifact(A, A.name());
                System.out.println(this.name() + " has dropped " + A.name());
                break;
 
            }
             
        case USE:
            if(emptyBag()) {
                Artifact b = returnArtifact();
                currentRoom.useKey(b);
                break;
            }
                             
            System.out.println("No doors to unlock");
            makeMove();
            break;
        default:
        	if ( currentRoom!= null ) {
        		currentRoom.removeCharacter(id, this);

        	}
            Direction d = currentRoom.returnDirection();
            if(currentRoom == null) {
            	System.out.println(super.name() + "appeared in Entrance Hall");
            	Place p = new Place();
            	p = p.placesMap.get(12);
            	p.addCharacter(super.id(), this);
            }
            if ( d != null ) {
                currentRoom = d.follow();
                if ( currentRoom != null ) {
                	currentRoom.addCharacter(id, this);

                }
            }
            else {
                exit();
                break;
            }
        }
        }
         
    }
     
     
     
}
 
 
class Player extends Character{
     
    Player(Scanner infile){
        super(infile);
        decision = new UI();
        health=100;
        baseAttack=10;
        money = new Money(100);
 
    }
    Player(int ID, String Name, String Desc, Place room){
        super(ID,Name,Desc,room);
        decision = new UI();
        health=100;
        baseAttack=10;
        money = new Money(100);
 
    }
    Player(String name){ // primarily used for battle from menu
        super(name);
        decision = new UI();
        health = 100;
        baseAttack = 10;
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
              System.out.println(c.name() + " what would you like to do: [DEFEND / STRONG ATTACK / MEDIUM ATTACK / WEAK ATTACK / RUN]?");
              c.printStats();
              Scanner input = new Scanner(System.in);
              String w = input.nextLine();
              m=Battle.getBattle().getBattletype(w, m);
              if(m==null) {
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