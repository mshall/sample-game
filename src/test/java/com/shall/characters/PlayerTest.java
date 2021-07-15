package com.shall.characters;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlayerTest {

	@Test
	public void testPhysicalAttackWithPoison() {
		Player player = new Player("MainTestPlayer", "Female", 't');
		Enemy e = new Enemy(10, 10, 10, 10, "TestMonster", false);
		assertEquals(false, player.physicalAtatck(player, e, true));
	}

	@Test
	public void testPhysicalAttackWithoutPoison() {
		Player player = new Player("MainTestPlayer", "Female", 't');
		Enemy e = new Enemy(10, 10, 10, 10, "TestMonster", false);
		assertEquals(false, player.physicalAtatck(player, e, false));
	}

	@Test
	public void testIsFirstAbilityUsageThatPoisonsTheAttack() {
		/* Creating a thief character that uses Poison Dagger */
		Player player = new Player("MainTestPlayer", "Female", 't');
		Enemy e = new Enemy(10, 10, 10, 10, "TestMonster", false);
		boolean isFirstAbility = player.isFirstAbilityUsage(player, e);
		assertEquals(true, isFirstAbility);
	}

	@Test
	public void testIsFirstAbilityUsageThatDoesNotPoisonTheAttack() {
		/* Creating a character that does not use Poison Dagger */
		Player player = new Player("MainTestPlayer", "Female", 'w');
		Enemy e = new Enemy(10, 10, 10, 10, "TestMonster", false);
		boolean isFirstAbility = player.isFirstAbilityUsage(player, e);
		assertEquals(false, isFirstAbility);
	}

	@Test
	public void testIsThirdAbilityUsageThatUsesDisable() {
		/* Creating a character that uses a disabling skill */
		Player player = new Player("MainTestPlayer", "Male", 'w');
		Enemy e = new Enemy(10, 10, 10, 10, "TestMonster", false);
		boolean isThirdAbility = player.isThirdAbilityUsage(player, e);
		assertEquals(true, isThirdAbility);
	}

	@Test
	public void testIsThirdAbilityUsageThatDoesNotUseDisable() {
		/* Creating a character that does not use a disabling skill */
		Player player = new Player("MainTestPlayer", "Male", 't');
		Enemy e = new Enemy(10, 10, 10, 10, "TestMonster", true);
		boolean isThirdAbility = player.isThirdAbilityUsage(player, e);
		assertEquals(false, isThirdAbility);
	}

	@Test
	public void testCalculateDamageDoneByPlayerAdditionCaseNotNegative() {
		/* player will do 5 damage to the enemy */
		Player player = new Player("MainTestPlayer", "Male", 't');
		Enemy e = new Enemy(10, 10, 10, 0, "TestMonster", false); // defense 0
		assertEquals(5, player.calculateDamageDoneByPlayer(player, e, 0, true));
	}

	@Test
	public void testCalculateDamageDoneByPlayerAdditionCaseNegative() {
		/* player will do negative damage to the enemy, therefore returning 0 */
		Player player = new Player("MainTestPlayer", "Male", 't');
		Enemy e = new Enemy(10, 10, 10, 10, "TestMonster", false);
		assertEquals(0, player.calculateDamageDoneByPlayer(player, e, 0, true));
	}

	@Test
	public void testCalculateDamageDoneByPlayerMultiplyingCaseNotNegative() {
		/* player will do 5 damage to the enemy */
		Player player = new Player("MainTestPlayer", "Male", 't');
		Enemy e = new Enemy(10, 10, 10, 0, "TestMonster", false); // defense 0
		assertEquals(5, player.calculateDamageDoneByPlayer(player, e, 1, false));
	}

	@Test
	public void testCalculateDamageDoneByPlayerMultiplyingCaseNegative() {
		/* player will do negative damage to the enemy, therefore returning 0 */
		Player player = new Player("MainTestPlayer", "Male", 't');
		Enemy e = new Enemy(10, 10, 10, 10, "TestMonster", false);
		assertEquals(0, player.calculateDamageDoneByPlayer(player, e, 1, false));
	}
}
