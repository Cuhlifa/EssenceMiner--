package scripts.Nodes;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSObject;

import scripts.EssenceMiner;
import scripts.EssenceMinerUtils.Node;

public class Mine extends Node {

	@SuppressWarnings("deprecation")
	@Override
	public int execute() {

		System.out.println("Mining");

		EssenceMiner.mainMiner.scriptState = "Mining Essence";

		final RSObject[] runeEssence = Objects.findNearest(20, "Rune Essence",
				"Pure Essence");
		
		if(runeEssence != null && runeEssence.length > 0 && runeEssence[0] != null){
			
			if(!runeEssence[0].isOnScreen()){
				
				WebWalking.walkTo(runeEssence[0].getPosition());
				
				Timing.waitCondition(new Condition() {
					
					@Override
					public boolean active() {
						return runeEssence[0].isOnScreen();
					}
					
				}, 10000);
				
			}
			
			if(Clicking.click(runeEssence[0])){
				
				Timing.waitCondition(new Condition() {
					
					@Override
					public boolean active() {
						return Player.getAnimation() != 1;
					}
					
				}, 2000);
				
			}
			
		}
		
		return 0;

	}

	@Override
	public boolean validate() {

		if (!Inventory.isFull() && Player.getAnimation() == -1 && EssenceMiner.isInMine()) {

			return true;

		} else {

			return false;

		}

	}

}
