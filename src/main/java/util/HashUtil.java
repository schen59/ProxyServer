package util;

/**
 * Utility functions for hash related methods.
 * @author Shaofeng Chen
 * @since 2/9/14
 */
public class HashUtil {

    /**
     * Get the hex string representation for byte array.
     * @param hash
     * @return String
     */
    public static String bytesToHex(byte[] hash) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                stringBuilder.append("0" + Integer.toHexString((0xFF & hash[i])));
            } else {
                stringBuilder.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return stringBuilder.toString();
    }
}
