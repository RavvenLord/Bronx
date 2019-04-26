package com.ravvenlord.bronx.plugin.mail;

import com.ravvenlord.bronx.mail.MailData;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BronxMailDataTest {

    private UUID senderUUID = UUID.randomUUID();
    private MailData data;

    @Before
    public void before() {
        ItemStack stack = mock(ItemStack.class);
        when(stack.getType()).thenReturn(Material.DIAMOND);
        when(stack.clone()).thenReturn(stack);

        this.data = new BronxMailData(senderUUID, "message", stack);
    }

    @Test
    public void getSender() {
        assertTrue(this.data.getSender().isPresent());
        assertEquals(this.senderUUID, this.data.getSender().get());
    }

    @Test
    public void getMessage() {
        assertTrue(this.data.getMessage().isPresent());
        assertEquals("message", this.data.getMessage().get());
    }

    @Test
    public void get() {
        assertEquals(Material.DIAMOND, this.data.get().getType());
    }
}
