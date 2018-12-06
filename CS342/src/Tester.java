/*Alexander Adrahtas
 * 651242494
 * aadrah2
 * aadrahta
 */
import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.*; 
import java.awt.*;
import java.awt.event.*;


public class Tester {
	static FileChooser f;
	static IO fileChooser;
    public static void main(String[] args) {
    	fileChooser = new IO();
    	
    	System.out.println("Name: Alexander Adrahatas NetId: aadrah");
    	System.out.println("Name: Manuel Torres NetID: mtorre66");
    	System.out.println("Name: Maurice Willams NetID: mwilli96");
    	f = new FileChooser();
        fileChooser.display(3);
        fileChooser.display(1);
        fileChooser.GUI2.addListeners(f);
        
        
    }
    static void startGame(String filename) {
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
 private static class FileChooser implements ActionListener{
	 public void actionPerformed(ActionEvent e) {
		 fileChooser.close(1);
		 String file = e.getActionCommand();
		 startGame(file);
	 }
 }
}