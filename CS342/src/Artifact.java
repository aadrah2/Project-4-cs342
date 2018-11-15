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
        // copy constructor that only copies certain fields.
        // e.g. we don't want to copy the numCopies field which signifies the number of copies
        // of a certain artifact a character can have 
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
     
    // the following functions that have copy appended to them are in reference to the amount of
    // a uniquely named artifact number of copies a character has. having 1 for numCopies implies
    // you have an extra copy of that item, but having 0 implies you don't have the artifac or
    // you do but only one, in which case we use hasArtifact to take care of that
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
     
    public static Set<Weapon> weapons= new HashSet<Weapon>();
    public static Set<String> weaponsNames= new HashSet<String>(); 
     
 
    Weapon(){
        Weapon fist = new Weapon("fists",30,0, "Your bare hands");
        weapons.add(fist); 
    }
     
    public Weapon(Weapon w) {
        super(w); // copy constructor
    }
    Weapon(Scanner infile){
        super(infile);
        boolean is_a_new_weapon = false;
        is_a_new_weapon = weaponsNames.add(super.name());
        if ( is_a_new_weapon ) {
            Weapon wp = new Weapon(this); // add copy of the weapon in the set
            weapons.add(wp); // add copy to the global weapons list
        }
    }
     
    Weapon(String Name, int Attack, int Defense){
        super(Name,Attack,Defense);
    }
     
    Weapon(String Name, int Attack, int Defense, String description){
        super(Name,Attack,Defense, description);
    }
     
    public Weapon isStandardWeapon(String source) { // returns the artifact for which contains yielded true
        for (Weapon w: weapons ) {
            if ( source.equalsIgnoreCase(w.name() ) ){
                return w;
            }
        }
         
        return null;
    }
     
    public void weaponInformation() { // same as display but doesn't print out pattern
        System.out.println("Weapon Name: " + super.name() );
        System.out.println("Desc:" + super.description() );
        System.out.println("Mobility: " + super.mobility() );       
    }
 
    public String name() {
        return super.name();
    }
}//end of weapon