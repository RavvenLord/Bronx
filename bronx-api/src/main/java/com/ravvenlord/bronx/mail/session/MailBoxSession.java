package com.ravvenlord.bronx.mail.session;

import java.util.Map;

/**
 * The mail box session instance represents the change of a mail box during a single session
 */
public interface MailBoxSession {

    /**
     * Records a given change on the session
     *
     * @param id the id of the object that was modified
     * @param action the action that occurred
     */
    void recordChange(long id, ModificationAction action);

    /**
     * Returns the log map of the current session
     *
     * @return the map instance
     */
    Map<Long, ModificationAction> log();
}
