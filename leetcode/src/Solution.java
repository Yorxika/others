import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Solution {

    /**
     * see [https://leetcode.cn/problems/teemo-attacking/]
     */
    public int findPoisonedDuration(int[] timeSeries, int duration) {
        int total = 0;
        int last = -1;
        for (int time : timeSeries) {
            int nextTime = duration + time - 1;
            total += last < time ? duration : nextTime - last;
            last = nextTime;
        }
        return total;
    }

    /**
     * see [https://leetcode.cn/problems/find-closest-lcci/]
     */
    public int findClosest(String[] words, String word1, String word2) {
        int last1 = -1;
        int last2 = -1;
        int result = Integer.MAX_VALUE;
        for (int i = 0; i < words.length; i++) {
            String cur = words[i];
            if (cur.equals(word1)) {
                last1 = i;
            } else if (cur.equals(word2)) {
                last2 = i;
            }

            if (last1 >= 0 && last2 >= 0) {
                int distance = Math.abs(last2 - last1);
                result = Math.min(distance, result);
                if (result == 1) {
                    return result;
                }
            }

        }
        return result;
    }

    /**
     * see [https://leetcode.cn/problems/smallest-k-lcci/]
     */
    public int[] smallestK(int[] arr, int k) {
//        Arrays.sort(arr);
//        return Arrays.copyOf(arr, k);
        if (k == 0 || arr.length == 0) {
            return new int[0];
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>(Collections.reverseOrder());
        for (int i = 0; i < arr.length; i++) {
            int cur = arr[i];
            if (i < k) {
                queue.add(cur);
            } else if (cur < queue.peek()) {
                queue.poll();
                queue.add(cur);
            }
        }
        int[] result = new int[k];
        for (int i = 0; i < k; i++) {
            result[i] = queue.poll();
        }
        return result;
    }

    /**
     * see [https://leetcode.cn/problems/find-majority-element-lcci/]
     */
    public int majorityElement(int[] nums) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int count = 0;
        int result = -1;
        for (int cur : nums) {
            if (count == 0) {
                result = cur;
            }
            if (cur == result) {
                count++;
            } else {
                count--;
            }
        }
        count = 0;
        for (int n : nums) {
            if (n == result) {
                count++;
            }
        }
        return count * 2 > nums.length ? result : -1;
    }

    /**
     * see [https://leetcode.cn/problems/get-kth-magic-number-lcci/]
     */
    public int getKthMagicNumber(int k) {
        int l = 1, m = 1, n = 1;
        int[] arr = new int[k];
        arr[0] = 1;
        for (int i = 1; i < arr.length; i++) {
            int nextL = 3 * arr[l - 1];
            int nextM = 5 * arr[m - 1];
            int nextN = 7 * arr[n - 1];
            int min = Math.min(nextL, Math.min(nextM, nextN));
            if (min == nextL) {
                arr[i] = nextL;
                l++;
            }
            if (min == nextM) {
                arr[i] = nextM;
                m++;
            }
            if (min == nextN) {
                arr[i] = nextN;
                n++;
            }
        }
        return arr[k - 1];
    }

    /**
     * see [https://leetcode.cn/problems/baby-names-lcci/]
     */
    public String[] trulyMostPopular(String[] names, String[] synonyms) {
        HashMap<String, Integer> map = new HashMap<>();
        for (String name : names) {
            int left = name.indexOf('(');
            int right = name.indexOf(')');
            int frequency = Integer.parseInt(name.substring(left + 1, right));
            map.put(name.substring(0, left), frequency);
        }

        HashMap<String, String> nameMap = new HashMap<>();
        for (String pair : synonyms) {
            int mid = pair.indexOf(',');
            String name1 = pair.substring(1, mid);
            String name2 = pair.substring(mid + 1, pair.length() - 1);

            // 找到祖宗
            while (nameMap.containsKey(name1)) {
                name1 = nameMap.get(name1);
            }

            // 找到祖宗
            while (nameMap.containsKey(name2)) {
                name2 = nameMap.get(name2);
            }

            // 合并
            if (!name2.equals(name1)) {
                int frequency = map.getOrDefault(name1, 0) + map.getOrDefault(name2, 0);
                if (name1.compareTo(name2) < 0) {
                    nameMap.put(name2, name1); // 大往小搜索
                    map.remove(name2);
                    map.put(name1, frequency);
                } else {
                    nameMap.put(name1, name2);
                    map.remove(name1);
                    map.put(name2, frequency);
                }
            }
        }

        String[] result = new String[map.size()];
        int index = 0;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            StringBuilder sb = new StringBuilder(entry.getKey());
            sb.append('(');
            sb.append(entry.getValue());
            sb.append(')');
            result[index] = sb.toString();
            index++;
        }

        return result;
    }

    /**
     * see [https://leetcode.cn/problems/missing-number-lcci/]
     */
    public int missingNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int n = nums.length;
        int result = n * (n + 1) / 2;
        for (int k : nums) {
            result -= k;
        }
        return result;
    }

    /**
     * see [https://leetcode.cn/problems/add-without-plus-lcci/]
     */
    public int add(int a, int b) {
        int m = a ^ b; // 异或 如果进位则为0
        int n = (a & b) << 1; // 进位
        while (n != 0) {
            int temp = m ^ n;
            n = (m & n) << 1;
            m = temp;
        }
        return m;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
    }

}
