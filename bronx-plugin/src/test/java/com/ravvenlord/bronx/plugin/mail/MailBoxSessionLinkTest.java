package com.ravvenlord.bronx.plugin.mail;

import com.ravvenlord.bronx.mail.MailBox;
import com.ravvenlord.bronx.mail.MailData;
import com.ravvenlord.bronx.mail.session.ModificationAction;
import com.ravvenlord.bronx.plugin.mail.session.BronxMailBoxSession;
import com.ravvenlord.bronx.plugin.util.ItemStackMock;
import org.bukkit.Material;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

import static org.junit.Assert.*;

public class MailBoxSessionLinkTest {

    private MailBox mailBox;

    @Before
    public void before() {
        final AtomicLong id = new AtomicLong(-1);
        this.mailBox = new BronxMailBox(new HashMap<>(), id::incrementAndGet, new BronxMailBoxSession(new HashMap<>()));
    }

    @Test
    public void testAddition() {
        MailData data = new BronxMailData(UUID.randomUUID(), null, ItemStackMock.mock(Material.COAL));

        this.mailBox.publish(data);

        Map<Long, ModificationAction> log = this.mailBox.getSession().log();
        assertEquals(1, log.size());

        Optional<Map.Entry<Long, ModificationAction>> first = log.entrySet().stream().findFirst();
        assertTrue(first.isPresent());

        Map.Entry<Long, ModificationAction> logEntry = first.get();
        assertEquals(data, this.mailBox.all().get(logEntry.getKey()));
    }

    @Test
    public void testRemoval() {
        MailData data = new BronxMailData(UUID.randomUUID(), null, ItemStackMock.mock(Material.STONE));

        Map<Long, MailData> all = new HashMap<>();
        all.put(5L, data);

        final AtomicLong id = new AtomicLong(-1);
        this.mailBox = new BronxMailBox(all, id::incrementAndGet, new BronxMailBoxSession(new HashMap<>()));

        this.mailBox.remove(5L);

        Map<Long, ModificationAction> log = this.mailBox.getSession().log();
        assertEquals(1, log.size());

        Optional<Map.Entry<Long, ModificationAction>> first = log.entrySet().stream().findFirst();
        assertTrue(first.isPresent());

        Map.Entry<Long, ModificationAction> logEntry = first.get();
        assertFalse(this.mailBox.all().containsKey(logEntry.getKey()));
    }

    @Test
    public void testAddAndRemove() {
        MailData data = new BronxMailData(UUID.randomUUID(), null, ItemStackMock.mock(Material.GRANITE));

        long id = this.mailBox.publish(data);
        this.mailBox.remove(id);

        assertEquals(0, this.mailBox.getSession().log().size());
    }
}
