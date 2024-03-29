package org.nemotech.rsc.plugins.npcs.ardougne.east;

import static org.nemotech.rsc.plugins.Plugin.npcTalk;
import static org.nemotech.rsc.plugins.Plugin.showMenu;
import org.nemotech.rsc.model.Shop;
import org.nemotech.rsc.model.player.InvItem;
import org.nemotech.rsc.model.NPC;
import org.nemotech.rsc.model.player.Player;

import org.nemotech.rsc.plugins.ShopInterface;
import org.nemotech.rsc.plugins.listeners.action.TalkToNpcListener;
import org.nemotech.rsc.plugins.listeners.executive.TalkToNpcExecutiveListener;

public class BakerMerchant implements ShopInterface, TalkToNpcExecutiveListener, TalkToNpcListener {

    private final Shop shop = new Shop("Bakers Stall", false, 15000, 100, 80,2, new InvItem(138, 10), new InvItem(330, 3), new InvItem(336, 8));

    @Override
    public void onTalkToNpc(Player p, NPC n) {
        npcTalk(p, n, "Good day " + (p.isMale() ? "Sir" : "Madame"),
                "Would you like ze nice freshly baked bread",
                "Or perhaps a nice piece of cake");
        int menu = showMenu(p, n, "Lets see what you have", "No thankyou");
        if(menu == 0) {
            org.nemotech.rsc.client.action.ActionManager.get(org.nemotech.rsc.client.action.impl.ShopHandler.class).handleShopOpen(shop);
        } 
    }

    // WHEN STEALING AND CAUGHT BY A MERCHANT ("Hey thats mine");
    // Delay player busy (3000); after stealing and Npc shout out to you.

    @Override
    public boolean blockTalkToNpc(Player p, NPC n) {
        return n.getID() == 325;
    }

    @Override
    public Shop[] getShops() {
        return new Shop[] { shop };
    }

    @Override
    public boolean isMembers() {
        return true;
    }
}
