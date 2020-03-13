package aldrigos.mc.worldedit.commands;

import aldrigos.mc.worldedit.*;
import cn.nukkit.Player;
import cn.nukkit.command.*;
import cn.nukkit.item.*;
import cn.nukkit.nbt.tag.CompoundTag;

public class WandCmd extends Command {
    public WandCmd(){
        super("/wand");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if(!sender.isPlayer()){
            Messages.PLAYERCMD.send(sender);
            return false;
        }

        if(!sender.hasPermission("we.wand")){
            Messages.NO_PERM.send(sender, "we.wand");
            return false;
        }

        Player player = (Player)sender;
        Item wand = new Item(ItemID.WOODEN_AXE);
        wand.setCustomName("WorldEdit Wand");
        CompoundTag c = new CompoundTag();
        c.putBoolean("wand", true);
        wand.setCustomBlockData(c);
        player.getInventory().addItem(wand);

        return true;
    }
}
