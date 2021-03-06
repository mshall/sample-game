package com.shall.characters;

import java.util.Scanner;

public class Player {
	/* All stats are based upon the basic attributes */

	// Player information
	String name = "";
	String gender = "";
	String character = "";
	int level = 1;
	int xPosition = 38;
	int yPosition = 19;
	final static int MAX_LEVEL = 10;

	// Basic attributes
	int strength = 10;
	int agility = 10;
	int magicka = 10;

	// Stats and abilities
	int healthPoints = strength * 5;
	int manaPoints = magicka * 5;
	int attackPoints = strength / 2;
	int defense = agility / 2;
	int exp = 0;
	String[] abilities = new String[4];
	boolean AragonUsedLastSkill = false;

	public Player(String newName, String newGender, char newCharacter) {
		this.name = newName;
		this.gender = newGender;

		switch (newCharacter) {
		case 'w': 
			this.character = "Aragon";
			abilities[0] = "Sword Chopping";
			abilities[1] = "Sheild Blocking";
			abilities[2] = "Melee Sweeping";
			abilities[3] = "Armor Rush";		
			break;
			
		case 't': 
			this.character = "Legolas";
			abilities[0] = "Arrows shower";
			abilities[1] = "slinger throwing";
			abilities[2] = "swift  stabbing";
			abilities[3] = "ASW purge";
			break;
			
		case 'm': 
			this.character = "Gandalf";
			abilities[0] = "Lighting hit";
			abilities[1] = "Tsunami";
			abilities[2] = "Volcano rupt";
			abilities[3] = "Hound houl";
		
			break;
		}

	}

	public void move(char movement) {
		switch (movement) {
		case 'w':
			this.setxPosition(this.getxPosition() - 1);
			break;
		case 'a':
			this.setyPosition(this.getyPosition() - 1);
			break;
		case 'd':
			this.setyPosition(this.getyPosition() + 1);
			break;
		case 's':
			this.setxPosition(this.getxPosition() + 1);
			break;
		default:
			System.out.println("Please enter a valid movement key (w,s,a,d)");
		}
	}

	public boolean physicalAtatck(Player player, Enemy enemy, boolean poison) {
		if (poison) {
			System.out.println("You attack with your poisoned blade! You made: "
					+ player.calculateDamageDoneByPlayer(player, enemy, 1, true) + " damage!");
			player.consumeManaAndRemoveEnemyHealth(player, enemy,
					player.calculateDamageDoneByPlayer(player, enemy, 1, true), 0);
			poison = false;
		} else {
			System.out.println(
					"You attack! You made: " + player.calculateDamageDoneByPlayer(player, enemy, 0, true) + " damage!");
			player.consumeManaAndRemoveEnemyHealth(player, enemy,
					player.calculateDamageDoneByPlayer(player, enemy, 0, true), 0);
		}
		return poison;
	}

	public char showAndSelectAbilityDuringBattle(Player p, Scanner userInput) {
		boolean emptySpellChoice = false;
		char choice = ' ';

		System.out.println("Your abilities are: ");
		String[] abilities = p.getAbilities();

		for (int i = 0; i < abilities.length; i++)
			System.out.println(i + ") " + abilities[i]);

		System.out.println("\nChoose according to number");
		userInput.reset();

		while (!emptySpellChoice) {
			choice = userInput.next().charAt(0);
			if (choice != '0' && choice != '1' && choice != '2' && choice != '3')
				System.out.println("Please choose the appropriate number.");
			else {
				if (abilities[Character.getNumericValue(choice)].equals("---Empty---"))
					System.out.println("Please choose a spell slot that it is not empty.");
				else
					emptySpellChoice = true;
			}
		}

		return choice;
	}

	public boolean isFirstAbilityUsage(Player p, Enemy e) {
		boolean poison = false;
		if (p.getCharacter() == "Aragon") {
			if (p.getManaPoints() >= 20) {
				System.out.println("You use " + abilities[0] + "! You made: "
						+ p.calculateDamageDoneByPlayer(p, e, 2, false) + " damage!");
				p.consumeManaAndRemoveEnemyHealth(p, e, p.calculateDamageDoneByPlayer(p, e, 2, false), 20);
			} else
				System.out.println("You do not have enough mana. You need 20 for this skill.");
		} else if (p.getCharacter() == "Legolas") {
			if (p.getManaPoints() >= 15) {
				System.out.println("You use " + abilities[0] + "! Your next attack will do more damage.");
				poison = true;
				p.setManaPoints(p.getManaPoints() - 15);
			} else
				System.out.println("You do not have enough mana. You need 15 for this skill.");
		} else {
			if (p.getManaPoints() >= 15) {
				System.out.println("You use! " + abilities[0] + " You made "
						+ p.calculateDamageDoneByPlayer(p, e, 3, true) + " damage.");
				p.consumeManaAndRemoveEnemyHealth(p, e, p.calculateDamageDoneByPlayer(p, e, 3, true), 15);
			} else
				System.out.println("You do not have enough mana. You need 15 for this skill.");
		}
		return poison;
	}

	public void isSecondAbilityUsage(Player p, Enemy e) {
		if (p.getCharacter() == "Aragon") {
			if (p.getManaPoints() >= 40) {
				System.out.println("You use " + abilities[1] + ". Your health is permanently regenerated +50");
				p.setHealthPoints(p.getHealthPoints() + 50);
			} else
				System.out.println("You do not have enough mana.");
		} else if (p.getCharacter() == "Legolas") {
			if (p.getManaPoints() >= 50) {
				System.out.println("You use " + abilities[1] + "! You made "
						+ p.calculateDamageDoneByPlayer(p, e, 3, false) + " damage");
				p.consumeManaAndRemoveEnemyHealth(p, e, p.calculateDamageDoneByPlayer(p, e, 3, false), 50);
			} else
				System.out.println("You do not have enough mana. You need 50 for this skill");
		} else {
			if (p.getManaPoints() >= 20) {
				System.out.println("You use " + abilities[1] + "! You made "
						+ p.calculateDamageDoneByPlayer(p, e, 10, true) + " damage");
				p.consumeManaAndRemoveEnemyHealth(p, e, p.calculateDamageDoneByPlayer(p, e, 10, true), 20);
			} else
				System.out.println("You do not have enough mana. You need 20 for this skill.");
		}
	}

	public boolean isThirdAbilityUsage(Player p, Enemy e) {
		boolean disable = false;

		if (p.getCharacter() == "Aragon") {
			System.out.println("You use " + abilities[2] + "! Your opponent is disabled for the next turn");
			disable = true;
		} else if (p.getCharacter() == "Legolas") {
			if (p.getManaPoints() >= 30) {
				System.out.println("You use " + abilities[2] + " to ignore the opponent's defense! \nYou made "
						+ p.calculateDamageDoneByPlayer(p, e, (1 + e.getDefense() / 2), true) + " damage");
				p.consumeManaAndRemoveEnemyHealth(p, e,
						p.calculateDamageDoneByPlayer(p, e, (1 + e.getDefense() / 2), true), 30);
			} else
				System.out.println("You do not have enough mana. You need 30 mana for this skill.");
		} else {
			if (p.getManaPoints() >= 35) {
				System.out.println("You use " + abilities[2] + "! Opponent is disabled for the next turn!");
				disable = true;
			} else
				System.out.println("You do not have enough mana. You need 35 for this skill.");
		}

		return disable;
	}

	public void isFourthAbilityUsage(Player p, Enemy e) {
		if (p.getCharacter() == "Aragon") {
			if (p.getAragonUsedLastSkill()) {
				System.out.println("You used " + abilities[3] + "! Your defense is increased by 5 permanently.");
				p.setAragonUsedLastSkill(false);
			} else
				System.out.println("That skill can be used only once.");
		} else if (p.getCharacter() == "Legolas") {
			if (p.getManaPoints() >= 60) {
				System.out.println("You used " + abilities[3] + ". Opponent is in near death.");
				p.consumeManaAndRemoveEnemyHealth(p, e, e.getHealthPoints() - 5, 60);
			} else
				System.out.println("You do not have enough mana. You need 60 mana for this skill.");
		} else {
			if (p.getManaPoints() >= 30) {
				System.out.println("You use " + abilities[3] + "! You made "
						+ p.calculateDamageDoneByPlayer(p, e, 25, true) + " damage.");
				p.consumeManaAndRemoveEnemyHealth(p, e, p.calculateDamageDoneByPlayer(p, e, 25, true), 30);
			} else
				System.out.println("You do not have enough mana. You need 30 for this skill.");
		}
	}

	public int calculateDamageDoneByPlayer(Player p, Enemy e, int bonusFactor, boolean bonusType) {
		if (bonusType) {
			// addition if true
			if ((p.getAttackPoints() + bonusFactor) - (e.getDefense() / 2) < 0)
				return 0;
			return ((p.getAttackPoints() + bonusFactor) - (e.getDefense() / 2));
		} else {
			if ((p.getAttackPoints() * bonusFactor) - (e.getDefense() / 2) < 0)
				return 0;
			return ((p.getAttackPoints() * bonusFactor) - (e.getDefense() / 2));
		}
	}

	public void consumeManaAndRemoveEnemyHealth(Player p, Enemy e, int damage, int manaToRemove) {
		e.setHealthPoints(e.getHealthPoints() - damage);
		p.setManaPoints(p.getManaPoints() - manaToRemove);
	}

	public void refreshSkills(Player p) {
		p.setAttackPoints(p.getStrength() / 2);
		p.setManaPoints(p.getMagicka() * 5);
		p.setHealthPoints(p.getStrength() * 5);
		p.setDefense(p.getAgility() / 2);
	}

	public void levelUp(Player p) {
		p.setLevel(p.getLevel() + 1);
		p.setExp(0);

		if (p.getCharacter().equals("Aragon")) {
			p.setStrength(p.getStrength() + 3);
			p.setAgility(p.getAgility() + 1);
			p.setMagicka(p.getMagicka() + 1);

			refreshSkills(p);
			addNewAbilities(p, 'w', p.getLevel());
		} else if (p.getCharacter().equals("Legolas")) {
			p.setStrength(p.getStrength() + 1);
			p.setAgility(p.getAgility() + 3);
			p.setMagicka(p.getMagicka() + 1);

			refreshSkills(p);
			addNewAbilities(p, 't', p.getLevel());
		} else {
			p.setStrength(p.getStrength() + 1);
			p.setAgility(p.getAgility() + 1);
			p.setMagicka(p.getMagicka() + 3);

			refreshSkills(p);
			addNewAbilities(p, 'm', p.getLevel());
		}
	}

	public void addNewAbilities(Player p, char profession, int level) {
		switch (profession) {
		case 'w': {
			switch (level) {
			case 3: {
				System.out.println("You have learned a new ability: Healing Salve. \n It heals 50 HP");
				p.modifyAbility(1, "Healing Salve");
			}
				break;
			case 6: {
				System.out.println("You have learned a new ability: Charge\nOpponent gets disabled for the next turn!");
				p.modifyAbility(2, "Charge");
			}
				break;
			case 10: {
				System.out.println(
						"You have learned a new ability: Best Defense\nYour defense permanently increases by 5");
				p.modifyAbility(3, "Best Defense");
			}
				break;
			}
		}
			break;
		case 't': {
			switch (level) {
			case 3: 
				System.out.println(
						"You have learned a new ability: Critical Strike. \n Your next attack does triple damage");
				p.modifyAbility(1, "Critical Strike");
			
				break;
			case 6: 
				System.out.println("You have learned a new ability: Wither\nOpponent's defense is ignored!");
				p.modifyAbility(2, "Wither");
			
				break;
			case 10: 
				System.out.println(
						"You have learned a new ability: Coup De Grace\nYour attack brings your enemy near death!");
				p.modifyAbility(3, "Coup De Grace");
			
				break;
			}
		}
			break;
		case 'm': 
			switch (level) {
			case 3: 
				System.out.println("You have learned a new ability: Electrocute. \n Deals 10+ damage!");
				p.modifyAbility(1, "Electrecute");			
				break;
				
			case 6: 
				System.out.println("You have learned a new ability: Degen Aura\nEnemy is disabled the next turn!");
				p.modifyAbility(2, "Degen Aura");			
				break;
				
			case 10: 
				System.out.println("You have learned a new ability: Death Dome\nDeals 25+ damage");
				p.modifyAbility(3, "Best Defense");			
				break;
			}		
			break;
		}
	}

	public void addExpAndCheckIfLeveledUp(Player p, int exp) {
		p.setExp(p.getExp() + exp);
		if (p.getExp() >= 50) {
			if (p.getLevel() == Player.MAX_LEVEL)
				System.out.println("You have achieved the maximum level of your abilities!");
			else {
				System.out.println("You have leveled up! Your basic attributes have grown stronger!");
				p.levelUp(p);
			}
		}
	}

	public String[] getAbilities() {
		return abilities;
	}

	public int getxPosition() {
		return xPosition;
	}

	public void setxPosition(int xPosition) {
		this.xPosition = xPosition;
	}

	public int getyPosition() {
		return yPosition;
	}

	public void setyPosition(int yPosition) {
		this.yPosition = yPosition;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setCharacter(String profession) {
		this.character = profession;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public void setMagicka(int magicka) {
		this.magicka = magicka;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public void setManaPoints(int manaPoints) {
		this.manaPoints = manaPoints;
	}

	public void setAttackPoints(int attackPoints) {
		this.attackPoints = attackPoints;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public void setAbilities(String[] abilities) {
		this.abilities = abilities;
	}

	public void setAragonUsedLastSkill(boolean AragonUsedLastSkill) {
		this.AragonUsedLastSkill = AragonUsedLastSkill;
	}

	public String getName() {
		return name;
	}

	public String getCharacter() {
		return character;
	}

	public String getGender() {
		return gender;
	}

	public int getLevel() {
		return level;
	}

	public int getStrength() {
		return strength;
	}

	public int getAgility() {
		return agility;
	}

	public int getMagicka() {
		return magicka;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public int getManaPoints() {
		return manaPoints;
	}

	public int getAttackPoints() {
		return attackPoints;
	}

	public int getDefense() {
		return defense;
	}

	public boolean getAragonUsedLastSkill() {
		return AragonUsedLastSkill;
	}

	public static int getMaxLevel() {
		return MAX_LEVEL;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

	public void modifyAbility(int index, String ability) {
		abilities[index] = ability;
	}
}