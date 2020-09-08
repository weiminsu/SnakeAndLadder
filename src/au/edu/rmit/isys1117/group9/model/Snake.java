package au.edu.rmit.isys1117.group9.model;

public class Snake
{
   private int head;
   private int tail;
   public Snake(int h, int t)
   {
       head = h;
       tail = t;
   }
   public int getHead() { return head; }
   public int getTail() { return tail; } 
   public void setPosition(int pos) {
	   head = pos;
   }
}
