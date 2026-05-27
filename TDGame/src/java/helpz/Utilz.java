/*
 * Decompiled with CFR 0.151.
 */
package helpz;

import java.util.ArrayList;

public class Utilz {
    public static int[][] ArrayListTo2Dint(ArrayList<Integer> list, int ySize, int xSize) {
        int[][] newArr = new int[ySize][xSize];
        int j = 0;
        while (j < newArr.length) {
            int i = 0;
            while (i < newArr[j].length) {
                int index = j * ySize + i;
                newArr[j][i] = list.get(index);
                ++i;
            }
            ++j;
        }
        return newArr;
    }

    public static int[] TwoDto1DintArr(int[][] twoArr) {
        int[] oneArr = new int[twoArr.length * twoArr[0].length];
        int j = 0;
        while (j < twoArr.length) {
            int i = 0;
            while (i < twoArr[j].length) {
                int index = j * twoArr.length + i;
                oneArr[index] = twoArr[j][i];
                ++i;
            }
            ++j;
        }
        return oneArr;
    }

    public static int GetHypoDistance(float x1, float y1, float x2, float y2) {
        float xDiff = Math.abs(x1 - x2);
        float yDiff = Math.abs(y1 - y2);
        return (int)Math.hypot(xDiff, yDiff);
    }
}

