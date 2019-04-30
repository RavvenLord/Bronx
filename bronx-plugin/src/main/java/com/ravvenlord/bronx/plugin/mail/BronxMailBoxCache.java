package com.ravvenlord.bronx.plugin.mail;

import com.ravvenlord.bronx.mail.MailBox;
import com.ravvenlord.bronx.mail.MailBoxCache;
import com.ravvenlord.bronx.storage.MailBoxStorage;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

public class BronxMailBoxCache implements MailBoxCache {

    private Map<UUID, CompletableFuture<MailBox>> cache;
    private MailBoxStorage storage;

    /**
     * Creates a new {@link BronxMailBoxCache}
     *
     * @param cache the cache map implementation
     * @param storage the storage provider
     */
    public BronxMailBoxCache(Map<UUID, CompletableFuture<MailBox>> cache, MailBoxStorage storage) {
        this.cache = cache;
        this.storage = storage;
    }

    /**
     * Returns the promise of an existing mail box instance for the given player. The returned {@link CompletableFuture}
     * may not be finished and calling {@link CompletableFuture#join()} may block the thread it was called on.
     * <p>
     * Every provided uuid will return a valid non-null {@link MailBox} instance, non existing ones will simply be
     * created from scratch and empty.
     *
     * @param player the players uuid which will be used as a key
     *
     * @return the future of the players mail box
     */
    @Override
    public CompletableFuture<MailBox> getBox(UUID player) {
        return this.cache.computeIfAbsent(player, uuid -> this.storage.pull(player));
    }

    /**
     * Clears the {@link MailBoxCache} content. Clearing this cache will promise that all data that is cleared has
     * already been saved.
     * <p>
     * This method will only ever clear instances which are wrapped in a {@link CompletableFuture} that is marked as
     * {@link CompletableFuture#isDone()}. Values that are currently being fetched will not be included in the cache
     * clearing.
     *
     * @param predicate the predicate.
     */
    @Override
    public void clearCache(BiPredicate<UUID, MailBox> predicate) {
        this.cache.entrySet().removeIf(e -> {
            UUID owner = e.getKey();
            CompletableFuture<MailBox> box = e.getValue();

            if (box.isDone() && predicate.test(owner, box.join())) {
                this.storage.push(owner, box.join());
                return true;
            }
            return false;
        });
    }

    /**
     * Accepts the given consumer for each {@link CompletableFuture} instance in the cache. This consumer will include
     * instances that have not been completed yet, which may lead to blocking the thread this method is called on.
     *
     * @param consumer the consumer instance
     */
    @Override
    public void forEachFuture(Consumer<CompletableFuture<MailBox>> consumer) {
        this.cache.values().forEach(consumer);
    }
}
