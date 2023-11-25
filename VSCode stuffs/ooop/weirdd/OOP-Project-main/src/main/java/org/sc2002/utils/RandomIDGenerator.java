package org.sc2002.utils;
import java.util.Random;


/**
 * The type Random id generator.
 *
 * @ClassName: RandomIDGenerator

 * @Create: 2023 -11-23 12:35
 */
public class RandomIDGenerator {

    /**
     * Gets random id.
     *
     * @return the random id
     */
    public static String getRandomID() {
        int length = 8;
        long timestamp = System.currentTimeMillis();
        Random random = new Random(timestamp);

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(10);
            sb.append(digit);
        }

        return sb.toString();
    }

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(String[] args){
         System.out.println(getRandomID());;
    }

}
