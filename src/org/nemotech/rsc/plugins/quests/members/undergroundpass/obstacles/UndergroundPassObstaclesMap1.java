package org.nemotech.rsc.plugins.quests.members.undergroundpass.obstacles;

import static org.nemotech.rsc.plugins.Plugin.HITS;
import static org.nemotech.rsc.plugins.Plugin.doLedge;
import static org.nemotech.rsc.plugins.Plugin.doRock;
import static org.nemotech.rsc.plugins.Plugin.getCurrentLevel;
import static org.nemotech.rsc.plugins.Plugin.getNearestNpc;
import static org.nemotech.rsc.plugins.Plugin.inArray;
import static org.nemotech.rsc.plugins.Plugin.message;
import static org.nemotech.rsc.plugins.Plugin.playerTalk;
import static org.nemotech.rsc.plugins.Plugin.showMenu;
import static org.nemotech.rsc.plugins.Plugin.sleep;
import org.nemotech.rsc.plugins.Plugin;
import org.nemotech.rsc.model.Point;
import org.nemotech.rsc.model.GameObject;
import org.nemotech.rsc.model.NPC;
import org.nemotech.rsc.model.player.Player;
import org.nemotech.rsc.model.World;

import org.nemotech.rsc.plugins.listeners.action.ObjectActionListener;
import org.nemotech.rsc.plugins.listeners.executive.ObjectActionExecutiveListener;
import org.nemotech.rsc.plugins.quests.members.undergroundpass.npcs.UndergroundPassKoftik;

public class UndergroundPassObstaclesMap1 implements ObjectActionListener, ObjectActionExecutiveListener {

    /** Quest Objects **/
    public static int UNDERGROUND_CAVE = 725;
    public static int CRUMBLED_ROCK = 728;
    public static int FIRST_SWAMP = 754;
    public static int PILE_OF_MUD_MAP_LEVEL_1 = 767;
    public static int LEVER = 733;
    public static int BLESSED_SPIDER_SWAMP_OBJ = 795;
    public static int CLEAR_ROCKS = 772;
    public static int DROP_DOWN_LEDGE = 812;

    /** Main floor of the cave rocks **/
    public static int[] MAIN_ROCKS = { 731, 737, 738, 739, 740, 741, 742, 743, 744, 745, 746, 747, 748, 749 };

    /** Main floor ledges **/
    public static int[] MAIN_LEDGE = { 732, 750, 751, 752, 753 };

    /** From failing the swamp on main floor - rock riddle to get back to main floor. **/
    public static int[] FAIL_SWAMP_ROCKS = { 756, 757, 758, 759, 760, 762, 763, 764, 765, 766 };

    /** Read rocks **/
    public static int[] READ_ROCKS = { 832, 833, 834, 835, 923, 922, 881 };

    /** Spear rocks **/
    public static int[] SPEAR_ROCKS = { 806, 807, 808, 809, 810, 811, 882, 883 };

    @Override
    public boolean blockObjectAction(GameObject obj, String cmd, Player p) {
        if(obj.getID() == UNDERGROUND_CAVE || obj.getID() == CRUMBLED_ROCK) {
            return true;
        }
        if(inArray(obj.getID(), READ_ROCKS)) {
            return true;
        }
        if(inArray(obj.getID(), MAIN_ROCKS)) {
            return true;
        }
        if(inArray(obj.getID(), MAIN_LEDGE)) {
            return true;
        }
        if(obj.getID() == FIRST_SWAMP) {
            return true;
        }
        if(inArray(obj.getID(), FAIL_SWAMP_ROCKS)) {
            return true;
        }
        if(obj.getID() == PILE_OF_MUD_MAP_LEVEL_1) {
            return true;
        }
        if(obj.getID() == LEVER) {
            return true;
        }
        if(obj.getID() == BLESSED_SPIDER_SWAMP_OBJ) {
            return true;
        }
        if(obj.getID() == CLEAR_ROCKS) {
            return true;
        }
        if(obj.getID() == DROP_DOWN_LEDGE) {
            return true;
        }
        if(inArray(obj.getID(), SPEAR_ROCKS)) {
            return true;
        }
        return false;
    }

    @Override
    public void onObjectAction(GameObject obj, String cmd, Player p) {
        if(obj.getID() == UNDERGROUND_CAVE) {
            switch(p.getQuestStage(Plugin.UNDERGROUND_PASS)) {
            case 0:
                p.message("You must first complete the biohazard quest...");
                p.message("...before you can enter");
                break;
            case 1:
                NPC koftik = getNearestNpc(p, UndergroundPassKoftik.KOFTIK, 10);
                if(koftik != null) {
                    UndergroundPassKoftik.koftikEnterCaveDialogue(p, koftik);
                }
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case -1:
                message(p, "you cautiously enter the cave");
                p.teleport(673, 3420);
                break;
            }
        }
        if(obj.getID() == CRUMBLED_ROCK) {
            message(p, "you climb the rock pile");
            p.teleport(713, 581);
        }
        if(inArray(obj.getID(), READ_ROCKS)) {
            message(p, "the writing seems to have been scracthed...",
                    "..into the rock with bare hands, it reads..");
            if(obj.getID() == 832) {
                p.getSender().sendAlert("@dre@All those who thirst for knowledge%@dre@Bow down to the lord.% %@dre@All you that crave eternal life%@dre@Come and meet your God.% %@dre@For no man nor beast can cast a spell%@dre@Against the wake of eternal hell.", true); 
            } else if(obj.getID() == 833) {
                p.getSender().sendAlert("@dre@Most men do live in fear of death%@dre@That it might steal their soul.% %@dre@Some work and pray to shield their life%@dre@From the ravages of the cold.% %@dre@But only those who embrace the end%@dre@Can truly make their life extend.% %@dre@And when all hope begins to fade% %@dre@look above and use nature as your aid", true); 
            } else if(obj.getID() == 834) {
                p.getSender().sendAlert("@dre@And now our God has given us%@dre@One who is from our own.% %@dre@A saviour who once sat upon%@dre@His father's glorious thrown.% %@dre@It is in your name that we will lead the attack Iban%@dre@son of Zamorak!", true); 
            } else if(obj.getID() == 835) {
                p.getSender().sendAlert("@dre@Here lies the sacred font%@dre@Where the great Iban will bless all his disciples%@dre@in the name of evil.% %@dre@Here the forces of darkness are so concentrated they rise%@dre@when they detect any positive force close by", true); 
            } else if(obj.getID() == 923) {
                p.getSender().sendAlert("@dre@Ibans Shadow% %@dre@Then came the hard part: recreating the parts of a man%@dre@that cannot be seen or touched: those intangible things%@dre@that are life itself. Using all the mystical force that I could%@dre@muster, I performed the ancient ritual of Incantia, a spell%@dre@so powerful that it nearly stole the life from my frail and%@dre@withered body. Opening my eyes again, I saw the three%@dre@demons that had been summoned. Standing in a triangle,%@dre@their energy was focused on the doll. These demons%@dre@would be the keepers of Iban's shadow. Black as night,%@dre@their shared spirit would follow his undead body like an%@dre@angel of death.", true); 
            } else if(obj.getID() == 922) {
                p.getSender().sendAlert("% %@dre@Crumbling some of the dove's bones onto the doll, I cast%@dre@my mind's eye onto Iban's body. My ritual was complete,%@dre@soon he would be coming to life. I, Kardia, had resurrected%@dre@the legendary Iban, the most powerful evil being ever to%@dre@take human form. And I alone knew that the same%@dre@process that I had used to create him, was also capable%@dre@of destroying him.% %@dre@But now I was exhausted. As I closed my eyes to sleep, I%@dre@was settled by a strange feeling of contentment%@dre@anticipation of the evil that Iban would soon unleash.", true); 
            } else if(obj.getID() == 881) {
                p.getSender().sendAlert("@dre@Leave this battered corpse be% %@dre@For now he lives as spirit alone% %@dre@Let his flesh rest and become one with the earth% %@dre@As it is the soil that shall rise to protect him% %@dre@Only as flesh becomes dust, as wood becomes ash...% %@dre@..will Iban's corpse embrace nature and finally rest", true); 
            }
        }
        if(inArray(obj.getID(), MAIN_ROCKS)) {
            doRock(obj, p, (int)(getCurrentLevel(p, HITS) / 42) + 1, true, -1);
        }
        if(obj.getID() == FIRST_SWAMP) {
            message(p, "you try to cross but you're unable to",
                    "the swamp seems to cling to your legs");
            p.message("you slowly feel yourself being dragged below");
            playerTalk(p, null, "gulp!");
            p.teleport(674, 3462);
            playerTalk(p, null, "aargh");
            p.damage((int)(getCurrentLevel(p, HITS) / 42) + 1);
            sleep(2000);
            p.teleport(677, 3462);
            sleep(650);
            p.teleport(680, 3465);
            sleep(650);
            p.teleport(682, 3462);
            sleep(650);
            p.teleport(683, 3465);
            sleep(650);
            p.teleport(685, 3464);
            sleep(650);
            p.teleport(687, 3462);
            sleep(650);
            playerTalk(p,null, "aargh");
            p.damage((int)(getCurrentLevel(p, HITS) / 42) + 1);
            p.teleport(690, 3461);
            message(p, "you tumble deep into the cravass",
                    "and land battered and bruised at the base");
        }
        if(inArray(obj.getID(), FAIL_SWAMP_ROCKS)) {
            doRock(obj, p, (int)(getCurrentLevel(p, HITS) / 42) + 1, true, -1);
        }
        if(obj.getID() == PILE_OF_MUD_MAP_LEVEL_1) {
            message(p, "you climb up the mud pile");
            p.teleport(685, 3420);
            message(p, "it leads into darkness, the stench is almost unbearable",
                    "you surface by the swamp, covered in muck");
        }
        if(inArray(obj.getID(), MAIN_LEDGE)) {
            doLedge(obj, p, (int)(getCurrentLevel(p, HITS) / 42) + 1);
        }
        if(obj.getID() == LEVER) {
            message(p, "you pull back on the old lever",
                    "the bridge slowly lowers");
            GameObject bridge_open = new GameObject(Point.getLocation(704, 3417), 727, 2, 0);
            GameObject bridge_closed = new GameObject(Point.getLocation(704, 3417), 726, 2, 0);
            World.getWorld().registerGameObject(bridge_open);
            World.getWorld().delayedSpawnObject(bridge_closed.getLoc(), 10000);
            p.teleport(709, 3420);
            sleep(650);
            p.teleport(706, 3420);
            sleep(650);
            p.teleport(703, 3420);
            p.message("you cross the bridge");
        }
        if(obj.getID() == BLESSED_SPIDER_SWAMP_OBJ) {
            message(p, "you step in rancid swamp",
                    "it clings to your feet, you cannot cross");
        }
        if(obj.getID() == CLEAR_ROCKS) {
            if(p.getX() == 695 && (p.getY() == 3436 || p.getY() == 3435)) {
                p.teleport(695, 3435);
                return;
            }
            message(p, "you move the rocks from your path");
            p.message("you hear a strange mechanical sound");
            World.getWorld().replaceGameObject(obj, 
                    new GameObject(obj.getLocation(), CLEAR_ROCKS + 1, obj.getDirection(), obj
                            .getType()));
            World.getWorld().delayedSpawnObject(obj.getLoc(), 3000);
            p.damage((int) (getCurrentLevel(p, HITS) * 0.2D));
            playerTalk(p, null, "aaarrghhh");
            message(p, "You've triggered a trap");
        }
        if(inArray(obj.getID(), SPEAR_ROCKS)) {
            if(cmd.equalsIgnoreCase("step over")) {
                message(p, "you step over the rock");
                p.message("you feel a thread tug at your boot");
                p.message("it's a trap");
                p.teleport(obj.getX(), obj.getY());
                World.getWorld().replaceGameObject(obj, 
                        new GameObject(obj.getLocation(), 805, obj.getDirection(), obj
                                .getType()));
                World.getWorld().delayedSpawnObject(obj.getLoc(), 5000);
                p.damage((int)(getCurrentLevel(p, HITS) / 6) + 1);
                playerTalk(p,null, "aaarghh");
            } else {
                message(p, "you search the rock",
                        "you find a trip wire");
                p.message("do you wish to disarm the trap?");
                int menu = showMenu(p,"yes, i'll have a go","no chance");
                if(menu == 0) {
                    message(p, "you carefully try and diconnect the trip wire");
                    p.message("you manage to delay the trap..");
                    p.message("...long enough to cross the rocks");
                    if(obj.getX() == p.getX() + 1)
                        p.teleport(obj.getX() + 1, obj.getY());
                    else
                        p.teleport(obj.getX() - 1, obj.getY());
                } else if(menu == 1) {
                    p.message("you back away from the trap");
                }
            }
        }
        if(obj.getID() == DROP_DOWN_LEDGE) {
            p.message("you drop down to the cave floor");
            p.teleport(706, 3439);
        }
    }
}
