//package com.company;
//import com.hankcs.hanlp.HanLP;
//import com.hankcs.hanlp.dictionary.CoreBiGramTableDictionary;
//import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
//import com.hankcs.hanlp.seg.CRF.CRFSegment;
//import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
//import com.hankcs.hanlp.seg.Other.CommonAhoCorasickDoubleArrayTrieSegment;
//import com.hankcs.hanlp.seg.Segment;
//import com.hankcs.hanlp.seg.common.Term;
//import com.hankcs.hanlp.corpus.tag.Nature;
//import com.hankcs.hanlp.dictionary.py.Pinyin;
//import com.hankcs.hanlp.corpus.dictionary.StringDictionary;
//import com.hankcs.hanlp.tokenizer.NotionalTokenizer;
//import com.hankcs.hanlp.dictionary.CoreDictionary;
//
//import java.util.*;
//
//import static com.hankcs.hanlp.corpus.tag.Nature.nz;
//
//
//
//
//public class Main {
//    public static void main(String[] args) {
//
//        String[] sentenceArray = new String[]
//                {
//                        "我们都超爱喝埤酒。",
//                        "坐在沙发，喝酒和看般球给我很高兴。",
//                        "我想要喝的东西，我餲得不得了。",
//                        "天明到厨房去拿啤酒跟絣干，请他的朋友一边看电视一边吃絣干、喝啤酒。",
//                        "他们吃了完絣干，也喝了完啤酒。",
//                        "你是客人，请你座着休息，先看电视吧！"
//                };
//        int wrong = 0;
//        Segment segment = new CRFSegment();
//        segment.enablePartOfSpeechTagging(true);
//        for (String sentence : sentenceArray) {
//            List<Hanzi> hanzis = new ArrayList<>();  // 单个汉字列表
//            List<Term> crfList = segment.seg(sentence); // 分词组
//            System.out.println("整句分词:"+crfList);
//            for (int i = 0; i < crfList.size(); i++) { // 对分词组里的每一组词进行判断
//                Term term = crfList.get(i);
//                // 如果是停用词,信任
//                if(CoreStopWordDictionary.contains(term.word)==true){
//                    char[] characters = term.word.toCharArray();
//                    for (int j = 0; j < term.length(); j++) {
//                        String tmpChar = new Character(characters[j]).toString();
//                        Hanzi word = new Hanzi(tmpChar, HanLP.convertToPinyinString(tmpChar, "", false));
//                        word.setRight();
//                        hanzis.add(word);
//                    }
//                    continue;
//                }
//                // 如果是长度大于2的专有名词,信任
//                if (term.length() > 2 && term.nature == nz) {
//                    char[] characters = term.word.toCharArray();
//                    for (int j = 0; j < term.length(); j++) {
//                        String tmpChar = new Character(characters[j]).toString();
//                        Hanzi word = new Hanzi(tmpChar, HanLP.convertToPinyinString(tmpChar, "", false));
//                        word.setRight();
//                        hanzis.add(word);
//                    }
//                }
//                // 如果是长度=2的专有名词,质疑
//                else if (term.length() == 2 && term.nature == nz) {
//                    char[] characters = term.word.toCharArray();
//                    for (int j = 0; j < term.length(); j++) {
//                        String tmpChar = new Character(characters[j]).toString();
//                        Hanzi word = new Hanzi(tmpChar, HanLP.convertToPinyinString(tmpChar, "", false));
//                        hanzis.add(word);
//                    }
//                }
//                // 如果是长度1,且不是停用词，质疑
//                else if (term.length() == 1) {
//                    Hanzi word = new Hanzi(term.word, HanLP.convertToPinyinString(term.word, "", false));
//                    hanzis.add(word);
//                }
//                // 其他情况,信任
//                else {
//                    char[] characters = term.word.toCharArray();
//                    for (int j = 0; j < term.length(); j++) {
//                        String tmpChar = new Character(characters[j]).toString();
//                        Hanzi word = new Hanzi(tmpChar, HanLP.convertToPinyinString(tmpChar, "", false));
//                        word.setRight();
//                        hanzis.add(word);
//                    }
//                }
//            }
//            // 2-gram检查单个字是否正确
//            System.out.println("n-gram检查前:"+hanzis);
//            for (int i=1;i<hanzis.size();i++) {
//                // 如果前一个为t,当前为t,跳过
//                if (hanzis.get(i - 1).ifright && hanzis.get(i).ifright)
//                    continue;
//
//                    // 其他情况下,检查
//                    // 如果big>0 当前字对的
//                else if (CoreBiGramTableDictionary.getBiFrequency(hanzis.get(i-1).character,hanzis.get(i).character)>0){
//                    hanzis.get(i).ifright = true;
//                    hanzis.get(i-1).ifright = true;
//                }
//                else
//                    continue;
//            }
//            System.out.println("n-gram检查后:"+hanzis);
//            for (int i=0;i<hanzis.size();i++){
//                if(hanzis.get(i).ifright==false){
//                    wrong++;
//                }
//            }
//        }
//        System.out.println("total wrong:"+wrong);
//    }
//}
//
//
//
