package scripts.Nodes;

import java.awt.Point;

import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;

import scripts.EssenceMiner;
import scripts.EssenceMinerExtras.Node;

public class LeaveEssence extends Node {

	@Override
	public void execute() {

		EssenceMiner.MainMiner.ScriptState = "Leaving Essence Cavern";
		EssenceMiner.MainMiner.Essence = null;
		System.out.println("Leaving Mine");
		RSNPC[] Portal = NPCs.findNearest("null", "Portal");

		if (Portal != null && Portal.length > 0) {
			
			System.out.println("Portals wasn't null");
			
			if (Portal[0].isOnScreen() && !Player.isMoving()) {
				
				System.out.println("Portals is on screen");
				
				Camera.setCameraRotation(Camera.getTileAngle(Portal[0]
						.getPosition()) - General.random(-30, 30));
				
				General.sleep(100, 260);
				
				Portal[0].click("Exit", "Use", "Leave");

			} else {

				if (Walking.blindWalkTo(Portal[0].getPosition())) {
					
					System.out.println("Portals is being walked to");
					
					Camera.setCameraRotation(Camera.getTileAngle(Portal[0]
							.getPosition()) - General.random(-30, 30));
					
					General.sleep(100, 260);
					
					Portal[0].hover(new Point(5, -5), new Point(2, -2));
					
					General.sleep(100, 175);
					
					Portal[0].click("Exit", "Use", "Leave");

				}

			}

		}

	}

	@Override
	public boolean validate() {

		if (Inventory.isFull() && EssenceMiner.MainMiner.isInMine()) {

			return true;

		} else {
			
			return false;
			
		}

	}

}
