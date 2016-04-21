package br.unisc.ia;

import java.util.Arrays;

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

    public static double[] convertBinaryStringToDoubleArray(double[] doubleList, String binaryString) {
        String[] listString = binaryString.split("(?!^)");
        for(int x=0;x < listString.length;x++){
            doubleList = addElementAtEndOfArray(doubleList, Integer.valueOf(listString[x]));
        }

        return doubleList;
    }

    private static double[] addElementAtEndOfArray(double[] arrayToCopy, int value) {
        double[] result = Arrays.copyOf(arrayToCopy, arrayToCopy.length + 1);
        result[result.length - 1] = value;
        return result;
    }
}
