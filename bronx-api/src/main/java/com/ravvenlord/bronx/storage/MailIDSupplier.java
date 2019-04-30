package com.ravvenlord.bronx.storage;

import com.ravvenlord.bronx.mail.MailData;

/**
 * The {@link MailIDSupplier} interface represents an object that is capable of fetching a new id for a {@link MailData}
 * instance.
 */
@FunctionalInterface
public interface MailIDSupplier {

    /**
     * Creates a new not-used id represented as a long. Calling this method will create the new id instance, hence this
     * method should not be called if no new id is needed.
     *
     * @return the id as a long
     */
    long fetch();

}
