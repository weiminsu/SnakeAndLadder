package au.edu.rmit.isys1117.group9.model;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class Board extends JPanel implements Runnable
{
   private static JFrame frame = new JFrame("Introduction to Programming Assignment 2");
   private double factor = 0.2;
   private int XMARGIN = 20;
   private int YMARGIN = 20;
   private int pieces[];
   private Dice dice;
   private String bLines[] = new String[8];
   private int bCount = 0;
   
   
   private Snake[] ss = new Snake[10];
   private Ladder[]ls = new Ladder[10];
   private Trap[] traps = new Trap[10];
   int snakesCount = 0;
   int laddersCount = 0;
   int trapsCount = 0;
   
   public void addMessage( String line)
   {
	   if (  bCount < 8)
	   {
		   if ( line.length() > 20)
		   {
		      String temp = line.substring(0,20);
		      bLines[bCount++] = temp;
		   }
		   else bLines[bCount++] = line;   
	   }
		  
	      
	   repaint();
   }   
	   
   public void clearMessages()
   {
	      bCount = 0;
	      repaint();
   }
   
   Dice getDice()
   {
      return dice;
   }

   public void add(Snake s)
   {
       if (snakesCount < 10)
       {
          ss[snakesCount] = s;
          snakesCount++;
       }
   }
   
   public void add(Ladder l)
   {
      if (laddersCount < 10)
      {
          ls[laddersCount] = l;
          laddersCount++;
      }
   }
   public void add(Trap t)
   {
      if (trapsCount < 10)
      {
          traps[trapsCount] = t;
          trapsCount++;
      }
   }  
   
   public Board()
   {
	   this(2);
   }

   public Board(int n)
   {
      if ( n > 4 || n < 2)
      {
         System.out.println("Minimum 2 players and Maximum 4 players");
         System.exit(0);
      }
    
	  Container contentPane = frame.getContentPane();
      contentPane.setLayout(new BorderLayout());
	
      frame.getContentPane().add(this,BorderLayout.CENTER);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setSize(640,520);
      frame.setVisible(true);
      new Thread(this).start();
      //  r.start();
         
	  pieces = new int[n];
      for (int i=0; i<n; i++)
          pieces[i] = 1;

      dice = new Dice(this);
   }

   public void run()
   {
      double inc = 0.05;
      while (true)
      {
        try {
          Thread.sleep(1000);
        }
        catch (Exception e) {}
      factor += inc;; 
      if (factor > 0.5 || factor < -0.5)
         inc = -inc;
      repaint();
     }
   }


   public void drawPieces(Graphics g)
   {
      if (pieces.length > 0)
      {
         g.setColor(Color.WHITE);   
         g.fillOval((int)getX(pieces[0])-10,getY(pieces[0])-10,20,20); 
         g.setColor(Color.BLACK);   
         g.drawString("1",(int)getX(pieces[0])-5,getY(pieces[0])+5); 
      }
      if (pieces.length > 1)
      {
         g.setColor(Color.RED);   
         g.fillOval((int)getX(pieces[1])+10,getY(pieces[1])-10,20,20); 
         g.setColor(Color.BLACK);   
         g.drawString("2",(int)getX(pieces[1])+15,getY(pieces[1])+5); 
      }
      if (pieces.length > 2)
      {
         g.setColor(Color.GRAY);   
         g.fillOval((int)getX(pieces[2])-10,getY(pieces[2])+10,20,20); 
         g.setColor(Color.BLACK);   
         g.drawString("3",(int)getX(pieces[2])-5,getY(pieces[2])+25); 
      }
      if (pieces.length > 3)
      {
         g.setColor(Color.CYAN);   
         g.fillOval((int)getX(pieces[3])+10,getY(pieces[3])+10,20,20); 
         g.setColor(Color.BLACK);   
         g.drawString("4",(int)getX(pieces[3])+15,getY(pieces[3])+25); 
      }
   }

   public void setPiece(int piece, int pos)
   {
      pieces[piece-1] = pos;    
      repaint();
   }


   public void drawLadder(Graphics g, int bottom, int top)
   {
      double x1 = getX(bottom);
      double y1 = getY(bottom);
      double x2 = getX(top);
      double y2 = getY(top);

     int steps = (int) Math.sqrt((y2-y1)*(y2-y1) + (x2-x1)*(x2-x1)) / 25 + 1;
 
      int xinc = 5;
      if ( x1 > x2)
         xinc = -xinc;
      int yinc = 5;
      if ( y1 > y2)
         yinc = -yinc;

      g.drawLine((int)(x1-xinc),(int)(y1+yinc),(int)(x2-xinc),(int)(y2+yinc)); 
      g.drawLine((int)(x1-xinc)-1,(int)(y1+yinc),(int)(x2-xinc)-1,(int)(y2+yinc)); 
      g.drawLine((int)(x1-xinc),(int)(y1+yinc)-1,(int)(x2-xinc),(int)(y2+yinc)-1); 

      g.drawLine((int)(x1+xinc),(int)(y1-yinc),(int)(x2+xinc),(int)(y2-yinc)); 
      g.drawLine((int)(x1+xinc)-1,(int)(y1-yinc),(int)(x2+xinc)-1,(int)(y2-yinc)); 
      g.drawLine((int)(x1+xinc),(int)(y1-yinc)-1,(int)(x2+xinc),(int)(y2-yinc)-1); 

      double xstep = (x2-x1)/(steps+1);
      double ystep = (y2-y1)/(steps+1);
      for (int i=0; i<steps; i++)
      {
         x1 += xstep;  y1 += ystep;
         g.drawLine((int)(x1+xinc),(int)(y1-yinc),(int)(x1-xinc),(int)(y1+yinc)); 
         g.drawLine((int)(x1+xinc)-1,(int)(y1-yinc),(int)(x1-xinc)-1,(int)(y1+yinc)); 
         g.drawLine((int)(x1+xinc),(int)(y1-yinc)-1,(int)(x1-xinc),(int)(y1+yinc)-1); 
      }   

   }

   private int getX(int pos)
   {
      pos--;
      if ( (pos/10) % 2 == 0 )
	  return XMARGIN + 10 + pos%10 * 40;
      else
	  return  XMARGIN  + 370 - pos%10 * 40;
   }
   private int getY(int pos)
   {
      pos--;
      return YMARGIN - 30 + 400 - pos/10 * 40;
   }
   
   public void drawSnake(Graphics g, int head, int tail)
   {
      int x1 = getX(head);
      int y1 = getY(head);
      int x2 = getX(tail);
      int y2 = getY(tail);

      int steps = (int) Math.sqrt((y2-y1)*(y2-y1) + (x2-x1)*(x2-x1)) / 150 * 18 + 24;


      double xstep = (double)(x2-x1)/(steps+1);
      double ystep = (double)(y2-y1)/(steps+1);

      double inc;
      double x = x1,y = y1;
      
      boolean odd = true;
      for (int i=0; i<steps+1; i++)
      {
         if ( (i%12) % 12 == 0)
             inc = 0;
         else if ( (i%12)% 11 == 0)
             inc = 10 * factor;
         else if ( (i%12)% 10 == 0)
             inc = 13 * factor;
         else if ( (i%12)% 9 == 0)
             inc = 15 * factor;
         else if ( (i%12)% 8 == 0)
             inc = 13 * factor;
         else if ( (i%12)% 7 == 0)
             inc = 10 * factor;
         else if ( (i%12)% 6 == 0)
             inc = 0 * factor;
         else if ( (i%12)% 5 == 0)
             inc = -10 * factor;
         else if ( (i%12)% 4 == 0)
             inc = -13 * factor;
         else if ( (i%12)% 3 == 0)
             inc = -15 * factor;
         else if ( (i%12)% 2 == 0)
             inc = -13 * factor;
         else 
             inc = -10 * factor;
         x += xstep;  y += ystep;
         if (odd) {
           g.setColor(Color.BLACK);
           odd = false;
         }
         else {
           g.setColor(Color.GREEN);
           odd = true;
         }
         if ( x2 > x1)
            g.fillOval((int)(x+inc),(int)(y-inc),20-10*i/steps,20-10*i/steps); 
         else
            g.fillOval((int)(x-inc),(int)(y-inc),20-10*i/steps,20-10*i/steps); 
      }   
   }


   public void paintComponent(Graphics g) {
      super.paintComponent(g);
      for (int i=0; i<10; i++)
      {
         for (int j=0; j<10; j++){
           if ((i+j)%2 == 0) 
              g.setColor(Color.YELLOW);
           else 
              g.setColor(Color.ORANGE);      
           
           for (int k=0; k<trapsCount; k++)            
              g.fillRect(XMARGIN + 40*i,YMARGIN+40*j, 40,40);

	     }

      }
      g.setColor(Color.BLACK);
 	  for (int k =0; k<trapsCount; k++) 
 	  {
 		 int num = traps[k].getLocation(); 
 		 if ( traps[k].getDuration() == 3 )
             g.setColor(Color.BLUE);   
 		 else if ( traps[k].getDuration() == 4 )
             g.setColor(Color.GRAY); 
 		 else
             g.setColor(Color.BLACK); 	 	
         g.fillRect(getX(num) -10 ,getY(num)-10,40,40);
   	  } 
      g.setColor(Color.BLACK); 
      for ( int i=0; i<100; i++)    	  
         g.drawString(""+(i+1), getX(i+1),getY(i+1)+20);
      for (int i=0; i<snakesCount; i++) 
      {
         drawSnake(g,ss[i].getHead(), ss[i].getTail());
      }
      for (int i=0; i<laddersCount; i++)
         drawLadder(g,ls[i].getBottom(), ls[i].getTop());

      g.setColor(Color.GRAY);
      g.fill3DRect(430,40,200,340,true);

      g.setColor(Color.WHITE);
      g.fill3DRect(440,50,180,320,true);
      g.setColor(Color.BLACK);
      Font font1 = new Font("TimesRoman",Font.BOLD,16);
      g.setFont(font1);
      g.drawString("Display",490,20);

      for (int i=0; i<bCount; i++)
  		g.drawString(bLines[i], 450, 70 + 30 * i);
               
      drawPieces(g);
      dice.draw(g);
   }
}