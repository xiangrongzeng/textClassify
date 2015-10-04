package com.libsvm;

import com.MyConst;
import com.Tools;
import com.tfidf.TF;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunder on 2015/10/4.
 * 将数据整理成libsvm需要的格式
 */
public class DataPrepare {

    public static void prepare(String folderPath,
                               String idfFilename,
                               String keyWordsFilename,
                               String trainFilename) throws IOException {
        File trainFile = new File(trainFilename);
        BufferedWriter trainFileWriter = new BufferedWriter(new FileWriter(trainFile, true));

        Map<String, Double> idf = loadIdf(idfFilename);

        ArrayList<String> keyWords = loadKeywords(keyWordsFilename);

        // 依次获取文件夹中文件的tf值
        folderPath = Tools.ensurePath(folderPath);
        String[] seg = folderPath.split("\\\\");
        String folderName = seg[seg.length-1];

        File folder = new File(folderPath);
        String[] fileList = folder.list();
        for(String filename : fileList){
            Map<String, Double> tf = TF.calcDf(folderPath + filename);

            turnToVector(tf, idf, keyWords, folderName, trainFileWriter);
        }

        trainFileWriter.close();
    }

    public static Map<String, Double> loadIdf(String idfFilename){
        // 读入idf值
        Map<String, Double> idf = new HashMap<>();
        File idfFile = new File(idfFilename);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(idfFile));
            String line;
            while((line = br.readLine()) != null){
                line = line.trim();
                String[] seg = line.split(",");
                String term = seg[0];
                Double termIdf = Double.valueOf(seg[1]);
                idf.put(term, termIdf);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return idf;
    }

    public static ArrayList<String> loadKeywords(String keyWordsFilename) {
        ArrayList<String> keyWords = new ArrayList<>();
        File idfFile = new File(keyWordsFilename);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(idfFile));
            String line;
            while((line = br.readLine()) != null) {
                String term = line.trim();
                keyWords.add(term);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return keyWords;
    }

    public static void turnToVector(Map<String, Double> tf,
                                     Map<String, Double> idf,
                                     ArrayList<String> keyWords,
                                     String folderName,
                                     BufferedWriter trainFileWriter) throws IOException {

        String vector = folderName;
        for(int i = 0; i < keyWords.size(); i++){
            String keyWord = keyWords.get(i);
            double termTf;
            if(tf.containsKey(keyWord)){
                termTf = tf.get(keyWord);

                double termIdf;
                if(idf.containsKey(keyWord)){
                    termIdf = idf.get(keyWord);
                }else {
                    termIdf = MyConst.SMALL_NUMBER;
                }

                double termTfIdf = termIdf*termTf;

                vector += " " + (i+1) + ":" + String.valueOf(termTfIdf);
            }
        }

        trainFileWriter.write(vector);
        trainFileWriter.newLine();
    }
}
