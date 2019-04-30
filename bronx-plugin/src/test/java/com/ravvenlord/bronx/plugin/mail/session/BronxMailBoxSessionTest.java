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
    public void recordChange() {
        this.session.recordChange(0, ModificationAction.CREATE);

        assertEquals(this.session.log(), of(0L, ModificationAction.CREATE));
    }

    @Test
    public void log() {
    }

    private <K, V> Map<K, V> of(K k, V v) {
        Map<K, V> map = new HashMap<>();
        map.put(k, v);
        return map;
    }
}
