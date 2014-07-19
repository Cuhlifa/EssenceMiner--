package scripts.Nodes;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.types.RSObject;

import scripts.EssenceMiner;
import scripts.EssenceMinerExtras.Node;

public class Mine extends Node {

	@Override
	public void execute() {
		
		EssenceMiner.MainMiner.ScriptState = "Mining Essence";
		
		final int Use;

		RSObject[] RuneEssence = Objects.findNearest(30, "Rune Essence","Pure Essence");
		
		if(EssenceMiner.AntiBan.BOOL_TRACKER.USE_CLOSEST.next() || RuneEssence.length < 2){
			
			Use = 0;
			
		}else{Use = 0;}
			
		if(RuneEssence[Use].isOnScreen()){
			
			DynamicClicking.clickRSModel(RuneEssence[Use].getModel(), "Mine");
			
		}else{
				
			//double check condition worked
			if(Walking.blindWalkTo(RuneEssence[Use].getPosition())){
				
				Camera.setCameraRotation(Camera.getTileAngle(RuneEssence[Use].getPosition()) - General.random(-30, 30));
				General.sleep(100, 260);
				DynamicClicking.clickRSModel(RuneEssence[Use].getModel(), "Mine");
				EssenceMiner.MainMiner.Essence = RuneEssence[Use].getModel();
				
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
