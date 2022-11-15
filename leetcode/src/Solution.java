import java.util.*;

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

    /**
     * see [https://leetcode.cn/problems/count-the-number-of-consistent-strings/]
     */
    public int countConsistentStrings(String allowed, String[] words) {
        boolean[] map = new boolean[26];
        for (int i = 0; i < allowed.length(); i++) {
            map[allowed.charAt(i) - 'a'] = true;
        }
        int res = 0;
        for (String s : words) {
            boolean contains = true;
            for (int i = 0; i < s.length(); i++) {
                if (!map[s.charAt(i) - 'a']) {
                    contains = false;
                    break;
                }
            }
            if (contains) {
                res++;
            }
        }
        return res;
    }

    /**
     * see [https://leetcode.cn/problems/sub-sort-lcci/]
     */
    public int[] subSort(int[] array) {
        if (array == null || array.length == 0) {
            return new int[]{-1, -1};
        }

        int left = -1;
        int right = -1;
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < array.length; i++) {
            int cur = array[i];

            // 正序遍历 确认右边的最小值都大于 中间最大
            if (cur >= max) {
                max = Math.max(cur, max);
            } else {
                right = i;
            }

            // 倒叙遍历 确认左边的最大值都小于 中间最小
            if (array[array.length - 1 - i] > min) {
                left = array.length - 1 - i;
            } else {
                min = Math.min(array[array.length - 1 - i], min);
            }
        }
        return new int[]{left, right};
    }

    /**
     思路:
     设三指针:左、右、扫描,遵照有序数组的规律,找到左侧最大和右侧最小逆序对,
     从左往右扫描得到最右逆序对,
     从右往左扫描得到最左逆序对,
     这两个逆序对的范围就是所求答案
     */
//    public int[] subSort(int[] array) {
//
//        //0.边界判断
//        if(array==null) return null;
//        if(array.length==0||array.length<2) return new int[]{-1,-1};
//
//        //1.定义需要的数据结构
//        int max = array[0];//假设0号元素是左侧最大值
//        int r = -1;//最右逆序对
//
//        //2.寻找逆序对
//
//        //2.1 寻找最右逆序对
//        for(int i = 1;i < array.length;i++) {
//            int val = array[i];
//            if(val < max) {//当前元素小于左侧最大值,就说明是逆序对
//                r = i;
//            }else {//当前元素大于左侧最大值,就更新max;数组本身就是升序的
//                max = val;
//            }
//        }
//
//        //2.2.数组本来就有序
//        if(r==-1) return new int[]{-1,-1};
//
//        //2.3 寻找最左逆序对
//        int min = array[array.length - 1];//最后一个值就是倒数第二个值右边最小值
//        int l = - 1;//假设最后一个元素就是右侧最小值
//        for(int i = array.length -1; i >= 0;i--) {
//            int val = array[i];
//            if(val > min) {
//                //当前值大于右侧最小值,就说明是逆序对;因为升序数组当前值应该小于右侧最小值
//                l = i;
//            }else {
//                min = val;
//            }
//
//        }
//
//        //4.返回结果
//        return new int[]{l,r};
//    }

    /**
     * see [https://leetcode.cn/problems/master-mind-lcci/]
     */
    public int[] masterMind(String solution, String guess) {
        int[] map1 = new int[4];
        int[] map2 = new int[26];
        for (int i = 0; i < 4; i++) {
            char ch = solution.charAt(i);
            map1[i] = ch;
            map2[ch - 'A'] += 1;
        }

        int exactly = 0;
        int part = 0;
        for (int i = 0; i < 4; i++) {
            char ch = guess.charAt(i);
            if (ch == map1[i]) {
                exactly++;
                map2[ch - 'A'] -= 1;
            }
        }

        for (int i = 0; i < 4; i++) {
            char ch = guess.charAt(i);
            if (map2[ch - 'A'] > 0 && ch != map1[i]) {
                part++;
                map2[ch - 'A'] -= 1;
            }
        }

        return new int[]{exactly, part};
    }

    /**
     * see [https://leetcode.cn/problems/contiguous-sequence-lcci/]
     */
    public int maxSubArray(int[] nums) {
        int max = nums[0];
        int sum = 0;
        for (int n : nums) {
            sum += n;
            if (sum < n) {
                sum = n;
            }

            if (sum > max) {
                max = sum;
            }
        }
        return max;
    }

    /**
     * see [https://leetcode.cn/problems/hanota-lcci/]
     */
    public void hanota(List<Integer> A, List<Integer> B, List<Integer> C) {
        moveHanota(A.size(), A, B, C);
    }

    /**
     * 将n 个圆盘经B 从A 移动到C
     */
    private void moveHanota(int n, List<Integer> A, List<Integer> B, List<Integer> C) {
        if (n == 1) {
            C.add(0, A.remove(0));
            return;
        }

        // 先把最上面n-1个移动到B
        moveHanota(n - 1, A, C, B);
        // 移动最后一个
        C.add(0, A.remove(0));
        // 在移动B上面的n-1个到c
        moveHanota(n - 1, B, A, C);
    }

    /**
     * see [https://leetcode.cn/problems/smallest-difference-lcci/]
     */
    public int smallestDifference(int[] a, int[] b) {
        // 先排序
        Arrays.sort(a);
        Arrays.sort(b);
        long min = Math.abs((long) a[0] - (long) b[0]);
        int i = 0;
        int j = 0;
        while (i < a.length && j < b.length) {
            long ai = (long) a[i];
            long bj = (long) b[j];
            long x = Math.abs(bj - ai);
            if (x == 0) {
                return 0;
            }
            min = Math.min(min, x);
            long diff = ai - bj;
            if (diff > 0) {
                j++;
            } else {
                i++;
            }
        }
        return (int) min;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/living-people-lcci/">面试题 16.10. 生存人数</a>]
     */
    public int maxAliveYear(int[] birth, int[] death) {
        final int base = 1900;
        int[] arr = new int[110];
        for (int i = 0; i < birth.length; i++) {
            int start = birth[i] - base;
            int end = death[i] - base;
            arr[start] += 1; // 存活人数+1
            arr[end + 1] -= 1; // 死亡人数-1
        }
        int year = 0;
        int max = 0;
        int sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
            if (sum > max) {
                max = sum;
                year = i;
            }
        }
        return year + base;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/maximum-lcci/">面试题 16.07. 最大数值</a>]
     */
    public int maximum(int a, int b) {
        long al = a;
        long bl = b;
        return (int) ((Math.abs(al - bl) + al + bl) / 2);
    }

    /**
     * see [<a href="https://leetcode.cn/problems/sum-swap-lcci/">面试题 16.21. 交换和</a>]
     */
    public int[] findSwapValues(int[] array1, int[] array2) {
        int sum1 = 0;
        int sum2 = 0;
        for (int i : array1) {
            sum1 += i;
        }

        for (int i : array2) {
            sum2 += i;
        }

        if ((sum1 + sum2) % 2 != 0) {
            return new int[0];
        }

        int result = (sum1 + sum2) / 2;
        Set<Integer> set = new HashSet<>();

        for (int i : array1) {
            set.add(result - sum1 + i);
        }

        for (int j : array2) {
            if (set.contains(j)) {
                return new int[]{result - sum2 + j, j};
            }
        }
        return new int[0];
    }

    /**
     * see [<a href="https://leetcode.cn/problems/maximum-units-on-a-truck/comments/">1710. 卡车上的最大单元数</a>]
     */
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        int[] map = new int[1001];
        for (int[] arr : boxTypes) {
            map[arr[1]] += arr[0];
        }
        int result = 0;
        for (int i = map.length - 1; i > 0; i--) {
            // 没有能装i个的箱子
            if (map[i] == 0) {
                continue;
            }
            if (truckSize > map[i]) {
                truckSize -= map[i];
                result += (map[i] * i);
            } else {
                result += (truckSize * i);
                return result;
            }
        }
        return result;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/factorial-zeros-lcci/">面试题 16.05. 阶乘尾数</a>]
     */
    public int trailingZeroes(int n) {
        int count = 0;
        while (n >= 5) {
            n /= 5;
            count += n;
        }
        return count;
    }

    public static void main(String[] args) {
        Solution s = new Solution();
        s.smallestDifference(new int[]{Integer.MIN_VALUE, 1}, new int[]{Integer.MAX_VALUE, 0});
    }

}
