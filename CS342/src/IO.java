import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.border.EmptyBorder;


public class IO {
  UserInterface GUIs[];
  GUI_1 GUI1=new GUI_1();
  GUI_2 GUI2=new GUI_2();
  public GUI_4 GUI4=new GUI_4(); 
  
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

class GUI_1 extends JFrame implements UserInterface{
	JPanel p;
	
	public void display() {    
          
	}
	public void display(Character c ) {
		
		String data[][]={ {"101","Amit","670000"},    
                {"102","Jai","780000"},    
                {"101","Sachin","700000"}};    
		String column[]={"ID","NAME","SALARY"};         
		JTable jt=new JTable(data,column);    
		jt.setBounds(30,40,200,300);          
		JScrollPane sp=new JScrollPane(jt);    
		this.add(sp);          
		this.setSize(300,400);    
		this.setVisible(true); 
		}
		public void closeWindow() {
        	this.setVisible(false);
        	this.dispose();
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
		buttons[0] = b1;
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
		f.setVisible(false);
		
    	
    }
}



class GUI_3 extends JFrame implements UserInterface{
	public JButton buttons[];
	public void display() {
		
	}
	public void closeWindow() {
		
	}
	public void display(Place currentRoom) {
		buttons=new JButton[currentRoom.directions.size()];
		//creating new panel and layout
		Panel p1; 
		
		setLayout(new GridLayout(1, 2));
		
		add(new Label("Directions:"));
		
		p1 = new Panel(new GridLayout(6, 3, 5, 5));
		int i=0;
		for(Direction d : currentRoom.directions.values()) {
			JButton b = new JButton(d.returnDir() +" : "+ d.getTo());
			p1.add(b);
			buttons[i]=b;
			i++;
		}
		add(p1);

	    setSize(450, 300);
	    setVisible(true);
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setTitle("Directions");
		
	}
	public void addItemListeners(ActionListener a) {
		for(int i=0; i<buttons.length; i++) {
			buttons[i].addActionListener(a);
		}
	}
}





class GUI_4 implements UserInterface{
	JFrame f;
	JPanel p;
	public JButton buttons[]=new JButton[8];
	JButton b[];
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
		
		buttons[0]=b1;
		buttons[1]=b2;
		buttons[2]=b3;
		buttons[3]=b4;
		buttons[4]=b5;
		buttons[5]=b6;
		buttons[6]=b7;
		buttons[7]=b8;
		
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
	public void displayItems(String[] items) {
		b=new JButton[items.length];
		p=new JPanel();
		f=new JFrame();
		for(int i = 0; i<items.length; i++) {
			b[0].setText(items[0]);
			p.add(b[0]);
		}
		f.add(p);
		
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
	public void addItemListeners(ActionListener a) {
		for(int i=0; i<buttons.length; i++) {
			b[i].addActionListener(a);
		}
	}
}
	