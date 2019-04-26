package com.ravvenlord.bronx.plugin.mail;

import com.ravvenlord.bronx.mail.MailData;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;
import java.util.UUID;

/**
 * The bronx default {@link MailData} implementation. This instance is immutable.
 */
public class BronxMailData implements MailData {

    private final UUID sender;
    private final String message;
    private final ItemStack displayStack;

    public BronxMailData(UUID sender, String message, ItemStack displayStack) {
        this.sender = sender;
        this.message = message;
        this.displayStack = displayStack;
    }

    /**
     * Returns the uuid of the player that send this mail. As mail can technically be send from a plugin as well, this
     * UUID may not exist and therefor can be null.
     *
     * @return the uuid instance wrapped in an {@link Optional}. If the mail was send by a player, this {@link Optional}
     * will never be {@link Optional#empty()}
     */
    @Override
    public Optional<UUID> getSender() {
        return Optional.ofNullable(this.sender);
    }

    /**
     * Returns the message that was assigned to this mail. As mail does not always require a message added, this method
     * only returns an {@link Optional} which may be empty if the sender simply did not specify a message.
     *
     * @return the message wrapped in an {@link Optional}
     */
    @Override
    public Optional<String> getMessage() {
        return Optional.ofNullable(this.message);
    }

    /**
     * Returns the item stack clone which is send in this mail.
     *
     * @return a {@link ItemStack} instance
     */
    @Override
    public ItemStack get() {
        return this.displayStack.clone();
    }
}
