package org.nemotech.rsc.plugins.npcs.shilo;

import static org.nemotech.rsc.plugins.Plugin.*;
import org.nemotech.rsc.model.NPC;
import org.nemotech.rsc.model.player.Player;
import org.nemotech.rsc.plugins.listeners.action.TalkToNpcListener;
import org.nemotech.rsc.plugins.listeners.executive.TalkToNpcExecutiveListener;

public class Serevel implements TalkToNpcExecutiveListener, TalkToNpcListener {

    public static final int SEREVEL = 623;

    @Override
    public void onTalkToNpc(Player p, NPC n) {
        if(n.getID() == SEREVEL) {
            playerTalk(p, n, "Hello");
            npcTalk(p, n, "Hello Bwana.",
                    "Are you interested in buying a ticket for the 'Lady of the Waves'?",
                    "It's a ship that can take you to either Port Sarim or Khazard Port",
                    "The ship lies west of Shilo Village and south of Cairn Island.",
                    "The tickets cost 100 Gold Pieces.",
                    "Would you like to purchase a ticket Bwana?");
            int menu = showMenu(p, n,
                    "Yes, that sounds great!",
                    "No thanks.");
            if(menu == 0) {
                if(hasItem(p, 10, 100)) {
                    removeItem(p, 10, 100);
                    npcTalk(p, n, "Great, nice doing business with you.");
                    addItem(p, 988, 1);
                } else {
                    npcTalk(p, n, "Sorry Bwana, you don't have enough money.",
                            "Come back when you have 100 Gold Pieces.");
                }
            } else if(menu == 1) {
                npcTalk(p, n, "Fair enough Bwana, let me know if you change your mind.");
            }
        }
    }

    @Override
    public boolean blockTalkToNpc(Player p, NPC n) {
        if(n.getID() == SEREVEL) {
            return true;
        }
        return false;
    }

}
