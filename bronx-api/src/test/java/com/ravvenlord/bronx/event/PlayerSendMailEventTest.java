package com.ravvenlord.bronx.event;

import com.ravvenlord.bronx.mail.MailData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;
import java.util.UUID;

import static junit.framework.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerSendMailEventTest {

    private UUID targetUuid;
    private MailData data;
    private PlayerSendMailEvent event;

    @BeforeClass
    public static void beforeClass() {
        PlayerSendMailEvent.getHandlerList().register(new RegisteredListener(mock(Listener.class)
                , mock(EventExecutor.class), EventPriority.HIGH, mock(Plugin.class), false));
    }

    @Before
    public void before() {
        this.targetUuid = UUID.randomUUID();
        UUID senderUuid = UUID.randomUUID();

        this.data = mock(MailData.class);
        when(data.getMessage()).thenReturn(Optional.empty());
        when(data.getSender()).thenReturn(Optional.of(senderUuid));

        this.event = new PlayerSendMailEvent(mock(Player.class), this.targetUuid, this.data);
    }

    @Test
    public void getTargetPlayer() {
        assertEquals(this.targetUuid, event.getTargetPlayer());
    }

    @Test
    public void getMailData() {
        assertEquals(this.data, event.getMailData());
    }

    @Test
    public void isCancelled() {
        assertFalse(event.isCancelled());
    }

    @Test
    public void setCancelled() {
        event.setCancelled(true);
        assertTrue(event.isCancelled());
    }

    @Test
    public void getHandlerList() {
        testHandlerList(PlayerSendMailEvent.getHandlerList());
    }

    @Test
    public void getHandlers() {
        testHandlerList(this.event.getHandlers());
    }

    private void testHandlerList(HandlerList list) {
        assertEquals(1, list.getRegisteredListeners().length);
    }
}
