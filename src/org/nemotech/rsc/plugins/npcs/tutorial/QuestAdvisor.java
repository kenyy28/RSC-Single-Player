package org.nemotech.rsc.plugins.npcs.tutorial;

import static org.nemotech.rsc.plugins.Plugin.npcTalk;
import static org.nemotech.rsc.plugins.Plugin.playerTalk;
import static org.nemotech.rsc.plugins.Plugin.showMenu;
import org.nemotech.rsc.model.NPC;
import org.nemotech.rsc.model.player.Player;
import org.nemotech.rsc.plugins.listeners.action.TalkToNpcListener;
import org.nemotech.rsc.plugins.listeners.executive.TalkToNpcExecutiveListener;

public class QuestAdvisor implements TalkToNpcExecutiveListener, TalkToNpcListener {
    /**
     * @author Davve
     * Tutorial island quest advisor
     */

    @Override
    public void onTalkToNpc(Player p, NPC n) {
        npcTalk(p, n, "Greetings traveller",
                "If you're interested in a bit of adventure",
                "I can recommend going on a good quest",
                "There are many secrets to be uncovered",
                "And wrongs to be set right",
                "If you talk to the various characters in the game",
                "Some of them will give you quests");
        playerTalk(p, n, "What sort of quests are there to do?");
        npcTalk(p, n, "If you select the bar graph in the menu bar",
                "And then select the quests tabs",
                "You will see a list of quests",
                "quests you have completed will show up in green",
                "You can only do each quest once");
        int menu = showMenu(p, n, "Thank you for the advice", "Can you recommend any quests?");
        if(menu == 0) {
            npcTalk(p, n, "good questing traveller");
            if(p.getCache().hasKey("tutorial") && p.getCache().getInt("tutorial") != 65) {
                p.getCache().set("tutorial", 65);
            }
        } else if(menu == 1) {
            npcTalk(p, n, "Well I hear the cook in Lumbridge castle is having some problems",
                    "When you get to Lumbridge, go into the castle there",
                    "Find the cook and have a chat with him");
            playerTalk(p, n, "Okay thanks for the advice");
            if(p.getCache().hasKey("tutorial") && p.getCache().getInt("tutorial") != 65) {
                p.getCache().set("tutorial", 65);
            }
        }
        
    }

    @Override
    public boolean blockTalkToNpc(Player p, NPC n) {
        return n.getID() == 489;
    }

}
