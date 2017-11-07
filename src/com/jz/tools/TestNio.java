package com.jz.tools;

import org.jetbrains.annotations.Nullable;

import java.io.IOException;

public class TestNio {

    /**
     * @author Victor
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        System.out.println(start+"===========================start====================");
        RuntimeMemory.getMemory();
        String infile = "D:\\lyd\\DataImportServiceImpl.java";
        String outfile = "D:\\lyd\\DataImportServiceImplTest.java";
        NioReader reader=new NioReader(infile);
        NioWriter writer=new NioWriter(outfile);
        String line;

        while ((line = reader.getNextLine()) != null) {
            try {
                if ((line == null) || line.trim().equals("")) {
                    continue;
                }
              String reString = reString(line);
                writer.putln(reString);
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

//    public static void main(String[] args) {
//        String s = "312_312_312ihudsaiu_wqhieuqwehn";
//        reString(s);
//    }

    @Nullable
    static String reString (String str){
        int size = str.indexOf("_");
        if (size != -1){
            String frist = str.substring(0,size);
            String type = str.substring(size+1,size+2).toUpperCase();
            String string = str.substring(size+2,str.length());
            return reString(frist+type+string);
        }else {
            return str;
        }
    }
}