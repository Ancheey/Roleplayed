package Poland.Ancheey.Roleplayed;

import net.minecraft.server.v1_16_R3.EntityHuman;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.EnumHand;
import org.apache.logging.log4j.core.net.Priority;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.*;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.ItemStack;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;

public class EventHandlerExtended implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        Player p = event.getPlayer();
        if(!StatExtended.IsUpToDate(p.getPersistentDataContainer())){
            StatExtended.SetPlayerDefault(p);
        }
        StatExtendedPlayerChecker.AddPlayer(p);
        StatExtended.CalculatePlayerStats(p);
    }
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event){
        Player p = event.getPlayer();
        StatExtendedPlayerChecker.RemovePlayer(p);
    }

    //only for testing
    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent e){
        if(e.getBlock().getType() == Material.SAND){
            StatExtended.CalculatePlayerStats(e.getPlayer());
            for (NamespacedKey key: StatExtended.UniversalStatKeys) {
                StatExtended.DebugBroadcastValue(e.getPlayer().getPersistentDataContainer(), key);
            }
        }
        if(e.getBlock().getType() == Material.DIRT){
            /*for(int i = 0; i < ItemExtended.Database.size(); i++){
                e.getPlayer().getInventory().addItem(ItemExtended.GetItem(i));
            }*/
        }
        e.getPlayer().getInventory().addItem(ItemExtended.GetItem(1).Create());
        //e.getPlayer().getInventory().addItem(ItemExtended.GetItem(10));
    }


    @EventHandler
    public void onPlayerRegainHealth(EntityRegainHealthEvent event){
        if(event.getEntity() instanceof Player && event.getRegainReason() == EntityRegainHealthEvent.RegainReason.CUSTOM)
        {
            event.setAmount(Utility.GetTrueAmount((LivingEntity) event.getEntity(), event.getAmount()));
        }
        else{
            event.setAmount(0);
        }

    }

    //Events About Experience and levels
    @EventHandler
    public void onPlayerExpChange(PlayerExpChangeEvent event){
        LevelExtended.AddExperience(event.getPlayer(), event.getAmount());
        event.setAmount(0);
    }

    @EventHandler
    public void onPlayerLevelUP(PlayerGainLevelEvent event){
        if(event.isHitMaxLevel()) {
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
        }
        else{
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1f, 1);
        }

    }

    @EventHandler
    public void onPlayerOpenAnyInventory(InventoryOpenEvent event){
        if(event.getInventory().getType() == InventoryType.ENCHANTING){
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        event.setDroppedExp(0);
    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onUnitHit(EntityDamageByEntityEvent event){
        boolean isPlayer = event.getEntity() instanceof Player;
        Random rand = new Random();
        LivingEntity victim = (LivingEntity) event.getEntity();

        //---------------------
        //Data setup
        //---------------------
        double damage = event.getDamage();
        double armor = StatExtended.GetValue(victim, StatExtended.ExtendedArmor);
        double armorToughness = StatExtended.GetValue(victim, StatExtended.ExtendedArmorToughness);

        double afterBlockDodgeDamage;
        double afterArmorPercentage;
        double afterEnchantPercentage = 1;
        if(isPlayer) {
            afterEnchantPercentage = Utility.GetEnchantProtectionPercentage(Utility.GetEnchantProtection(((Player)victim), event.getCause()), 2.5, 0);
        }
        double afterPotionPercentage = Utility.GetPotionProtection(victim, event.getCause());

        double finalDamage; //Damage after calculations

        //---------------------
        //Weapon damage calculations
        //---------------------

        if(event.getDamager() instanceof Player){ //In case enemy is a player and has a weaponExtended that isn't a bow
            ItemStack item = ((Player) event.getDamager()).getInventory().getItemInMainHand();
            
            if(item != null && item.getType() != Material.BOW && item.getType() != Material.CROSSBOW){
                damage = (rand.nextDouble() * StatExtended.GetValue(item,StatExtended.ExtendedDamageDifference)) + StatExtended.GetValue(item,StatExtended.ExtendedMinDamage) + StatExtended.GetValue(event.getDamager(),StatExtended.ExtendedAttackPower);
                armor = Math.max(0,armor - StatExtended.GetValue(item, StatExtended.ExtendedArmorPenetration));
                if(((Player)event.getDamager()).getAttackCooldown()  != 1 || ((Player)victim).getLevel() < StatExtended.GetValue(item, StatExtended.ExtendedRequiredLevel)){
                    damage *= 0.05;
                }
            }
        }
        if(event.getDamager() instanceof Arrow){ //In case player was hit by an arrow (Arrow needs VERSION minDamage, damageDif, ArP, RAp
            Entity arrow = event.getDamager();

            if(StatExtended.IsUpToDate(arrow.getPersistentDataContainer())) {
                damage = (rand.nextDouble() * StatExtended.GetValue(arrow, StatExtended.ExtendedDamageDifference)) + StatExtended.GetValue(arrow, StatExtended.ExtendedMinDamage) + StatExtended.GetValue(arrow, StatExtended.ExtendedRangedAttackPower);
                armor = Math.max(0,armor - StatExtended.GetValue(arrow, StatExtended.ExtendedArmorPenetration));

            }
            arrow.remove();
        }
        //---------------------
        //Check Block and dodge
        //---------------------
        if(isPlayer && ((Player)victim).isBlocking() &&((Player)victim).getLevel() >= StatExtended.GetValue(((Player) victim).getInventory().getItemInOffHand(), StatExtended.ExtendedRequiredLevel))
        {
            afterBlockDodgeDamage = Math.max(0, damage - StatExtended.GetValue(victim, StatExtended.ExtendedBlockRating));
            victim.getWorld().playSound(victim.getLocation(), Sound.ITEM_SHIELD_BLOCK,3f, 0.5f);
        }
        else{
            afterBlockDodgeDamage = damage;
        }

        if((rand.nextDouble() < Utility.GetDodgeChance(victim)) && (!isPlayer || !((Player)victim).isBlocking())){
            afterBlockDodgeDamage = 0;
            victim.getWorld().playSound(victim.getLocation(), Sound.ENTITY_ENDER_DRAGON_FLAP,3f, 0.5f);
        }


        //---------------------
        //Calculate damage reduction from available data
        //---------------------
        afterArmorPercentage = armor != 0 ?  Utility.CalculateArmorReduction(armor, armorToughness) : 1;


        double damageAfterArmor = afterBlockDodgeDamage * afterArmorPercentage;
        double damageAfterEnchant = damageAfterArmor * afterEnchantPercentage;
        finalDamage = damageAfterEnchant * afterPotionPercentage;

        event.setCancelled(true);

        double damageToSubtract = Utility.GetTrueAmount(victim, finalDamage);
        victim.damage(damageToSubtract);

        DecimalFormat df = new DecimalFormat("#.###");

            //Debug
        event.getDamager().sendMessage(ChatColor.BLUE + "" + ChatColor.AQUA +  victim.getName() + ": " + victim.getHealth() + "/" + Utility.GetRealMaxHP(victim));
        //Bukkit.broadcastMessage(ChatColor.GREEN + "Base Damage received: " + ChatColor.GOLD + damage);
        //Bukkit.broadcastMessage(ChatColor.GREEN + "Damage Blocked/Dodged: "+ ChatColor.GOLD + df.format(damage - afterBlockDodgeDamage) );
        //Bukkit.broadcastMessage(ChatColor.GREEN + "After Armor: " + ChatColor.GOLD +  df.format(damageAfterArmor)  + ChatColor.GREEN +  " mod " + ChatColor.GOLD +  df.format(afterArmorPercentage) + "%");
        //Bukkit.broadcastMessage(ChatColor.GREEN + "After Enchants: " + ChatColor.GOLD +  df.format(damageAfterEnchant) + ChatColor.GREEN +  " reduced by " + ChatColor.GOLD +  df.format(Math.abs(afterEnchantPercentage - 1)  * 100)+ "%");
        //Bukkit.broadcastMessage(ChatColor.GREEN + "After Potions: " + ChatColor.GOLD +  df.format(finalDamage) + ChatColor.GREEN +  " reduced by " + ChatColor.GOLD +  df.format(Math.abs(afterPotionPercentage - 1)  * 100)+ "%");
        //Bukkit.broadcastMessage(ChatColor.GREEN + "Conversion: " + ChatColor.GOLD + df.format(damageToSubtract)+"/" + Utility.GetVisualMaxHP(victim) + ChatColor.GREEN+ " from "+ ChatColor.GOLD + df.format(finalDamage)+ "/" +  df.format(Utility.GetRealMaxHP(victim)));
        //Bukkit.broadcastMessage(ChatColor.LIGHT_PURPLE + "Total Damage Reduction: " + ChatColor.GOLD + df.format((1 - (finalDamage/damage))*100) + "%");


    }

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onBowShot(EntityShootBowEvent event){
        if(!StatExtended.IsSetUp(event.getEntity().getPersistentDataContainer()))
            return;
        if(event.getEntity() instanceof Player && ((Player)event.getEntity()).getLevel() < StatExtended.GetValue(event.getBow(), StatExtended.ExtendedRequiredLevel))
            return;
        StatExtended.SetArrowValues(event.getProjectile(),event.getEntity(), event.getBow(), event.getForce());
    }
}
