package quotes;

public class StatusAttribute {
	
	static String [] attributeDescription = {"Max damage \ntaken before \nplayer 'dies'.\n\nEach level \nincreases max \nhealth \nby 1 heart.",
											"Amount of health \nregenerated \nper 10 seconds.\n\nEach level \nincreases \nregeneration \nby half heart.",
											"Max magic energy \navailable \nbefore player \ncan't cast \nanymore spells.\n\nEach level \nincreases \ntotal mana by 1 \ncrystal.",
											"Amount of mana\nregenerated \nper 8 seconds.\n\nEach level \nincreases \nregeneration \nby half crystal.",
											"Total \ndamage dealt \nto an enemy. \n\nIncludes \nnatural and\nweapon damage. \n\nEach level \nincreases \nnatural \nstrength by 1.",
											"Total damage \nnegated from \nenemy attacks. \n\nIncludes \nnatural and \nequipment defense. \n\nEach level \nincreases negation \nby 1.",
											"Chances of \ndodging an \nincoming \nenemy attack.\n\nEach level \nincreases \nyour chances \nby 10%.",
											"Amount of energy \navailable \nbefore player \ncan't attack or use \ntools anymore. \n\nEach level \nincreases \nmax mana and\nregeneration",
											"The rate \nat which \nthe player \nmoves. \n\nEach level \nincreases \nmovement speed \nby 1 unit.",
											"Amount of \nknockback \nplayer inflicts \nupon enemies. \n\nEach level \nincreases \nknockback rate \nby 1.",
											"Chances of \ndealing a \ncritical attack \nto an enemy.\n\nEach level \nincreases \nyour chances \nby 10%.",
											"Reset all your \nupgrades and \nretrieve all \navailable upgrade \npoints."};
											
	
	public static String getAttributeDescription (int i) { return attributeDescription [i];}
}
