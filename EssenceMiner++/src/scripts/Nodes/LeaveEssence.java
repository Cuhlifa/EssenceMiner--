package scripts.Nodes;

import java.awt.Point;

import org.tribot.api.Clicking;
import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;

import scripts.EssenceMiner;
import scripts.EssenceMinerUtils.Node;

public class LeaveEssence extends Node {

	@Override
	public int execute() {

		EssenceMiner.mainMiner.scriptState = "Leaving Essence Cavern";
		EssenceMiner.mainMiner.essence = null;
		System.out.println("Leaving Mine");
		final RSNPC[] Portal = NPCs.findNearest("null", "Portal");

		if(Portal != null && Portal.length > 0 && Portal[0] != null){
			
			if(!Portal[0].isOnScreen()){
				
				WebWalking.walkTo(Portal[0].getPosition());
				
				Timing.waitCondition(new Condition() {
					
					@Override
					public boolean active() {
						return Portal[0].isOnScreen();
					}
				}, 3000);
				
			}
			
			if(Clicking.click(Portal[0])){
				
				Timing.waitCondition(new Condition() {
					
					@Override
					public boolean active() {
						return !EssenceMiner.isInMine();
					}
				}, 4000);
				
			}
			
		}
		
		return 0;

	}

	@Override
	public boolean validate() {

		return Inventory.isFull() && EssenceMiner.isInMine();

	}

}
