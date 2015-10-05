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

    /**
     * 将一个文件夹中的每个文件表示成一个文本向量，写入同一个文件中
     * @param folderPath 文件夹路径
     * @param idfFilename idf文件路径
     * @param keyWordsFilename 关键词文件路径
     * @param trainFilename 最终生成的文件
     * @throws IOException
     */
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

    /**
     * 导入idf值
     * @param idfFilename idf文件路径
     * @return idf Map
     */
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

    /**
     * 导入关键词
     * @param keyWordsFilename 关键词文件路径
     * @return 关键词列表
     */
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

    /**
     * 将一个文件表示成一行向量，并写入到向量文件中
     * @param tf 该文件中每个词的tf值
     * @param idf idf Map
     * @param keyWords 关键词列表
     * @param folderName 文件夹名字，也就是类名
     * @param trainFileWriter 向量文件的写出缓冲
     * @throws IOException
     */
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

    /**
     * 将一个“数据库”中的文件表示成文本向量并写入一个文件中
     * @param dataBasePath 数据库路径
     * @param idfFilename idf文件路径
     * @param keyWordsFilename 关键词文件路径
     * @param trainFilename 生成的文件
     * @throws IOException
     */
    public static void prepareDataBase(String dataBasePath,
                                       String idfFilename,
                                       String keyWordsFilename,
                                       String trainFilename) throws IOException {
        dataBasePath = Tools.ensurePath(dataBasePath);
        File dataBase = new File(dataBasePath);
        for(String folderName : dataBase.list()){
            prepare(dataBasePath + folderName,
                    idfFilename,
                    keyWordsFilename,
                    trainFilename);
        }
    }
}
