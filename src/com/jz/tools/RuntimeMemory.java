package com.jz.tools;

import java.text.DecimalFormat;

public class RuntimeMemory {
    private static final int MB = 1024 * 1024;
    public static DecimalFormat df = new DecimalFormat("#####.##");

    public static void getMemory() {
        Runtime run = Runtime.getRuntime();
        long max = run.maxMemory();
        long total = run.totalMemory();
        long free = run.freeMemory();
        long usable = max - total + free;
        StringBuffer sb = new StringBuffer();
        sb.append("[RuntimeMemory] Max :").append(getMb(max)).append(
                " Assigned :").append(getMb(total)).append(" Free :").append(
                getMb(free)).append(" Usable :").append(getMb(usable));
        System.out.println(sb.toString());
    }
    private static String getMb(long memory) {
        double mb = (double) memory / MB;
        return "[" + df.format(mb) + " MB]; ";
    }
}