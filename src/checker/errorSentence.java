package checker;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 * 包含句子以及错误信息的结构体
 */
public class errorSentence{
    String sentence;
    boolean ifright;
    Map<Integer,String> correction;

    public errorSentence(){}
    public errorSentence(String s){
        this.sentence = s;
        correction = new HashMap<>();
    }

    // 添加错字位置及正确的字
    public void addCorrection(int i,String character){
        correction.put(i,character);
    }

    // 从相应位置找到对应的字
    public String getLocCorrection(int i){
        try{
            return correction.get(i);
        }
        catch (Exception e){
            return "";
        }
    }

    // 返回所有错字位置
    public Set<Integer> getAllLoc(){
        return correction.keySet();
    }

    public static void main(String[] args) {

    }
}
