package org.nemotech.rsc.plugins.misc;

import org.nemotech.rsc.event.SingleEvent;
import org.nemotech.rsc.event.impl.BatchEvent;
import static org.nemotech.rsc.plugins.Plugin.message;
import static org.nemotech.rsc.plugins.Plugin.showMenu;
import static org.nemotech.rsc.plugins.Plugin.sleep;
import org.nemotech.rsc.model.player.InvItem;
import org.nemotech.rsc.model.GameObject;
import org.nemotech.rsc.model.player.Player;
import org.nemotech.rsc.plugins.Plugin;
import org.nemotech.rsc.plugins.listeners.action.InvActionListener;
import org.nemotech.rsc.plugins.listeners.action.InvUseOnObjectListener;
import org.nemotech.rsc.plugins.listeners.executive.InvActionExecutiveListener;
import org.nemotech.rsc.plugins.listeners.executive.InvUseOnObjectExecutiveListener;
import org.nemotech.rsc.util.Formulae;

public class DragonstoneAmulet extends Plugin implements InvActionListener, InvActionExecutiveListener, InvUseOnObjectListener, InvUseOnObjectExecutiveListener {

    /** RUB **/
    public static int CHARGED_DRAGONSTONE_AMULET = 597;
    /** RE-CHARGE AMULET **/
    public static int DRAGONSTONE_AMULET = 522;
    public static int FOUNTAIN_OF_HEROES = 282;

    @Override
    public boolean blockInvAction(InvItem item, Player p) {
        if(item.getID() == CHARGED_DRAGONSTONE_AMULET) {
            return true;
        }
        return false;
    }

    @Override
    public void onInvAction(InvItem item, Player p) {
        if(item.getID() == CHARGED_DRAGONSTONE_AMULET) {
            p.message("You rub the amulet");
            sleep(600);
            p.message("Where would you like to teleport to?");
            int menu = showMenu(p, "Edgeville", "Karamja", "Draynor village", "Al Kharid", "Seers", "Yanille", "Nowhere");
            if(p.getLocation().inWilderness() && System.currentTimeMillis() - p.getCombatTimer() < 10000) {
                p.message("You need to stay out of combat for 10 seconds before using a teleport.");
                return;
            }
            if (p.getLocation().wildernessLevel() >= 30 || (p.getLocation().inModRoom() && !p.isAdmin())) {
                p.message("A mysterious force blocks your teleport!");
                p.message("You can't use this teleport after level 30 wilderness");
                return;
            }
            if(p.getInventory().countId(1039) > 0) {
                p.message("You can't teleport with ana in the barrel in your inventory.");
                return;
            }
            if(p.getInventory().hasItemId(812)) {
                p.message("the plague sample is too delicate...");
                p.message("it disintegrates in the crossing");
                while(p.getInventory().countId(812) > 0) {
                    p.getInventory().remove(new InvItem(812));
                }
            }
            if(menu != -1) {
                if(menu == 0) { // Edgeville
                    p.teleport(226, 447, true);
                } else if(menu == 1) { // Karamja
                    p.teleport(360, 696, true);
                } else if(menu == 2) { // Draynor Village
                    p.teleport(214, 632, true);
                } else if(menu == 3) { // Al Kharid
                    p.teleport(72, 696, true);
                } else if(menu == 4) { // Seers (ADDON - Does not exist in real RSC).
                    p.teleport(516, 460, true);     
                } else if(menu == 5) { // Yanille (ADDON - Does not exist in real RSC). 
                    p.teleport(587, 761, true);     
                } else if(menu == 6) { // nothing
                    p.message("Nothing interesting happens");
                    return;
                }
                if(!p.getCache().hasKey("charged_ds_amulet")) {
                    p.getCache().set("charged_ds_amulet", 1);   
                } else {
                    int rubs = p.getCache().getInt("charged_ds_amulet");    
                    if(rubs >= 3) {
                        p.getInventory().replace(597, 522);
                        p.getCache().remove("charged_ds_amulet");
                    } else {
                        p.getCache().put("charged_ds_amulet", rubs + 1);
                    }
                }
            }
        }
    }

    @Override
    public boolean blockInvUseOnObject(GameObject obj, InvItem item, Player p) {
        if(obj.getID() == FOUNTAIN_OF_HEROES && item.getID() == DRAGONSTONE_AMULET) {
            return true;
        }
        return false;
    }

    @Override
    public void onInvUseOnObject(GameObject obj, final InvItem item, Player p) {
        if(obj.getID() == FOUNTAIN_OF_HEROES && item.getID() == DRAGONSTONE_AMULET) {
            p.setBusy(true);
            p.message("You dip the amulet in the fountain");
            sleep(1000);
            p.setBatchEvent(new BatchEvent(p, 650, Formulae.getRepeatTimes(p, CRAFTING)) {
                @Override
                public void action() {
                    if (!owner.getInventory().hasItemId(item.getID())) {
                        interrupt();
                        return;
                    }
                    if (owner.getInventory().remove(item) > -1) {
                        owner.getInventory().add(new InvItem(597));
                    } else
                        interrupt();
                }
            });
            message(p, "You feel more power emanating from it than before",
                    "you can now rub this amulet to teleport",
                    "Though using it to much means you will need to recharge it");
            p.message("It now also means you can find more gems when mining");
            p.setBusy(false);
        }
    }
}