package com.ravvenlord.bronx.mail.session;

import com.ravvenlord.bronx.mail.MailBox;
import com.ravvenlord.bronx.mail.MailData;

/**
 * The {@link ModificationAction} enum represents the different actions that can be performed on the {@link MailBox}
 */
public enum ModificationAction {
    /**
     * Represents a {@link MailData} being created. The corresponding value can be found on the {@link MailBox} itself.
     */
    CREATE,

    /**
     * Represents a {@link MailData} being deleted. There is no value to be found in the {@link MailBox} instance as it
     * has been deleted.
     */
    DELETE;
}
