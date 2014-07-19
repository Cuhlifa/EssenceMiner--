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
import org.tribot.api2007.Projection;
import org.tribot.api2007.Skills;
import org.tribot.api2007.Login.STATE;
import org.tribot.api2007.Skills.SKILLS;
import org.tribot.api2007.types.RSModel;
import org.tribot.api2007.types.RSTile;
import org.tribot.script.Script;
import org.tribot.script.ScriptManifest;
import org.tribot.script.interfaces.Painting;

import scripts.EssenceMinerExtras.Node;
import scripts.Nodes.AntiBan;
import scripts.Nodes.Bank;
import scripts.Nodes.LeaveEssence;
import scripts.Nodes.Mine;
import scripts.Nodes.Varibles;
import scripts.Nodes.WalkToAubury;

@ScriptManifest(authors = { "Peticca10" }, category = "Mining", name = "EssenceMiner++")
public class EssenceMiner extends Script implements Painting {

	public static ABCUtil AntiBan = new ABCUtil();
	public static EssenceMiner MainMiner;
	public int CurrentLevel;
	public int CurrentXP;
	public RSModel Essence = null;
	public int GainedLevel;
	public int GainedXP;
	public int GainedXPHour = 0;
	public long hours;
	public int InventoryCount;
	public int MinedOres = 0;
	public int MinedOresHour = 0;
	public long minutes;
	public ArrayList<Node> Nodes = new ArrayList<>();
	public Image Overlay;
	public RSTile[] path;
	public final int[] PICKAXES = { 1265, 1267, 1269, 1271, 1273, 1275 };
	public int Profit = 0;
	public int ProfitHour = 0;
	public long RunTime;
	public String ScriptState = "Idle";
	public long seconds;
	public int StartingLevel;
	public int StartingXP;
	public long StartTime;

	public boolean isInMine() {
		System.out.println("Checking is in mine");
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

		// Node loop
		while (true) {

			if (Login.getLoginState() == STATE.INGAME) {

				System.out.println("Checking Nodes");
				for (Node node : Nodes) {

					if (node.validate()) {
						node.execute();
					}

				}

			}

			General.sleep(1978, 3467);

		}

	}

	@Override
	public void onPaint(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		Mouse.setSpeed(General.random(110, 140));

		// draw overlay
		if (Login.getLoginState() == STATE.INGAME && Overlay != null) {

			g.drawImage(Overlay, 8, 306, null);

		}

		if (path != null && path.length > 0) {

			for (RSTile tile : path) {

				if (tile.isOnScreen()) {
					g2d.draw(Projection.getTileBoundsPoly(tile, 0));
				}

			}

		}

		if (Essence != null) {

			for (Polygon p : Essence.getTriangles()) {

				g2d.setColor(Color.RED);
				g2d.draw(p);

			}

		}

		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", Font.BOLD, 12));
		g.drawString(MinedOres + " (" + MinedOresHour + ")", 85, 385);
		g.drawString(GainedXP + " (" + GainedXPHour + ")", 307, 385);

		g.drawString(Profit + " (" + ProfitHour + ")", 85, 438);
		g.drawString(CurrentLevel + " (" + GainedLevel + ")", 307, 438);

	}

	@Override
	public void run() {

		if (Login.getLoginState() == STATE.INGAME) {

			System.out.println("Starting Script");

			// Init variables
			MainMiner = this;
			General.useAntiBanCompliance(true);
			StartingLevel = SKILLS.MINING.getActualLevel();
			StartingXP = Skills.getXP(SKILLS.MINING);
			StartTime = System.currentTimeMillis();
			Nodes.add(new Bank());
			Nodes.add(new LeaveEssence());
			Nodes.add(new Mine());
			Nodes.add(new WalkToAubury());
			Nodes.add(new AntiBan());
			Nodes.add(new Varibles());
			InventoryCount = Inventory.getCount("Rune Essence");
			try {
				Overlay = ImageIO.read(new URL("http://imgur.com/54WwU6p.png"));
			} catch (Exception e) {
			}
			Loop();

		} else {

			sleep(2000);
			run();

		}

	}

}
