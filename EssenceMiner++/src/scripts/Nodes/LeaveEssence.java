package scripts.Nodes;

import java.awt.Point;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;

import scripts.EssenceMiner;
import scripts.EssenceMinerExtras.Node;

public class LeaveEssence extends Node {

	@Override
	public void execute() {

		EssenceMiner.mainMiner.scriptState = "Leaving Essence Cavern";
		EssenceMiner.mainMiner.essence = null;
		System.out.println("Leaving Mine");
		RSNPC[] Portal = NPCs.findNearest("null", "Portal");

		if (Portal != null && Portal.length > 0 && Portal[0] != null) {

			System.out.println("Portals wasn't null");

			if (Portal[0].isOnScreen()
					&& !Player.isMoving()
					&& PathFinding.distanceTo(Portal[0].getPosition(), true) < 5) {

				System.out.println("Portals is on screen");

				Camera.setCameraRotation(Camera.getTileAngle(Portal[0]
						.getPosition()) - General.random(-30, 30));

				General.sleep(100, 260);

				if (Portal[0] != null
						&& !DynamicClicking.clickRSTile(
								Portal[0].getPosition(), 1)) {

					if (Portal.length > 1) {
						DynamicClicking.clickRSTile(Portal[1].getPosition(), 1);
					}

				}

			} else {

				if (Walking.blindWalkTo(Portal[0].getPosition())) {

					System.out.println("Portals is being walked to");

					Camera.setCameraRotation(Camera.getTileAngle(Portal[0]
							.getPosition()) - General.random(-30, 30));

					General.sleep(100, 260);

					Portal[0].hover(new Point(5, -5), new Point(2, -2));

					General.sleep(100, 175);

					if (Portal[0] != null
							&& !DynamicClicking.clickRSTile(
									Portal[0].getPosition(), 1)) {

						if (Portal.length > 1) {
							DynamicClicking.clickRSTile(
									Portal[1].getPosition(), 1);
						}

					}

				}

			}

		}

		return;

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

		if (Inventory.isFull() && isInMine()) {

			return true;

		} else {

			return false;

		}

	}

}
