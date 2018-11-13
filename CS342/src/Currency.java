package src;

/*This class is the currency for the game and there can be multiple currencies but for now
 * there's only one, and this class will start off with a child class called Money which will
 * be the physical money each character has
 * Below is my write up for Currency - Maurice Williams 
 * Note that for the first currency's name will be dollar and I am just using money to refer to dollar so
 * in money constructor, super's argument will be dollar
 * 
 * Currency will be separated into two classes. Parent class is currency and child class is money. 
 * Each place can have money in it(not just because there is a merchant). For now, each character 
 * has money(only one Money object is needed). Each player starts off with $100. If a player 
 * defeats another player, I suggest that all their money goes to the victor. Each NPC except 
 * merchants starts off by having $20, and when that NPC is defeated then the victor gets that 
 * $20. NPC’s have no need to use currency. Merchants will just start off with indefinite amount 
 * of money and it can be just coded as ‘indefinite’(haven’t thought about how to do this yet). 
 * When a player is defeated by an NPC, then that player’s money gets left in that place and can 
 * be picked up later. Also I haven’t thought about the details yet but based on his GDF file, the 
 * rubies or gems or whatever’s value field will be able to be cashed in for that dollar amount 
 * when sold to a merchant. Well, maybe it should be scaled down in the hundreds area.
 */
public class Currency {


	
	private int value; // this is a percentage value of this currency out of 100, but because there's only
	// one currency, it will be 100% value. Maybe for some sort of future versions there can be
	// another currency which would have lower value(70%) for example. Then you can figure out
	// how much one currency is worth based off its value
	private String name; // name of the currency 
	public Currency()
	{
		value = 100;
		name = null;
	}
	public Currency(String name, int value) {
		this.name = name;
		this.value = value;
	}

	
	public int currencyValue() {
		return value;
	}
}
