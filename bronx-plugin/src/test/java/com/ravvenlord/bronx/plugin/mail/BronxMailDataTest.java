package com.ravvenlord.bronx.plugin.mail;

import com.ravvenlord.bronx.mail.MailData;
import com.ravvenlord.bronx.plugin.util.ItemStackMock;
import org.bukkit.Material;
import org.junit.Before;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class BronxMailDataTest {

    private UUID senderUUID = UUID.randomUUID();
    private MailData data;

    @Before
    public void before() {
        this.data = new BronxMailData(senderUUID, "message", ItemStackMock.mock(Material.DIAMOND));
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
