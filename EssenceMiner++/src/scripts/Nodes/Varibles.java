package scripts.Nodes;

import java.util.concurrent.TimeUnit;

import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Skills.SKILLS;

import scripts.EssenceMiner;
import scripts.EssenceMinerExtras.Node;
import scripts.EssenceMinerExtras.ZybezItem;

public class Varibles extends Node {

	EssenceMiner Miner = EssenceMiner.mainMiner;

	@Override
	public void execute() {

		Miner.path = PathFinding.generatePath(Player.getPosition(),
				Game.getDestination(), false);

		Miner.currentXP = Skills.getXP(SKILLS.MINING);

		Miner.gainedXP = Miner.currentXP - Miner.startingXP;

		Miner.currentLevel = Skills.getActualLevel(SKILLS.MINING);

		Miner.gainedLevel = Miner.currentLevel - Miner.startingLevel;

		Miner.runTime = (System.currentTimeMillis() - Miner.startTime) / 1000;

		Miner.hours = TimeUnit.SECONDS.toHours(Miner.runTime);

		Miner.minutes = TimeUnit.SECONDS.toMinutes(Miner.runTime
				- TimeUnit.HOURS.toSeconds(Miner.hours));

		Miner.seconds = Miner.runTime
				- (TimeUnit.HOURS.toSeconds(Miner.hours) + TimeUnit.MINUTES
						.toSeconds(Miner.minutes));

		Miner.minedOresHour = 0;

		Miner.profit = Miner.minedOres
				* new ZybezItem("Rune Essence").getAverage();

		if (Miner.profit > 0) {

			Miner.profitHour = (int) ((Miner.profit * 3600) / Miner.runTime);

		}

		if (Miner.minedOres > 0) {

			Miner.minedOresHour = (int) ((Miner.minedOres * 3600) / Miner.runTime);

		}

		if (Miner.gainedXP > 0) {

			Miner.gainedXPHour = (int) ((Miner.gainedXP * 3600) / Miner.runTime);

		}

		int InvCount = Inventory.getCount("Rune Essence");

		if (InvCount > Miner.inventoryCount) {

			Miner.minedOres += InvCount - Miner.inventoryCount;

			Miner.inventoryCount = InvCount;

		}

	}

	@Override
	public boolean validate() {

		return true;

	}

}
