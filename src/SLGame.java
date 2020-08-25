import au.edu.rmit.isys1117.group9.model.*;

import javax.swing.*;
import java.util.*;

public class SLGame
{
	
   // These arrays and variables are required only for part II
   Snake snakes[] = new Snake[10];    	// array can store up to 10 au.edu.rmit.isys1117.group9.model.Snake objects
   Ladder ladders[] = new Ladder[10];  	// array can store up to 10 au.edu.rmit.isys1117.group9.model.Ladder objects
   Trap traps[] = new Trap[10];       	// array can store up to 10 au.edu.rmit.isys1117.group9.model.Trap objects
   String name[] = new String[2];  		// array for storing the names
   int snakesCount = 0;				 
   int laddersCount = 0;	
   int trapsCount = 0;
   
   
   // Creating a au.edu.rmit.isys1117.group9.model.Board, dice and a Scanner objects
   Board bd = new Board();
   Dice dice = bd.getDice();
   Scanner scan = new Scanner(System.in);
      
   // This method creates Snakes and Ladders at fixed positions for Part A
   // Start-up code is given just to get you started
   // For Part B, modify this code to allow users to specify snake, ladder and trap positions
   // You must enforce all design rules when placing snakes, ladders and traps
   // If the design rules are not violated users must re-enter values
   public void setup(Board bd)
   {  
	  int choice = 0;
	  bd.add(new Trap(25,3));
	  bd.add(new Trap(95,3));
	  trapsCount = 1;
	
	  bd.add(new Snake(92,34));
	  bd.add(new Snake(62,12));	  
	  bd.add(new Snake(41,3));  
	  bd.add(new Snake(99,10));  
	  
	  snakesCount = 3;

	  bd.add(new Ladder(7,49));
	  bd.add(new Ladder(55,90));
	  bd.add(new Ladder(38,86));
	  laddersCount = 3;
	  
   }	   
     
   // A method to print a message and to read an int value in the range specified
   int getInt(String message, int from, int to)
   {
	   String s;
	   int n = 0;
	   boolean invalid;
	   do {
		 invalid = false;
	     s = (String)JOptionPane.showInputDialog(
	      bd,  message,  "Customized Dialog",
	          JOptionPane.PLAIN_MESSAGE);	
	      try {
	         n = Integer.parseInt(s);
	         if (n < from || n > to )
	    	     plainMessage("Re-enter: Input not in range " + from + " to " + to);
	      }
	      catch (NumberFormatException nfe)
	      {
	    	  plainMessage("Re-enter: Invalid number");
	    	  invalid = true;
	      }
	   } while ( invalid || n < from || n > to);
	   return n;
   }

   // A method to print a message and to read a String
   String getString(String message)
   {
	   String s = (String)JOptionPane.showInputDialog(
	      bd,  message,  "Customized Dialog",
	          JOptionPane.PLAIN_MESSAGE);	
	   return s;
   }   

   // A method to print a message
   void plainMessage(String message)
   {
        JOptionPane.showMessageDialog(bd,
		    message, "A prompt message",
		    JOptionPane.PLAIN_MESSAGE);
   }
   
   
   // The main method implementing the game logic for Part A
   // For Part A you may use the hard coded values in the setup() method 
   // You will need to change the code for Part B
   // For user interaction use the methods getString, getInt and plainMessage
   // For display/erase on the board use bd.addMessage(), bd.clearMessages()

   public void control()
   {   
	  int numPlayers = 2;

      setup(bd);   // setup method currently hard-codes the values

      bd.clearMessages();  // clears the display board  
      
      String name1 = getString("Player 1 name : ");
      String name2 = getString("Player 2 name : ");  
      bd.clearMessages();
      bd.addMessage("Current Players are");
      bd.addMessage("Player 1 : " + name1);      
      bd.addMessage("Player 2 : " + name2);
      int p1Location = 1;
      int p2Location = 1;
      bd.setPiece(1,p1Location);
      bd.setPiece(2,p2Location);
      int val = getInt(name1 + ": Enter 0 to throw dice. Enter 1 - 6 for Testing.", 0, 6);
      if ( val == 0)
          val = dice.roll();
      else 
    	  dice.set(val);
      p1Location += val;
      plainMessage(name1 + ": moving to " + p1Location);
      bd.setPiece(1, p1Location);
      if ( p1Location == 7 )
      {
    	  p1Location = 49; // going up the first  ladder
          plainMessage(name1 + ": goin up a ladder to " + p1Location);
          bd.setPiece(1, p1Location);
      } 
    	  
      // here we are allowing use to set the dice value for testing purposes
      val = getInt(name2 + ": Enter 0 to throw dice. Enter 1 - 6 for Testing.",0,6);
      if ( val == 0)
          val = dice.roll();
      else
    	  dice.set(val);
      p2Location += val;
      plainMessage(name2 + ": moving to " + p2Location);
      bd.setPiece(2, p2Location);
      if ( p2Location == 7 )
      {
    	  p2Location = 49; // going up the first ladder
          plainMessage(name2 + ": going up a ladder to " + p2Location);
          bd.setPiece(2, p2Location);
      }

      
      plainMessage("The rest is up to you. You may have to introduce additional variables.");

      // Complete the game logic
      // throwing or setting the dice 
      // moving by dice value and showing the pieces at new position 
      // moving up the ladder, going down the snake or being trapped (lose 3 moves)  
      
      bd.addMessage("Continue until");
      bd.addMessage("a player gets to 100");
      bd.addMessage("Remember to have fun!");   
      bd.addMessage("Danger: Traps,Snakes");    
      bd.addMessage("au.edu.rmit.isys1117.group9.model.Trap: lose 3 moves");
   }
   
   // The very first method to be called
   // This method constructs a SLGame object and calls its control method 
   public static void main(String args[])
   {
       SLGame slg = new SLGame();
       
       slg.control();
   }


}