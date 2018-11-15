import java.util.Map;
import java.util.Random;
import java.util.Scanner;
enum battleType{
	  StrongAttack("Strong","strong","Strong Attack"),MediumAttack("Medium","medium","Medium Attack"),
	  WeakAttack("Weak","weak","Weak Attack"), Defend("Defend", "defend", "DEFEND"), Run("Run","Runaway","run"),
	  NONE("none", "NONE", "None");

	private String arg1;
	private String arg2;
	private String arg3;

	private battleType(String a, String b, String c) {
		this.arg1= a;
		this.arg2= b;
		this.arg3= c;
	}
	public boolean match(String command) {
		if(arg1.matches(command) ||arg2.matches(command)||arg3.matches(command)  ) {
			return true;
		}
		return false;
	}
}
public class Battle {
	
	
	
	
	Random rand=new Random();
	private Character fighter1;
	private Character fighter2;
	private Artifact weapon1;
	private Artifact weapon2;
	private static Battle battle;
	
	
	
	Battle(){
		battle=null;
		
	}
	
	static Battle getBattle() {
		if(battle==null) {
			battle=new Battle();
		}
		return battle;
	}
	
	public battleType getBattletype(String a, battleType m) {
		for(battleType B: battleType.values()) {
			if(B.match(a)) {
				m=B;
				return m;
			}
		}
		return null;
	}
	public battleType getBattletypeValue(String a, battleType m) {
		for(battleType B: battleType.values()) {
			if(B.match(a)) {
				return B;
			}
		}
		battleType bb = battleType.NONE;
		return bb;
	}
	
	void battlePractice() { // this comes from the menu, instead of two active characters playing, you can pick the name for your characters
		// and use any weapons you want
		Scanner userInput = new Scanner(System.in);
		System.out.println("Enter the name of the first fighter");
		String line = userInput.nextLine();
		Player p1 = new Player(line);
		System.out.println("Enter the name of the second fighter");
		line = userInput.nextLine();
		Player p2 = new Player(line);
		System.out.println(p1.name() + ", select one of the following weapons to use: ");
		Weapon wp = new Weapon();
		boolean fighter1_chose_weapon = false;
		boolean fighter2_chose_weapon = false;
		Weapon fighter1sWeapon = null;
		Weapon fighter2sWeapon = null;
		while ( fighter1_chose_weapon == false ) {
			for ( Map.Entry<String, Weapon > entry: wp.weapons.entrySet() ) {
				entry.getValue().inventoryPrint();
			}
			line = userInput.nextLine();
			fighter1sWeapon = wp.isStandardWeapon(line);
			if ( fighter1sWeapon != null ) {
				fighter1_chose_weapon = true;
				System.out.println(p1.name() + " has selected to use : " + fighter1sWeapon.name());
			}
			else {
				System.out.println("Entered name is not valid. Please choose a weapon to fight with.");
			}
		}

		System.out.println(p2.name() + ", select one of the following weapons to use: ");
		
		while ( fighter2_chose_weapon == false ) {
			for ( Map.Entry<String, Weapon > entry: wp.weapons.entrySet() ) {
				entry.getValue().inventoryPrint();
			}
			line = userInput.nextLine();
			fighter2sWeapon = wp.isStandardWeapon(line);
			if ( fighter2sWeapon != null ) {
				fighter2_chose_weapon= true;
				System.out.println(p2.name() + " has selected to use : " + fighter2sWeapon.name());

			}
			else {
				System.out.println("Entered name is not valid. Please choose a weapon to fight with.");
			}
		}
		
		System.out.println("THE BATTLE WIL NOW BEGIN!!");
		if ( fighter1sWeapon != null && fighter2sWeapon != null ) {
			standardBattle(p1, p2, fighter1sWeapon, fighter2sWeapon );
		}

	}
	
	void standardBattle(Character c1, Character c2, Weapon w1, Weapon w2) {
		fighter1=c1;
		fighter2=c2;
		battleType m1 = battleType.Run;
		battleType m2 = battleType.Run;
		
		if(c1 instanceof Player ) {

		weapon1 = w1;
		}
		else {
			weapon2 = w2;
		}
		if(c2 instanceof Player ) {

		weapon2 = w2;
		}

		
		while(fighter1.alive() && fighter2.alive()) { 
			m1=fighter1.decision.getAttack(fighter1);
			m2=fighter2.decision.getAttack(fighter2);
			int move1 = evalMove(fighter1,m1, weapon1);
		    int move2 = evalMove(fighter2,m2, weapon2);
		    executeMove(move1,move2);
			}
		if(fighter1.alive()) {
			System.out.println("Character " + fighter1.name()+ " has won the battle!");
			fighter2.death();
		}
		else{
			System.out.println("Character " + fighter2.name()+ " has won the battle!");
			fighter1.death();
		}
	}
	void battle(Character c1, Character c2) {
		fighter1=c1;
		fighter2=c2;
		battleType m1 = battleType.Run;
		battleType m2 = battleType.Run;
		System.out.println("\nCharacter " + c1.name() + " and Character " + c2.name()+ " have begun battle");
		
		if(c1 instanceof Player ) {

		System.out.println(fighter1.name() + " what weapon would you like to use.");
		fighter1.printWeaponInventory();
		Scanner input = new Scanner(System.in);
		String w = input.next();
		weapon1=fighter1.getWeapon(w);
		}
		else {
			weapon2=fighter2.getLargestWeapon();
		}
		if(c2 instanceof Player ) {

		System.out.println(fighter2.name() + " what weapon would you like to use.");
		fighter2.printWeaponInventory();
		Scanner input = new Scanner(System.in);
		String w = input.nextLine();
		weapon2=fighter2.getWeapon(w);
		}
		else {
			weapon2=fighter2.getLargestWeapon();
		}
		
		
		while(fighter1.alive() && fighter2.alive()) { 
			m1=fighter1.decision.getAttack(fighter1);
			m2=fighter2.decision.getAttack(fighter2);
			int move1 = evalMove(fighter1,m1, weapon1);
		    int move2 = evalMove(fighter2,m2, weapon2);
		    executeMove(move1,move2);
			}
		if(fighter1.alive()) {
			System.out.println("Character " + fighter1.name()+ " has won the battle!");
			fighter2.death();
		}
		else{
			System.out.println("Character " + fighter2.name()+ " has won the battle!");
			fighter1.death();
		}
	}
		
		
		
	int evalMove(Character fighter, battleType m, Artifact w) {
		int move=0;
	    int randomNumber = 0;
	    switch(m) {
		case Defend:
			randomNumber = rand.nextInt(25);
			fighter.addStamina(25);
			move = -w.getDefense() - randomNumber; // come back to this
			return move;
		case StrongAttack:
			randomNumber = rand.nextInt(40);
			fighter.removeStamina(45);
			move = fighter.returnBaseAttack() + randomNumber + w.getAttack() ;
			return move;
		case MediumAttack:
			randomNumber = rand.nextInt(25);
			fighter1.removeStamina(35);
			move = fighter.returnBaseAttack() + randomNumber + w.getAttack();
			return move;
		case WeakAttack:
			randomNumber = rand.nextInt(10);
			fighter.removeStamina(25);
			move = fighter.returnBaseAttack() + randomNumber + w.getAttack();
			return move;
		case Run:
		
		default:
			fighter.addStamina(30);
			move=-10;
			return move;
		}
	    
	    
	}
	
	void executeMove(int m1, int m2) {
		if(m1>0 && m2>0) {
			fighter1.removeHealth(m2);
			System.out.println(fighter1.name()+ " has lost " + m2 + " health" );
			fighter2.removeHealth(m1);
			System.out.println(fighter2.name()+ " has lost " + m1 + " health" );
		}
		else if(m1<0 && m2>0) {
			if(m1+m2<=0) {
				System.out.println("Attack did no damage");
				return;
			}
			fighter1.removeHealth(m1+m2);
			System.out.println(fighter1.name() + " Choose to defend and lost " + (m1+m2) + " amount of health");
		}
		else if(m1>0 && m2<0) {
			if(m1+m2<=0) {
				System.out.println("Attack did no damage");
				return;
			}
			fighter2.removeHealth(m1+m2);
			System.out.println(fighter2.name() + " Choose to defend and lost " + (m1+m2) + " amount of health");
		}
		else {
			System.out.println("Both players have chosen to defend");
		}
	}
	
}
