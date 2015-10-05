package com;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunder on 2015/10/1.
 * 分词
 */
public class Segment {
    ArrayList<String> stopWords = new ArrayList<>();
    public Segment(String stopFilename){
        File f = new File(stopFilename);
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;

            while((line = br.readLine()) != null) {
                String word = line.trim();
                stopWords.add(word);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对单个文件进行分词
     * @param inFilename 需要被分词的文件路径
     * @param outFilename 分词后文件输出路径
     */
    public void cutFile(String inFilename, String outFilename){
        System.out.println("cut file: " + inFilename);

        String content = "";
        try {
            content = Tools.readWholeFile(inFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Term> parses = BaseAnalysis.parse(content);

        String outStr = "";
        for(Term term : parses){
            if(!(stopWords.contains(term.getName()))
                    && !term.getName().matches("\\pP")
                    && !term.getName().matches("\\pZ")){
                outStr += term.getName() + MyConst.LINE_SEPARATE;
            }
        }
        try {
            Tools.writeToFile(outStr, outFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对只包含文本文件的文件夹中的文件依次进行分词
     * @param inFolderPath 需要被分词的文件夹
     * @param outFolderPath 输出的文件夹
     */
    public void cutFolder(String inFolderPath, String outFolderPath){
        inFolderPath = Tools.ensurePath(inFolderPath);
        outFolderPath = Tools.ensurePath(outFolderPath);
        File outFolder = new File(outFolderPath);
        if(!outFolder.exists()){
            outFolder.mkdirs();
        }

        File folder = new File(inFolderPath);
        String[] filenameList = folder.list();
        for(String filename : filenameList){
            cutFile(inFolderPath+filename, outFolderPath+filename);
        }
    }

    /**
     * 对文本数据库进行分词：即该文件夹中包含若干个子文件夹，每个子文件夹中包含的文件属于同一类别
     * 每个文件夹的名字作为该类别的名字，需要为1、2、3等整数作为文件夹的名字
     * @param inFolderPath 需要分词的数据库路径
     * @param outFolderPath 上述分词得到的结果写入该文件夹中，结构跟原来相同。
     */
    public void cutDataBase(String inFolderPath, String outFolderPath){
        inFolderPath = Tools.ensurePath(inFolderPath);
        outFolderPath = Tools.ensurePath(outFolderPath);

        File folder = new File(inFolderPath);
        String[] folderList = folder.list();
        for(String folderName : folderList){
            cutFolder(inFolderPath+folderName, outFolderPath+folderName);
        }
    }
}
