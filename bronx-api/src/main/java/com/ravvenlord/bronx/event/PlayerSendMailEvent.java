package com.ravvenlord.bronx.event;

import com.ravvenlord.bronx.mail.MailData;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import java.util.UUID;

/**
 * The {@link PlayerSendMailEvent} is called right before the mail is actually send. This event will not be called if
 * the target player cannot be found nor if the player input was invalid.
 * <p>
 * Hence both {@link PlayerSendMailEvent#getTargetPlayer()} nor {@link PlayerSendMailEvent#getMailData()} will never be
 * null.
 */
public class PlayerSendMailEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private UUID targetPlayer;
    private MailData mailData;
    private boolean isCancelled;

    /**
     * Creates a new {@link PlayerSendMailEvent} with the given values.
     *
     * @param targetPlayer the uuid of the target player as the target of this mail may not be online.
     * @param mailData the mail data that the player is about to send
     */
    public PlayerSendMailEvent(Player player, UUID targetPlayer, MailData mailData) {
        super(player);
        this.targetPlayer = targetPlayer;
        this.mailData = mailData;
    }

    /**
     * Returns the uuid of the target player the sender is trying to send this mail to
     *
     * @return the uuid.
     */
    public UUID getTargetPlayer() {
        return targetPlayer;
    }

    /**
     * Returns the mail data this mail will have. This mail data instance is finalized and all values are filled.
     *
     * @return the mail data instance.
     */
    public MailData getMailData() {
        return mailData;
    }

    /**
     * Gets the cancellation state of this event. A cancelled event will not be executed in the server, but will still
     * pass to other plugins
     *
     * @return true if this event is cancelled
     */
    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    /**
     * Sets the cancellation state of this event. A cancelled event will not be executed in the server, but will still
     * pass to other plugins.
     *
     * @param cancel true if you wish to cancel this event
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
