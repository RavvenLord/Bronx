package com.ravvenlord.bronx.plugin;

import com.ravvenlord.bronx.Bronx;
import com.ravvenlord.bronx.mail.MailBoxCache;

/**
 * The {@link BronxCore} object represents the actual main class of this {@link Bronx} implementation.
 */
public class BronxCore implements Bronx {

    private MailBoxCache mailBoxCache;

    public BronxCore(MailBoxCache mailBoxCache) {
        this.mailBoxCache = mailBoxCache;
    }

    /**
     * Returns the {@link MailBoxCache} instance of the bronx plugin. This instance will never be null.
     *
     * @return the {@link MailBoxCache} instance.
     */
    @Override
    public MailBoxCache getMailBoxCache() {
        return this.mailBoxCache;
    }
}
