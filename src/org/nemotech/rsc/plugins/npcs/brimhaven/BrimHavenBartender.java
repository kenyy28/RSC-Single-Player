package org.nemotech.rsc.plugins.npcs.brimhaven;

import static org.nemotech.rsc.plugins.Plugin.addItem;
import static org.nemotech.rsc.plugins.Plugin.hasItem;
import static org.nemotech.rsc.plugins.Plugin.message;
import static org.nemotech.rsc.plugins.Plugin.npcTalk;
import static org.nemotech.rsc.plugins.Plugin.playerTalk;
import static org.nemotech.rsc.plugins.Plugin.showMenu;
import org.nemotech.rsc.model.NPC;
import org.nemotech.rsc.model.player.Player;
import org.nemotech.rsc.plugins.listeners.action.TalkToNpcListener;
import org.nemotech.rsc.plugins.listeners.executive.TalkToNpcExecutiveListener;

public final class BrimHavenBartender implements TalkToNpcExecutiveListener,
        TalkToNpcListener {

    @Override
    public void onTalkToNpc(Player p, NPC n) {
        if (n.getID() == 279) {
            npcTalk(p, n, "Yohoho me hearty what would you like to drink?");
            String[] options;
            if (p.getCache().hasKey("barcrawl")
                    && !p.getCache().hasKey("barfour")) {
                options = new String[] { "Nothing thankyou",
                        "A pint of Grog please", "A bottle of rum please",
                        "I'm doing Alfred Grimhand's barcrawl" };
            } else {
                options = new String[] { "Nothing thankyou",
                        "A pint of Grog please", "A bottle of rum please" };
            }
            int firstMenu = showMenu(p, n, options);
            if (firstMenu == 0) {// NOTHING
            } else if (firstMenu == 1) {
                npcTalk(p, n, "One grog coming right up", "That'll be 3 gold");
                if (hasItem(p, 10, 3)) {
                    p.message("You buy a pint of Grog");
                    p.getInventory().remove(10, 3);
                    addItem(p, 598, 1);
                } else {
                    playerTalk(p, n,
                            "Oh dear. I don't seem to have enough money");
                }
            } else if (firstMenu == 2) {
                npcTalk(p, n, "That'll be 27 gold");
                if (hasItem(p, 10, 27)) {
                    p.message("You buy a bottle of rum");
                    p.getInventory().remove(10, 27);
                    addItem(p, 318, 1);
                } else {
                    playerTalk(p, n,
                            "Oh dear. I don't seem to have enough money");
                }
            } else if (firstMenu == 3) {
                npcTalk(p, n, "Haha time to be breaking out the old supergrog",
                        "That'll be 15 coins please");
                if (hasItem(p, 10, 15)) {
                    message(p,
                            "The bartender serves you a glass of strange thick dark liquid",
                            "You wince and drink it", "You stagger backwards",
                            "You think you see 2 bartenders signing 2 barcrawl cards");
                    p.getCache().store("barfour", true);
                } else {
                    playerTalk(p, n, "I don't have 15 coins right now");
                }
            }
        }
    }

    @Override
    public boolean blockTalkToNpc(Player p, NPC n) {
        if (n.getID() == 279) {
            return true;
        }
        return false;
    }
}
