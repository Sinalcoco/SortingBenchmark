
public enum ActionColor {
	RED(0xFFFF0000), GREEN(0xFF00FF00), BLUE(0xFF0000FF), YELLOW(0xFF00FFFF);
	
	private int hexValue;
	ActionColor(int theHexValue)
	{
		hexValue = theHexValue;
	}
	
	public int hexValue()
	{
		return hexValue;
	}
}
