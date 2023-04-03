package enums;


/**
 * 
 * @author bollabollo
 * ATTACK - objects that directly boost physical or magical damage
 * DEFENSE - objects that directly boost defense or dexterity
 * TOOL - objects that allow you to change and modify game environment
 * CONSUMMABLE - objects that is used by the player to boost something
 * INTERACT - objects that can be used to interact with game environment - keys
 * ACCESSORY - objects that add additional stats but does not lie in defense or attack
 * NONPICKUP - objects that cannot be pick up
 */
public enum ObjectType {
	ATTACK,
	DEFENSE,
	TOOL,
	CONSUMMABLE,
	POWERUP,
	INTERACT,
	ACCESSORY,
	NONPICKUP;
}
