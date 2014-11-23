/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.ascaria.network.central.utils;

/**
 *
 * @author Ascaria Quynn
 */
public class FasterMath {

    /**
     * Returns sum of integers as long.
     * @param ints
     * @return
     */
    public static long sum(int[] ints) {
        long sum = 0;
        for(int i : ints) {
            sum += i;
        }
        return sum;
    }
}
