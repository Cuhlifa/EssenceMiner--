package scripts.Nodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSObject;

import scripts.EssenceMiner;
import scripts.EssenceMinerExtras.Node;

public class Mine extends Node {

	@SuppressWarnings("deprecation")
	@Override
	public void execute() {

		System.out.println("Mining");

		EssenceMiner.mainMiner.scriptState = "Mining Essence";

		RSObject[] RuneEssence = Objects.findNearest(20, "Rune Essence",
				"Pure Essence");

		if (RuneEssence != null && RuneEssence.length > 0
				&& RuneEssence[0] != null) {

			System.out.println("Essence Wasn't null");

			if (PathFinding.distanceTo(RuneEssence[0].getPosition(), true) > 5) {

				System.out.println("Walking to essence");
				Walking.blindWalkTo(RuneEssence[0].getPosition());

			} else if (RuneEssence[0].isOnScreen()) {

				System.out.println("Rune Essence was on screen");

				RSModel Essence = RuneEssence[0].getModel();
				if (Essence != null
						&& DynamicClicking.clickRSModel(Essence, "Mine")) {

					EssenceMiner.mainMiner.essence = RuneEssence[0].getModel();

				}

				EssenceMiner.mainMiner.essence = RuneEssence[0].getModel();

			} else {

				System.out.println("Walkign to Rune Essence");

				if (Walking.blindWalkTo(RuneEssence[0].getPosition())) {

					Camera.setCameraRotation(Camera.getTileAngle(RuneEssence[0]
							.getPosition()) - General.random(-30, 30));

					General.sleep(100, 260);

					RSModel Essence = RuneEssence[0].getModel();
					if (Essence != null
							&& DynamicClicking.clickRSModel(Essence, "Mine")) {

						EssenceMiner.mainMiner.essence = RuneEssence[0]
								.getModel();

					}

				}

			}

			EssenceMiner.antiBan.BOOL_TRACKER.USE_CLOSEST.reset();

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

		if (!Inventory.isFull() && Player.getAnimation() == -1 && isInMine()) {

			return true;

		} else {

			return false;

		}

	}

}
