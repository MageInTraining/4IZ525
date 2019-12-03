/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;
import static main.Servitor.binToHex;
import static main.Servitor.hexToBin;
import static main.Servitor.hexToPlain;
import static main.Servitor.joinBlocks;
import static main.Servitor.permute;
import static main.Servitor.plainToHex;
import static main.Servitor.splitToBlocks;
import static tables.Permutations.INITIAL_PERMUTATION;

/**
 *
 * @author cen62777
 */
public class Main{
    
    static String message = "This is a plaintext message, my friends! :)";
    
    public static void main(String[] args){
        System.out.println(
                    hexToPlain(
                            binToHex(
                                    joinBlocks(
                                            splitToBlocks(
                                                    hexToBin(
                                                            plainToHex(message)
                                                    )
                                            )
                                    )
                            )
                    )
            );
    }
    
    public static List<boolean[]> mainSequence(List<boolean[]> inputList){
        
        //For each message block of 64 bit lenght this will be done:
        for(boolean[] bArray:inputList){
            
            boolean[] left = null;
            boolean[] right = null;
            
            // Initial permutation
            boolean[] rightAndLeft = permute(bArray, INITIAL_PERMUTATION);
            
            // Split into Left and Right block
            for(int i = 0; i < 32; i++){
                left[i] = rightAndLeft[i];
                right[32+i] = rightAndLeft[32+i];
            }
            
            //perform one round
            //TODO
        }
        return null;
    }
    
    
}
