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

	EssenceMiner Miner = EssenceMiner.MainMiner;

	@Override
	public void execute() {

		Miner.path = PathFinding.generatePath(Player.getPosition(),
				Game.getDestination(), false);

		Miner.CurrentXP = Skills.getXP(SKILLS.MINING);
		
		Miner.GainedXP = Miner.CurrentXP - Miner.StartingXP;
		
		Miner.CurrentLevel = Skills.getActualLevel(SKILLS.MINING);
		
		Miner.GainedLevel = Miner.CurrentLevel - Miner.StartingLevel;
		
		Miner.RunTime = (System.currentTimeMillis() - Miner.StartTime) / 1000;
		
		Miner.hours = TimeUnit.SECONDS.toHours(Miner.RunTime);
		
		Miner.minutes = TimeUnit.SECONDS.toMinutes(Miner.RunTime
				- TimeUnit.HOURS.toSeconds(Miner.hours));
		
		Miner.seconds = Miner.RunTime
				- (TimeUnit.HOURS.toSeconds(Miner.hours) + TimeUnit.MINUTES
						.toSeconds(Miner.minutes));
		
		Miner.MinedOresHour = 0;
		
		Miner.Profit = Miner.MinedOres
				* new ZybezItem("Rune Essence").getAverage();
		

		if (Miner.Profit > 0) {
			
			Miner.ProfitHour = (int) ((Miner.Profit * 3600) / Miner.RunTime);
			
		}

		if (Miner.MinedOres > 0) {
			
			Miner.MinedOresHour = (int) ((Miner.MinedOres * 3600) / Miner.RunTime);
			
		}

		if (Miner.GainedXP > 0) {
			
			Miner.GainedXPHour = (int) ((Miner.GainedXP * 3600) / Miner.RunTime);
			
		}

		int InvCount = Inventory.getCount("Rune Essence");
		
		if (InvCount > Miner.InventoryCount) {

			Miner.MinedOres += InvCount - Miner.InventoryCount;
			
			Miner.InventoryCount = InvCount;

		}

	}

	@Override
	public boolean validate() {

		return true;

	}

}
