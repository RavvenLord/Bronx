package com.ravvenlord.bronx.plugin.mail.session;

import com.ravvenlord.bronx.mail.session.MailBoxSession;
import com.ravvenlord.bronx.mail.session.ModificationAction;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class BronxMailBoxSessionTest {

    private MailBoxSession session;

    @Before
    public void before() {
        this.session = new BronxMailBoxSession(new HashMap<>());
    }

    @Test
    public void recordCreateChange() {
        this.session.recordChange(0, ModificationAction.CREATE);
        assertEquals(of(0L, ModificationAction.CREATE), this.session.log());
    }

    @Test
    public void recordDeleteChange() {
        this.session.recordChange(1, ModificationAction.DELETE);
        assertEquals(of(1L, ModificationAction.DELETE), this.session.log());
    }

    @Test
    public void recordNoneChangeChange() {
        this.session.recordChange(0, ModificationAction.CREATE);
        this.session.recordChange(0, ModificationAction.DELETE);

        assertEquals(0, this.session.log().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void recordNullChange() {
        this.session.recordChange(1, null);
    }

    @Test
    public void log() {
        Map<Long, ModificationAction> map = new HashMap<>();
        map.put(0L, ModificationAction.CREATE);
        map.put(5L, ModificationAction.DELETE);

        this.session = new BronxMailBoxSession(map);

        assertEquals(map, this.session.log());
    }

    private <K, V> Map<K, V> of(K k, V v) {
        Map<K, V> map = new HashMap<>();
        map.put(k, v);
        return map;
    }
}
