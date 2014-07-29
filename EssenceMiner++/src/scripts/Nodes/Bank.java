package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
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
import scripts.EssenceMinerUtils.Node;

public class Bank extends Node {

	@Override
	public void execute() {

		EssenceMiner.mainMiner.scriptState = "Banking";

		if (!Banking.isInBank()) {

			WebWalking.walkToBank();

		} else {

			if (Banking.openBank()) {

				Timing.waitCondition(new Condition() {
					
					@Override
					public boolean active() {
						
						return Banking.isBankScreenOpen();
						
					}
				}, 1000);
				
				if (Banking.isBankScreenOpen()) {

					System.out.println("Bank is open");

					Banking.depositAllExcept(EssenceMiner.PICKAXES);

					General.sleep(EssenceMiner.antiBan.DELAY_TRACKER.NEW_OBJECT
							.next());

					EssenceMiner.antiBan.DELAY_TRACKER.NEW_OBJECT.reset();

					if(Banking.close()){
						
						Timing.waitCondition(new Condition() {
							
							@Override
							public boolean active() {
								
								return Banking.close();
								
							}
						}, 1000);
						
						Walking.blindWalkTo(WalkToAubury.auburyTile);

						EssenceMiner.mainMiner.inventoryCount = 0;
						
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

		if (Inventory.isFull() && !isInMine()) {

			return true;

		} else {
			return false;
		}

	}

}
