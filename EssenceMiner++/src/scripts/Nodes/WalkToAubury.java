package scripts.Nodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSObject;
import org.tribot.api2007.types.RSTile;

import scripts.EssenceMiner;
import scripts.EssenceMinerExtras.Node;

public class WalkToAubury extends Node {

	public static RSTile AuburyTile = new RSTile(3253, 3401);

	@Override
	public void execute() {

		if (PathFinding.distanceTo(AuburyTile, false) > 5) {
			
			Walking.blindWalkTo(AuburyTile);
			
		}else{
			
			RSNPC[] Aubury = NPCs.find("Aubury");

			System.out.println("Walking to aubury");

			if (Aubury != null && Aubury.length > 0) {

				System.out.println("Aubury wasn't null");

				if (PathFinding.canReach(Aubury[0].getPosition(), false)) {
					System.out.println("Aubury is reachable");

					if (Aubury[0].isOnScreen()) {

						System.out.println("Aubury is on screen");

						Camera.setCameraRotation(Camera.getTileAngle(Aubury[0]
								.getPosition()) - General.random(-30, 30));
						General.sleep(100, 260);

						RSModel AuburyModel = Aubury[0].getModel();
						if (!EssenceMiner.MainMiner.isInMine()
								&& AuburyModel != null) {

							DynamicClicking.clickRSModel(Aubury[0].getModel(),
									"Teleport");

						}

					} else {

						if (Walking.blindWalkTo(AuburyTile)) {

							System.out.println("Aubury is being walked to");

							Camera.setCameraRotation(Camera.getTileAngle(Aubury[0]
									.getPosition()) - General.random(-30, 30));

							General.sleep(100, 260);

							RSModel AuburyModel1 = Aubury[0].getModel();
							if (!EssenceMiner.MainMiner.isInMine()
									&& AuburyModel1 != null) {

								DynamicClicking.clickRSModel(AuburyModel1,
										"Teleport");

							}

						}

					}

				} else {

					System.out.println("checking doors");

					RSTile[] path = PathFinding.generatePath(Player.getPosition(),
							AuburyTile, false);

					if (path != null && path.length > 0) {

						for (RSTile tile : path) {

							RSObject Doortile = Doors.getDoorAt(tile);
							if (Doortile != null
									|| Doors.isDoorAt(tile, false)) {

								Doors.handleDoorAt(tile, true);

							}

						}

					}

				}
				
				return;

			}
			
		}
			
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
