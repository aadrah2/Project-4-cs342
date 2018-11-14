//manuel torres
import java.lang.Enum;

public class Move {
	public enum moveType{
		  GO("go","Go","GO"),USE("USE","Use","use"), GET("get","Get","GET"),DROP("DROP" , "Drop", "drop"), EXIT("Exit", "EXIT","exit"), 
		  QUIT("QUIT","Quit","quit"), INVENTORY("inventory","INVENTORY","Inventory"), LOOK("look","Look","LOOK"),
		  MERCHANT("MERCHANT", "Merchant", "merchant"),NONE(" ", "NONE","None");

		private String arg1;
		private String arg2;
		private String arg3;

		private moveType(String a, String b, String c) {
			this.arg1= a;
			this.arg2= b;
			this.arg3= c;
		}
		private boolean match(String command) {
			if(arg1.matches(command) ||arg2.matches(command)||arg3.matches(command)  ) {
				return true;
			}
			return false;
		}
	}
	public moveType type;
	
	public Move getMoveType(String command) {
		for(moveType M: moveType.values()) {
			if(M.match(command)) {
				type=M;
				return this;
			}
			
		}
		return null;
	}
	public moveType returnRandomMove(int num) {
		moveType[] moves = moveType.values();
		return moves[num];
	}
		
}