package scripts.Nodes;

import org.tribot.api2007.Player;
import org.tribot.api2007.Skills.SKILLS;

import scripts.EssenceMiner;
import scripts.EssenceMinerUtils.Node;

public class AntiBan extends Node {

	@Override
	public void execute() {

		EssenceMiner.antiBan.performCombatCheck();
		EssenceMiner.antiBan.performEquipmentCheck();
		EssenceMiner.antiBan.performExamineObject();
		EssenceMiner.antiBan.performFriendsCheck();
		EssenceMiner.antiBan.performLeaveGame();
		EssenceMiner.antiBan.performMusicCheck();
		EssenceMiner.antiBan.performPickupMouse();
		EssenceMiner.antiBan.performQuestsCheck();
		EssenceMiner.antiBan.performRandomMouseMovement();
		EssenceMiner.antiBan.performRandomRightClick();
		EssenceMiner.antiBan.performRotateCamera();
		EssenceMiner.antiBan.performTimedActions(SKILLS.MINING);
		EssenceMiner.antiBan.performXPCheck(SKILLS.MINING);

	}

	@Override
	public boolean validate() {

		return Player.getAnimation() != -1;

	}

}
