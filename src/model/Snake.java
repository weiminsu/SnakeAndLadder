package model;

public class Snake extends Entity
{

   public Snake(int top, int bottom)
   {
       this.top = top;
       this.bottom = bottom;
   }

   public void setHeadPosition(int pos) {
	   top = pos;
   }

   public void setTailPosition(int pos) {
	   bottom = pos;
   }
}
