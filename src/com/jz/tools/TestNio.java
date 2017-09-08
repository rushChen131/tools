package com.jz.tools;

import java.io.IOException;

public class TestNio {

    /**
     * @author Victor
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        RuntimeMemory.getMemory();
        String infile = "D:\\test.txt";
        String outfile = "D:\\copy.txt";

        NioReader reader=new NioReader(infile);
        NioWriter writer=new NioWriter(outfile);
        String line;

        while ((line = reader.getNextLine()) != null) {
            try {
                if ((line == null) || line.trim().equals("")) {
                    continue;
                }
                writer.putln(line);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        reader.close();
        writer.close();
        long end = System.currentTimeMillis();
        System.out.println((end - start) + "ms");
        RuntimeMemory.getMemory();
    }
}