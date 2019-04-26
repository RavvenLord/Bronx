package com.ravvenlord.bronx.mail;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * The mail box provider instance simply represents the mail box cache that stores the mail boxes of players.
 */
public interface MailBoxCache {

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
    CompletableFuture<MailBox> getBox(UUID player);

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
    void clearCache(Predicate<MailBox> predicate);

    /**
     * Accepts the given consumer for each {@link CompletableFuture} instance in the cache. This consumer will include
     * instances that have not been completed yet, which may lead to blocking the thread this method is called on.
     *
     * @param consumer the consumer instance
     */
    void forEachFuture(Consumer<CompletableFuture<MailBox>> consumer);

    /**
     * Accepts the given consumer for each {@link MailBox} instance that is available in this cache.
     * <p>
     * This method will not accept {@link MailBox} instances that are scheduled in {@link CompletableFuture}s but have
     * not been completely loaded yet.
     *
     * @param consumer the consumer instance,
     */
    default void forEach(Consumer<MailBox> consumer) {
        forEachFuture(future -> {
            if (future.isDone()) consumer.accept(future.join());
        });
    }
}
