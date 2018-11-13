import java.util.Random;
import java.util.Scanner;
enum battleType{
	  StrongAttack("Strong","strong","Strong Attack"),MediumAttack("Medium","medium","Medium Attack"),
	  WeakAttack("Weak","weak","Weak Attack"), Defend("Defend", "defend", "DEFEND"), Run("Run","Runaway","run");

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
	
	public boolean getBattletype(String a, battleType m) {
		for(battleType B: battleType.values()) {
			if(B.match(a)) {
				m=B;
				return true;
			}
		}
		return false;
	}
	
	
	void battle(Character c1, Character c2, boolean pvp) {
		fighter1=c1;
		fighter2=c2;
		battleType m1 = battleType.Run;
		battleType m2 = battleType.Run;
		
		System.out.println(fighter1.name() + " what weapon would you like to use.");
		fighter1.printInventory();
		Scanner input = new Scanner(System.in);
		String w = input.next();
		weapon1=fighter1.getArtifact(w);
		
		if(pvp) {
		System.out.println(fighter2.name() + " what weapon would you like to use.");
		fighter2.printInventory();
		input = new Scanner(System.in);
		w = input.nextLine();
		weapon1=fighter2.getArtifact(w);
		}
		else {
			
		}
		
		
		while(fighter1.alive() && fighter2.alive()) {
			m1=fighter1.decision.getAttack(fighter1);
			m1=fighter2.decision.getAttack(fighter2);
			int move1 = evalMove(fighter1,m1, weapon1);
		    int move2 = evalMove(fighter1,m2, weapon2);
		    executeMove(move1,move2);
			}
	}
		
		
		
	int evalMove(Character fighter, battleType m, Artifact w) {
		int move=0;
	    int randomNumber = 0;
	    switch(m) {
		case Defend:
			randomNumber = rand.nextInt(25);
			fighter.addStamina(25);
			move = -10 - randomNumber;
			return move;
		case StrongAttack:
			randomNumber = rand.nextInt(40);
			fighter.removeStamina(45);
			move = fighter.returnBaseAttack() + randomNumber + w.getAttack();
			return move;
		case MediumAttack:
			randomNumber = rand.nextInt(25);
			fighter1.removeStamina(35);
			move = fighter.returnBaseAttack() + randomNumber+ w.getAttack();;
			return move;
		case WeakAttack:
			randomNumber = rand.nextInt(10);
			fighter.removeStamina(25);
			move = fighter.returnBaseAttack() + randomNumber + w.getAttack();;
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
			System.out.println(fighter1.name()+ " has lost" + m2 + " health" );
			fighter2.removeHealth(m1);
			System.out.println(fighter2.name()+ " has lost" + m1 + " health" );
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
