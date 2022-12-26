package Poland.Ancheey.Roleplayed;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class StatExtendedPlayerChecker {
    private static List<ExtendedItemPlayerPackage> PlayerPackages = new ArrayList<>();
    public static void AddPlayer(Player p){
        PlayerPackages.add(new ExtendedItemPlayerPackage(p));
    }
    public static void RemovePlayer(Player p){
        PlayerPackages.removeIf(pack -> pack.getOwner() == p);
    }

    public static void Tick(){
        boolean recalculate;
        for (ExtendedItemPlayerPackage DataPack: PlayerPackages) {
            recalculate = false;
            //-----------------------
            //Health per 5 seconds /5
            //-----------------------
            if(DataPack.getOwner().getHealth() != Utility.GetVisualMaxHP((DataPack.getOwner())))
            {
                double healingValue = StatExtended.GetValue(DataPack.getOwner(), StatExtended.ExtendedHealthPer5Seconds)/ 5;
                EntityRegainHealthEvent event = new EntityRegainHealthEvent(DataPack.getOwner(),healingValue, EntityRegainHealthEvent.RegainReason.CUSTOM);
                Bukkit.getPluginManager().callEvent(event);

                double newHPVal = Math.min(DataPack.getOwner().getHealth() + event.getAmount(), Utility.GetVisualMaxHP(DataPack.getOwner()));
                DataPack.getOwner().setHealth(newHPVal);

            }

            //-----------------------
            //Check items
            //-----------------------
            PlayerInventory PI = DataPack.getOwner().getInventory();
            ItemExtended CurrentCheck;
            if(DataPack.Head != (CurrentCheck = ItemExtended.GetItem(PI.getHelmet()))){
                DataPack.Head = CurrentCheck;
                recalculate = true;
            }
            if(DataPack.Chest != (CurrentCheck = ItemExtended.GetItem(PI.getChestplate()))){
                DataPack.Chest = CurrentCheck;
                recalculate = true;
            }
            if(DataPack.Legs != (CurrentCheck = ItemExtended.GetItem(PI.getLeggings()))){
                DataPack.Legs = CurrentCheck;
                recalculate = true;
            }
            if(DataPack.Feet != (CurrentCheck = ItemExtended.GetItem(PI.getBoots()))){
                DataPack.Feet = CurrentCheck;
                recalculate = true;
            }
            if(DataPack.OffHand != (CurrentCheck = ItemExtended.GetItem(PI.getItemInOffHand()))){
                DataPack.OffHand = CurrentCheck;
                recalculate = true;
            }
            if(DataPack.MainHand != (CurrentCheck = ItemExtended.GetItem(PI.getItemInMainHand()))){
                DataPack.MainHand = CurrentCheck;
                recalculate = true;
            }
            if(recalculate){
                StatExtended.CalculatePlayerStats(DataPack.getOwner());
            }
        }
    }

}
