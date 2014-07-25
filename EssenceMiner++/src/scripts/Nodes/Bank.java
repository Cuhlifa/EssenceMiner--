package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;

import scripts.EssenceMiner;
import scripts.EssenceMinerExtras.Node;

public class Bank extends Node {

	@Override
	public void execute() {

		EssenceMiner.mainMiner.scriptState = "Banking";
		EssenceMiner.mainMiner.path = PathFinding.generatePath(
				Player.getPosition(), Game.getDestination(), false);
		System.out.println("Walking to bank");

		if (!Banking.isInBank()) {

			WebWalking.walkToBank();

		} else {

			if (Banking.openBank()) {

				System.out.println("Opening bank");

				General.sleep(300, 550);

				if (Banking.isBankScreenOpen()) {

					System.out.println("Bank is open");

					Banking.depositAllExcept(EssenceMiner.PICKAXES);

					General.sleep(EssenceMiner.antiBan.DELAY_TRACKER.NEW_OBJECT
							.next());

					EssenceMiner.antiBan.DELAY_TRACKER.NEW_OBJECT.reset();

					Banking.close();

					General.sleep(100, 250);

					Walking.blindWalkTo(WalkToAubury.AuburyTile);

					EssenceMiner.mainMiner.inventoryCount = 0;

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

		if (Inventory.isFull() && !isInMine()) {

			return true;

		} else {
			return false;
		}

	}

}
