package com.ravvenlord.bronx.mail;

import com.ravvenlord.bronx.mail.session.MailBoxSession;

import java.util.Map;

/**
 * The {@link MailBox} interface represents a single players mail box with all the mail currently send to the player.
 */
public interface MailBox {

    /**
     * Returns the entire mail box content. The map instance itself may never be null, but it may simply be empty.
     * <p>
     * The returned map instance is only a shallow copy of the actual instance, hence modifying it will not have any
     * actual affect on the {@link MailBox} instance.
     * <p>
     * The returned map maps
     *
     * @return the map instance.
     */
    Map<Long, MailData> all();

    /**
     * Adds the given mail data to the mail box.
     * <p>
     * The provided mail data mustn't be null as the {@link MailBox} instance only contains actual contents.
     * <p>
     * Note that calling this method reserves a new unique id for the {@link MailData} instance. Calling this method
     * should only ever be called if the {@link MailData} instance should be actually stored.
     *
     * @param data the data instance to publish to this mail box.
     *
     * @return the id of the newly added mail data
     *
     * @throws IllegalArgumentException may be thrown if the provided {@link MailData} instance is null.
     */
    long publish(MailData data) throws IllegalArgumentException;

    /**
     * Removes the mail data instance with the given id.
     *
     * @param id the mail id instance to remove.
     *
     * @return if there was a mail instance that matched this id.
     */
    boolean remove(long id);

    /**
     * Returns the {@link MailBoxSession} instance of this {@link MailBox}.
     *
     * @return the mail box session
     */
    MailBoxSession getSession();
}
