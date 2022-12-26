package Poland.Ancheey.Roleplayed;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class LevelExtended {

    //Experience (value) Needed to reach next level
    public static int MaxLevel = 30;

    public static NamespacedKey ExtendedLevel = new NamespacedKey(Main.main, "ExtendedLevel");


    public static Map<Integer, Integer> LevelExpPair = new HashMap<Integer, Integer>(){
        {
            put(0,0);
            put(1,30);
            put(2,70);
            put(3,120);
            put(4,180);
            put(5,250);
            put(6,330);
            put(7,420);
            put(8,520);
            put(9,630);
            put(10,750);
            put(11,1000);
            put(12,1200);
            put(13,1450);
            put(14,1750);
            put(15,2100);
            put(16,2500);
            put(17,2950);
            put(18,3450);
            put(19,4000);
            put(20,4600);
            put(21,6000);
            put(22,8200);
            put(23,11000);
            put(24,14600);
            put(25,19000);
            put(26,24000);
            put(27,29000);
            put(28,34000);
            put(29,39000);

            //30 being the max level
        }
    };
    public static void AddExperience(Player p, double value){
        int currentLevel = p.getLevel();
        double currentExp = p.getExp() * LevelExpPair.getOrDefault(currentLevel, 100000);
        Bukkit.broadcastMessage(ChatColor.BLUE+"Got " + value+ "xp");

        while(value > 0 && currentLevel < MaxLevel){
            if(currentExp + value < LevelExpPair.getOrDefault(currentLevel, 100000)){
                p.setExp((float)(currentExp + value)/LevelExpPair.getOrDefault(currentLevel, 100000));
                value = 0;
            }
            else {
                value -= (LevelExpPair.getOrDefault(currentLevel, 100000) - currentExp);

                currentLevel++;
                p.setLevel(currentLevel);
                currentExp = 0;
                p.setExp(0);
                Bukkit.getPluginManager().callEvent(new PlayerGainLevelEvent(p, currentLevel == MaxLevel));

            }
        }
        Bukkit.broadcastMessage("At " + p.getExp() * LevelExpPair.getOrDefault(currentLevel, 100000) + "/" + LevelExpPair.getOrDefault(currentLevel, 100000));
    }
    public static void SetCreatureEntity(int level, double droppedExp){

    }
}
