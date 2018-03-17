package checker;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CoreBiGramTableDictionary;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.NShort.NShortSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


class A{
    public int a;
    A(int a){
        this.a = a;
    }
}
class B extends A{
    int b;
    B(int a){
        super(a);
    }
    B(int a,int b){
        super(a);
        this.b = b;
    }
}

public class test {


    public static void main(String[] args) throws IOException {
        System.out.println(CoreBiGramTableDictionary.getBiFrequency("身","体"));
        System.out.print(CoreDictionary.contains("中华"));
        System.out.println(CoreDictionary.getTermFrequency("你好"));
        System.out.println(HanLP.segment("王天华，你好马？"));

        PerceptronLexicalAnalyzer analyzer = new PerceptronLexicalAnalyzer();
        System.out.println(analyzer.seg("王天华，你好马？"));

        Segment segment = new CRFSegment();
        System.out.println(segment.seg("王天华，你好马？"));
    }
}
