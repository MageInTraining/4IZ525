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
 * @author cen62777
 */
public class Main{
    
    static String message = "This is a plaintext message, my friends!";
    
    public static void main(String[] args){
        hexToBin("00");
        System.out.println(plainToHex("oj"));
        System.out.println(Integer.parseInt("1111", 2));
        System.out.println(Integer.toHexString(15));
        System.out.println(hexToPlain("6A"));
    }
    
    static String plainToHex(String message){
        char[] ch = message.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (char c:ch){
            int i = (int) c;
            builder.append(Integer.toHexString(i).toUpperCase());
        }
        return builder.toString();
    }

    static List<Boolean> hexToBin(String hexMessage){
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
    
    static String binToHex(List<Boolean> bitList){
        String bitString = bitList.toString();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < bitList.size(); i = i+4){
            int x = Integer.parseInt(bitString.substring(i, i+3), 2);
            builder.append(Integer.toHexString(x));
        }
        return builder.toString();
    }
    
    static String hexToPlain(String hexMessage){
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < hexMessage.length(); i+=2) {
            String str = hexMessage.substring(i, i+2);
            builder.append((char)Integer.parseInt(str, 16));
        }
        return builder.toString();
    }
    
    static List<boolean[]> splitToBlocks(List<Boolean> input){
        if(input.size()%64 != 0){
            int indexOfSet = input.size() + 1;
            int toAdd = 64 - input.size()%64;
            for(int i = 0; i < toAdd; i++){
                input.set(indexOfSet, Math.random() < 5);
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
    
    static boolean[] permutation(boolean[] input, int[] table){
        boolean[] output = new boolean[table.length];
        for(int i:table){
            output[i] = input[table[i]];
        }
        return output;
    }
    
    static List<Boolean> joinBlocks(List<boolean[]> input){
        List<Boolean> output = new ArrayList<>();
        for(boolean[] ba:input){
            for(int i =0; i < 64; i++){
                output.add(ba[i]);
            }
        }
        return output;
    }
}
