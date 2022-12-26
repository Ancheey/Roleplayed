package Poland.Ancheey.Roleplayed;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class StatExtended {

    //Data
    public static NamespacedKey ExtendedVersion = new NamespacedKey(Main.main, "ExtendedVersion"); //string

    //Universal - double
    public static NamespacedKey ExtendedMaxHealth = new NamespacedKey(Main.main, "ExtendedMaxHealth"); //white
    public static NamespacedKey ExtendedArmor = new NamespacedKey(Main.main, "ExtendedArmor"); //white
    public static NamespacedKey ExtendedArmorToughness = new NamespacedKey(Main.main, "ExtendedArmorToughness"); //white
    public static NamespacedKey ExtendedArmorPenetration = new NamespacedKey(Main.main, "ExtendedArmorPenetration"); //green
    public static NamespacedKey ExtendedLuck = new NamespacedKey(Main.main, "ExtendedLuck"); //white
    public static NamespacedKey ExtendedHaste = new NamespacedKey(Main.main, "ExtendedHaste");//green
    public static NamespacedKey ExtendedAttackPower = new NamespacedKey(Main.main, "ExtendedAttackPower");//green
    public static NamespacedKey ExtendedRangedAttackPower = new NamespacedKey(Main.main, "ExtendedRangedAttackPower");//green
    public static NamespacedKey ExtendedBlockRating = new NamespacedKey(Main.main, "ExtendedBlockRating");//green - shield
    public static NamespacedKey ExtendedMovementSpeed = new NamespacedKey(Main.main, "ExtendedMovementSpeed"); //white
    public static NamespacedKey ExtendedMagicPower = new NamespacedKey(Main.main, "ExtendedMagicPower");//green
    public static NamespacedKey ExtendedHealthPer5Seconds = new NamespacedKey(Main.main, "ExtendedHealthPer5Seconds");//green
    public static NamespacedKey ExtendedRequiredLevel = new NamespacedKey(Main.main, "ExtendedRequiredLevel");//green
    public static List<NamespacedKey> UniversalStatKeys = new ArrayList<NamespacedKey>() {
        {
            add(ExtendedMaxHealth);
            add(ExtendedArmor);
            add(ExtendedArmorToughness);
            add(ExtendedArmorPenetration);
            add(ExtendedLuck);
            add(ExtendedHaste);
            add(ExtendedAttackPower);
            add(ExtendedRangedAttackPower);
            add(ExtendedBlockRating);
            add(ExtendedMovementSpeed);
            add(ExtendedMagicPower);
            add(ExtendedHealthPer5Seconds);
            add(ExtendedRequiredLevel);
        }
    };
    //Weapon Only - double
    public static NamespacedKey ExtendedMinDamage = new NamespacedKey(Main.main, "ExtendedMinDamage");
    public static NamespacedKey ExtendedDamageDifference = new NamespacedKey(Main.main, "ExtendedMaxDamage");
    public static NamespacedKey ExtendedAttackSpeed = new NamespacedKey(Main.main, "ExtendedAttackSpeed");
    public static List<NamespacedKey> WeaponStatKeys = new ArrayList<NamespacedKey>() {
        {
            add(ExtendedMinDamage);
            add(ExtendedDamageDifference);
            add(ExtendedAttackSpeed);
        }
    };

    public static boolean IsUpToDate(PersistentDataContainer c) {
        if (c.has(ExtendedVersion, PersistentDataType.STRING)) {
            return c.get(ExtendedVersion, PersistentDataType.STRING).equals(Main.main.getDescription().getVersion());
        }
        return false;
    }
    public static boolean IsSetUp(PersistentDataContainer c) {
        return c.has(ExtendedVersion, PersistentDataType.STRING);
    }

    //Sets up player stat page
    public static void SetPlayerDefault(Player p) {
        PersistentDataContainer c = p.getPersistentDataContainer();
        c.set(ExtendedVersion, PersistentDataType.STRING, Main.main.getDescription().getVersion());
        for (NamespacedKey key : UniversalStatKeys) {
            if (key == ExtendedMaxHealth) {
                c.set(key, PersistentDataType.DOUBLE, 20.0);
            } else {
                c.set(key, PersistentDataType.DOUBLE, 0.0);
            }
        }
    }
    public static void SetCreatureEntityDefault(Entity p) {
        PersistentDataContainer c = p.getPersistentDataContainer();
        c.set(ExtendedVersion, PersistentDataType.STRING, Main.main.getDescription().getVersion());
        for (NamespacedKey key : UniversalStatKeys) {
            if (key == ExtendedMaxHealth) {
                c.set(key, PersistentDataType.DOUBLE, 20.0);
            } else {
                c.set(key, PersistentDataType.DOUBLE, 0.0);
            }
        }
        for (NamespacedKey key : WeaponStatKeys) {
            if (key == ExtendedAttackSpeed) {
                c.set(key, PersistentDataType.DOUBLE, 4.0);
            } else {
                c.set(key, PersistentDataType.DOUBLE, 1.0);
            }
        }
    }
    //Set up base item stats for later modification
    public static void SetDefault(PersistentDataContainer c) {
        c.set(ExtendedVersion, PersistentDataType.STRING, Main.main.getDescription().getVersion());
        for (NamespacedKey key : UniversalStatKeys) {
            c.set(key, PersistentDataType.DOUBLE, 0.0);
        }
        for (NamespacedKey key : WeaponStatKeys) {
            c.set(key, PersistentDataType.DOUBLE, 0.0);
            if (key == ExtendedAttackSpeed) {
                c.set(key, PersistentDataType.DOUBLE, 1.0);
            }
        }
    }

    public static Entity SetArrowValues(Entity arrow, LivingEntity shooter, ItemStack bow, float force){
        PersistentDataContainer container = arrow.getPersistentDataContainer();
        container.set(ExtendedVersion, PersistentDataType.STRING, Main.main.getDescription().getVersion());
        container.set(ExtendedMinDamage, PersistentDataType.DOUBLE,GetValue(bow, ExtendedMinDamage) * force);
        container.set(ExtendedDamageDifference, PersistentDataType.DOUBLE, GetValue(bow, ExtendedDamageDifference)*force);
        container.set(ExtendedArmorPenetration, PersistentDataType.DOUBLE, GetValue(shooter, ExtendedArmorPenetration)*force);
        container.set(ExtendedRangedAttackPower, PersistentDataType.DOUBLE, GetValue(shooter, ExtendedRangedAttackPower)*force);
        return arrow;
    }

    //Plain adding of some stat if it has been initialized
    public static void AddValue(PersistentDataContainer container, NamespacedKey key, double val) {
        double prevVal = 0;
        if (container.has(key, PersistentDataType.DOUBLE)) {
            prevVal = container.get(key, PersistentDataType.DOUBLE);
            container.set(key, PersistentDataType.DOUBLE, prevVal + val);
        }
    }
    public static void SetValue(PersistentDataContainer container, NamespacedKey key, double val) {
        container.set(key, PersistentDataType.DOUBLE, val);
    }

    public static double GetValue(Entity e, NamespacedKey key) {
        PersistentDataContainer container = e.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.DOUBLE)) {
            return container.get(key, PersistentDataType.DOUBLE);
        }
        return 0;
    }

    public static double GetValue(ItemStack i, NamespacedKey key) {
        PersistentDataContainer container = i.getItemMeta().getPersistentDataContainer();
        if (container.has(key, PersistentDataType.DOUBLE)) {
            return container.get(key, PersistentDataType.DOUBLE);
        }
        return 0;
    }

    public static void DebugBroadcastValue(PersistentDataContainer container, NamespacedKey key) {
        if (container.has(key, PersistentDataType.DOUBLE)) {
            Bukkit.broadcastMessage(key.getKey() + " = " + container.get(key, PersistentDataType.DOUBLE));
        }
    }


    //TODO: SPLIT INTO SEPARATE METHODS! MY EYES HURT
    public static void CalculatePlayerStats(Player p) {
        PersistentDataContainer container = p.getPersistentDataContainer();

        boolean hasOverLeveledItems = false;

        double currentHP = Utility.GetCurrentHP(p);

        SetPlayerDefault(p);

        ItemStack CustomHead = p.getInventory().getHelmet();
        ItemStack CustomChest = p.getInventory().getChestplate();
        ItemStack CustomLegs = p.getInventory().getLeggings();
        ItemStack CustomBoots = p.getInventory().getBoots();
        ItemStack CustomOffHand = p.getInventory().getItemInOffHand();
        ItemStack CustomMainHand = p.getInventory().getItemInMainHand();

        double Value;
        if(ItemExtended.GetItem(CustomHead) != null && ItemExtended.GetItem(CustomHead).getSlot() == ItemExtended.ItemSlot.Head){//Head
            if(p.getLevel() >= StatExtended.GetValue(CustomHead, StatExtended.ExtendedRequiredLevel)) {
                for (NamespacedKey key : UniversalStatKeys) {
                    if ((Value = StatExtended.GetValue(CustomHead, key)) != 0) {
                        AddValue(container, key, Value);
                    }
                }
            }
            else{
            hasOverLeveledItems = true;
            }
        }
        if(ItemExtended.GetItem(CustomChest) != null && ItemExtended.GetItem(CustomChest).getSlot() == ItemExtended.ItemSlot.Chest){//Chest
            if(p.getLevel() >=StatExtended.GetValue(CustomChest, StatExtended.ExtendedRequiredLevel)) {
                for (NamespacedKey key : UniversalStatKeys) {
                    if ((Value = StatExtended.GetValue(CustomChest, key)) != 0) {
                        AddValue(container, key, Value);
                    }
                }
            }
            else{
                hasOverLeveledItems = true;
            }
        }
        if(ItemExtended.GetItem(CustomLegs) != null && ItemExtended.GetItem(CustomLegs).getSlot() == ItemExtended.ItemSlot.Legs){//Legs
            if(p.getLevel() >=StatExtended.GetValue(CustomLegs, StatExtended.ExtendedRequiredLevel)) {
                for (NamespacedKey key : UniversalStatKeys) {
                    if ((Value = StatExtended.GetValue(CustomLegs, key)) != 0) {
                        AddValue(container, key, Value);
                    }
                }
            }
            else{
                hasOverLeveledItems = true;
            }
        }
        if(ItemExtended.GetItem(CustomBoots) != null && ItemExtended.GetItem(CustomBoots).getSlot() == ItemExtended.ItemSlot.Feet){//Feet
            if(p.getLevel() >=StatExtended.GetValue(CustomBoots, StatExtended.ExtendedRequiredLevel)) {
                for (NamespacedKey key : UniversalStatKeys) {
                    if ((Value = StatExtended.GetValue(CustomBoots, key)) != 0) {
                        AddValue(container, key, Value);
                    }
                }
            }
            else{
                hasOverLeveledItems = true;
            }
        }
        if(ItemExtended.GetItem(CustomOffHand) != null && ItemExtended.GetItem(CustomOffHand).getSlot() == ItemExtended.ItemSlot.Offhand){//OffHand
            if(p.getLevel() >=StatExtended.GetValue(CustomOffHand, StatExtended.ExtendedRequiredLevel)) {
                for (NamespacedKey key : UniversalStatKeys) {
                    if ((Value = StatExtended.GetValue(CustomOffHand, key)) != 0) {
                        AddValue(container, key, Value);
                    }
                }
            }
            else{
                hasOverLeveledItems = true;
            }
        }
        if(ItemExtended.GetItem(CustomMainHand) != null && ItemExtended.GetItem(CustomMainHand).getSlot() == ItemExtended.ItemSlot.MainHand){//MainHand
            if(p.getLevel() >=StatExtended.GetValue(CustomMainHand, StatExtended.ExtendedRequiredLevel)) {
                for (NamespacedKey key : UniversalStatKeys) {
                    if ((Value = StatExtended.GetValue(CustomMainHand, key)) != 0) {
                        AddValue(container, key, Value);
                    }
                }
            }
            else{
                hasOverLeveledItems = true;
            }
        }
        double displayArmor = Utility.GetDisplayArmor(p);

        if(p.getAttribute(Attribute.GENERIC_ARMOR).getValue() != displayArmor){
            for (AttributeModifier am : p.getAttribute(Attribute.GENERIC_ARMOR).getModifiers()) {
                p.getAttribute(Attribute.GENERIC_ARMOR).removeModifier(am);
            }
            p.getAttribute(Attribute.GENERIC_ARMOR).addModifier(new AttributeModifier("ExtendedArmorDisplay", displayArmor, AttributeModifier.Operation.ADD_NUMBER));
        }
        if(hasOverLeveledItems)
            p.sendMessage(ChatColor.RED + "You will not benefit from some of your equipment due to its requirements");

        double displayHP = Utility.GetDisplayHP(p, currentHP);
        if(p.getHealth() != displayHP)
            p.setHealth(displayHP);
    }

}
