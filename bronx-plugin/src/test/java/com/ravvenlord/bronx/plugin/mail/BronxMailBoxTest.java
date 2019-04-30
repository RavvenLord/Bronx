package com.ravvenlord.bronx.plugin.mail;

import com.ravvenlord.bronx.mail.MailBox;
import com.ravvenlord.bronx.mail.MailData;
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

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class BronxMailBoxTest {

    private AtomicLong concurrentMailDataID;
    private MailBox mailBox;

    @Before
    public void before() {
        concurrentMailDataID = new AtomicLong(-1);
        mailBox = new BronxMailBox(new HashMap<>(), concurrentMailDataID::incrementAndGet
                , new BronxMailBoxSession(new HashMap<>()));
    }

    @Test
    public void publish() {
        mailBox.publish(new BronxMailData(UUID.randomUUID(), "test", ItemStackMock.mock(Material.DIAMOND)));

        Map<Long, MailData> all = mailBox.all();

        assertEquals(1, all.size());

        Optional<MailData> first = all.values().stream().findFirst();
        assertTrue(first.isPresent());
        assertEquals(Material.DIAMOND, first.get().get().getType());
    }

    @Test
    public void remove() {
        publish();

        mailBox.remove(this.concurrentMailDataID.get());
        assertEquals(0, mailBox.all().size());
    }

    @Test
    public void getSession() {
        assertEquals(0, this.mailBox.getSession().log().size());
    }
}
