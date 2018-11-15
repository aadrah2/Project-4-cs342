public class Money extends Currency {
    //the class that extends currency, currently there's only one form of currency
    /*
     * each character will get money. each money object will have an amount which denotes how
     *much money a character has. in the Money constructor, the first parameter is the name of
     *the currency, and the second parameter is the percentage value quality rate of 
     *this currency, which is %100 for now. this class also has methods to be used by players
     *in the game where they can interact with people like merchants to buy and sell things
     *
     */
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
    public void spendMoney(int decremental) { // spends money from a character budget
        amount = amount - decremental;
    }
     
    public int balance() { // returns the amount of money a character has
        return amount;
    }
    // just in case we need something like this
     
 
}