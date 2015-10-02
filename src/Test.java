import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sunder on 2015/10/1.
 * ≤‚ ‘
 */
public class Test {
    public static void main(String[] args) {
//        new Segment().cutFile(MyConst.TEST_FILENAME, MyConst.TEST_TERM_FILENAME);
        new Segment(MyConst.STOP_WORD_FILENAME).cutFolder(MyConst.TEST_FOLDER_PATH, MyConst.TEST_TERM_FOLDER_PATH);

        try {
            IDF.doStatistics(MyConst.TEST_TERM_FOLDER_PATH);
            IDF.calcIdf(MyConst.TEST_TERM_IDF_FILENAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        try {
//            TF.calcDf(MyConst.TEST_TERM_FILENAME);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if("£ª".matches("\\pP")){
//            System.out.println("yes");
//        }
    }
}
