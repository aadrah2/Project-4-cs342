import java.lang.Enum;

public class Move {
	public enum moveType{
		  GO("go","Go","GO", "g"),USE("USE","Use","use", "u"), GET("get","Get","GET", "g"),DROP("DROP" , "Drop", "drop", "d"), EXIT("Exit", "EXIT","exit", "e"), 
		  QUIT("QUIT","Quit","quit", "q"), INVENTORY("inventory","INVENTORY","Inventory", "i"), LOOK("look","Look","LOOK", "l"),
		  MERCHANT("MERCHANT", "Merchant", "merchant", "m"), BATTLE("battle", "BATTLE", "Battle", "b"), NONE(" ", "NONE","None", " ");

		private String arg1;
		private String arg2;
		private String arg3;
		private String arg4;

		private moveType(String a, String b, String c, String d) {
			this.arg1= a;
			this.arg2= b;
			this.arg3= c;
			this.arg4 = d;
		}
		private boolean match(String command) {
			if(arg1.equalsIgnoreCase(command) ||arg2.equalsIgnoreCase(command)||arg3.equalsIgnoreCase(command)
					|| arg4.equalsIgnoreCase(command)) {
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

