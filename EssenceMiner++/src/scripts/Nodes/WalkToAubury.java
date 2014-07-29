package scripts.Nodes;

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
	public void execute() {

		if (PathFinding.distanceTo(auburyTile, false) > 5) {

			Walking.blindWalkTo(auburyTile);

		} else {

			final RSNPC[] aubury = NPCs.find("Aubury");

			System.out.println("Walking to aubury");

			if (aubury != null && aubury.length > 0 && aubury[0] != null) {

				System.out.println("Aubury wasn't null");

				if (PathFinding.canReach(aubury[0].getPosition(), false)) {
					System.out.println("Aubury is reachable");

					if (aubury[0].isOnScreen()) {

						System.out.println("Aubury is on screen");

						Camera.setCameraRotation(Camera.getTileAngle(aubury[0]
								.getPosition()) - General.random(-30, 30));
						General.sleep(100, 260);

						RSModel AuburyModel = aubury[0].getModel();
						if (!EssenceMiner.mainMiner.isInMine()
								&& AuburyModel != null) {

							if(DynamicClicking.clickRSModel(aubury[0].getModel(),"Teleport")){
								
								Timing.waitCondition(new Condition() {
									
									@Override
									public boolean active() {
									
										return isInMine();
									
									}
								}, 1000);
								
							}

						}

					} else {

						WebWalking.walkTo(auburyTile);
						
						Timing.waitCondition(new Condition() {
							
							@Override
							public boolean active() {
								
								return aubury[0].isOnScreen();
							
							}
						}, 200);
						
					}

				} else {

					System.out.println("checking doors");

					RSTile[] path = PathFinding.generatePath(
							Player.getPosition(), auburyTile, false);

					if (path != null && path.length > 0) {

						for (RSTile tile : path) {

							RSObject doorTile = Doors.getDoorAt(tile);
							if (doorTile != null || Doors.isDoorAt(tile, false)) {

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
