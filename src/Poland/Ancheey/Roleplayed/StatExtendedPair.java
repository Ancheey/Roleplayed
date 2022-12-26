package Poland.Ancheey.Roleplayed;

import org.bukkit.NamespacedKey;

public class StatExtendedPair {
    NamespacedKey Key;
    double Value;
    public StatExtendedPair(NamespacedKey k, double v){
        Key = k;
        Value = v;
    }
    public NamespacedKey getKey(){
        return Key;
    }
    public double getValue(){
        return Value;
    }
    public void SwapKey(NamespacedKey k){
        Key = k;
    }
    public void SetValue(double v){
        Value = v;
    }
}
