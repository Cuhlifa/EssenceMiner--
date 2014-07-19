package scripts.Nodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.types.RSObject;

import scripts.EssenceMiner;
import scripts.EssenceMinerExtras.Node;

public class Mine extends Node {

	@Override
	public void execute() {
		
		System.out.println("Mining");
		
		EssenceMiner.MainMiner.ScriptState = "Mining Essence";
		
		RSObject[] RuneEssence = Objects.findNearest(30, "Rune Essence","Pure Essence");
		
		if(RuneEssence[0].isOnScreen()){
			System.out.println("Rune Essence was on screen");
			DynamicClicking.clickRSModel(RuneEssence[0].getModel(), "Mine");
			
		}else{
				
			System.out.println("Walkign to Rune Essence");
			if(Walking.blindWalkTo(RuneEssence[0].getPosition())){
				
				Camera.setCameraRotation(Camera.getTileAngle(RuneEssence[0].getPosition()) - General.random(-30, 30));
				General.sleep(100, 260);
				DynamicClicking.clickRSModel(RuneEssence[0].getModel(), "Mine");
				EssenceMiner.MainMiner.Essence = RuneEssence[0].getModel();
				
			}
			
			
		}
		
		EssenceMiner.AntiBan.BOOL_TRACKER.USE_CLOSEST.reset();
			
	}

	@Override
	public boolean validate() {

		if(!Inventory.isFull() && Player.getAnimation() == -1 && EssenceMiner.MainMiner.isInMine()){
			
			return true;
			
		}else{return false;}
		
	}

}
