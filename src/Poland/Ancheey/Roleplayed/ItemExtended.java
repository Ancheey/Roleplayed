package Poland.Ancheey.Roleplayed;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.craftbukkit.libs.jline.internal.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.util.*;

public class ItemExtended {

    //List of all available items
    public static List<ItemExtended> Database = new ArrayList<>();


    public List<StatExtendedPair> StatExtendedModifiers;

    public static NamespacedKey ExtendedIDKey = new NamespacedKey(Main.main, "ExtendedID");
    public static NamespacedKey ExtendedIDEnchant = new NamespacedKey(Main.main, "ExtendedIDEnchant");
    public static NamespacedKey ExtendedIDRandom = new NamespacedKey(Main.main, "ExtendedIDRandom");

    private final int _ID;
    private final String _name;
    private final Rarity _rarity;
    private final ItemSlot _slot;
    private final Material _mat;
    private final ItemType _type;

    private final ItemMeta meta;

    public ItemExtended(String name, Material material, Rarity rarity, ItemSlot slot, ItemType type, StatExtendedPair[] SEM){
        StatExtendedModifiers = new ArrayList<>(Arrays.asList(SEM));

        Database.add(this);
        _ID = Database.size() - 1;
        _name = name;
        _rarity = rarity;
        _slot = slot;
        _mat = material;
        _type = type;



        ItemStack PH = new ItemStack(material);
        meta = PH.getItemMeta();
        StatExtended.SetDefault(meta.getPersistentDataContainer());

        for (StatExtendedPair KVP: StatExtendedModifiers) {
            StatExtended.SetValue(meta.getPersistentDataContainer(), KVP.getKey(), KVP.getValue());
        }
        meta.removeAttributeModifier(EquipmentSlot.HEAD);
        meta.removeAttributeModifier(EquipmentSlot.CHEST);
        meta.removeAttributeModifier(EquipmentSlot.LEGS);
        meta.removeAttributeModifier(EquipmentSlot.FEET);
        meta.removeAttributeModifier(EquipmentSlot.HAND);
        meta.removeAttributeModifier(EquipmentSlot.OFF_HAND);


        //meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setUnbreakable(true);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
        meta.getPersistentDataContainer().set(ExtendedIDKey, PersistentDataType.INTEGER, _ID);
    }

    public ItemStack Create(){
        ItemStack item = new ItemStack(_mat);
        setName(item);
        setAttributes(item);
        setLore(item);
        return item;
    }


    //getEquipmentSlot
    public ItemSlot getSlot(){
        return _slot;
    }
    //getDefaultName
    public String getName(){
        return _name;
    }
    //getDefaultRarity
    public Rarity getRarity()
    {
        return _rarity;
    }
    public Material getType(){
        return _mat;
    }
    //Set item name
    private static void setLore(ItemStack item){
        DecimalFormat df = new DecimalFormat("#,###");
        DecimalFormat dfSmall = new DecimalFormat("#.##");
        List<String> lore = new ArrayList<>();
        ItemMeta meta = item.getItemMeta();

        ItemExtended i = GetItem(item);


        double Value = 0;
        if(meta == null)    return;
        if(i._slot == ItemSlot.MainHand){ //Weapon
            lore.add(ChatColor.DARK_GRAY + "Main Hand");
            if(item.getType() == Material.BOW){ //ranged wep
                if((Value = StatExtended.GetValue(item, StatExtended.ExtendedMinDamage))!= 0){
                    lore.add(ChatColor.WHITE + "| " + df.format(Value) + " - " + df.format(Value + StatExtended.GetValue(item, StatExtended.ExtendedDamageDifference) )+ " Ranged Damage");
                    lore.add(ChatColor.WHITE + "| (" + df.format((Value + ((StatExtended.GetValue(item, StatExtended.ExtendedDamageDifference))/2))/1.1) + " dps)");
                }
            }
            else if(item.getType() == Material.CROSSBOW){
                if((Value = StatExtended.GetValue(item, StatExtended.ExtendedMinDamage))!= 0){
                    lore.add(ChatColor.WHITE + "| " + df.format(Value) + " - " + df.format(Value + StatExtended.GetValue(item, StatExtended.ExtendedDamageDifference) )+ " Ranged Damage");
                    lore.add(ChatColor.WHITE + "| (" + df.format((Value + ((StatExtended.GetValue(item, StatExtended.ExtendedDamageDifference))/2))/1.25) + " dps)");
                }
            }
            else{//Mainhand melee wep
                if((Value = StatExtended.GetValue(item, StatExtended.ExtendedMinDamage))!= 0 && StatExtended.GetValue(item, StatExtended.ExtendedAttackSpeed)!= 0){
                    lore.add(ChatColor.WHITE + "| " + df.format(Value) + " - " + df.format(Value + StatExtended.GetValue(item, StatExtended.ExtendedDamageDifference)) + " Damage every " + dfSmall.format(1/StatExtended.GetValue(item, StatExtended.ExtendedAttackSpeed) )+ "s");
                    lore.add(ChatColor.WHITE + "| (" + df.format((Value + ((StatExtended.GetValue(item, StatExtended.ExtendedDamageDifference))/2))*StatExtended.GetValue(item, StatExtended.ExtendedAttackSpeed))  + " dps)");
                }
            }
            if((Value = StatExtended.GetValue(item, StatExtended.ExtendedArmor))!= 0){//Armor
                lore.add(ChatColor.WHITE + "+" + df.format(Value) + " Armor");
            }
            if((Value = StatExtended.GetValue(item, StatExtended.ExtendedArmorToughness))!= 0){//Toughness
                lore.add(ChatColor.WHITE + "+" + df.format(Value) + " Toughness");
            }
        }
        else if(i._slot == ItemSlot.Offhand){
            lore.add(ChatColor.DARK_GRAY + "Off Hand");
            if(item.getType() == Material.SHIELD){
                if((Value = StatExtended.GetValue(item, StatExtended.ExtendedArmor))!= 0){
                    lore.add(ChatColor.WHITE + "| " + df.format(Value) + " Armor");
                }
                if((Value = StatExtended.GetValue(item, StatExtended.ExtendedArmorToughness))!= 0){
                    lore.add(ChatColor.WHITE + "| " + df.format(Value) + " Toughness");
                }
                if((Value = StatExtended.GetValue(item, StatExtended.ExtendedBlockRating))!= 0){
                    lore.add(ChatColor.WHITE + "| " + df.format(Value) + " Blocked Damage");
                }
            }
            else{
                if((Value = StatExtended.GetValue(item, StatExtended.ExtendedArmor))!= 0){
                    lore.add(ChatColor.WHITE + "+" + df.format(Value) + " Armor");
                }
                if((Value = StatExtended.GetValue(item, StatExtended.ExtendedArmorToughness))!= 0){
                    lore.add(ChatColor.WHITE + "+" + df.format(Value) + " Toughness");
                }
            }
        }
        else{
            if(i._slot == ItemSlot.Head){
                lore.add(ChatColor.DARK_GRAY + i._type.toString() + " Head");
            }
            else if(i._slot == ItemSlot.Chest){
                lore.add(ChatColor.DARK_GRAY + i._type.toString() +  " Chest");
            }
            else if(i._slot == ItemSlot.Legs){
                lore.add(ChatColor.DARK_GRAY + i._type.toString() +  " Legs");
            }
            else if(i._slot == ItemSlot.Feet){
                lore.add(ChatColor.DARK_GRAY + i._type.toString() +  " Feet");
            }
            else if(i._slot == ItemSlot.Other){
                lore.add(ChatColor.DARK_GRAY + i._type.toString());
            }
            if((Value = StatExtended.GetValue(item, StatExtended.ExtendedArmor))!= 0){
                lore.add(ChatColor.WHITE + "| " + df.format(Value) + " Armor");
            }
            if((Value = StatExtended.GetValue(item, StatExtended.ExtendedArmorToughness))!= 0){
                lore.add(ChatColor.WHITE + "| " + df.format(Value) + " Toughness");
            }
        }
        //white stats
        if((Value = StatExtended.GetValue(item, StatExtended.ExtendedMaxHealth))!= 0){
            lore.add(ChatColor.WHITE + "+" + df.format(Value) + " Health");
        }
        if((Value = StatExtended.GetValue(item, StatExtended.ExtendedLuck))!= 0){
            lore.add(ChatColor.WHITE + "+" + df.format(Value) + " Luck");
        }
        if((Value = StatExtended.GetValue(item, StatExtended.ExtendedMovementSpeed))!= 0){
            lore.add(ChatColor.WHITE + "+" + dfSmall.format(Value* 100) + "% Movement Speed");
        }
        //Green stats
        if((Value = StatExtended.GetValue(item, StatExtended.ExtendedArmorPenetration))!= 0){
            lore.add(ChatColor.GREEN + "Equip: Increases your armor penetration by " + df.format(Value) + ".");
        }
        if((Value = StatExtended.GetValue(item, StatExtended.ExtendedHaste))!= 0){
            lore.add(ChatColor.GREEN + "Equip: Increases your melee haste by " + df.format(Value) + ".");
        }
        if((Value = StatExtended.GetValue(item, StatExtended.ExtendedAttackPower))!= 0){
            lore.add(ChatColor.GREEN + "Equip: Increases your melee attack power by " + df.format(Value) + ".");
        }
        if((Value = StatExtended.GetValue(item, StatExtended.ExtendedRangedAttackPower))!= 0){
            lore.add(ChatColor.GREEN + "Equip: Increases your ranged attack power by " + df.format(Value) + ".");
        }
        if((Value = StatExtended.GetValue(item, StatExtended.ExtendedBlockRating))!= 0 && item.getType() != Material.SHIELD){
            lore.add(ChatColor.GREEN + "Equip: Increases your block rating by " + df.format(Value) + ".");
        }
        if((Value = StatExtended.GetValue(item, StatExtended.ExtendedMagicPower))!= 0){
            lore.add(ChatColor.GREEN + "Equip: Increases the power of your magic effects by " + df.format(Value) + ".");
        }
        if((Value = StatExtended.GetValue(item, StatExtended.ExtendedHealthPer5Seconds))!= 0){
            lore.add(ChatColor.GREEN + "Equip: Increases your health regeneration by " + df.format(Value) + " per 5 seconds.");
        }
        if((Value = StatExtended.GetValue(item, StatExtended.ExtendedRequiredLevel))!= 0){
            lore.add(ChatColor.RED + "Requires level " + df.format(Value) + ".");
        }



        meta.setLore(lore);
        item.setItemMeta(meta);
    }
    private static void setName(ItemStack item){
        ItemMeta meta = item.getItemMeta();
        ItemExtended i = GetItem(item);

        if(meta == null)    return;
        switch (i._rarity){
            case Uncommon:
                    meta.setDisplayName(ChatColor.GREEN + i._name);
                    break;
            case Rare:
                    meta.setDisplayName(ChatColor.DARK_BLUE + i._name);
                    break;
            case Epic:
                    meta.setDisplayName(ChatColor.DARK_PURPLE + i._name);
                    break;
            case Legendary:
                    meta.setDisplayName(ChatColor.GOLD + i._name);
                    break;
            default:
                    meta.setDisplayName(i._name);
                    break;
        }
        item.setItemMeta(meta);
    }
    private static void setAttributes(ItemStack item){

        ItemExtended i = GetItem(item);
        ItemMeta meta = item.getItemMeta();
        if(meta == null)    return;

        //Movement speed
        AttributeModifier ms = new AttributeModifier(UUID.randomUUID(),"ExtendedMovementSpeed", StatExtended.GetValue(item, StatExtended.ExtendedMovementSpeed), AttributeModifier.Operation.MULTIPLY_SCALAR_1, ItemSlotToEquipmentSlot(i._slot));
        meta.addAttributeModifier(Attribute.GENERIC_MOVEMENT_SPEED, ms);
        if(i._slot == ItemSlot.MainHand){
            AttributeModifier as = new AttributeModifier(UUID.randomUUID(),"ExtendedAttackSpeed", (StatExtended.GetValue(item, StatExtended.ExtendedAttackSpeed) - 4), AttributeModifier.Operation.ADD_NUMBER, ItemSlotToEquipmentSlot(i._slot));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, as);
        }
        if(Utility.GetHastePercentage(StatExtended.GetValue(item, StatExtended.ExtendedHaste)) > 0){
            AttributeModifier ht = new AttributeModifier(UUID.randomUUID(),"ExtendedHaste", Utility.GetHastePercentage(StatExtended.GetValue(item, StatExtended.ExtendedHaste)), AttributeModifier.Operation.ADD_SCALAR, ItemSlotToEquipmentSlot(i._slot));
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, ht);
        }
        item.setItemMeta(meta);
    }
    public static ItemExtended GetItem(int id){
        if(Database.size() > id){
            return Database.get(id);
        }
        return null;
    }
    public static ItemExtended GetItem(@Nullable ItemStack item){
        if(item == null)
            return null;

        ItemMeta meta = item.getItemMeta();
        if(meta == null)    return null;
        if(meta.getPersistentDataContainer().has(ExtendedIDKey, PersistentDataType.INTEGER)){
            int id = meta.getPersistentDataContainer().get(ExtendedIDKey, PersistentDataType.INTEGER);
            return GetItem(id);
        }
        else{
            return null;
        }

    }

    public enum Rarity{
        Common,
        Uncommon,
        Rare,
        Epic,
        Legendary
    }

    public static EquipmentSlot ItemSlotToEquipmentSlot(ItemSlot slot){
        switch (slot){
            case Head:
                return EquipmentSlot.HEAD;
            case Chest:
                return EquipmentSlot.CHEST;
            case Legs:
                return EquipmentSlot.LEGS;
            case Feet:
                return EquipmentSlot.FEET;
            case MainHand:
                return EquipmentSlot.HAND;
            case Offhand:
                return EquipmentSlot.OFF_HAND;
            default:
                return null;
        }
    }

    public  enum ItemSlot{
        Head,
        Chest,
        Legs,
        Feet,
        MainHand,
        Offhand,
        Other
    }
    public enum ItemType{
        Cloth,
        Leather,
        Chain,
        Plate,
        Junk,
        Quest,
        Other,

    }
}