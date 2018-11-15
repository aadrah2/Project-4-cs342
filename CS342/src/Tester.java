/*Alexander Adrahtas
 * 651242494
 * aadrah2
 * aadrahta
 */
import java.io.*;
import java.util.*;
 
 
public class Tester {
     
    public static void main(String[] args) {
    	System.out.println("Name: Alexander Adrahatas NetId: aadrah");
    	System.out.println("Name: Manuel Torres NetID: mtorre66");
    	System.out.println("Name: Maurice Willams NetID: mwilli96");
        System.out.println("What would you like to use?");
        Scanner input = new Scanner(System.in);
        String filename = input.nextLine();
        if(args.length>0) {
            filename=args[0];
        }
         
        Scanner infile=null;
        try {
            infile=new Scanner(new File(filename));
        }
        catch(FileNotFoundException e) {
            System.err.println("File not found" + filename);
            System.exit(-3);
        }
        Game g = new Game(infile);
        g.play();
    }
 
}