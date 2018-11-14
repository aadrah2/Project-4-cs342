//manuel torres
public interface DecisionMaker {
	public Move getMove(Character c, Place p, String command);
	public battleType getAttack(Character c);
}