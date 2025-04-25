package com.getthecolor.nailtonebe.utils;

public class ColorUtils {

    public static int[] hexToRgb(String hex) {
        hex = hex.replace("#", "");
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new int[]{r, g, b};
    }

    public static double colorDistance(int[] rgb1, int[] rgb2) {
        int rDiff = rgb1[0] - rgb2[0];
        int gDiff = rgb1[1] - rgb2[1];
        int bDiff = rgb1[2] - rgb2[2];
        return Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
    }
}
