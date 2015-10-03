package com.tfidf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunder on 2015/10/2.
 * 计算文档中每个词的tf值
 */
public class TF {
    /**
     * 计算单个文件中每个词的df值
     * @param inFilename 文件路径
     * @return 词为键，df为值的hashmap
     * @throws IOException
     */
    public static Map<String, Double> calcDf(String inFilename) throws IOException {
        Map<String, Integer> termStatistics = new HashMap<>();
        int totalTermNumber = 0;

        // 统计
        File f = new File(inFilename);
        BufferedReader br = new BufferedReader(new FileReader(f));
        String line;
        while((line = br.readLine()) != null){
            String term = line.trim();
            int termNumber;
            if(termStatistics.containsKey(term)){
                termNumber = termStatistics.get(term);
            }else{
                termNumber = 0;
            }
            termStatistics.put(term, termNumber+1);

            totalTermNumber += 1;
        }

        // 计算
        Map<String, Double> tf = new HashMap<>();
        for(String term : termStatistics.keySet()){
            int termNumber = termStatistics.get(term);
            double thisDf = 1.0*termNumber/totalTermNumber;
            tf.put(term, thisDf);
        }

        return tf;
    }
}
