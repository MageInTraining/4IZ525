/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import static main.Servitor.doShift;
import static main.Servitor.permute;
import static tables.Permutations.PC_2;
import static tables.Shifts.SHIFTS;

/**
 *
 * @author cen62777
 */
public class Round {
    
    public static void doOneRound(boolean[] left, boolean[] right
            , int roundNumber, boolean[] c, boolean[] d){
        
        //round key construction
        boolean[] roundKey = null;
        c = doShift(c, SHIFTS[roundNumber]);
        d = doShift(d, SHIFTS[roundNumber]);
        
        boolean[] helper = null;
        int x = 0;
        for(int i = 0; i < c.length; i++){
            helper[i] = c[i];
            x++;
        }
        for(int i = 0; i < d.length; i++){
            helper[x-1] = d[i];
        }
        
        roundKey = permute(helper, PC_2);
        
        boolean[] fFunctionOutput
                = FeistelFunction.doFeistelFunction(right
                        , roundKey, roundNumber);
        
        boolean[] helperRight = right;
        
        right = Servitor.doXOR(left, fFunctionOutput);
        left = helperRight;
        
    }
    
}
