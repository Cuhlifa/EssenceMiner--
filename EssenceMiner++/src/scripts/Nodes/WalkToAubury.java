package scripts.Nodes;

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
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.EssenceMiner;
import scripts.EssenceMinerUtils.Node;

public class WalkToAubury extends Node {

	public static RSTile auburyTile = new RSTile(3253, 3401);

	@Override
	public int execute() {

		final RSNPC[] aubury = NPCs.find("Aubury");

		System.out.println("Walking to aubury");

		if (aubury != null && aubury.length > 0 && aubury[0] != null) {

			if(!aubury[0].isOnScreen()){
				
				WebWalking.walkTo(aubury[0].getPosition());
				
				Timing.waitCondition(new Condition() {
					
					@Override
					public boolean active() {
						return aubury[0].isOnScreen();
					}
					
				}, 10000);
				
			}
			
			if(DynamicClicking.clickRSTile(aubury[0], 1)){
				
				Timing.waitCondition(new Condition() {
					
					@Override
					public boolean active() {
						return EssenceMiner.isInMine();
					}
					
				}, 5000);
				
			}
			
		}

	return 0;
		
	}

	public boolean isInMine() {

		RSObject[] Walls = Objects.find(17, 1441, 1440);
		RSObject[] Essence = Objects.find(30, "Rune Essence", "Pure Essence");

		if (Walls != null && Walls.length > 0) {

			if (Essence != null && Essence.length > 0) {

				return true;

			} else {
				return false;
			}

		} else {
			return false;
		}

	}

	@Override
	public boolean validate() {

		if (!Inventory.isFull() && !isInMine()) {

			return true;

		} else {
			return false;
		}

	}

}
