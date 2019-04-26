package com.ravvenlord.bronx.mail;

import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * The mail data interface represents a single mails data. This interface extends the {@link Supplier} with generic
 * specified to the {@link ItemStack}. Using {@link Supplier#get()} will return the {@link ItemStack} instance that was
 * send in this mail.
 */
public interface MailData extends Supplier<ItemStack> {

    /**
     * Returns the uuid of the player that send this mail. As mail can technically be send from a plugin as well, this
     * UUID may not exist and therefor can be null.
     *
     * @return the uuid instance wrapped in an {@link Optional}. If the mail was send by a player, this {@link Optional}
     * will never be {@link Optional#empty()}
     */
    Optional<UUID> getSender();

    /**
     * Returns the message that was assigned to this mail. As mail does not always require a message added, this method
     * only returns an {@link Optional} which may be empty if the sender simply did not specify a message.
     *
     * @return the message wrapped in an {@link Optional}
     */
    Optional<String> getMessage();
}
