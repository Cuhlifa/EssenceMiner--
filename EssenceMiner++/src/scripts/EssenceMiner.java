package scripts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import org.tribot.api.General;
import org.tribot.api.input.Mouse;
import org.tribot.api.util.ABCUtil;
import org.tribot.api2007.Inventory;
import org.tribot.api2007.Login;
import org.tribot.api2007.Objects;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import scripts.EssenceMinerUtils.Node;
import scripts.Nodes.*;

@ScriptManifest(authors = { "Peticca10" }, category = "Mining", name = "EssenceMiner++")
public class EssenceMiner extends Script implements Painting {

	public static ABCUtil antiBan = new ABCUtil();
	public static EssenceMiner mainMiner;
	public int currentLevel;
	public int currentXP;
	public RSModel essence = null;
	public int gainedLevel;
	public int gainedXP;
	public int gainedXPHour = 0;
	public long hours;
	public int inventoryCount;
	public int minedOres = 0;
	public int minedOresHour = 0;
	public long minutes;
	public ArrayList<Node> nodes = new ArrayList<>();
	public Image overlay;
	public RSTile[] path;
	public static final String[] PICKAXES = { "Bronze pickaxe", "Iron pickaxe",
			"Steel pickaxe", "Mithril pickaxe", "Adamant pickaxe",
			"Rune pickaxe", "Dragon pickaxe" };
	public int profit = 0;
	public int profitHour = 0;
	public long runTime;
	public String scriptState = "Idle";
	public long seconds;
	public int startingLevel;
	public int startingXP;
	public long startTime;

	public static boolean isInMine() {
		
		if ((Objects.find(17, 1441, 1440).length > 0)) {

			if (Objects.find(30, "Rune Essence", "Pure Essence").length > 0) {

				return true;

			} else {
				return false;
			}

		} else {
			return false;
		}

	}

	public void Loop() {

		while (true) {

			if (Login.getLoginState() == STATE.INGAME) {

				Mouse.setSpeed(General.random(110, 140));
				
				System.out.println("Checking Nodes");
				for (Node node : nodes) {

					if (node.validate()) {
						General.sleep(node.execute());
					}

				}

			}

			General.sleep(1978, 3467);

		}

	}

	@Override
	public void onPaint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		if (Login.getLoginState() == STATE.INGAME && overlay != null) {

			g.drawImage(overlay, 8, 306, null);

		if (essence != null) {

			for (Polygon p : essence.getTriangles()) {

				g2d.setColor(Color.RED);
				g2d.draw(p);

			}

		}

		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", Font.BOLD, 12));
		g.drawString("RunTime: " + hours + "H " + minutes + "M " + seconds
				+ "S", 15, 40);
		g.drawString(minedOres + " (" + minedOresHour + ")", 85, 385);
		g.drawString(gainedXP + " (" + gainedXPHour + ")", 307, 385);

		g.drawString(profit + " (" + profitHour + ")", 85, 438);
		g.drawString(currentLevel + " (" + gainedLevel + ")", 307, 438);
		
	}

	}

	@Override
	public void run() {

		if (Login.getLoginState() == STATE.INGAME) {

			System.out.println("Starting Script");

			// Init variables
			mainMiner = this;
			General.useAntiBanCompliance(true);
			startingLevel = SKILLS.MINING.getActualLevel();
			startingXP = Skills.getXP(SKILLS.MINING);
			startTime = System.currentTimeMillis();
			nodes.add(new Bank());
			nodes.add(new LeaveEssence());
			nodes.add(new Mine());
			nodes.add(new WalkToAubury());
			nodes.add(new AntiBan());
			nodes.add(new Varibles());
			inventoryCount = Inventory.getCount("Rune Essence");

			try {
				overlay = ImageIO.read(new URL("http://imgur.com/54WwU6p.png"));
			} catch (Exception e) {
			}
			Loop();

		} else {

			sleep(2000);
			run();

		}

	}

}
