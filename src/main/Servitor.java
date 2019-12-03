/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stehl
 */
public class Servitor {
    
    public static String plainToHex(String message){
        char[] ch = message.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c:ch){
            int i = (int) c;
            builder.append(Integer.toHexString(i).toUpperCase());
        }
        return builder.toString();
    }

    public static List<Boolean> hexToBin(String hexMessage){
        char[] hex = hexMessage.toUpperCase().toCharArray();
        List<Boolean> bitList = new ArrayList<>();
        for(char h:hex){
            int i = Integer.parseInt(String.valueOf(h), 16);
            for(int x = 8; x >= 1; x= x/2){
                bitList.add((i/x)==1);
                i = i%x;
            }
        }
        return bitList;
    }
    
    public static String binToHex(List<Boolean> bitList){
        StringBuilder intBuilder = new StringBuilder();
        for(boolean b:bitList){
            if(b==true){
               intBuilder.append(1);
            }else{
               intBuilder.append(0); 
            }
        }
        String bitString = intBuilder.toString();
        StringBuilder hexBuilder = new StringBuilder();
        for(int i = 0; i < bitList.size(); i = i+4){
            int x = Integer.parseInt(bitString.substring(i, i+4), 2);
            hexBuilder.append(Integer.toHexString(x));
        }
        return hexBuilder.toString();
    }
    
    public static String hexToPlain(String hexMessage){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hexMessage.length(); i= i + 2) {
            String str = hexMessage.substring(i, i+2);
            builder.append((char)Integer.parseInt(str, 16));
        }
        return builder.toString();
    }
    
    public static List<boolean[]> splitToBlocks(List<Boolean> input){
        if(input.size()%64 != 0){
            int toAdd = 64 - input.size()%64;
            for(int i = 0; i < toAdd; i++){
                input.add( Math.random() < 0.5);
            }
        }
        List<boolean[]> output = new ArrayList<>();
        int numberOfBlocks = input.size()/64;
        int index = 0;
        for(int i = 0; i < numberOfBlocks; i++){
            boolean[] bArray = new boolean[64];
            for(int j = 0; j < 64; j++){
                bArray[j] = input.get(index);
                index++;
            }
            output.add(bArray);
        }
        return output;
    }
    
    public static boolean[] permutation(boolean[] input, int[] table){
        boolean[] output = new boolean[table.length];
        for(int i:table){
            output[i] = input[table[i]];
        }
        return output;
    }
    
    public static List<Boolean> joinBlocks(List<boolean[]> input){
        List<Boolean> output = new ArrayList<>();
        for(boolean[] ba:input){
            for(int i =0; i < 64; i++){
                output.add(ba[i]);
            }
        }
        return output;
    }
    
    public static boolean[] permute(boolean[] inputArray, int[] table){
        
        boolean[] outputArray = null;
        
        for(int i = 0; i< table.length; i++){
            outputArray[i] = inputArray[table[i]];
        }
        return outputArray;
    }
    
    public static boolean[] doXOR(boolean[] a, boolean[] b){
        boolean[] output = null;
        for(int i = 0; i < a.length; i++){
            output[i]= ((a[i] && !b[i]) || (!a[i] && b[i]));
        }
        return output;
    }
    
    public static boolean[] doShift(boolean[] key, int shiftBy){
        boolean[] helper = key;
        for(int i = 0; i < key.length; i++){
            if((i - shiftBy)>= 0){
                key[i]=helper[i - shiftBy];
            }else{
                key[i] = helper[key.length-shiftBy-1];
            }
        }
        return key;
    }
    
    public static int BoolValue(boolean input){
        return input ? 1 : 0;
    }
    
    public static List<Boolean> useSBox(boolean[] input, int[][] sbox){
        
        StringBuilder sb1 = new StringBuilder();
        
        sb1.append(BoolValue(input[0]));
        sb1.append(BoolValue(input[5]));
        
        int row = Integer.parseInt(sb1.toString(), 2);
        
        StringBuilder sb2 = new StringBuilder();
        
        sb2.append(BoolValue(input[1]));
        sb2.append(BoolValue(input[2]));
        sb2.append(BoolValue(input[3]));
        sb2.append(BoolValue(input[4]));
        
        int column = Integer.parseInt(sb2.toString(), 2);
        
        char[] cArray = Integer.toBinaryString(sbox[row][column]).toCharArray();
        List<Boolean> output = new ArrayList<>();
        
        for(int i = 0; i < 4; i++){
            if(cArray[i]=='0'){
                output.add(false);
            }else{
                output.add(true);
            }
        }
        return output;
    }
    
}
