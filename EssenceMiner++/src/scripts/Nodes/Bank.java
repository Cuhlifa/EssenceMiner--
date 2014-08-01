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
	public int execute() {

		EssenceMiner.mainMiner.scriptState = "Banking";

		if (!Banking.isInBank()) {

			WebWalking.walkToBank();
			
			Timing.waitCondition(new Condition() {
				
				@Override
				public boolean active() {
					return Banking.isInBank();
				}
			}, 20000);

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
						
					Timing.waitCondition(new Condition() {
						
						@Override
						public boolean active() {
							return Inventory.getCount("Rune Essence","Pure Essence") == 0;
						}
					}, 1000);
					
					WebWalking.walkTo(WalkToAubury.auburyTile);

					EssenceMiner.mainMiner.inventoryCount = 0;
						
				}

			}

		}

		return 0;

	}

	@Override
	public boolean validate() {

		return Inventory.isFull() && !EssenceMiner.isInMine();

	}

}
