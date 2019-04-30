package com.ravvenlord.bronx.storage;

import com.ravvenlord.bronx.mail.MailBox;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * The {@link MailBoxStorage} represents an abstraction layer to any given database below. The {@link MailBoxStorage} is
 * not going to cache any values and will always return the current value found in the storage system.
 */
public interface MailBoxStorage {

    /**
     * Pushes the given mail box into the mail box storage.
     *
     * @param owner the owner uuid of the given mail box
     * @param box the mail box instance to push
     *
     * @return a {@link CompletableFuture} instance that will be completed once the {@link MailBox} instance was pushed
     *
     * @throws IllegalArgumentException if the passed {@link MailBox} instance is null
     */
    CompletableFuture<Void> push(UUID owner, MailBox box) throws IllegalArgumentException;

    /**
     * Pulls a fresh {@link MailBox} instance from the {@link MailBoxStorage} that is linked to the given owners {@link
     * UUID}. The returned {@link CompletableFuture} instance wraps the asynchronous storage call.
     *
     * @param owner the owner uuid
     *
     * @return the {@link MailBox} instance or a fresh {@link MailBox} instance for the owner
     *
     * @throws IllegalArgumentException if the passed {@link UUID} is null
     */
    CompletableFuture<MailBox> pull(UUID owner) throws IllegalArgumentException;
}
