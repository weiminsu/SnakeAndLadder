package au.edu.rmit.isys1117.group9.model;

public class Trap {
	private int location;
	private int duration;
	public Trap(int loc, int dur)
	{
		location = loc;
		duration = dur;
	}
	public int getLocation()
	{
		return location;
	}
	public int getDuration()
	{
		return duration;
	}
}
