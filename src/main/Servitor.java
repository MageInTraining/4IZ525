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
    
    public static List<List<Boolean>> splitToBlocks(List<Boolean> input){
        
        //add random values to reach block size of 64 bit
        if(input.size()%64 != 0){
            int toAdd = 64 - input.size()%64;
            for(int i = 0; i < toAdd; i++){
                input.add( Math.random() < 0.5);
            }
        }
        
        List<List<Boolean>> output = new ArrayList<>();
        int numberOfBlocks = input.size()/64;
        int index = 0;
        
        //create a list for each 64-bit block
        for(int i = 0; i < numberOfBlocks; i++){
            
            List<Boolean> bArray = new ArrayList<>();
            
            //transfer part of payload from input list into block list
            for(int j = 0; j < 64; j++){
                bArray.add(input.get(index));
                index++;
            }
            
            //add block list to list of blocks
            output.add(bArray);
        }
        return output;
    }
    
    public static List<Boolean> joinBlocks(List<List<Boolean>> input){
        
        List<Boolean> output = new ArrayList<>();
        
        //do for each block list
        for(List<Boolean> bl:input){
            
            //go over entire block payload
            for(int i =0; i < 64; i++){
                
                //extract block payload into output list
                output.add(bl.get(i));
            }
        }
        return output;
    }
    
    public static void permutation(){}
    
    public static void permute(){}
    
    public static void doXOR(){}
    
    public static void doShift(){}
    
    public static void BoolValue(Boolean input){}
    
    public static void useSBox(Boolean[] input, int[][] sbox){}
}
