package com.ravvenlord.bronx.plugin.mail;

import com.ravvenlord.bronx.mail.MailBox;
import com.ravvenlord.bronx.mail.MailData;
import com.ravvenlord.bronx.mail.session.MailBoxSession;
import com.ravvenlord.bronx.mail.session.ModificationAction;
import com.ravvenlord.bronx.storage.MailIDSupplier;
import org.apache.commons.lang.Validate;

import java.util.HashMap;
import java.util.Map;

/**
 * The {@link BronxMailBox} is a {@link HashMap} based implementation of the {@link MailBox} interface.
 */
public class BronxMailBox implements MailBox {

    private Map<Long, MailData> map;
    private MailIDSupplier idSupplier;
    private MailBoxSession session;

    /**
     * Creates a new {@link BronxMailBox} instance based on the provided map
     *
     * @param map the map of all currently stored mail data
     * @param idSupplier the supplier for new ids
     * @param session the session record of the mail box
     */
    public BronxMailBox(Map<Long, MailData> map, MailIDSupplier idSupplier, MailBoxSession session) {
        this.map = map;
        this.idSupplier = idSupplier;
        this.session = session;
    }

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
    @Override
    public Map<Long, MailData> all() {
        return new HashMap<>(this.map);
    }

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
    @Override
    public long publish(MailData data) throws IllegalArgumentException {
        Validate.notNull(data, "The provided MailData instance was null and cannot be published to a mail box");

        long newID = this.idSupplier.fetch();
        this.map.put(newID, data);
        this.getSession().recordChange(newID, ModificationAction.CREATE);
        return newID;
    }

    /**
     * Removes the mail data instance with the given id.
     *
     * @param id the mail id instance to remove.
     *
     * @return if there was a mail instance that matched this id.
     */
    @Override
    public boolean remove(long id) {
        if (this.map.remove(id) != null) {
            this.getSession().recordChange(id, ModificationAction.DELETE);
            return true;
        }
        return false;
    }

    /**
     * Returns the {@link MailBoxSession} instance of this {@link MailBox}.
     *
     * @return the mail box session
     */
    @Override
    public MailBoxSession getSession() {
        return this.session;
    }
}
