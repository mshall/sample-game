package com.shall.app;

import java.util.Scanner;

import com.shall.characters.Enemy;
import com.shall.characters.Player;
import com.shall.datamanagement.*;
import com.shall.map.Map;

public class Game {
	public static Scanner userInput = new Scanner(System.in);
	public static boolean zoramUndefeated = true;

	// ---------------------

	void startGame() {

		char playerChoice = ' ';
		Player mainCharacter = null;
		SaveData data = null;
		Enemy enemies[] = new Enemy[14];
		int i;

		intro();
		playerChoice = getPlayerChoice();

		switch (playerChoice) {
		case '1':
			mainCharacter = initiateNewGame();
			break;

		case '2':
			try {
				data = (SaveData) ResourceManager.load("../saves/character.save");
			} catch (Exception e) {
				System.out.println("Could not load saved data: " + e.getMessage());
			}
			mainCharacter = new Player(data.getName(), data.getGender(), (data.playerCharacter == "Male") ? 'm' : 'f');
			ResourceManager.loadTheDataInThePlayer(data, mainCharacter);
			System.out.println("\n\tGAME LOADED");
			break;

		case '3':
			System.out.println("See you soon :)");
			System.exit(0);
			break;
		}

		userInput.reset();

		// generate map and monsters

		for (i = 0; i < 3; i++)
			enemies[i] = new Enemy(20, 20, 5, 2, "Goblin", false);
		for (i = 3; i < 6; i++)
			enemies[i] = new Enemy(25, 20, 6, 3, "Skeleton", false);
		for (i = 6; i < 8; i++)
			enemies[i] = new Enemy(23, 30, 5, 4, "Rat-Man", false);
		for (i = 8; i < 10; i++)
			enemies[i] = new Enemy(35, 40, 6, 5, "Salamander", false);
		for (i = 10; i < 12; i++)
			enemies[i] = new Enemy(30, 35, 6, 5, "Kobold", false);

		// creating the two bosses
		enemies[12] = new Enemy(35, 100, 7, 5, "Spectre", true);
		enemies[13] = new Enemy(70, 100, 10, 10, "Zoram", true);

		Map map = new Map();
		map.generateMapLayout();
		map.bringPlayerIntoMap(mainCharacter.getxPosition(), mainCharacter.getyPosition());
		for (i = 0; i < 12; i++)
			map.bringMonsterToMap(enemies[i].getxPosition(), enemies[i].getyPosition(), enemies[i].getName());
		map.bringMonsterToMap(enemies[12].getxPosition(), enemies[12].getyPosition(), enemies[12].getName());
		map.bringMonsterToMap(enemies[13].getxPosition(), enemies[13].getyPosition(), enemies[13].getName());
		// the game begins
		while (Game.zoramUndefeated) {
			map.printMap();
			menu();
			userInput.reset();

			playerChoice = getPlayerMove();

			switch (playerChoice) {
			case 'w': {
				manageMovement(mainCharacter, map, enemies, 'w');
			}
				break;
			case 'a': {
				manageMovement(mainCharacter, map, enemies, 'a');
			}
				break;
			case 'd': {
				manageMovement(mainCharacter, map, enemies, 'd');
			}
				break;
			case 's': {
				manageMovement(mainCharacter, map, enemies, 's');
			}
				break;
			case 'i':
				showStats(mainCharacter);
				break;
			case 'q': {
				System.out.println("See you next time!");
				System.exit(0);
			}
				break;
			case 'v': {
				data = ResourceManager.createSaveData(mainCharacter);
				try {
					ResourceManager.save(data, "../saves/character.save");
					System.out.println("Data saved successfully!");
				} catch (Exception e) {
					System.out.println("Could not save: " + e.getMessage());
				}
			}
			}

			playerChoice = ' ';
		}

		if (!Game.zoramUndefeated) {
			System.out.println("You Win!");
			System.out.println("Hopefully you will retake this adventure again!\nExiting...");
			System.exit(0);
		}
		userInput.close();
	}

	void intro() {
		System.out.println("Welcome traveler to this mysterious trial you will be facing!");
		System.out.println("This is the magical land of Marghor, ruled by the tyrannical sorceror Zoram.");
		System.out.println("We bid you welcome!");
		System.out.println("Since you accept the challenge to defeat Zoram (which main character doesn't?)");
		System.out.println("You must choose...");
		System.out.println("\t1: Start a new game");
		System.out.println("\t2: Continue");
		System.out.println("\t3: Exit");
	}

	void menu() {
		System.out.println("Press 'w' key to move up");
		System.out.println("Press 's' key to move down");
		System.out.println("Press 'a' key to move left");
		System.out.println("Press 'd' key to move right");
		System.out.println("Press 'q' key to exit");
	}

	void showStats(Player p) {
		System.out.println("**********Player Info********");
		System.out.print("\tName: " + p.getName());
		System.out.print("\n\tLevel: " + p.getLevel());
		System.out.print("\n\tProfession: " + p.getCharacter());
		System.out.print("\n\tGender: " + p.getGender());
		System.out.println("\n--------------BASIC ATTRIBUTES----------");
		System.out.print("\tStrength: " + p.getStrength());
		System.out.print("\n\tAgility: " + p.getAgility());
		System.out.print("\n\tMagic: " + p.getMagicka());
		System.out.println("\n-------------------STATS----------------");
		System.out.print("\tHP: " + p.getHealthPoints());
		System.out.print("\n\tMP: " + p.getManaPoints());
		System.out.print("\n\tAttack: " + p.getAttackPoints());
		System.out.print("\n\tDefense: " + p.getDefense());
		System.out.println("\n\tEXP: " + p.getExp());
	}

	public Enemy checkWhichEnemy(int x, int y, Enemy[] enemies) {
		for (int i = 0; i < enemies.length; i++) {
			if (x == enemies[i].getxPosition() && y == enemies[i].getyPosition())
				return enemies[i];
		}
		return null;
	}

	void manageMovement(Player p, Map map, Enemy[] enemies, char movement) {
		switch (movement) {
		case 'w': {
			if (map.checkIfOutOfBoundaries(p.getxPosition() - 1, p.getyPosition())) {
				char encounter = map.checkForEncounter(p.getxPosition() - 1, p.getyPosition());
				fightOrFindNothing(encounter, p, enemies, p.getxPosition() - 1, p.getyPosition());
				map.updateMap(p.getxPosition() - 1, p.getyPosition(), movement);
				p.move(movement);
			} else
				System.out.println("cannot step out of our small world map, right?");
		}
			break;
		case 'a': {
			if (map.checkIfOutOfBoundaries(p.getxPosition(), p.getyPosition() - 1)) {
				char encounter = map.checkForEncounter(p.getxPosition(), p.getyPosition() - 1);
				fightOrFindNothing(encounter, p, enemies, p.getxPosition(), p.getyPosition() - 1);
				map.updateMap(p.getxPosition(), p.getyPosition() - 1, movement);
				p.move(movement);
			} else
				System.out.println("cannot step out of our small world map, right?");
		}
			break;
		case 'd': {
			if (map.checkIfOutOfBoundaries(p.getxPosition(), p.getyPosition() + 1)) {
				char encounter = map.checkForEncounter(p.getxPosition(), p.getyPosition() + 1);
				fightOrFindNothing(encounter, p, enemies, p.getxPosition(), p.getyPosition() + 1);
				map.updateMap(p.getxPosition(), p.getyPosition() + 1, movement);
				p.move(movement);
			} else
				System.out.println("cannot step out of our small world map, right?");
		}
			break;
		case 's': {
			if (map.checkIfOutOfBoundaries(p.getxPosition() + 1, p.getyPosition())) {
				char encounter = map.checkForEncounter(p.getxPosition() + 1, p.getyPosition());
				fightOrFindNothing(encounter, p, enemies, p.getxPosition() + 1, p.getyPosition());
				map.updateMap(p.getxPosition() + 1, p.getyPosition(), movement);
				p.move(movement);
			} else
				System.out.println("cannot step out of our small world map, right?");
		}
			break;
		}
	}

	public boolean fightOrFindNothing(char encounter, Player p, Enemy[] enemies, int enemyPositionX,
			int enemyPositionY) {
		if (encounter == ' ')
			System.out.println("Nothing interesting here!");
		else
			return fight(p, checkWhichEnemy(enemyPositionX, enemyPositionY, enemies));
		return false; // return false if there was no fight
	}

	boolean fight(Player p, Enemy e) {
		char choice = ' ';
		boolean poison = false;
		boolean disabledByPlayer = false;
		int turns = 0;

		if (e.getName().equals("Zoram"))
			System.out.println("You face the mighty and evil Zoram.\nGet Ready!");
		else
			System.out.println("You have stumbled upon " + e.getName() + ", prepare yourself!");

		while (p.getHealthPoints() > 0 && e.getHealthPoints() > 0) {

			// player's turn
			System.out.println(p.getName() + " HP: " + p.getHealthPoints() + " | MP: " + p.getManaPoints());
			System.out.println(e.getName() + " HP: " + e.getHealthPoints());
			System.out.println("It is your turn");
			System.out.println("What will your move be?");
			System.out.print("'a' for attack\n'b' for abilities\n");

			while (choice != 'a' && choice != 'b') {
				choice = userInput.next().charAt(0);
				if (choice != 'a' && choice != 'b')
					System.out.println("Please choose the appropriate choice.");
			}

			switch (choice) {
			case 'a':
				poison = p.physicalAtatck(p, e, poison);
				break;
			case 'b': {
				switch (p.showAndSelectAbilityDuringBattle(p, userInput)) {
				case '0': {
					poison = p.isFirstAbilityUsage(p, e);
				}
					break;
				case '1': {
					p.isSecondAbilityUsage(p, e);
				}
					break;
				case '2': {
					disabledByPlayer = p.isThirdAbilityUsage(p, e);
				}
					break;
				case '3': {
					p.isFourthAbilityUsage(p, e);
				}
					break;
				}
			}
			}

			// enemy's turn
			System.out.println("It is " + e.getName() + "'s turn!");
			if (!disabledByPlayer) {
				if (e.getIsBoss()) {
					switch (e.getName()) {
					case "Spectre": {
						if (turns == 0) {
							System.out.println("Spectre throws demonic flames from his sickle\nAnd does: "
									+ e.calculateDamageDoneByEnemyAndRemoveHealth(p, e, 15) + " damage.");
							turns = 5;
						} else
							System.out.println("Spectre attacks you and does: "
									+ e.calculateDamageDoneByEnemyAndRemoveHealth(p, e, 0) + " damage.");
					}
						break;
					case "Zoram": {
						if (turns == 0) {
							System.out.println(
									"The sorceror Zoram uses his healing abilities. His health increases by 50.");
							e.setHealthPoints(e.getHealthPoints() + 50);
						} else
							System.out.println("The sorceror Zoram attacks you and does: "
									+ e.calculateDamageDoneByEnemyAndRemoveHealth(p, e, 0) + " damage.");
					}
						break;
					}
				} else
					System.out.println(e.getName() + " attacks you and does "
							+ e.calculateDamageDoneByEnemyAndRemoveHealth(p, e, 0) + " damage\n");

				if (turns > 0 || turns - 1 == 0)
					turns--;
			} else {
				System.out.println("Enemy is disabled for this turn.");
				disabledByPlayer = false;
			}
			choice = ' ';
		}
		if (p.getHealthPoints() <= 0) {
			System.out.println("Oh snap, you died :(");
			System.out.println("Game over!");
			System.exit(0);
		}
		if (e.getName().equals("Zoram")) {
			System.out
					.println("You defeated the evil" + e.getName() + " and won " + e.getExpAmountWhenKilled() + " EXP");
			System.out.println("His reign ends here!\nCongratulations " + p.getName() + "!\nYou truly are remarkable!");
			Game.zoramUndefeated = false;
		} else
			System.out.println("You defeated " + e.getName() + " and won " + e.getExpAmountWhenKilled() + " EXP");
		p.setHealthPoints(p.getHealthPoints() + 10);
		p.setManaPoints(p.getManaPoints() + 10);
		p.addExpAndCheckIfLeveledUp(p, e.getExpAmountWhenKilled());
		return true; // return true if fight is over and player is still alive
	}

	public void greetCharacter(char character) {
		switch (character) {
		case 'w':
			System.out.print("\nSlice them with your sword!.\n");
			break;
		case 't':
			System.out.print("\nShower them with your arrows!\n");
			break;
		case 'm':
			System.out.print("\nCast them with your wand!\n");
			break;
		}
	}

	public char getPlayerMove() {
		char playerChoice = ' ';
		while (playerChoice != 'w' && playerChoice != 'a' && playerChoice != 'd' && playerChoice != 's'
				&& playerChoice != 'i' && playerChoice != 'v' && playerChoice != 'q') {
			playerChoice = userInput.next().charAt(0);
			if (playerChoice != 'w' && playerChoice != 'a' && playerChoice != 'd' && playerChoice != 's'
					&& playerChoice != 'i' && playerChoice != 'v' && playerChoice != 'q')
				System.out.println("Please enter the appropriate choice.");
		}
		return playerChoice;
	}

	public char getPlayerChoice() {
		char playerChoice = ' ';
		while (playerChoice != '1' && playerChoice != '2' && playerChoice != '3') {
			playerChoice = userInput.next().charAt(0);
			if (playerChoice != '1' && playerChoice != '2' && playerChoice != '3')
				System.out.println("\nPlease enter '1', '2' or '3'");
		}
		return playerChoice;
	}

	public char getPlayerCharacter() {
		char character = ' ';
		while (character != 'w' && character != 't' && character != 'm') {
			character = userInput.next().charAt(0);
			if (character != 'w' && character != 't' && character != 'm')
				System.out.println("Please enter 'w', 't' or 'm'");
		}
		return character;
	}

	public char getPlayerGender() {
		char gender = ' ';
		while (gender != 'm' && gender != 'f') {
			gender = userInput.next().charAt(0);
			if (gender != 'm' && gender != 'f')
				System.out.println("Please enter 'm' or 'f'");
		}
		return gender;
	}

	public Player initiateNewGame() {
		Player mainCharacter = null;
		System.out.println("What is your name?");
		String name = userInput.next();
		System.out.println("\nYou have a good name! " + name);
		System.out.println("Gender? \n'm' for Male\n'f' for Female\n");
		userInput.reset();
		char gender = getPlayerGender();

		System.out.println("What is your favorite character to play with?");
		System.out.println("Aragon, Legolas or Gandalf?");
		System.out.println("'w' for Aragon\n't' for Legolas\n'm' for Gadnalf\n");
		userInput.reset();

		char playerCharacter = getPlayerCharacter();

		greetCharacter(playerCharacter);
		mainCharacter = new Player(name, (gender == 'm') ? "Male" : "Female", playerCharacter);
		return mainCharacter;
	}
}
