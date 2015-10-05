package com;

import com.MyConst;
import com.Segment;
import com.libsvm.DataPrepare;
import com.libsvm.SVM;
import com.tfidf.IDF;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunder on 2015/10/5.
 * 主类
 */
public class Main {
    public static void main(String[] args) {
//        // 分词
//        new Segment(MyConst.STOP_WORD_FILENAME)
//                .cutDataBase(MyConst.TEST_DOC_DATABASE_PATH, MyConst.TEST_TERM_DATABASE_PATH);
//        // 算idf
//        Map<String, Double> idf = new HashMap<>();
//        try {
//            idf = IDF.calcDataBaseIdf(MyConst.TEST_TERM_DATABASE_PATH, MyConst.TEST_TERM_IDF_FILENAME);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // 关键词
//        try {
//            KeyWord.select(idf, MyConst.KEY_WORD_NUMBER, MyConst.KEY_WORD_FILENAME);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // 生成文本向量
//        try {
//            DataPrepare.prepareDataBase(MyConst.TEST_TERM_DATABASE_PATH,
//                    MyConst.TEST_TERM_IDF_FILENAME,
//                    MyConst.KEY_WORD_FILENAME,
//                    MyConst.TRAIN_FILENAME);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // 训练
//        try {
//            SVM.train(MyConst.TRAIN_FILENAME, MyConst.MODEL_FILENAME);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // 预测
        try {
            SVM.predict(MyConst.TRAIN_FILENAME, MyConst.MODEL_FILENAME, MyConst.PREDICT_RESULT_FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
