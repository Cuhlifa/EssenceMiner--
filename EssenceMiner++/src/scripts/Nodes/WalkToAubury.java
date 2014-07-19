package scripts.Nodes;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.tribot.api.DynamicClicking;
import org.tribot.api.General;
import org.tribot.api2007.Camera;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.NPCs;
import org.tribot.api2007.PathFinding;
import org.tribot.api2007.Player;
import org.tribot.api2007.Walking;
import org.tribot.api2007.WebWalking;
import org.tribot.api2007.ext.Doors;
import org.tribot.api2007.types.RSNPC;
import org.tribot.api2007.types.RSTile;

import scripts.EssenceMiner;
import scripts.EssenceMinerExtras.Node;

public class WalkToAubury extends Node{

	public static RSTile AuburyTile = new RSTile(3253,3401);
	
	@Override
	public void execute() {
		
		EssenceMiner.MainMiner.ScriptState = "Walking To Aubury";
		WebWalking.walkTo(AuburyTile);
		RSNPC[] Aubury  = NPCs.find("Aubury");
		
		if(Aubury != null && Aubury.length > 0){
			
			if(PathFinding.canReach(Aubury[0].getPosition(), false)){
				
				if(Aubury[0].isOnScreen()){
					
					Camera.setCameraRotation(Camera.getTileAngle(Aubury[0].getPosition()) - General.random(-30, 30));
					General.sleep(100, 260);
					if(!EssenceMiner.MainMiner.isInMine() && Aubury[0].getModel() != null){DynamicClicking.clickRSModel(Aubury[0].getModel(), "Teleport");}
					
				}else{
					
					if(WebWalking.walkTo(AuburyTile)){
						
						Camera.setCameraRotation(Camera.getTileAngle(Aubury[0].getPosition()) - General.random(-30, 30));
						General.sleep(100, 260);
						if(!EssenceMiner.MainMiner.isInMine() && Aubury[0].getModel() != null){DynamicClicking.clickRSModel(Aubury[0].getModel(), "Teleport");}
						
					}
					
				}
				
			}else{
				
				RSTile[] path = PathFinding.generatePath(Player.getPosition(), AuburyTile, false);
				
				if(path != null && path.length> 0){
					
					for(RSTile tile : path){
						
						if(Doors.getDoorAt(tile) != null || Doors.isDoorAt(tile, false)){
							
							Doors.handleDoorAt(tile, true);
							
						}
						
					}
					
				}
				
				
			}
			
		}else{
			
			Walking.walkTo(AuburyTile);
			
		}
		
	}

	@Override
	public boolean validate() {
		
		if(!Inventory.isFull() && !EssenceMiner.MainMiner.isInMine()){
			
			return true;
			
		}else{return false;}
		
	}

}
