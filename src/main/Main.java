package main;

import java.util.ArrayList;
import java.util.List;
import static main.Servitor.binToHex;
import static main.Servitor.doPermute;
import static main.Servitor.hexToBin;
import static main.Servitor.hexToPlain;
import static main.Servitor.joinBlocks;
import static main.Servitor.plainToHex;
import static main.Servitor.splitToBlocks;
import static main.Servitor.updateKey;
import static tables.Permutations.FINAL_PERMUTATION;
import static tables.Permutations.INITIAL_PERMUTATION;
import static tables.Permutations.PC_1;

/**
 *
 * @author Milan Stehlík
 */
public class Main{
    
    
    
    static List<Boolean> rightSide;
    static List<Boolean> leftSide;
    static List<Boolean> leftKey;
    static List<Boolean> rightKey;
    
    //whether to encrypt (TRUE) or decrypt (FALSE) 
    static boolean encrypt; 
    
    //all the keyes to be used in algorithm
    static List<List<Boolean>> keyRing;
    
    public static void main(String[] args){
        
        //Give message and key parametr
        
//        List<Boolean> msgEncoded = hexToBin(plainToHex("PERLSUCK"));
//        List<Boolean> msgEncoded = hexToBin("f9f5ccbd3ec0e2e1d6761c250e525221");
        List<Boolean> msgEncoded = hexToBin(plainToHex("suck me like java"));
//        List<Boolean> msgEncoded = hexToBin("85e813540f0ab405");
//        List<Boolean> keyEncoded = hexToBin("FACCAA56AD663277");
        List<Boolean> keyEncoded = hexToBin(plainToHex("Alabastr"));
        
        //Encrypt or decrypt?
        encrypt = false;
        
        //Splits the message into 64-bit blocks
        List<List<Boolean>> msgEncodedBlocks = splitToBlocks(msgEncoded);
        
        //fill keyRing array - keys for encryption AND decryption
        List<Boolean> keyPermuted = doPermute(keyEncoded, PC_1);
        leftKey = keyPermuted.subList(0, 28);
        rightKey = keyPermuted.subList(28, 56);
        
        keyRing = new ArrayList<>();
        for(int i = 0; i < 16; i++){
            keyRing.add(Servitor.updateKey(leftKey, rightKey, i));
        }
                

        //Go through Feistel Schema for each 64-bit block of encoded message
        List<List<Boolean>> msgEncryptedBlocks = new ArrayList<>();
        
        for(List<Boolean> block: msgEncodedBlocks){
            
            System.out.printf("%n%n%n///***A BLOCK***///%n%n");
            msgEncryptedBlocks.add(mainSequence(block));
        }
        
        //Join all the encrypted blocks into output list
        List<Boolean> msgEncr = joinBlocks(msgEncryptedBlocks);
        
        System.out.printf("%n%S%n", "========================================");
        System.out.printf("%n %-20S %S%n", "output: ", binToHex(msgEncr));
        System.out.printf("%n %-20S %S%n", "output inplain: "
                , hexToPlain(binToHex(msgEncr)));
        System.out.printf("%n%S%n", "========================================");        
    }

    private static List<Boolean> mainSequence(List<Boolean> block) {
        
        List<Boolean> initialPermutation
                = Servitor.doPermute(block, INITIAL_PERMUTATION);
        
        leftSide = initialPermutation.subList(0, 32);
        Servitor.booleanSTDReader(leftSide
                , "    Left side after initial permutation");
        rightSide = initialPermutation.subList(32, 64);
        Servitor.booleanSTDReader(rightSide
                , "    Right side after initial permutation");
        
        //go through all 16 rounds of Feistel Schema
        for(int i = 0; i < 16; i++){
            
            System.out.printf("%n    //**ROUND " + (i+1) + "**//%n");
            
            //left and right side are being modified inside run of the method
            Servitor.doRound(i);
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
