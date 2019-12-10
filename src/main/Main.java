/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;
import static main.Servitor.binToHex;
import static main.Servitor.doPermute;
import static main.Servitor.hexToBin;
import static main.Servitor.joinBlocks;
import static main.Servitor.splitToBlocks;
import static main.Servitor.updateKey;
import static tables.Permutations.FINAL_PERMUTATION;
import static tables.Permutations.INITIAL_PERMUTATION;
import static tables.Permutations.PC_1;

/**
 *
 * @author cen62777
 */
public class Main{
    
    static List<Boolean> rightSide;
    static List<Boolean> leftSide;
    static List<Boolean> leftKey;
    static List<Boolean> rightKey;
    
    public static void main(String[] args){
        
        List<Boolean> msgEncoded = hexToBin("0123456789ABCDEF");
        List<Boolean> keyEncoded = hexToBin("133457799BBCDFF1");
        
        List<List<Boolean>> msgEncodedBlocks = splitToBlocks(msgEncoded);
                
        List<List<Boolean>> msgEncryptedBlocks = new ArrayList<>();
        
        //Go through Feistel Schema for each 64-bit block of encoded message
        for(List<Boolean> block: msgEncodedBlocks){
            
            System.out.println("///***A BLOCK***///");
            msgEncryptedBlocks.add(mainSequence(block, keyEncoded));
        }
        
        //Join all the encrypted blocks into output list
        List<Boolean> msgEncr = joinBlocks(msgEncryptedBlocks);
        
        System.out.println("Encrypted message: " + binToHex(msgEncr));        
    }

    private static List<Boolean> mainSequence(List<Boolean> block
            , List<Boolean> keyEncoded) {
        
        List<Boolean> initialPermutation
                = Servitor.doPermute(block, INITIAL_PERMUTATION);
        
        leftSide = initialPermutation.subList(0, 32);
        Servitor.booleanSTDReader(leftSide
                , "    Left side after initial permutation");
        rightSide = initialPermutation.subList(32, 64);
        Servitor.booleanSTDReader(rightSide
                , "    Right side after initial permutation");
                
        List<Boolean> keyPermuted = doPermute(keyEncoded, PC_1);
        
        leftKey = keyPermuted.subList(0, 28);
        rightKey = keyPermuted.subList(28, 56);
        
        //go through all 16 rounds of Feistel Schema
        for(int i = 0; i < 16; i++){
            
            //gets round key from key
            //key itself is being modified inside run of the method
            //left and right side are being modified inside run of the method
            System.out.println("    //**ROUND " + (i+1) + "**//");
            Servitor.doRound(leftSide, rightSide
                    , updateKey(leftKey, rightKey, i));
        }
        
        //final joining of left and right side of this 64-bit block
        //leftSide.addAll(rightSide);
        List<Boolean> output = new ArrayList<>();
        output.addAll(rightSide);
        output.addAll(leftSide);
        
        //final permutation of this 64-bit block
        List<Boolean> finalPermutation = doPermute(output, FINAL_PERMUTATION);
        
        return finalPermutation;
    }
}
