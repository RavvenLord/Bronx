package com.ravvenlord.bronx.plugin.mail.session;

import com.ravvenlord.bronx.mail.session.MailBoxSession;
import com.ravvenlord.bronx.mail.session.ModificationAction;

import java.util.HashMap;
import java.util.Map;

public class BronxMailBoxSession implements MailBoxSession {

    private Map<Long, ModificationAction> actionMap;

    public BronxMailBoxSession(Map<Long, ModificationAction> actionMap) {
        this.actionMap = actionMap;
    }

    /**
     * Records a given change on the session
     *
     * @param id the id of the object that was modified
     * @param action the action that occurred
     */
    @Override
    public void recordChange(long id, ModificationAction action) {
        switch (action) {
            case CREATE:
                this.actionMap.put(id, ModificationAction.CREATE);
                break;
            case DELETE:
                if (this.actionMap.get(id) == ModificationAction.CREATE) this.actionMap.remove(id);
                else this.actionMap.put(id, ModificationAction.DELETE);
                break;
            default:
                throw new IllegalArgumentException(String.format("Could not record action %s", action.name()));
        }
    }

    /**
     * Returns the log map of the current session
     *
     * @return the map instance
     */
    @Override
    public Map<Long, ModificationAction> log() {
        return new HashMap<>(this.actionMap);
    }


}
