package src;


public class Money extends Currency {

	int amount; // amount of money a character has 
	public Money(){
		super("dollar", 100); // name of currency is dollar and has 100% value rate
	}
	public Money(Character c) { // gets a character. if player, gets 100 dollars. if npc, gets 20
		
	}
	public Money(int amount) {
		super();
		this.amount = amount;
		
	}
	public void addMoney(int additional) { // adds money to what a character has 
		amount = amount + additional;
	}
	public void spendMoney(int decremental) {
		amount = amount - decremental;
	}
	
	public int balance() {
		return amount;
	}
	// just in case we need something like this
	

}
