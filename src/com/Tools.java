package com;

import java.io.*;

/**
 * Created by sunder on 2015/10/1.
 * 工具类
 */
public class Tools {

    public static String readWholeFile(String filename) throws IOException {
        File f = new File(filename);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        String content = "";
        while((line = br.readLine()) != null){
            content += line;
        }
        br.close();
        return content;
    }

    public static void writeToFile(String content, String filename) throws IOException {
        File f = new File(filename);
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        bw.write(content);
        bw.close();
    }

    public static String ensurePath(String folderPath){
        if(!(folderPath.substring(folderPath.length()-1).equals("\\"))){
            folderPath += "\\";
        }
        return folderPath;
    }
}
