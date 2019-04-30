package com.ravvenlord.bronx.plugin.mail;

import com.ravvenlord.bronx.mail.MailBox;
import com.ravvenlord.bronx.mail.MailBoxCache;
import com.ravvenlord.bronx.plugin.mail.session.BronxMailBoxSession;
import com.ravvenlord.bronx.plugin.util.ItemStackMock;
import com.ravvenlord.bronx.storage.MailBoxStorage;
import org.bukkit.Material;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class BronxMailBoxCacheTest {

    private static final UUID RANDOM_PLAYER_UUID = UUID.randomUUID();

    private MailBoxCache cache;

    @Before
    public void before() {
        this.cache = new BronxMailBoxCache(new HashMap<>(), new MailBoxStorage() {
            private AtomicLong id = new AtomicLong(-1);

            @Override
            public CompletableFuture<Void> push(UUID owner, MailBox box) throws IllegalArgumentException {
                return CompletableFuture.completedFuture(null);
            }

            @Override
            public CompletableFuture<MailBox> pull(UUID owner) throws IllegalArgumentException {
                return CompletableFuture.completedFuture(new BronxMailBox(new HashMap<>(), id::incrementAndGet
                        , new BronxMailBoxSession(new HashMap<>())));
            }
        });
    }

    @Test
    public void getBox() {
        MailBox box = this.cache.getBox(RANDOM_PLAYER_UUID).join();
        box.publish(new BronxMailData(RANDOM_PLAYER_UUID, "", ItemStackMock.mock(Material.COAL)));

        MailBox otherBox = this.cache.getBox(RANDOM_PLAYER_UUID).join();
        assertEquals(1, otherBox.all().size());
    }

    @Test
    public void clearCache() {
        UUID otherPlayer = UUID.randomUUID();
        MailBox otherBox = this.cache.getBox(otherPlayer).join();
        otherBox.publish(new BronxMailData(RANDOM_PLAYER_UUID, "", ItemStackMock.mock(Material.COAL)));

        MailBox box = this.cache.getBox(RANDOM_PLAYER_UUID).join();
        box.publish(new BronxMailData(RANDOM_PLAYER_UUID, "", ItemStackMock.mock(Material.COAL)));

        this.cache.clearCache((u, $) -> RANDOM_PLAYER_UUID.equals(u));

        MailBox reRequestedBox = this.cache.getBox(RANDOM_PLAYER_UUID).join();
        assertEquals(0, reRequestedBox.all().size());

        MailBox reRequestedOtherBox = this.cache.getBox(otherPlayer).join();
        assertEquals(1, reRequestedOtherBox.all().size());
    }

    @Test
    public void forEachFuture() {
        this.cache.getBox(RANDOM_PLAYER_UUID);
        this.cache.forEachFuture(b -> assertTrue(b.isDone()));
    }

    @Test
    public void forEach() {
        this.cache.getBox(RANDOM_PLAYER_UUID).join();
        this.cache.forEach(b -> assertEquals(0, b.all().size()));
    }
}
