import java.util.HashMap;
import java.util.Map;

/**
 * see [<a href="https://leetcode.cn/problems/words-frequency-lcci/">面试题 16.02. 单词频率</a>]
 */
public class WordsFrequency {

    private Map<String, Integer> map;

    public WordsFrequency(String[] book) {
        map = new HashMap<>();
        for (String s : book) {
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
    }

    public int get(String word) {
        return map.getOrDefault(word, 0);
    }

}
