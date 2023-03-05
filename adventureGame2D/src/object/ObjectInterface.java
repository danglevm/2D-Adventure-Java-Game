package object;

public interface ObjectInterface {
	
	public abstract void setDefaultAttributes();
	
	/*
	 * Default value is 0 so non-combat objects help deal or sustain no damage
	 */
	public default int getAttackValue() {return 0;};
	
	public default int getDefenseValue() {return 0;};
}
