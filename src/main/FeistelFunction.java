/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.ArrayList;
import java.util.List;
import static main.Servitor.doXOR;
import static main.Servitor.permute;
import static tables.Permutations.EXPANSION;
import static tables.SBoxes.S_BOX_1;
import static tables.SBoxes.S_BOX_2;
import static tables.SBoxes.S_BOX_3;
import static tables.SBoxes.S_BOX_4;
import static tables.SBoxes.S_BOX_5;
import static tables.SBoxes.S_BOX_6;
import static tables.SBoxes.S_BOX_7;
import static tables.SBoxes.S_BOX_8;

/**
 *
 * @author cen62777
 */
public class FeistelFunction {
    
    public static boolean [] doFeistelFunction(boolean[] right, boolean[] key
            , int roundNumber){
        
        boolean[] x = permute(right, EXPANSION);
        
        boolean[] y = doXOR(x, key);

        boolean[] fFunctionOutput = null;
        int iterator = 0;
        
        for(boolean b: Servitor.useSBox(y, S_BOX_1)){
            fFunctionOutput[iterator] = b;
            iterator++;
            
        }
        for(boolean b: Servitor.useSBox(y, S_BOX_2)){
            fFunctionOutput[iterator] = b;
            iterator++;
        }
        for(boolean b: Servitor.useSBox(y, S_BOX_3)){
            fFunctionOutput[iterator] = b;
            iterator++;
        }
        for(boolean b: Servitor.useSBox(y, S_BOX_4)){
            fFunctionOutput[iterator] = b;
            iterator++;
        }
        for(boolean b: Servitor.useSBox(y, S_BOX_5)){
            fFunctionOutput[iterator] = b;
            iterator++;
        }
        for(boolean b: Servitor.useSBox(y, S_BOX_6)){
            fFunctionOutput[iterator] = b;
            iterator++;
        }
        for(boolean b: Servitor.useSBox(y, S_BOX_7)){
            fFunctionOutput[iterator] = b;
            iterator++;
        }
        for(boolean b: Servitor.useSBox(y, S_BOX_8)){
            fFunctionOutput[iterator] = b;
            iterator++;
        }
        
        return fFunctionOutput;
    }
}