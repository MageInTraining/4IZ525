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
    
    static String message = "This is a plaintext message, my friends! :)";
    static String key = "Alabaster";
    
    static List<Boolean> rightSide;
    static List<Boolean> leftSide;
    static List<Boolean> leftKey;
    static List<Boolean> rightKey;
    
    public static void main(String[] args){
//        System.out.println(
//                    hexToPlain(
//                            binToHex(
//                                    joinBlocks(
//                                            splitToBlocks(
//                                                    hexToBin(
//                                                            plainToHex(message)
//                                                    )
//                                            )
//                                    )
//                            )
//                    )
//            );
//        List<Boolean> msgEncoded = hexToBin(plainToHex(message));
//        List<Boolean> keyEncoded = hexToBin(plainToHex(key));
        
        List<Boolean> msgEncoded = hexToBin("0123456789ABCDEF");
        List<Boolean> keyEncoded = hexToBin("133457799BBCDFF1");
        
        List<List<Boolean>> msgEncBlocks = splitToBlocks(msgEncoded);
        
        List<List<Boolean>> msgEncrBlocks = new ArrayList<>();
        
        //Go through Feistel Schema for each 64-bit block of encoded message
        for(List<Boolean> block: msgEncBlocks){
            
             msgEncrBlocks.add(mainSequence(block, keyEncoded));
             
        }
        
        List<Boolean> msgEncr = joinBlocks(msgEncrBlocks);
        
//        System.out.println("Encrypted message: "
//                + hexToPlain(binToHex(msgEncr)));
        System.out.println("Encrypted message: "
                + binToHex(msgEncr));
        
    }

    private static List<Boolean> mainSequence(List<Boolean> block
            , List<Boolean> keyEncoded) {
        
        List<Boolean> initialPermutation
                = Servitor.doPermute(block, INITIAL_PERMUTATION);
        
        Main.leftSide = initialPermutation.subList(0, 32);
        Main.rightSide = initialPermutation.subList(32, 64);
        
        List<Boolean> keyPermuted = doPermute(keyEncoded, PC_1);
        
        Main.leftKey = keyPermuted.subList(0, 28);
        Main.rightKey = keyPermuted.subList(28, 56);
        
        //go through all 16 rounds of Feistel Schema
        for(int i = 0; i < 16; i++){
            
            //gets round key from key
            //key itself is being modified inside run of the method
            //left and right side are being modified inside run of the method
            Servitor.doRound(Main.leftSide, Main. rightSide, updateKey(Main.leftKey, Main.rightKey, i));
        }
        
        //final joining of left and right side of this 64-bit block
        Main.leftSide.addAll(Main.rightSide);
        
        //final permutation of this 64-bit block
        List<Boolean> finalPermutation = doPermute(Main.leftSide, FINAL_PERMUTATION);
        
        return finalPermutation;
    }
}
