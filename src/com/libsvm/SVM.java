package com.libsvm;

import java.io.IOException;

/**
 * Created by sunder on 2015/10/5.
 * 训练和预测
 */
public class SVM {
    public static void train(String filename, String modelFilename) throws IOException {
        String[] parm = {"-s", "0", "-t", "2", filename, modelFilename};
        svm_train.main(parm);
    }

    public static void predict(String filename, String modelFilename, String outFilename) throws IOException {
        String[] parm = {"-b", "0", filename, modelFilename, outFilename};
        svm_predict.main(parm);
    }
}
