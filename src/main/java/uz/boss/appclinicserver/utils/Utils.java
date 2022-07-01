package uz.boss.appclinicserver.utils;

/**
 * Author: Muhammad
 * Date: 29.06.2022
 * Time: 14:13
 */

public class Utils {
    public static String generateUniqueCode(){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            result.append((int) (Math.random() * 10));
        }
        return result.toString();
    }
}
