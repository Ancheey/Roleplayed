package Poland.Ancheey.Roleplayed;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

public class Main extends JavaPlugin {
    public static org.bukkit.plugin.Plugin main;
    @Override
    public void onLoad() {
        super.onLoad();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        main = this;

        //Create items
        new ItemExtended("Silvergrove Slasher",  Material.IRON_SWORD, ItemExtended.Rarity.Epic, EquipmentSlot.HAND, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedMinDamage, 15),
                new StatExtendedPair(StatExtended.ExtendedDamageDifference, 24),
                new StatExtendedPair(StatExtended.ExtendedAttackSpeed, 2),
                new StatExtendedPair(StatExtended.ExtendedLuck , 36),
                new StatExtendedPair(StatExtended.ExtendedAttackPower, 3),
                new StatExtendedPair(StatExtended.ExtendedArmorPenetration, 12),
                new StatExtendedPair(StatExtended.ExtendedRequiredLevel, 30),
        });
        new ItemExtended("Ardent Defender",  Material.SHIELD, ItemExtended.Rarity.Epic, EquipmentSlot.OFF_HAND, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 90),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 32),
                new StatExtendedPair(StatExtended.ExtendedBlockRating, 15),
                new StatExtendedPair(StatExtended.ExtendedMaxHealth, 30),
                new StatExtendedPair(StatExtended.ExtendedHealthPer5Seconds, 3),
                new StatExtendedPair(StatExtended.ExtendedRequiredLevel, 30),
        });
        new ItemExtended("Heroic Helmet",  Material.IRON_HELMET, ItemExtended.Rarity.Rare, EquipmentSlot.HEAD, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 45),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 5),
                new StatExtendedPair(StatExtended.ExtendedBlockRating, 3),
        });
        new ItemExtended("Chestpiece of the Paragon",  Material.IRON_CHESTPLATE, ItemExtended.Rarity.Rare, EquipmentSlot.CHEST, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 70),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 9),
                new StatExtendedPair(StatExtended.ExtendedMaxHealth, 25),
                new StatExtendedPair(StatExtended.ExtendedAttackPower, 9),
                new StatExtendedPair(StatExtended.ExtendedMagicPower, 14),
        });
        new ItemExtended("Glorious Legguards",  Material.IRON_LEGGINGS, ItemExtended.Rarity.Rare, EquipmentSlot.LEGS, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 65),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 8),
                new StatExtendedPair(StatExtended.ExtendedBlockRating, 4),
                new StatExtendedPair(StatExtended.ExtendedMaxHealth, 12),
        });
        new ItemExtended("Lavawalker Threads",  Material.NETHERITE_BOOTS, ItemExtended.Rarity.Epic, EquipmentSlot.FEET, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 39),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 4),
                new StatExtendedPair(StatExtended.ExtendedMagicPower, 25),
                new StatExtendedPair(StatExtended.ExtendedMovementSpeed, 0.10),
        });
        new ItemExtended("Obliterating Siegebow",  Material.CROSSBOW, ItemExtended.Rarity.Epic, EquipmentSlot.HAND, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedMinDamage, 80),
                new StatExtendedPair(StatExtended.ExtendedDamageDifference, 29),
                new StatExtendedPair(StatExtended.ExtendedMovementSpeed , -0.02),
                new StatExtendedPair(StatExtended.ExtendedRangedAttackPower, 12),
                new StatExtendedPair(StatExtended.ExtendedArmorPenetration, 56),
        });
        new ItemExtended("Beaststalker Hood",  Material.LEATHER_HELMET, ItemExtended.Rarity.Rare, EquipmentSlot.HEAD, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 15),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 2),
                new StatExtendedPair(StatExtended.ExtendedRangedAttackPower, 24),
        });
        new ItemExtended("Hunters Favorite Cloak",  Material.LEATHER_CHESTPLATE, ItemExtended.Rarity.Rare, EquipmentSlot.CHEST, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 27),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 3),
                new StatExtendedPair(StatExtended.ExtendedMaxHealth, 12),
                new StatExtendedPair(StatExtended.ExtendedRangedAttackPower, 43),
                new StatExtendedPair(StatExtended.ExtendedHealthPer5Seconds, 1),
        });
        new ItemExtended("Linked Guards of Penetrating",  Material.CHAINMAIL_LEGGINGS, ItemExtended.Rarity.Rare, EquipmentSlot.LEGS, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 32),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 5),
                new StatExtendedPair(StatExtended.ExtendedRangedAttackPower, 12),
                new StatExtendedPair(StatExtended.ExtendedAttackPower, 12),
                new StatExtendedPair(StatExtended.ExtendedArmorPenetration, 12),
        });
        new ItemExtended("Quiet Mulchstompers",  Material.LEATHER_BOOTS, ItemExtended.Rarity.Rare, EquipmentSlot.FEET, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 12),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 1),
                new StatExtendedPair(StatExtended.ExtendedMaxHealth, 5),
                new StatExtendedPair(StatExtended.ExtendedRangedAttackPower, 18),
                new StatExtendedPair(StatExtended.ExtendedMovementSpeed, 0.05),
                new StatExtendedPair(StatExtended.ExtendedHaste, 20),
        });
        new ItemExtended("Gamblers Bow",  Material.BOW, ItemExtended.Rarity.Epic, EquipmentSlot.HAND, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedMinDamage, 2),
                new StatExtendedPair(StatExtended.ExtendedDamageDifference, 20),
                new StatExtendedPair(StatExtended.ExtendedLuck, 25),
                new StatExtendedPair(StatExtended.ExtendedRangedAttackPower, 3),
                new StatExtendedPair(StatExtended.ExtendedMovementSpeed, 0.06),
        });
        new ItemExtended("Gamblers Hood",  Material.LEATHER_HELMET, ItemExtended.Rarity.Rare, EquipmentSlot.HEAD, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 15),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 2),
                new StatExtendedPair(StatExtended.ExtendedAttackPower, 2),
                new StatExtendedPair(StatExtended.ExtendedLuck, 18),
        });
        new ItemExtended("Gamblers Cloak",  Material.LEATHER_CHESTPLATE, ItemExtended.Rarity.Rare, EquipmentSlot.CHEST, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 27),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 3),
                new StatExtendedPair(StatExtended.ExtendedMaxHealth, 12),
                new StatExtendedPair(StatExtended.ExtendedAttackPower, 4),
                new StatExtendedPair(StatExtended.ExtendedLuck, 29),
        });
        new ItemExtended("Gamblers Leggings",  Material.CHAINMAIL_LEGGINGS, ItemExtended.Rarity.Rare, EquipmentSlot.LEGS, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 32),
                new StatExtendedPair(StatExtended.ExtendedMaxHealth, 5),
                new StatExtendedPair(StatExtended.ExtendedAttackPower, 2),
                new StatExtendedPair(StatExtended.ExtendedMaxHealth, 12),
                new StatExtendedPair(StatExtended.ExtendedLuck, 26),
        });
        new ItemExtended("Gamblers Stompers",  Material.LEATHER_BOOTS, ItemExtended.Rarity.Rare, EquipmentSlot.FEET, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedArmor, 12),
                new StatExtendedPair(StatExtended.ExtendedArmorToughness, 1),
                new StatExtendedPair(StatExtended.ExtendedMaxHealth, 5),
                new StatExtendedPair(StatExtended.ExtendedLuck, 18),
                new StatExtendedPair(StatExtended.ExtendedHealthPer5Seconds, 4),
        });
        new ItemExtended("Jack's Sword of Gambling",  Material.GOLDEN_SWORD, ItemExtended.Rarity.Rare, EquipmentSlot.HAND, new StatExtendedPair[]{
                new StatExtendedPair(StatExtended.ExtendedMinDamage, 0.1),
                new StatExtendedPair(StatExtended.ExtendedDamageDifference, 15),
                new StatExtendedPair(StatExtended.ExtendedAttackSpeed, 1.5),
                new StatExtendedPair(StatExtended.ExtendedLuck , 36),
                new StatExtendedPair(StatExtended.ExtendedHealthPer5Seconds, 4),
        });

        //Initialize handlers
        Bukkit.getServer().getPluginManager().registerEvents(new EventHandlerExtended(), this);

        for (Player p: Bukkit.getOnlinePlayers()) {
            StatExtendedPlayerChecker.AddPlayer(p);
        }

        BukkitScheduler bs = getServer().getScheduler();
        bs.scheduleSyncRepeatingTask(main, StatExtendedPlayerChecker::Tick,0,20);
    }
}
