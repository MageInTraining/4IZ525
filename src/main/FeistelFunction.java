/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static main.Servitor.permute;
import static tables.Permutations.EXPANSION;

/**
 *
 * @author cen62777
 */
public class FeistelFunction {
    
    public static boolean [] doFeistelFunction(boolean[] right, boolean[] key
            , int roundNumber){
        
        boolean[] x = permute(right, EXPANSION);
        return null;
    }
}