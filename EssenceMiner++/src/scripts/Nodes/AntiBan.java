package scripts.Nodes;

import org.tribot.api2007.Player;
import org.tribot.api2007.Skills.SKILLS;

import scripts.EssenceMiner;
import scripts.EssenceMinerExtras.Node;

public class AntiBan extends Node {

	@Override
	public void execute() {

		EssenceMiner.AntiBan.performCombatCheck();
		
		EssenceMiner.AntiBan.performEquipmentCheck();
		
		EssenceMiner.AntiBan.performExamineObject();
		
		EssenceMiner.AntiBan.performFriendsCheck();
		
		EssenceMiner.AntiBan.performLeaveGame();

		EssenceMiner.AntiBan.performMusicCheck();
		
		EssenceMiner.AntiBan.performPickupMouse();
		
		EssenceMiner.AntiBan.performQuestsCheck();
		
		EssenceMiner.AntiBan.performRandomMouseMovement();
		
		EssenceMiner.AntiBan.performRandomRightClick();
		
		EssenceMiner.AntiBan.performRotateCamera();
		
		EssenceMiner.AntiBan.performTimedActions(SKILLS.MINING);
		
		EssenceMiner.AntiBan.performXPCheck(SKILLS.MINING);
		
	}

	@Override
	public boolean validate() {
		
		return Player.getAnimation() == 624;
		
	}

}
