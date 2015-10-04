package com;

/**
 * Created by sunder on 2015/10/1.
 * ³£Á¿
 */
public class MyConst {
    static String HOME_PATH = "c:\\Users\\sunder\\Documents\\myfile\\textClassify\\data\\";

    public static String STOP_WORD_FILENAME = HOME_PATH + "stop_words.txt";
    public static String KEY_WORD_FILENAME = HOME_PATH + "key_words.txt";
    public static String TRAIN_FILENAME = HOME_PATH + "train";
    public static int KEY_WORD_NUMBER = 50;
    public static Double SMALL_NUMBER = 0.0001;

    public static String TEST_FILENAME = HOME_PATH + "test\\test.txt";
    public static String TEST_TERM_FILENAME = HOME_PATH + "test\\term.txt";

    public static String TEST_FOLDER_PATH = HOME_PATH + "test\\documents";
    public static String TEST_TERM_FOLDER_PATH = HOME_PATH + "test\\terms";

    public static String TEST_TERM_IDF_FILENAME = HOME_PATH + "test\\idf.txt";

    public static String LINE_SEPARATE = "\n";
}
