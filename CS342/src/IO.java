import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.border.EmptyBorder;


public class IO {
  private UserInterface GUIs[];
  GUI_1 GUI1=new GUI_1();
  GUI_2 GUI2=new GUI_2();
  GUI_4 GUI4=new GUI_4(); 
  
  IO(){
	  GUIs=new UserInterface[4];
	  GUIs[0]=GUI1;
	  GUIs[1]=GUI2;
	  GUIs[3]=GUI4;
  }
  void display(int i) {
	  GUIs[i].display();
  }
  void close(int i) {
	  GUIs[i].closeWindow();
  }
  void display(Character c) {
	  GUI1.display(c);
  }
  GUI_1 GUI() {
	  return GUI1;
  }
  
}

class GUI_1 implements UserInterface{
	JFrame f; 
	JPanel p;
	
	public void display() {    
          
	}
	public void display(Character c ) {
		f=new JFrame("What file would you like to choose"); 
		p=new JPanel();
		JButton b1 = new JButton("MysticCity 4.0.txt");
	
		JButton b2 = new JButton("MysticCity 5.1.txt");
	
		p.add(b1);
		p.add(b2);
		f.add(p);
		f.setSize(300,300);    
	    f.setVisible(true);  
		}
		public void closeWindow() {
        	f.setVisible(false);
        	f.dispose();
        }
       
    }

class GUI_2 implements UserInterface{
	JFrame f;
	JPanel p;
	public JButton buttons[]=new JButton[2];
	
	public void display() {
		f=new JFrame("What file would you like to choose"); 
		
		p=new JPanel();
		JButton b1 = new JButton("MysticCity 4.0.txt");
		buttons[0]=b1;
		JButton b2 = new JButton("MysticCity 5.1.txt");
		buttons[1]=b2;
		p.add(b1);
		p.add(b2);
		f.add(p);
		f.setSize(300,300);    
	    f.setVisible(true);  
		}
	
	public void addListeners(ActionListener a) {
		for(int i=0; i<buttons.length; i++) {
			buttons[i].addActionListener(a);
		}
	}
	public void closeWindow() {
		
    	
    }
}



class GUI_3 extends JFrame implements UserInterface{
	  private ArrayList<JCheckBox> boxes = new ArrayList<JCheckBox>();
	  private ArrayList<JLabel> labels = new ArrayList<JLabel>();
	 
	  JSplitPane splitPane;
	  EmptyBorder border1 = new EmptyBorder(5, 5, 5, 5); // invisible border that gives spacing around text fields
	  private JPanel leftPanel = new JPanel();
	  private JPanel rightPanel = new JPanel();
	  JLabel labelOne = new JLabel();
	  JLabel labelTwo = new JLabel();
	  JButton submit = new JButton("Submit");
	
	  public GUI_3() {
		    leftPanel = new JPanel(new GridBagLayout());
		 
		 
		    rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS) ); // output goes down the y axis
		 
		    addLabel("Yadayada"); // example of adding a label and the label adds some text. the function 
		    //addLabel also puts out border invisible space around each text field
		     
		    addLabel("Buda buda");
		 
		    splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, new JScrollPane(rightPanel));
		 
		    add(splitPane);
		 
		    addBoxes();
		    
		    leftPanel.add(submit); // add the submit button 
		     
		  }
		   
		  void addLabel(String s) { // adds text to the right panel in label, adds string s
		      JLabel temp = new JLabel();
		      temp.setText(s);
		      temp.setBorder(border1);
		      labels.add(temp);
		      rightPanel.add(temp);
		  }
		 
		  void addBoxes() { // function that adds the check boxes 
		    int i = 0;
		 
		    GridBagConstraints gbc = new GridBagConstraints();
		    gbc.gridwidth = GridBagConstraints.REMAINDER;
		    for (i = 0; i < 10; i++) {
		      JCheckBox tempBox = new JCheckBox("word " + i);
		      boxes.add(tempBox);
		      leftPanel.add(tempBox, gbc);
		    }
		  }
		   
		  public void display() {
			  
		  }
		  public void closeWindow() {
			  
		  }
		 
		  public static void main(String[] args) {
		    // TODO Auto-generated method stub
		 
		    GUI_3 cb = new GUI_3();
		    cb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		    cb.pack();
		    cb.setLocationRelativeTo(null);
		    //cb.addBoxListeners();
		 
		    cb.setVisible(true);
		  }
	  
	  
}





class GUI_4 implements UserInterface{
	JFrame f;
	JPanel p;
	public JButton buttons[]=new JButton[2];
	public void display() {
		f=new JFrame("What Move would you like to make");
		p=new JPanel();
		JButton b1=new JButton("GO");
		JButton b2=new JButton("LOOK");
		JButton b3=new JButton("GET");
		JButton b4=new JButton("DROP");
		JButton b5=new JButton("USE");
		JButton b6=new JButton("INVENTORY");
		JButton b7=new JButton("MERCHANT");
		JButton b8=new JButton("BATTLE");
		
		p.add(b1);
		p.add(b2);
		p.add(b3);
		p.add(b4);
		p.add(b5);
		p.add(b6);
		p.add(b7);
		p.add(b8);
		f.add(p);
		f.setSize(600,600);    
	    f.setVisible(true);
		
	}
	public void closeWindow() {
		f.setVisible(false);
		f.disable();
	}
	public void addListeners(ActionListener a) {
		for(int i=0; i<buttons.length; i++) {
			buttons[i].addActionListener(a);
		}
	}
}
	