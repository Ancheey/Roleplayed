package Poland.Ancheey.Roleplayed;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerGainLevelEvent extends Event {
    private Player player;
    private boolean hitMaxLevel;
    public PlayerGainLevelEvent(Player p, boolean hitMax){
        player = p;
        hitMaxLevel = hitMax;
    }
    public Player getPlayer(){
        return  player;
    }

    public boolean isHitMaxLevel() {
        return hitMaxLevel;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
