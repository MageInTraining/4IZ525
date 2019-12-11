/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static tables.Permutations.EXPANSION;
import static tables.Permutations.PC_2;
import static tables.Permutations.P_PERMUTATION;
import static tables.SBoxes.S_BOX_1;
import static tables.SBoxes.S_BOX_2;
import static tables.SBoxes.S_BOX_3;
import static tables.SBoxes.S_BOX_4;
import static tables.SBoxes.S_BOX_5;
import static tables.SBoxes.S_BOX_6;
import static tables.SBoxes.S_BOX_7;
import static tables.SBoxes.S_BOX_8;
import static tables.Shifts.SHIFTS;

/**
 *
 * @author Milan Stehlík
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
            int toAdd = (64-(input.size()%8))/8;
//            int toAdd = 64 - input.size()%64;
            for(int i = 0; i < toAdd; i++){
//                input.add( Math.random() < 0.5);
                 input.add(false);
                 input.add(true);
                 input.add(false);
                 input.add(true);
                 input.add(true);
                 input.add(true);
                 input.add(true);
                 input.add(true);
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
    
    public static List<Boolean> doPermute(List<Boolean> input, int[] table){
        
        List<Boolean> output = new ArrayList<>();
        
        for(int i: table){
            output.add(input.get(i-1));
        }
        return output;
    }
    
    public static List<Boolean> updateKey(List<Boolean> leftKey
            , List<Boolean> rightKey, int roundNumber){
        
        //update both sides of a key
        leftKey = doShift(leftKey, SHIFTS[roundNumber]);
        rightKey = doShift(rightKey, SHIFTS[roundNumber]);
        
        //create round key basis
        List<Boolean> roundKey = new ArrayList<>();
        roundKey.addAll(leftKey);
        roundKey.addAll(rightKey);
        
        //return permuted basis - the actual round key
        Main.leftKey = leftKey;
        Main.rightKey = rightKey;
        
        //output permutation
        roundKey = doPermute(roundKey, PC_2);
        
        return roundKey;
    }
    
    public static List<Boolean> doShift(List<Boolean> list, int shiftBy){
        
        List<Boolean> helperList = new ArrayList<>();

        for(int j = 0; j < (list.size()); j++){
            if((j + shiftBy) < list.size()){
                helperList.add(list.get(j + shiftBy));
            }else{
                helperList.add(list.get(j + shiftBy - list.size()));
            }
        }
        return helperList;
    }
    
    public static void doRound(int roundNumber){
        
        //temporary list for switching left and right side
        List<Boolean> rightHelper = Main.rightSide;
        
        Main.rightSide = doXOR(Main.leftSide
                , doFeistelFunction(Main.rightSide, roundNumber));
        Servitor.booleanSTDReader(Main.rightSide
                , "        next Left side = this Right side");
        Main.leftSide= rightHelper;
    }
    
    public static List<Boolean> doFeistelFunction(List<Boolean> rightSide
            , int roundNumber){
        
        List<Boolean> output= new ArrayList<>();
        
        //do expansion, preparing input for Feistel function
        rightSide = doPermute(rightSide, EXPANSION);
        Servitor.booleanSTDReader(rightSide, "        E(Right side)");
        
        
        List<Boolean> sBoxInput = new ArrayList<>();
        //figure out whether this is encryption or decryption
        if(Main.encrypt){
            Servitor.booleanSTDReader(Main.keyRing.get(roundNumber)
                    , "        Round key ");        
            sBoxInput = doXOR(rightSide, Main.keyRing.get(roundNumber));
        }else{
            Servitor.booleanSTDReader(Main.keyRing.get(15-roundNumber)
                    , "        Round key ");        
            sBoxInput = doXOR(rightSide, Main.keyRing.get(15-roundNumber));
        }
        Servitor.booleanSTDReader(sBoxInput
                , "        E(Right side) XOR roundKey");
        
        output = doPermute(useSBoxes(sBoxInput), P_PERMUTATION);
        Servitor.booleanSTDReader(output, "        f(rightSide, roundKey)");
        
        return output;
    }
    
    public static List<Boolean> doXOR(List<Boolean> listOne
            , List<Boolean> listTwo){
        
        List<Boolean> output = new ArrayList<>();
        
        for(int i = 0; i< listOne.size(); i++){
            output.add(! listOne.get(i).equals(listTwo.get(i)));
        }
        
        return output;
    }
    
    public static List<Boolean> useSBoxes(List<Boolean> input){
        
        List<Boolean> output = new ArrayList<>();
        
        output.addAll(useSBox(S_BOX_1, input, 0));
        output.addAll(useSBox(S_BOX_2, input, 6));
        output.addAll(useSBox(S_BOX_3, input, 12));
        output.addAll(useSBox(S_BOX_4, input, 18));
        output.addAll(useSBox(S_BOX_5, input, 24));
        output.addAll(useSBox(S_BOX_6, input, 30));
        output.addAll(useSBox(S_BOX_7, input, 36));
        output.addAll(useSBox(S_BOX_8, input, 42));
        
        Servitor.booleanSTDReader(output, "        sBox output");
        return output;
    }

    private static List<Boolean> useSBox(int[][] sBox, List<Boolean> input
            , int i) {
        
//        int row = Integer.parseInt(
//                booleanToBin(input.get(i)).toString()
//                + booleanToBin(input.get(i+5)).toString(), 2
//        );
        int row = Integer.parseInt(
                Integer.toString(booleanToBin(input.get(i)))
                + Integer.toString(booleanToBin(input.get(i+5)))
                , 2);
        int column = Integer.parseInt(
                Integer.toString(booleanToBin(input.get(i+1)))
                + Integer.toString(booleanToBin(input.get(i+2)))
                + Integer.toString(booleanToBin(input.get(i+3)))
                + Integer.toString(booleanToBin(input.get(i+4)))
                , 2);
        int sBoxValue = sBox[row][column];
        
        List<Boolean> output = new ArrayList<>();
        
        int helperInt = sBoxValue;
        
        output.add((helperInt / 8) > 0);
        helperInt = helperInt % 8;
        output.add((helperInt / 4) > 0);
        helperInt = helperInt % 4;
        output.add((helperInt / 2) > 0);
        helperInt = helperInt % 2;
        output.add((helperInt / 1) > 0);
        
        return output;
    }
    
    private static int booleanToBin(Boolean b){
        return b ? 1 : 0;
    }
    
    public static void booleanSTDReader(List<Boolean> input, String whatIRead){;
        StringBuilder sb = new StringBuilder();
        for(Boolean b:input){
            if(b){
                sb.append('1');
            }else{
                sb.append('0');
            }
        };
        System.out.printf("%-50S %s %n", whatIRead, sb.toString());
    }
}
