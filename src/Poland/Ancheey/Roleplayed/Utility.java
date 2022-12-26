package Poland.Ancheey.Roleplayed;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffectType;

public class Utility {

    public static double ArmorHalfwayPoint = 300;
    public static double ArmorToughnessHalfwayPoint = 200;
    public static double DodgeHalfwayPoint = 300;
    public static double HasteHalfwayPoint = 300;

    public static double CalculateArmorReduction(double armor, double armorToughness){
        double aTProtection = ArmorHalfwayPoint * (1 - ((armorToughness + 1) / (ArmorToughnessHalfwayPoint + armorToughness)));
        return (1 - (armor /(aTProtection + armor + Math.max(0, ArmorToughnessHalfwayPoint - armor))));
    }
    public static int GetEnchantProtection(Player p, EntityDamageEvent.DamageCause cause){
        int Protection = 0;
        int BlastProtection = 0;
        int ProjectileProtection = 0;
        int FireProtection = 0;

        int effectiveProtection = 0;
        PlayerInventory i = p.getInventory();

        //get enchant levels
        for (ItemStack item : i.getArmorContents()) {
            if(item == null)
                continue;
            Protection += item.getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
            BlastProtection += item.getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS);
            ProjectileProtection += item.getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE);
            FireProtection += item.getEnchantmentLevel(Enchantment.PROTECTION_FIRE);
        }
        Protection += i.getItemInMainHand().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
        BlastProtection += i.getItemInMainHand().getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS);
        ProjectileProtection += i.getItemInMainHand().getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE);
        FireProtection += i.getItemInMainHand().getEnchantmentLevel(Enchantment.PROTECTION_FIRE);

        Protection += i.getItemInOffHand().getEnchantmentLevel(Enchantment.PROTECTION_ENVIRONMENTAL);
        BlastProtection += i.getItemInOffHand().getEnchantmentLevel(Enchantment.PROTECTION_EXPLOSIONS);
        ProjectileProtection += i.getItemInOffHand().getEnchantmentLevel(Enchantment.PROTECTION_PROJECTILE);
        FireProtection += i.getItemInOffHand().getEnchantmentLevel(Enchantment.PROTECTION_FIRE);

        //normal protection
        effectiveProtection += Protection;

        //projectile protection
        if(cause  == EntityDamageEvent.DamageCause.PROJECTILE){
            effectiveProtection += ProjectileProtection * 2;
        }
        else {
            effectiveProtection += ProjectileProtection != 0 ? ProjectileProtection /2 : 0;
        }
        //Blast prostection
        if(cause  == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION
                || cause  == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION){
            effectiveProtection += BlastProtection * 2;
        }
        else {
            effectiveProtection += BlastProtection != 0 ? BlastProtection/2 : 0;
        }
        //fire protection
        if(cause  == EntityDamageEvent.DamageCause.FIRE
                || cause == EntityDamageEvent.DamageCause.FIRE_TICK){
            effectiveProtection += FireProtection * 2;
        }
        else {
            effectiveProtection += FireProtection != 0 ? FireProtection /2 : 0;
        }

        return effectiveProtection;
    }

    //Returns the percentage of damage after reduction from enchant protections depending on the source with diminishing returns
    //2.5% -> 4.75% -> 6.775% With 22% maximum at Protection 20
    //startVal should always be 2.5%
    public static double GetEnchantProtectionPercentage(int ArmorVal, double startVal, int iteration){
        if(ArmorVal == 0){
            return 1;
        }
        if(iteration == ArmorVal - 1){
            return 1 - (startVal /100);
        }
        else{
            double multi = 0.9;
            for(int i = 0 ; i < iteration; i++){
                multi *= 0.9;
            }
            startVal += 2.5 * multi;
            return GetEnchantProtectionPercentage(ArmorVal,startVal,iteration + 1);
        }
    }

    public static double GetPotionProtection(LivingEntity p, EntityDamageEvent.DamageCause cause){
        double damageProtection = 0;
        if(p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE)){
            damageProtection += 1 + p.getPotionEffect(PotionEffectType.DAMAGE_RESISTANCE).getAmplifier();
        }

        if(cause == EntityDamageEvent.DamageCause.FIRE || cause == EntityDamageEvent.DamageCause.FIRE_TICK ){
            if(p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE)){
                damageProtection += 1 + p.getPotionEffect(PotionEffectType.FIRE_RESISTANCE).getAmplifier();
            }
        }

        if(damageProtection > 0){
            return (1 - (damageProtection / (4 + damageProtection)));
        }
        else
            return 1;
    }

    public static double GetHastePercentage(double haste){
        if(haste == 0)
            return 0;
        return ((haste / (haste + HasteHalfwayPoint)) / 2);
    }

    public static double GetDisplayArmor(LivingEntity p){
        return 20 -(Utility.CalculateArmorReduction(StatExtended.GetValue(p, StatExtended.ExtendedArmor),StatExtended.GetValue(p, StatExtended.ExtendedArmorToughness)) * 20);
    }
    public static double GetDisplayHP(LivingEntity p, double currentHP){
        return Math.min(20, (currentHP / StatExtended.GetValue(p, StatExtended.ExtendedMaxHealth))*20 );
    }
    public static  double GetRealMaxHP(LivingEntity e){
        return StatExtended.GetValue(e, StatExtended.ExtendedMaxHealth) > 0 ? StatExtended.GetValue(e, StatExtended.ExtendedMaxHealth) : e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
    }
    public static  double GetVisualMaxHP(LivingEntity e){
        return e.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
    }
    public static double GetCurrentHP(LivingEntity p){
        return p.getHealth() / p.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() * StatExtended.GetValue(p,StatExtended.ExtendedMaxHealth);
    }
    public static double GetTrueAmount(LivingEntity target, double trueValue){
        return StatExtended.GetValue(target, StatExtended.ExtendedMaxHealth) >0 ? (trueValue / StatExtended.GetValue(target, StatExtended.ExtendedMaxHealth)) * target.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() : trueValue;
    }
    public static double GetDodgeChance(LivingEntity p){
        double val = StatExtended.GetValue(p, StatExtended.ExtendedLuck) + 1;
        return val / (DodgeHalfwayPoint + val);
    }
}
