package com.ravvenlord.bronx.plugin.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public interface ItemStackMock {

    static ItemStack mock(Material material) {
        ItemStack stack = Mockito.mock(ItemStack.class);
        when(stack.clone()).thenReturn(stack);
        when(stack.getType()).thenReturn(material);
        return stack;
    }

}
