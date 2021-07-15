package com.shall.datamanagement;

import java.io.Serializable;

public class SaveData implements Serializable {

	private static final long serialVersionUID = 1L;

	public String name, gender, playerCharacter;
	public String[] abilities = new String[4];
	public int level, xPosition, yPosition, strength, agility, magicka, healthPoints, manaPoints, attackPoints, defense,
			exp;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPlayerCharacter() {
		return playerCharacter;
	}

	public void setPlayerCharacter(String playerCharacter) {
		this.playerCharacter = playerCharacter;
	}

	public String[] getAbilities() {
		return abilities;
	}

	public void setAbilities(String[] abilities) {
		this.abilities = abilities;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
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

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getAgility() {
		return agility;
	}

	public void setAgility(int agility) {
		this.agility = agility;
	}

	public int getMagicka() {
		return magicka;
	}

	public void setMagicka(int magicka) {
		this.magicka = magicka;
	}

	public int getHealthPoints() {
		return healthPoints;
	}

	public void setHealthPoints(int healthPoints) {
		this.healthPoints = healthPoints;
	}

	public int getManaPoints() {
		return manaPoints;
	}

	public void setManaPoints(int manaPoints) {
		this.manaPoints = manaPoints;
	}

	public int getAttackPoints() {
		return attackPoints;
	}

	public void setAttackPoints(int attackPoints) {
		this.attackPoints = attackPoints;
	}

	public int getDefense() {
		return defense;
	}

	public void setDefense(int defense) {
		this.defense = defense;
	}

	public int getExp() {
		return exp;
	}

	public void setExp(int exp) {
		this.exp = exp;
	}

}
