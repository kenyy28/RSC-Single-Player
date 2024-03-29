package org.nemotech.rsc.plugins.misc;

import static org.nemotech.rsc.plugins.Plugin.*;

import org.nemotech.rsc.util.Util;
import org.nemotech.rsc.model.player.InvItem;
import org.nemotech.rsc.model.player.Player;
import org.nemotech.rsc.plugins.listeners.action.InvActionListener;
import org.nemotech.rsc.plugins.listeners.executive.InvActionExecutiveListener;

public class Casket implements InvActionListener, InvActionExecutiveListener {

    public static final int CASKET = 549;
    public static final int COINS = 10;
    public static final int UNCUT_SAPPHIRE = 160;
    public static final int UNCUT_EMERALD = 159;
    public static final int UNCUT_RUBY = 158;
    public static final int UNCUT_DIAMOND = 157;
    public static final int TOOTH_KEY_HALF = 526;
    public static final int LOOP_KEY_HALF = 527;

    @Override
    public boolean blockInvAction(InvItem item, Player p) {
        if(item.getID() == CASKET) {
            return true;
        }
        return false;
    }

    @Override
    public void onInvAction(InvItem item, Player p) {
        if(item.getID() == CASKET) {
            p.setBusyTimer(1300);

            int randomChanceOpen = Util.random(0, 1081);

            message(p, 1300, "you open the casket");
            p.message("you find some treasure inside!");

            removeItem(p, CASKET, 1);

            // Coins, 54.11% chance
            if(randomChanceOpen <= 585) {
                // Randomly gives different coin amounts
                int randomChanceCoin = Util.random(0, 6);
                if(randomChanceCoin == 0) {
                    addItem(p, COINS, 10);
                }
                else if(randomChanceCoin == 1) {
                    addItem(p, COINS, 20);
                }
                else if(randomChanceCoin == 2) {
                    addItem(p, COINS, 40);
                }
                else if(randomChanceCoin == 3) {
                    addItem(p, COINS, 80);
                }
                else if(randomChanceCoin == 4) {
                    addItem(p, COINS, 160);
                }
                else if(randomChanceCoin == 5) {
                    addItem(p, COINS, 320);
                }
                else {
                    addItem(p, COINS, 640);
                }
            }
            // Uncut sapphire, 25.34% chance
            else if(randomChanceOpen > 585 && randomChanceOpen <= 859) {
                addItem(p, UNCUT_SAPPHIRE, 1);
            }
            // Uncut emerald, 12.11% chance
            else if(randomChanceOpen > 859 && randomChanceOpen <= 990) {
                addItem(p, UNCUT_EMERALD, 1);
            }
            //Uncut ruby, 5.27% chance
            else if(randomChanceOpen > 990 && randomChanceOpen <= 1047) {
                addItem(p, UNCUT_RUBY, 1);
            }
            // Uncut diamond, 1.57% chance
            else if(randomChanceOpen > 1047 && randomChanceOpen <= 1064) {
                addItem(p, UNCUT_DIAMOND, 1);
            }
            // Tooth halves, 1.56% chance
            else {
                // Randomly give one part or the other
                int randomChanceKey = Util.random(0, 1);
                if(randomChanceKey == 0) {
                    addItem(p, TOOTH_KEY_HALF, 1);
                }
                else {
                    addItem(p, LOOP_KEY_HALF, 1);
                }
            }
        } 
    }
}