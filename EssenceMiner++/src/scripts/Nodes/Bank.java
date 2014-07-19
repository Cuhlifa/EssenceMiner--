package scripts.Nodes;

import org.tribot.api.General;
import org.tribot.api.Timing;
import org.tribot.api.types.generic.Condition;
import org.tribot.api2007.Banking;
import org.tribot.api2007.Game;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSTile;

import scripts.EssenceMiner;
import scripts.EssenceMinerExtras.Node;

public class Bank extends Node{

	@Override
	public void execute() {
		
		EssenceMiner.MainMiner.ScriptState = "Banking";
		EssenceMiner.MainMiner.path = PathFinding.generatePath(Player.getPosition(),Game.getDestination(),false);
		
		if(WebWalking.walkToBank()){
			
			if(Banking.openBank()){
				
				Timing.waitCondition(new Condition() {
					
					@Override
					public boolean active() {
						
						return Banking.isBankScreenOpen();
						
					}
					
				}, 4000);
				
				//double check if condition fails
				if(Banking.isBankScreenOpen()){
					
					Banking.depositAllExcept(EssenceMiner.MainMiner.PICKAXES);
					General.sleep(EssenceMiner.AntiBan.DELAY_TRACKER.NEW_OBJECT.next());
					EssenceMiner.AntiBan.DELAY_TRACKER.NEW_OBJECT.reset();
					Banking.close();
					General.sleep(100, 250);
					WebWalking.walkTo(WalkToAubury.AuburyTile);
					EssenceMiner.MainMiner.InventoryCount = 0;
					
				}
				
			}
			
		}
		
	}

	@Override
	public boolean validate() {

		if(Inventory.isFull() && !EssenceMiner.MainMiner.isInMine()){
			
			return true;
			
		}else{return false;}
		

	}
		
}
