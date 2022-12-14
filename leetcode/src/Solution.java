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

    /**
     * see [<a href="https://leetcode.cn/problems/global-and-local-inversions/">775. 全局倒置与局部倒置</a>]
     */
    public boolean isIdealPermutation(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            if (Math.abs(nums[i] - i) > 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/t9-lcci/">面试题 16.20. T9键盘</a>]
     */
    public List<String> getValidT9Words(String num, String[] words) {
        int[] map = new int[]{2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9};
        int n = num.length();
        int[] nums = new int[n];
        // 为减少计算，把num字符串转换成int数组
        for (int i = 0; i < n; i++) {
            nums[i] = num.charAt(i) - '0';
        }
        List<String> res = new ArrayList<>();
        for (String word : words) {
            boolean flag = true;
            for (int i = 0; i < nums.length; i++) {
                if (map[word.charAt(i) - 'a'] != nums[i]) {
                    flag = false;
                    break;
                }
            }
            if (flag) {
                res.add(word);
            }
        }
        return res;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/number-of-matching-subsequences/">792. 匹配子序列的单词数</a>]
     */
    public int numMatchingSubseq(String s, String[] words) {
        Map<Integer, List<Integer>> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            int key = ch - 'a';
            List<Integer> list = map.getOrDefault(key, new ArrayList<>());
            list.add(i);
            map.put(key, list);
        }

        int count = 0;
        for (String w : words) {
            boolean flag = true;
            int prePosition = -1;
            for (int j = 0; j < w.length(); j++) {
                int key = w.charAt(j) - 'a';
                if (!map.containsKey(key)) {
                    flag = false;
                    break;
                }
                List<Integer> list = map.get(key);
                // 找到第一个index比prePosition大的
                int l = 0;
                int r = list.size() - 1;
                while (l < r) {
                    int mid = (l + r) >> 1;
                    if (list.get(mid) > prePosition) {
                        r = mid;
                    } else {
                        l = mid + 1;
                    }
                }
                if (r < 0 || list.get(r) <= prePosition) {
                    flag = false;
                    break;
                }
                prePosition = list.get(r);
            }

            if (flag) {
                count++;
            }
        }
        return count;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/sum-of-subsequence-widths/">891. 子序列宽度之和</a>]
     */
    public int sumSubseqWidths(int[] nums) {
        int mod = (int) (1e9 + 7);
        // 首先打表计算2^n值
        int n = nums.length + 1;
        long[] value = new long[n];
        value[0] = 1; // 2^0 = 1
        for (int i = 1; i < n; i++) {
            value[i] = value[i - 1] * 2 % mod;
        }

        long ans = 0;
        // 排序后，对于任意一个数i，在其后面的k-i-1个数中，共有2^(k-i-1)个子列
        // 在其之前 共有2^i 个子列
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            // nums[i]在2^i 个子列中充当了最大值
            ans += (value[i] * nums[i]) % mod;
            ans %= mod;
            // 在2^n - i - 1 个子列中充当了最小值
            ans -= (value[nums.length - i - 1] * nums[i]) % mod;
            ans %= mod;
        }
        return (int) ans;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/find-the-highest-altitude/">1732. 找到最高海拔</a>]
     */
    public int largestAltitude(int[] gain) {
        int max = 0;
        int pre = 0;
        for (int i = 0; i < gain.length; i++) {
            int cur = pre + gain[i];
            max = Math.max(cur, max);
            pre = cur;
        }
        return max;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/pattern-matching-lcci/">面试题 16.18. 模式匹配</a>]
     */
    public boolean patternMatching(String pattern, String value) {
        int a = 0;
        int b = 0;
        int indexA = -1, indexB = -1;
        boolean flagA = false, flagB = false;
        boolean[] map = new boolean[pattern.length()];
        // 统计a b数目
        for (int i = 0; i < pattern.length(); i++) {
            if (pattern.charAt(i) - 'a' == 0) {
                a++;
                map[i] = true;
                if (!flagA) {
                    indexA = i;
                    flagA = true;
                }
            } else {
                b++;
                if (!flagB) {
                    flagB = true;
                    indexB = i;
                }
            }
        }
        // 空串
        if (value == null || value.isEmpty()) {
            return a <= 0 || b <= 0;
        }
        // 只有1个模式时，直接匹配全部
        if (a == 1 || b == 1) {
            return true;
        }
        // 暴力匹配
        for (int aLength = 0; aLength < value.length(); aLength++) {
            for (int bLength = 0; bLength < value.length(); bLength++) {
                // 求解ax + by = n
                if (aLength * a + bLength * b != value.length()) {
                    continue;
                }
                String pa = "";
                if (indexA >= 0) {
                    // 构造字符串a b的数量 * y的长度
                    pa = value.substring(bLength * indexA, bLength * indexA + aLength);
                }
                String pb = "";
                if (indexB >= 0) {
                    // 构造字符串b
                    pb = value.substring(aLength * indexB, aLength * indexB + bLength);
                }
                // a b 所代表的字符串不能相同
                if (pa.equals(pb)) {
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                for (boolean v : map) {
                    if (v) {
                        sb.append(pa);
                    } else {
                        sb.append(pb);
                    }
                }
                if (value.equals(sb.toString())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/champagne-tower/">799. 香槟塔</a>]
     */
    public double champagneTower(int poured, int query_row, int query_glass) {
        double[][] ca = new double[102][102];
        ca[0][0] = poured;
        for (int l = 0; l <= query_row; l++) {
            for (int r = 0; r <= l; r++) {
                double d = (ca[l][r] - 1.0) / 2;
                if (d > 0) {
                    ca[l + 1][r] += d;
                    ca[l + 1][r + 1] += d;
                }
            }
        }
        return Math.min(1.0, ca[query_row][query_glass]);
    }

    /**
     * see [<a href="https://leetcode.cn/problems/soup-servings/">808. 分汤</a>]
     */
    public double soupServings(int n) {
        // 抄作业
        if (n >= 4451) {
            return 1;
        }
        double[][] dp = new double[n + 1][n + 1];
        return soupServings(n, n, dp);
    }

    private double soupServings(int a, int b, double[][] dp) {
        if (a <= 0) {
            // a b 同时变为空的概率 / 2
            if (b <= 0) {
                return 0.5;
            }
            // a 为空
            return 1;
        }
        if (b <= 0) {
            return 0;
        }
        if (dp[a][b] != 0) {
            return dp[a][b];
        }
        //四叉树分叉
        dp[a][b] += 0.25 * soupServings(a - 100, b - 0, dp);
        dp[a][b] += 0.25 * soupServings(a - 75, b - 25, dp);
        dp[a][b] += 0.25 * soupServings(a - 50, b - 50, dp);
        dp[a][b] += 0.25 * soupServings(a - 25, b - 75, dp);
        return dp[a][b];
    }

    /**
     * see [<a href="https://leetcode.cn/problems/nth-magical-number/">878. 第 N 个神奇数字</a>]
     */
    public int nthMagicalNumber(int N, int A, int B) {
        // 最小公倍数 C
        // 目标N 满足 x / A + X / B - x / C >= N
        // 其中x 为任意一正整数
        int C = A * B / nthMagicalNumberGcd(A, B);
        int n = C / A + C / B - 1;
        long div = N / n;
        long mod = N % n;
        long x = xthMagicalNumber(A, B, mod);
        int M = (int) 1e9 + 7;
        return (int) ((div * C + x) % M);
    }

    /**
     * 求最大公约数
     */
    private int nthMagicalNumberGcd(int a, int b) {
        return b == 0 ? a : nthMagicalNumberGcd(b, a % b);
    }

    private long xthMagicalNumber(long a, long b, long x) {
        if (x == 0) {
            return 0;
        }
        double sum = a + b;
        long x0 = (long) (b * x / sum);
        long x1 = (long) (b * (x + 1) / sum);
        if (x1 == x0) {
            return xthMagicalNumber(b, a, x);
        }
        return x1 * a;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/maximum-number-of-balls-in-a-box/">1742. 盒子中小球的最大数量</a>]
     */
    public int countBalls(int lowLimit, int highLimit) {
        int[] map = new int[46];
        int firstIndex = countBallsN(lowLimit);
        map[firstIndex] = 1; // 初始化第一个数字lowLimit所在编号盒子的小球数量
        for (int i = lowLimit; i < highLimit; i++) {
            for (int pre = i; pre % 10 == 9; pre /= 10) {
                // 根据前一个数的末位是否为9，来重新定位下一个数的位置
                firstIndex -= 9; // 前移9位
            }
            // 下一个数字所在盒子
            firstIndex++;
            map[firstIndex] += 1;
        }
        int max = 0;
        for (int k : map) {
            max = Math.max(k, max);
        }
        return max;
    }

    private int countBallsN(int i) {
        int k = 0;
        while (i > 0) {
            k += i % 10;
            i /= 10;
        }
        return k;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/number-of-subarrays-with-bounded-maximum/">795. 区间子数组个数</a>]
     */
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int res = 0;
        int l = -1; // 左指针
        int r = -1; // 右指针
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] >= left && nums[i] <= right) {
                r = i;
            } else if (nums[i] > right) {
                l = i;
                r = -1;
            }
            if (r != -1) {
                res += r - l;
            }
        }
        return res;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/expressive-words/">809. 情感丰富的文字</a>]
     */
    public int expressiveWords(String s, String[] words) {
        int ans = 0;
        for (String word : words) {
            if (canExpand(word, s)) {
                ans++;
            }
        }
        return ans;
    }

    /**
     * a 能扩展到B吗
     */
    private boolean canExpand(String a, String b) {
        int i = 0;
        int j = 0;
        while (i < a.length() && j < b.length()) {
            if (a.charAt(i) != b.charAt(j)) {
                return false;
            }
            int charA = a.charAt(i);
            int ca = 0;
            while (i < a.length() && a.charAt(i) == charA) {
                ca++;
                i++;
            }
            int charB = b.charAt(j);
            int cb = 0;
            while (j < b.length() && b.charAt(j) == charB) {
                cb++;
                j++;
            }
            if (ca == cb) {
                continue;
            }
            if (ca > cb || cb < 3) {
                return false;
            }
        }
        return i == a.length() && j == b.length();
    }

    /**
     * see [<a href="https://leetcode.cn/problems/check-if-array-is-sorted-and-rotated/">1752. 检查数组是否经排序和轮转得到</a>]
     */
    public boolean check(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            // 校验是否递减
            if (nums[i] > nums[(i + 1) % nums.length]) {
                count++;
            }
        }
        return count <= 1;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/largest-sum-of-averages/">813. 最大平均值和的分组</a>
     */
    public double largestSumOfAverages(int[] nums, int k) {
        // dp[i][k]表示前i个数构成k个子数组时的最大平均值, 那么对于所有 0 <= j <= i-1
        // dp[i][k] = Math.max(dp[i][k], dp[j][k-1] + (A[j+1] + ... + A[i+1]) / (i-j))
        double[][] dp = new double[nums.length + 1][k + 1];
        // 前缀和
        double[] sum = new double[nums.length + 1];
        for (int i = 1; i <= nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i - 1];
            dp[i][1] = sum[i] / i;
        }
        for (int i = 1; i <= nums.length; i++) {
            for (int j = 2; j <= k; j++) {
                for (int l = 0; l < i; l++) {
                    dp[i][j] = Math.max(dp[i][j], dp[l][j - 1] + (sum[i] - sum[l]) / (i - l));
                }
            }
        }
        return dp[nums.length][k];
    }

    /**
     * @see <a href="https://leetcode.cn/problems/minimum-changes-to-make-alternating-binary-string/">1758. 生成交替二进制字符串的最少操作数</a>
     */
    public int minOperations1758(String s) {
        if (s.length() == 1) {
            return 0;
        }
        char[] arr = s.toCharArray();
        int c0 = 0;
        for (int i = 0; i < s.length(); i++) {
            // 第0位为0
            if (arr[i] % 2 == i % 2) {
                c0++;
            }
        }
        return Math.min(c0, s.length() - c0);
    }

    /**
     * @see <a href="https://leetcode.cn/problems/find-nearest-point-that-has-the-same-x-or-y-coordinate/">1779. 找到最近的有相同 X 或 Y 坐标的点</a>
     */
    public int nearestValidPoint(int x, int y, int[][] points) {
        int min = Integer.MAX_VALUE;
        int res = -1;
        if (points.length == 0) {
            return res;
        }
        int i = 0;
        for (int[] point : points) {
            int x1 = point[0];
            int y1 = point[1];
            if (x == x1 || y == y1) {
                int temp = Math.abs(x - x1) + Math.abs(y - y1);
                if (min > temp) {
                    min = Math.min(min, temp);
                    res = i;
                }
            }
            i++;
        }
        return res;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/minimum-number-of-operations-to-move-all-balls-to-each-box/">1769. 移动所有球到每个盒子所需的最小操作数</a>
     */
    public int[] minOperations(String boxes) {
        // 第i个盒子以及其左边有多少个球
        int left = boxes.charAt(0) - '0';
        // 第i个盒子以及其右边有多少个球
        int right = 0;
        // 把所有球移动到第i个盒子需要第操作步骤
        int opertaion = 0;
        int len = boxes.length();
        for (int i = 1; i < len; i++) {
            if (boxes.charAt(i) == '1') {
                right++;
                opertaion += i;
            }
        }
        int[] result = new int[boxes.length()];
        // 初始化0
        result[0] = opertaion;
        for (int i = 1; i < len; i++) {
            result[i] = opertaion + left - right;
            opertaion += left - right;
            // 更新当前位置的左右球数量
            if (boxes.charAt(i) == '1') {
                left++;
                right--;
            }
        }
        return result;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/second-largest-digit-in-a-string/">1796. 字符串中第二大的数字</a>
     */
    public int secondHighest(String s) {
        int[] arr = new int[]{-1, -1};
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch >= '0' && ch <= '9') {
                int val = ch - '0';
                if (val > arr[1]) {
                    arr[0] = arr[1];
                    arr[1] = val;
                } else if (val != arr[1] && val > arr[0]) {
                    arr[0] = val;
                }
            }
        }
        return arr[0];
    }

    /**
     * @see <a href="https://leetcode.cn/problems/closest-dessert-cost/">1774. 最接近目标价格的甜点成本</a>
     */
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
//        HashSet<Integer> set = new HashSet<>(20005);
//        for (int c : baseCosts) {
//            set.add(c);
//        }
//        for (int c : toppingCosts) {
//            for (int v : set.toArray(new Integer[0])) {
//                set.add(v + c);
//                set.add(v + c * 2);
//            }
//        }
//        int ans = -20000;
//        for (int v : set) {
//            if (Math.abs(v - target) < Math.abs(ans - target)) {
//                ans = v;
//            }
//        }
//        return ans;
        boolean[] dp = new boolean[target + 10];
        int ans = Integer.MAX_VALUE;
        for (int cost : baseCosts) {
            if (cost > target) {
                ans = Math.min(ans, cost);
            } else {
                // 只选一种基料，满足
                dp[cost] = true;
            }
        }
        // 辅料
        for (int top : toppingCosts) {
            // 这里计算两次，是对应可以添加1/2种辅料的情况
            // 第一次循环能计算出添加了1次辅料dp[j - top]的情况，第二次可以往后推
            for (int i = 0; i < 2; i++) {
                for (int j = target; j >= 0; j--) {
                    // 超过
                    if (dp[j] && j + top > target) {
                        ans = Math.min(ans, j + top);
                    }
                    if (j > top) {
                        // 如果j-top能拼凑出来，那么j也能拼凑出来，视为添加了一种辅料
                        dp[j] |= dp[j - top];
                    }
                }
            }
        }

        // ans 是大于target的最小值，那么ans-target就是target右侧的最小距离
        // 只需要遍历[target - ans + target, target]这个区间的值就好了
        for (int i = target; i >= target - ans + target && i >= 0; i--) {
            if (dp[i]) {
                return i;
            }
        }
        return ans;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/number-of-different-integers-in-a-string/">1805. 字符串中不同整数的数目</a>
     */
    public int numDifferentIntegers(String word) {
        Set<String> set = new HashSet<>();
        for (int i = 0; i < word.length(); i++) {
            char ch = word.charAt(i);
            if (ch >= '0' && ch <= '9') {
                int j = i;
                while (j < word.length() && word.charAt(j) >= '0' && word.charAt(j) <= '9') {
                    j++;
                }
                // 去掉前导0
                while (i < word.length() && word.charAt(i) == '0') {
                    i++;
                }
                set.add(word.substring(i, j));
                i = j;
            }
        }
        return set.size();
    }

    /**
     * @see <a href="https://leetcode.cn/problems/equal-sum-arrays-with-minimum-number-of-operations/">1775. 通过最少操作次数使数组的和相等</a>
     */
    public int minOperations(int[] nums1, int[] nums2) {
        // 无相等可能
        if (nums1.length > 6 * nums2.length || nums2.length > 6 * nums1.length) {
            return -1;
        }
        int sum1 = 0;
        int sum2 = 0;
        for (int i = 0; i < nums1.length; i++) {
            sum1 += nums1[i];
        }
        for (int i = 0; i < nums2.length; i++) {
            sum2 += nums2[i];
        }
        if (sum1 < sum2) {
            return minOperations(nums2, nums1);
        }
        // 有map[i]个数，可以使得diff 减少i
        int[] map = new int[6];
        for (int i = 0; i < nums1.length; i++) {
            // 变小跨度
            map[nums1[i] - 1]++;
        }
        for (int i = 0; i < nums2.length; i++) {
            // 变大跨度
            map[6 - nums2[i]]++;
        }
        int diff = sum1 - sum2;
        int time = 0;
        // 从跨度值最大的开始计算，可以是nuns[1]的值变1，也可以是nums[2]的值变6
        for (int i = 5; ; i--) {
            // 满足相等条件，取下界的次数
            if (diff - i * map[i] <= 0) {
                return time + (diff + i - 1) / i;
            } else {
                // diff 大于跨度的变化值，减diff
                time += map[i];
                diff = diff - i * map[i];
            }
        }
    }

    /**
     * @see <a href="https://leetcode.cn/problems/determine-color-of-a-chessboard-square/">1812. 判断国际象棋棋盘中一个格子的颜色</a>
     */
    public boolean squareIsWhite(String coordinates) {
        return coordinates.charAt(0) % 2 + coordinates.charAt(1) % 2 == 1;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/check-if-number-is-a-sum-of-powers-of-three/">1780. 判断一个数字是否可以表示成三的幂的和</a>
     */
    public boolean checkPowersOfThree(int n) {
        while (n > 0) {
            if (n % 3 == 2) {
                return false;
            }
            n /= 3;
        }
        return true;
//        int[] arr = new int[] {1,3,9,27,81,243,729,2187,6561,19683,59049,177147,531441,1594323,4782969,14348907,43046721};
//        for (int i = arr.length - 1; i >= 0; i--) {
//            if (n >= arr[i]) {
//                n -= arr[i];
//            }
//        }
//        return n == 0;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/minimum-operations-to-make-the-array-increasing/">1827. 最少操作使数组递增</a>
     */
    public int minOperations(int[] nums) {
        if (nums.length == 1) {
            return 0;
        }
        int ans = 0;
        for (int i = 1; i < nums.length; i++) {
            int pre = nums[i - 1];
            int cur = nums[i];
            if (cur <= pre) {
                ans += pre - cur + 1;
                nums[i] = pre + 1;
            }
        }
        return ans;
    }

    /**
     * <a href="https://leetcode.cn/problems/sum-of-beauty-of-all-substrings/">1781. 所有子字符串美丽值之和</a>
     */
    public int beautySum(String s) {
        int len = s.length();
        int sum = 0;
        for (int i = 0; i < len; i++) {
            int[] map = new int[26];
            for (int j = i; j < len; j++) {
                map[s.charAt(j) - 'a']++;
                int max = 0;
                int min = Integer.MAX_VALUE;
                for (int k = 0; k < map.length; k++) {
                    if (map[k] == 0) {
                        continue;
                    }
                    max = Math.max(max, map[k]);
                    min = Math.min(min, map[k]);
                }
                sum += (max - min);
            }
        }
        return sum;
    }

    /**
     * <a href="https://leetcode.cn/problems/check-if-the-sentence-is-pangram/">1832. 判断句子是否为全字母句</a>
     */
    public boolean checkIfPangram(String sentence) {
        char[] arr = sentence.toCharArray();
        int[] map = new int[26];
        for (char ch : arr) {
            map[ch - 'a']++;
        }
        for (int v : map) {
            if (v == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/sum-of-digits-of-string-after-convert/">1945. 字符串转化后的各位数字之和</a>
     */
    public int getLucky(String s, int k) {
        int n = s.length(), res = 0;
        //s转化数字，同步执行一次按位求和，k-1
        for (int i = 0; i < n; ++i) {
            int num = s.charAt(i) - 'a' + 1;
            while (num > 0) {
                res += num % 10;
                num /= 10;
            }
        }
        --k;
        //循环k-1次按位求和
        while (k > 0) {
            int tem = res;
            res = 0;
            while (tem > 0) {
                res += tem % 10;
                tem /= 10;
            }
            --k;
        }
        return res;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/minimum-elements-to-add-to-form-a-given-sum/">1785. 构成特定和需要添加的最少元素</a>
     */
    public int minElements(int[] nums, int limit, int goal) {
        long sum = 0;
        for (int n : nums) {
            sum += n;
        }
        long abs = Math.abs(sum - goal);
        return (int) Math.ceil((double) abs / limit);
    }

    /**
     * @see <a href="https://leetcode.cn/problems/form-array-by-concatenating-subarrays-of-another-array/">1764. 通过连接另一个数组的子数组得到一个数组</a>
     */
    public boolean canChoose(int[][] groups, int[] nums) {
        int p1 = 0;
        int p2 = 0;
        int n = nums.length;
        for (int[] g : groups) {
            p2 = 0;
            while (p2 < g.length && p1 < n) {
                if (nums[p1++] == g[p2]) {
                    p2++;
                } else {
                    // 从开始的下一个数字开始重新匹配
                    p1 -= p2;
                    p2 = 0;
                }
            }
            if (p1 >= n && p2 != g.length) {
                return false;
            }
        }
        return true;
    }

    /**
     * <a href="https://leetcode.cn/problems/find-if-path-exists-in-graph/">1971. 寻找图中是否存在路径</a>
     */
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        if (source == destination) {
            return true;
        }
        // 构造并查集 fa表示父节点数组
        int[] fa = new int[n];
        for (int i = 0; i < n; ++i) {
            fa[i] = i;
        }
        for (int[] edge : edges) {
            union1971(fa, edge[0], edge[1]);
        }
        // 是否在同一集合里
        return findRoot1971(fa, source) == findRoot1971(fa, destination);
    }

    private int findRoot1971(int[] fa, int u) {
        while (fa[u] != u) {
            fa[u] = fa[fa[u]];
            u = fa[u];
        }
        return u;
    }

    private void union1971(int[] fa, int u, int v) {
        int fp = findRoot1971(fa, u);
        int fq = findRoot1971(fa, v);
        // 连通
        fa[fp] = fq;
    }

    /**
     * <a href="https://leetcode.cn/problems/minimum-limit-of-balls-in-a-bag/">1760. 袋子里最少数目的球</a>
     */
    public int minimumSize(int[] nums, int maxOperations) {
        int max = 0;
        for (int e : nums) {
            max = Math.max(max, e);
        }
        // 给定maxOperations 次操作次数，能否可以使得单个袋子里球数目的最大值不超过y
        int l = 1;
        int pos = max;
        while (l <= max) {
            int mid = l + ((max - l) >> 1);
            int cnt = 0;
            for (int e : nums) {
                // 将第i个桶装有mid个球的操作次数
                cnt += (e - 1) / mid;
            }
            if (cnt <= maxOperations) {
                max = mid - 1;
                pos = mid;
            } else {
                l = mid + 1;
            }
        }
        return pos;
    }

    /**
     * <a href="https://leetcode.cn/problems/maximum-score-from-removing-stones/">1753. 移除石子的最大得分</a>
     */
    public int maximumScore(int a, int b, int c) {
        if (a + b < c) {
            return a + b;
        }
        if (a + c < b) {
            return a + c;
        }
        if (c + b < a) {
            return b + c;
        }
        return (a + b + c) / 2;
    }


    /**
     * @see <a href="https://leetcode.cn/problems/final-value-of-variable-after-performing-operations/">2011. 执行操作后的变量值</a>
     */
    public int finalValueAfterOperations(String[] operations) {
        int result = 0;
        //不论是X++ ++X --X X-- 都只需要看中间一位
        for (String operation : operations) {
            if ('+' == operation.charAt(1)) {
                ++result;
            } else {
                --result;
            }
        }
        return result;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/largest-merge-of-two-strings/">1754. 构造字典序最大的合并字符串</a>
     */
    public String largestMerge(String word1, String word2) {
        int idx1 = 0;
        int idx2 = 0;
        StringBuilder sb = new StringBuilder();
        String temp1 = "";
        String temp2 = "";
        while (idx1 < word1.length() && idx2 < word2.length()) {
            temp1 = word1.substring(idx1);
            temp2 = word2.substring(idx2);
            if (temp1.compareTo(temp2) > 0) {
                sb.append(word1.charAt(idx1));
                idx1++;
            } else {
                sb.append(word2.charAt(idx2));
                idx2++;
            }
        }
        while (idx1 < word1.length()) {
            sb.append(word1.charAt(idx1));
            idx1++;
        }
        while (idx2 < word2.length()) {
            sb.append(word2.charAt(idx2));
            idx2++;
        }
        return sb.toString();
    }

    /**
     * @see <a href="https://leetcode.cn/problems/count-number-of-homogenous-substrings/submissions/">1759. 统计同构子字符串的数目</a>
     */
    public int countHomogenous(String s) {
        int n = s.length();
        long res = 1;
        int temp = s.charAt(0);
        int count = 1;
        for (int i = 1; i < n; i++) {
            if (s.charAt(i) == temp) {
                count++;
                res = res + count;
            } else {
                temp = s.charAt(i);
                res = res + 1;
                count = 1;
            }
        }
        return (int) (res % 1000000007);
    }

    /**
     * @see <a href="https://leetcode.cn/problems/minimum-moves-to-convert-string/submissions/">2027. 转换字符串的最少操作次数</a>
     */
    public int minimumMoves(String s) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch - 'X' == 0) {
                res++;
                i = Math.min(i + 2, s.length());
            }
        }
        return res;
    }

    //todo 1687， 1691, 1697, 1703, 1799， 1739

    public static void main(String[] args) {
        Solution s = new Solution();
        s.minOperations("0100");
        s.minimumMoves("OXOOX");
    }

}
