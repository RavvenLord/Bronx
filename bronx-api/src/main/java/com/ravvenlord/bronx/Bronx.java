package com.ravvenlord.bronx;

import com.ravvenlord.bronx.mail.MailBoxCache;
import org.bukkit.plugin.ServicesManager;

/**
 * The {@link Bronx} interface resembles the entry point into the bronx api. An instance of this class can be requested
 * from the spigot {@link ServicesManager}
 */
public interface Bronx {

    /**
     * Returns the {@link MailBoxCache} instance of the bronx plugin. This instance will never be null.
     *
     * @return the {@link MailBoxCache} instance.
     */
    MailBoxCache getMailBoxCache();
}
