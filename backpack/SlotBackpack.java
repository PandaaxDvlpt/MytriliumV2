package fr.pandaax.mytrilium.backpack;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotBackpack extends Slot
{
    public SlotBackpack(IInventory inv, int index, int x, int y)
    {
        super(inv, index, x, y);
    }


    @Override
    public boolean isItemValid(ItemStack stack)
    {
        return !(stack.getItem() instanceof ItemBackpack);
    }
}