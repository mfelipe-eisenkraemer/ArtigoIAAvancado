package br.unisc.ia;

/**
 * Created by MateusFelipe on 21/04/2016.
 */
public class ConvertUtils {

    public static String convertToBinary( int value, int minSize ){
        String binaryString = Integer.toBinaryString(value);
        while( binaryString.length() < minSize ){
            binaryString = "0".concat(binaryString);
        }
        return binaryString;
    }
}
