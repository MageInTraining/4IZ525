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
import static tables.Permutations.PC_1;

/**
 *
 * @author cen62777
 */
public class Main{
    
    static String message = "This is a plaintext message, my friends! :)";
    static String key = "Alabastr";
    
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
    }
    
    public static void mainSequence(List<List<Boolean>> inputList
            , List<Boolean> key){
        
    } 
}
