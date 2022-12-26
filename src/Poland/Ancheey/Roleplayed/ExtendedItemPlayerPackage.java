package Poland.Ancheey.Roleplayed;

import org.bukkit.entity.Player;

public class ExtendedItemPlayerPackage {
    private final Player Owner;

    public ExtendedItemPlayerPackage(Player p){
        Owner = p;
    }

    public ItemExtended Head;
    public ItemExtended Chest;
    public ItemExtended Legs;
    public ItemExtended Feet;
    public ItemExtended OffHand;
    public ItemExtended MainHand;

    public Player getOwner(){
        return Owner;
    }
}
