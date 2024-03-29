package org.nemotech.rsc.plugins.npcs.varrock;

import static org.nemotech.rsc.plugins.Plugin.addItem;
import static org.nemotech.rsc.plugins.Plugin.hasItem;
import static org.nemotech.rsc.plugins.Plugin.message;
import static org.nemotech.rsc.plugins.Plugin.npcTalk;
import static org.nemotech.rsc.plugins.Plugin.playerTalk;
import static org.nemotech.rsc.plugins.Plugin.removeItem;
import static org.nemotech.rsc.plugins.Plugin.showMenu;
import org.nemotech.rsc.model.NPC;
import org.nemotech.rsc.model.player.Player;
import org.nemotech.rsc.plugins.listeners.action.TalkToNpcListener;
import org.nemotech.rsc.plugins.listeners.executive.TalkToNpcExecutiveListener;

public class JollyBoarInnBartender implements TalkToNpcListener, TalkToNpcExecutiveListener {

    @Override
    public void onTalkToNpc(Player p, NPC n) {
        npcTalk(p, n, "Yes please?");
        String[] options = {};
        if (p.getCache().hasKey("barcrawl") && !p.getCache().hasKey("barone")) {
            options = new String[] {
                    "I'll have a beer please", 
                    "Any hints where I can go adventuring?", 
                    "Heard any good gossip?", 
                    "I'm doing Alfred Grimhand's barcrawl"
            };
        } else {
            options = new String[] {
                    "I'll have a beer please", 
                    "Any hints where I can go adventuring?", 
                    "Heard any good gossip?"
            };
        }
        int reply = showMenu(p, n, options);
        if (reply == 0) {
            npcTalk(p, n, "Ok, that'll be two coins");

            if (hasItem(p, 10, 2)) {
                p.message("You buy a pint of beer");
                removeItem(p, 10, 2);
                addItem(p, 193, 1);
            } else {
                playerTalk(p, n, "Oh dear, I don't seem to have enough money");
            }
        } else if(reply == 1) {
            npcTalk(p, n, 
                    "It's funny you should say that",
                    "An adventurer passed through here, the other day,",
                    "claiming to have found a dungeon full of treasure,",
                    "guarded by vicious skeletal warriors",
                    "He said he found the entrance in a ruined town",
                    "deep in the woods to the west of here, behind the palace",
                    "Now how much faith you put in that story is up to you,",
                    "but it probably wouldn't do any harm to have a look"
                    );
            playerTalk(p, n, "Thanks", "I may try that at some point");
        } else if(reply == 2) {
            npcTalk(p, n, 
                    "I'm that well up on the gossip out here",
                    "I've heard that the bartender in the Blue Moon Inn has gone a little crazy",
                    "He keeps claiming he is part of something called a computer game",
                    "What that means, I don't know",
                    "That's probably old news by now though"
                    );
        } else if(reply == 3) {
            npcTalk(p, n, "Ah, there seems to be a fair few doing that one these days",
                    "My supply of Olde Suspiciouse is starting to run low",
                    "It'll cost you 10 coins");
            if (hasItem(p, 10, 10)) {
                removeItem(p, 10, 10);
                message(p, "You buy a pint of Olde Suspiciouse",
                        "You gulp it down",
                        "Your head is spinning",
                        "The bartender signs your card");
                p.getCache().store("barone", true);
                playerTalk(p, n, "Thanksh very mush");
            } else {
                playerTalk(p, n, "I don't have 10 coins right now");
            }
        }
    }
    
    @Override
    public boolean blockTalkToNpc(Player p, NPC n) {
        return n.getID() == 44;
    }
}
