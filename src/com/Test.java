package com;

import com.libsvm.DataPrepare;
import com.libsvm.SVM;
import com.tfidf.IDF;
import com.tfidf.TF;

import java.io.IOException;
import java.util.Map;

/**
 * Created by sunder on 2015/10/1.
 * 测试功能
 */
public class Test {
    public static void main(String[] args) {
//        new com.Segment(MyConst.STOP_WORD_FILENAME).cutFile(com.MyConst.TEST_FILENAME, com.MyConst.TEST_TERM_FILENAME);
//        new Segment(MyConst.STOP_WORD_FILENAME).cutFolder(MyConst.TEST_FOLDER_PATH, MyConst.TEST_TERM_FOLDER_PATH);

//        try {
//            TF.calcDf(com.MyConst.TEST_TERM_FILENAME);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        try {
//            IDF.doStatistics(MyConst.TEST_TERM_FOLDER_PATH);
//            Map<String, Double> idf = IDF.calcIdf(MyConst.TEST_TERM_IDF_FILENAME);
//            KeyWord.select(idf, MyConst.KEY_WORD_NUMBER, MyConst.KEY_WORD_FILENAME);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        try {
//            DataPrepare.prepare(MyConst.TEST_TERM_FOLDER_PATH,
//                    MyConst.TEST_TERM_IDF_FILENAME,
//                    MyConst.KEY_WORD_FILENAME,
//                    MyConst.TRAIN_FILENAME);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            SVM.train(MyConst.TRAIN_FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
