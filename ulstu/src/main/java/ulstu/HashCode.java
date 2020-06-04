package ulstu;

import lombok.Value;

@Value
public class HashCode {
    static int CONSTANT = 257;

    public static int hashCode(String str){
        int hash = 0;
        var p = 1L;
        for (char ch : str.toCharArray()) {
            hash = Math.toIntExact((hash + (int) ch % Integer.MAX_VALUE) % Integer.MAX_VALUE);
            p = (p * CONSTANT) % Integer.MAX_VALUE;
        }
        return hash;
    }
}
