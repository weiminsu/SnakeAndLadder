package model;

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
   public void setHeadPosition(int pos) {
	   head = pos;
   }
   
   public void setTailPosition(int pos) {
	   tail = pos;
   }
}
