package model;

public class Snake extends Entity
{

   public Snake(int h, int t)
   {
       top = h;
       bottom = t;
   }


   public void setHeadPosition(int pos) {
	   top = pos;
   }

   public void setTailPosition(int pos) {
	   bottom = pos;
   }
}
