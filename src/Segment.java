import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;

import java.io.IOException;
import java.util.List;

/**
 * Created by sunder on 2015/10/1.
 * ·Ö´Ê
 */
public class Segment {

    public void cutFile(String inFilename, String outFilename){
        System.out.println("cut file: " + inFilename);

        String content = "";
        try {
            content = Tools.readWholeFile(inFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(content);

//        System.out.println(BaseAnalysis.parse(content));
        List<Term> parses = BaseAnalysis.parse(content);

        String outStr = "";
        for(Term term : parses){
            outStr += term.getName() + "\n";
        }
        try {
            Tools.writeToFile(outStr, outFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
