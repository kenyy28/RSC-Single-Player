package org.nemotech.rsc.plugins.skills;

import org.nemotech.rsc.plugins.Plugin;
import org.nemotech.rsc.event.SingleEvent;
import org.nemotech.rsc.util.Formulae;
import org.nemotech.rsc.external.EntityManager;
import org.nemotech.rsc.external.definition.extra.ItemSmithingDef;
import org.nemotech.rsc.event.impl.BatchEvent;
import org.nemotech.rsc.Constants;
import org.nemotech.rsc.client.sound.SoundEffect;
import org.nemotech.rsc.model.player.InvItem;
import org.nemotech.rsc.model.GameObject;
import org.nemotech.rsc.model.NPC;
import org.nemotech.rsc.model.player.Player;
import org.nemotech.rsc.model.ChatMessage;
import org.nemotech.rsc.model.World;
import org.nemotech.rsc.plugins.Plugin;
import static org.nemotech.rsc.plugins.Plugin.SMITHING;
import static org.nemotech.rsc.plugins.Plugin.message;
import static org.nemotech.rsc.plugins.Plugin.npcYell;
import static org.nemotech.rsc.plugins.Plugin.showBubble;
import static org.nemotech.rsc.plugins.Plugin.showMenu;
import org.nemotech.rsc.plugins.listeners.action.InvUseOnObjectListener;
import org.nemotech.rsc.plugins.listeners.executive.InvUseOnObjectExecutiveListener;

public class Smithing implements InvUseOnObjectListener, InvUseOnObjectExecutiveListener {

    @Override
    public boolean blockInvUseOnObject(GameObject obj, InvItem item, Player player) {
        return obj.getID() == 177 || obj.getID() == 50;
    }

    @Override
    public void onInvUseOnObject(GameObject obj, final InvItem item, final Player player) {
        int toMake = -1;
        switch (obj.getID()) {
        case 177: /* Fixed dorics anvil. */
            if (player.getQuestStage(3) > -1) {
                NPC doric = World.getWorld().getNpc(144, 323, 327, 487, 492, true);
                npcYell(player, doric, "Heh who said you could use that?");
                player.message("You need to finish Doric's quest to use this anvil");
                return;
            }
        case 50: // Anvil
            int minSmithingLevel = Formulae.minSmithingLevel(item.getID());
            if(item.getID() == 168) {
                player.message("To forge items use the metal you wish to work with the anvil");
                return;
            }
            if (minSmithingLevel < 0) {
                player.message("Nothing interesting happens");
                return;
            }
            if (player.getCurStat(13) < minSmithingLevel) {
                player.message("You need at least level "
                        + minSmithingLevel + " smithing to work with " + item.getDef().getName().toLowerCase().replaceAll("bar", ""));
                return;
            }
            if (player.getInventory().countId(168) < 1) {
                player.message("You need a hammer to work the metal with.");
                return;
            }
            /*if(item.getID() == 172 && player.getQuestStage(Constants.Quests.LEGENDS_QUEST) >= 0 && player.getQuestStage(Constants.Quests.LEGENDS_QUEST) <= 2) {
                player.message("You're not quite sure what to make from the gold..");
                return;
            }*/
            player.message("What would you like to make?");
            if(item.getID() == 172) {
                int goldOption = showMenu(player, "Golden bowl.", "Cancel");
                if(player.isBusy()) {
                    return;
                }
                switch (goldOption) {
                case 0:
                    message(player, "You hammer the metal...");
                    if(player.getInventory().countId(172) < 2) {
                        player.message("You need two bars of gold to make this item.");
                    } else {
                        if(Formulae.failCalculation(player, SMITHING, 50)) {
                            for (int x = 0; x < 2; x++) {
                                player.getInventory().remove(172, 1);
                            }
                            player.message("You forge a beautiful bowl made out of solid gold.");
                            player.getInventory().add(new InvItem(1188, 1));
                            player.incExp(13, 30, true);
                        } else {
                            player.message("You make a mistake forging the bowl..");
                            player.message("You pour molten gold all over the floor..");
                            player.getInventory().remove(172, 1);
                            player.incExp(13, 1, true);
                        }
                    }
                    break;
                }
                return;
            }
            int categoryOption;
            if (item.getID() == 171) {
                categoryOption = showMenu(player, "Make Weapon", "Make Armour",
                        "Make Missile Heads", "Make Steel Nails", "Cancel");
            } else if(item.getID() == 169) {
                categoryOption = showMenu(player, "Make Weapon", "Make Armour",
                        "Make Missile Heads", "Make Craft Item", "Cancel");
            } else {
                categoryOption = showMenu(player, "Make Weapon", "Make Armour",
                        "Make Missile Heads", "Cancel");
            }
            if(player.isBusy()) {
                return;
            }
            switch (categoryOption) {
            case 0: /* Weapon */
                player.message("Choose a type of weapon to make");
                int typeOption = showMenu(player, "Dagger", "Throwing Knife", "Sword",
                        "Axe", "Mace");
                if(player.isBusy()) {
                    return;
                }
                switch (typeOption) {
                case 0:
                    toMake = 0;
                    break;
                case 1:
                    if (!Constants.MEMBER_WORLD) {
                        player.message("This feature is members only");
                        break;
                    }
                    toMake = 1;
                    break;
                case 2:
                    player.message("What sort of sword do you want to make?");
                    int sortOption = showMenu(player,  "Short Sword",
                            "Long Sword (2 bars)", "Scimitar (2 bars)",
                            "2-handed Sword (3 bars)");
                    if(player.isBusy()) {
                        return;
                    }
                    switch (sortOption) {
                    case 0:
                        toMake = 2;
                        break;
                    case 1:
                        toMake = 3;
                        break;
                    case 2:
                        toMake = 4;
                        break;
                    case 3:
                        toMake = 5;
                        break;
                    }
                    break;
                case 3:
                    player.message("What sort of axe do you want to make?");
                    int sortOption2 = showMenu(player,  "Hatchet", "Battle Axe (3 bars)");
                    if(player.isBusy()) {
                        return;
                    }
                    switch (sortOption2) {
                    case 0:
                        toMake = 6;
                        break;
                    case 1:
                        toMake = 8;
                        break;
                    }
                    break;
                case 4:
                    toMake = 9;
                    break;
                }
                break;
            case 1: /* Armour */
                player.message("Choose a type of armour to make");
                int typeOption2 = showMenu(player,  "Helmet", "Shield", "Armour");
                if(player.isBusy()) {
                    return;
                }
                switch (typeOption2) {
                case 0: /* Helmet */
                    player.message("What sort of helmet do you want to make?");
                    int option = showMenu(player, "Medium Helmet",
                            "Large Helmet (2 bars)");
                    switch (option) {
                    case 0:
                        toMake = 10;
                        break;
                    case 1:
                        toMake = 11;
                        break;
                    }
                    break;
                case 1: /* Shield */
                    player.message("What sort of shield do you want to make?");
                    int sortShield = showMenu(player,  "Square Shield (2 bars)",
                            "Kite Shield (3 bars)");
                    if(player.isBusy()) {
                        return;
                    }
                    switch (sortShield) {
                    case 0:
                        toMake = 12;
                        break;
                    case 1:
                        toMake = 13;
                        break;
                    }
                    break;
                case 2: /* Armour */
                    player.message("What sort of armour do you want to make?");
                    int sortArmour = showMenu(player, "Chain Mail Body (3 bars)",
                            "Plate Mail Body (5 bars)",
                            "Plate Mail Legs (3 bars)", "Plated Skirt (3 bars)");
                    if(player.isBusy()) {
                        return;
                    }
                    switch (sortArmour) {
                    case 0:
                        toMake = 14;
                        break;
                    case 1:
                        toMake = 15;
                        break;
                    case 2:
                        toMake = 16;
                        break;
                    case 3:
                        toMake = 17;
                        break;
                    }
                    break;
                }
                break;
            case 2: /* Arrowtips */
                if (!Constants.MEMBER_WORLD) {
                    player.message("This feature is members only");
                    break;
                }
                int arrowOption;
                if(player.getQuestStage(Plugin.TOURIST_TRAP) >= 8 || player.getQuestStage(Plugin.TOURIST_TRAP) == -1) {
                    arrowOption = showMenu(player, "Make Arrow Heads.", "Forge Dart Tips.", "Cancel.");
                } else {
                    arrowOption = showMenu(player, "Make Arrow Heads.", "Cancel.");
                }
                if(player.isBusy()) {
                    return;
                }
                switch (arrowOption) {
                case 0:
                    toMake = 18;
                    break;
                case 1:
                    if(player.getQuestStage(Plugin.TOURIST_TRAP) >= 8 || player.getQuestStage(Plugin.TOURIST_TRAP) == -1) {
                        toMake = 20;
                    }
                    break;
                }
                break;
            case 3:
                if(item.getID() == 171) {
                    if (player.getCurStat(13) < 34) {
                        player.message("You need to be at least level 34 smithing to do that");
                        return;
                    }
                    if (player.getInventory().countId(171) < 1) {
                        player.message("You need 1 bar of metal to make this item");
                        return;
                    }
                    showBubble(player, item);
                    player.getInventory().remove(171, 1);
                    player.message("You hammer the metal and make some nails");
                    player.getInventory().add(new InvItem(419, 2));
                    player.incExp(13, Formulae.getSmithingExp(171, 1), true);
                }
                if(item.getID() == 169) {
                    player.message("What sort of craft item do you want to make?");
                    int bronzeWireOption = showMenu(player, "Bronze Wire(1 bar)", "Cancel");
                    if(player.isBusy()) {
                        return;
                    }
                    if (player.getInventory().countId(169) < 1) {
                        player.message("You need 1 bar of metal to make this item");
                        return;
                    }
                    switch (bronzeWireOption) {
                    case 0:
                        showBubble(player, item);
                        player.getInventory().remove(169, 1);
                        player.message("You hammer the Bronze Bar and make some bronze wire");
                        player.getInventory().add(new InvItem(979, 1));
                        player.incExp(13, Formulae.getSmithingExp(169, 1), true);
                        break;
                    }
                }
                break;
            }

            if(toMake == -1) {
                return;
            }

            final ItemSmithingDef def = EntityManager.getSmithingDef((Formulae
                    .getBarType(item.getID()) * 21) + toMake);
            if (def == null) {
                player.message("Nothing interesting happens");
                return;
            }
            if (player.getCurStat(13) < def.getRequiredLevel()) {
                player.message("You need to be at least level "
                        + def.getRequiredLevel() + " smithing to do that");
                return;
            }

            String[] options = {"Make 1",
                    "Make 5",
                    "Make 10",
            "Make All"};

            int makeCount = showMenu(player, options);

            if(makeCount == -1) {
                return;
            }

            int maximumMakeCount = player.getInventory().countId(item.getID()) / def.getRequiredBars();

            makeCount = makeCount != 3 ? Integer.parseInt(options[makeCount].replaceAll("Make ", "")) : maximumMakeCount;

            player.setBatchEvent(new BatchEvent(player, 650, makeCount) {
                @Override
                public void action() {
                    if (owner.getInventory().countId(item.getID()) < def.getRequiredBars()) {
                        owner.message("You need " + def.getRequiredBars() + " bars of metal to make this item");
                        interrupt();
                        return;
                    }
                    if (owner.getFatigue() >= 7500) {
                        owner.message("You are too tired to smith");
                        interrupt();
                        return;
                    }
                    owner.getSender().sendSound(SoundEffect.ANVIL);
                    for (int x = 0; x < def.getRequiredBars(); x++) {
                        owner.getInventory().remove(new InvItem(item.getID(), 1));
                    }
                    showBubble(owner, item);
                    if (EntityManager.getItem(def.getItemID()).isStackable()) {
                        owner.message("You hammer the metal and make " + def.getAmount() + " "
                                + EntityManager.getItem(def.getItemID()).getName().toLowerCase());
                        owner.getInventory().add(new InvItem(def.getItemID(), def.getAmount()));
                    } else {
                        owner.message("You hammer the metal and make a "
                                + EntityManager.getItem(def.getItemID()).getName().toLowerCase());
                        for (int x = 0; x < def.getAmount(); x++) {
                            owner.getInventory().add(new InvItem(def.getItemID(), 1));
                        }
                    }
                    owner.incExp(13,
                            Formulae.getSmithingExp(item.getID(), def.getRequiredBars()), true);
                    //System.out.println("Smithed xp : " + Formulae.getSmithingExp(item.getID(), def.getRequiredBars()));
                }
            });

            break;
        }
    }

}