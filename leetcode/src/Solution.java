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

            // ????????????
            while (nameMap.containsKey(name1)) {
                name1 = nameMap.get(name1);
            }

            // ????????????
            while (nameMap.containsKey(name2)) {
                name2 = nameMap.get(name2);
            }

            // ??????
            if (!name2.equals(name1)) {
                int frequency = map.getOrDefault(name1, 0) + map.getOrDefault(name2, 0);
                if (name1.compareTo(name2) < 0) {
                    nameMap.put(name2, name1); // ???????????????
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
        int m = a ^ b; // ?????? ??????????????????0
        int n = (a & b) << 1; // ??????
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

            // ???????????? ????????????????????????????????? ????????????
            if (cur >= max) {
                max = Math.max(cur, max);
            } else {
                right = i;
            }

            // ???????????? ????????????????????????????????? ????????????
            if (array[array.length - 1 - i] > min) {
                left = array.length - 1 - i;
            } else {
                min = Math.min(array[array.length - 1 - i], min);
            }
        }
        return new int[]{left, right};
    }

    /**
     ??????:
     ????????????:??????????????????,???????????????????????????,??????????????????????????????????????????,
     ???????????????????????????????????????,
     ???????????????????????????????????????,
     ?????????????????????????????????????????????
     */
//    public int[] subSort(int[] array) {
//
//        //0.????????????
//        if(array==null) return null;
//        if(array.length==0||array.length<2) return new int[]{-1,-1};
//
//        //1.???????????????????????????
//        int max = array[0];//??????0???????????????????????????
//        int r = -1;//???????????????
//
//        //2.???????????????
//
//        //2.1 ?????????????????????
//        for(int i = 1;i < array.length;i++) {
//            int val = array[i];
//            if(val < max) {//?????????????????????????????????,?????????????????????
//                r = i;
//            }else {//?????????????????????????????????,?????????max;???????????????????????????
//                max = val;
//            }
//        }
//
//        //2.2.?????????????????????
//        if(r==-1) return new int[]{-1,-1};
//
//        //2.3 ?????????????????????
//        int min = array[array.length - 1];//??????????????????????????????????????????????????????
//        int l = - 1;//?????????????????????????????????????????????
//        for(int i = array.length -1; i >= 0;i--) {
//            int val = array[i];
//            if(val > min) {
//                //??????????????????????????????,?????????????????????;??????????????????????????????????????????????????????
//                l = i;
//            }else {
//                min = val;
//            }
//
//        }
//
//        //4.????????????
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
     * ???n ????????????B ???A ?????????C
     */
    private void moveHanota(int n, List<Integer> A, List<Integer> B, List<Integer> C) {
        if (n == 1) {
            C.add(0, A.remove(0));
            return;
        }

        // ???????????????n-1????????????B
        moveHanota(n - 1, A, C, B);
        // ??????????????????
        C.add(0, A.remove(0));
        // ?????????B?????????n-1??????c
        moveHanota(n - 1, B, A, C);
    }

    /**
     * see [https://leetcode.cn/problems/smallest-difference-lcci/]
     */
    public int smallestDifference(int[] a, int[] b) {
        // ?????????
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
     * see [<a href="https://leetcode.cn/problems/living-people-lcci/">????????? 16.10. ????????????</a>]
     */
    public int maxAliveYear(int[] birth, int[] death) {
        final int base = 1900;
        int[] arr = new int[110];
        for (int i = 0; i < birth.length; i++) {
            int start = birth[i] - base;
            int end = death[i] - base;
            arr[start] += 1; // ????????????+1
            arr[end + 1] -= 1; // ????????????-1
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
     * see [<a href="https://leetcode.cn/problems/maximum-lcci/">????????? 16.07. ????????????</a>]
     */
    public int maximum(int a, int b) {
        long al = a;
        long bl = b;
        return (int) ((Math.abs(al - bl) + al + bl) / 2);
    }

    /**
     * see [<a href="https://leetcode.cn/problems/sum-swap-lcci/">????????? 16.21. ?????????</a>]
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
     * see [<a href="https://leetcode.cn/problems/maximum-units-on-a-truck/comments/">1710. ???????????????????????????</a>]
     */
    public int maximumUnits(int[][] boxTypes, int truckSize) {
        int[] map = new int[1001];
        for (int[] arr : boxTypes) {
            map[arr[1]] += arr[0];
        }
        int result = 0;
        for (int i = map.length - 1; i > 0; i--) {
            // ????????????i????????????
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
     * see [<a href="https://leetcode.cn/problems/factorial-zeros-lcci/">????????? 16.05. ????????????</a>]
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
     * see [<a href="https://leetcode.cn/problems/global-and-local-inversions/">775. ???????????????????????????</a>]
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
     * see [<a href="https://leetcode.cn/problems/t9-lcci/">????????? 16.20. T9??????</a>]
     */
    public List<String> getValidT9Words(String num, String[] words) {
        int[] map = new int[]{2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 7, 8, 8, 8, 9, 9, 9, 9};
        int n = num.length();
        int[] nums = new int[n];
        // ?????????????????????num??????????????????int??????
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
     * see [<a href="https://leetcode.cn/problems/number-of-matching-subsequences/">792. ???????????????????????????</a>]
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
                // ???????????????index???prePosition??????
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
     * see [<a href="https://leetcode.cn/problems/sum-of-subsequence-widths/">891. ?????????????????????</a>]
     */
    public int sumSubseqWidths(int[] nums) {
        int mod = (int) (1e9 + 7);
        // ??????????????????2^n???
        int n = nums.length + 1;
        long[] value = new long[n];
        value[0] = 1; // 2^0 = 1
        for (int i = 1; i < n; i++) {
            value[i] = value[i - 1] * 2 % mod;
        }

        long ans = 0;
        // ?????????????????????????????????i??????????????????k-i-1??????????????????2^(k-i-1)?????????
        // ???????????? ??????2^i ?????????
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            // nums[i]???2^i ??????????????????????????????
            ans += (value[i] * nums[i]) % mod;
            ans %= mod;
            // ???2^n - i - 1 ??????????????????????????????
            ans -= (value[nums.length - i - 1] * nums[i]) % mod;
            ans %= mod;
        }
        return (int) ans;
    }

    /**
     * see [<a href="https://leetcode.cn/problems/find-the-highest-altitude/">1732. ??????????????????</a>]
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
     * see [<a href="https://leetcode.cn/problems/pattern-matching-lcci/">????????? 16.18. ????????????</a>]
     */
    public boolean patternMatching(String pattern, String value) {
        int a = 0;
        int b = 0;
        int indexA = -1, indexB = -1;
        boolean flagA = false, flagB = false;
        boolean[] map = new boolean[pattern.length()];
        // ??????a b??????
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
        // ??????
        if (value == null || value.isEmpty()) {
            return a <= 0 || b <= 0;
        }
        // ??????1?????????????????????????????????
        if (a == 1 || b == 1) {
            return true;
        }
        // ????????????
        for (int aLength = 0; aLength < value.length(); aLength++) {
            for (int bLength = 0; bLength < value.length(); bLength++) {
                // ??????ax + by = n
                if (aLength * a + bLength * b != value.length()) {
                    continue;
                }
                String pa = "";
                if (indexA >= 0) {
                    // ???????????????a b????????? * y?????????
                    pa = value.substring(bLength * indexA, bLength * indexA + aLength);
                }
                String pb = "";
                if (indexB >= 0) {
                    // ???????????????b
                    pb = value.substring(aLength * indexB, aLength * indexB + bLength);
                }
                // a b ?????????????????????????????????
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
     * see [<a href="https://leetcode.cn/problems/champagne-tower/">799. ?????????</a>]
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
     * see [<a href="https://leetcode.cn/problems/soup-servings/">808. ??????</a>]
     */
    public double soupServings(int n) {
        // ?????????
        if (n >= 4451) {
            return 1;
        }
        double[][] dp = new double[n + 1][n + 1];
        return soupServings(n, n, dp);
    }

    private double soupServings(int a, int b, double[][] dp) {
        if (a <= 0) {
            // a b ???????????????????????? / 2
            if (b <= 0) {
                return 0.5;
            }
            // a ??????
            return 1;
        }
        if (b <= 0) {
            return 0;
        }
        if (dp[a][b] != 0) {
            return dp[a][b];
        }
        //???????????????
        dp[a][b] += 0.25 * soupServings(a - 100, b - 0, dp);
        dp[a][b] += 0.25 * soupServings(a - 75, b - 25, dp);
        dp[a][b] += 0.25 * soupServings(a - 50, b - 50, dp);
        dp[a][b] += 0.25 * soupServings(a - 25, b - 75, dp);
        return dp[a][b];
    }

    /**
     * see [<a href="https://leetcode.cn/problems/nth-magical-number/">878. ??? N ???????????????</a>]
     */
    public int nthMagicalNumber(int N, int A, int B) {
        // ??????????????? C
        // ??????N ?????? x / A + X / B - x / C >= N
        // ??????x ?????????????????????
        int C = A * B / nthMagicalNumberGcd(A, B);
        int n = C / A + C / B - 1;
        long div = N / n;
        long mod = N % n;
        long x = xthMagicalNumber(A, B, mod);
        int M = (int) 1e9 + 7;
        return (int) ((div * C + x) % M);
    }

    /**
     * ??????????????????
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
     * see [<a href="https://leetcode.cn/problems/maximum-number-of-balls-in-a-box/">1742. ??????????????????????????????</a>]
     */
    public int countBalls(int lowLimit, int highLimit) {
        int[] map = new int[46];
        int firstIndex = countBallsN(lowLimit);
        map[firstIndex] = 1; // ????????????????????????lowLimit?????????????????????????????????
        for (int i = lowLimit; i < highLimit; i++) {
            for (int pre = i; pre % 10 == 9; pre /= 10) {
                // ????????????????????????????????????9???????????????????????????????????????
                firstIndex -= 9; // ??????9???
            }
            // ???????????????????????????
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
     * see [<a href="https://leetcode.cn/problems/number-of-subarrays-with-bounded-maximum/">795. ?????????????????????</a>]
     */
    public int numSubarrayBoundedMax(int[] nums, int left, int right) {
        int res = 0;
        int l = -1; // ?????????
        int r = -1; // ?????????
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
     * see [<a href="https://leetcode.cn/problems/expressive-words/">809. ?????????????????????</a>]
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
     * a ????????????B???
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
     * see [<a href="https://leetcode.cn/problems/check-if-array-is-sorted-and-rotated/">1752. ??????????????????????????????????????????</a>]
     */
    public boolean check(int[] nums) {
        int count = 0;
        for (int i = 0; i < nums.length; i++) {
            // ??????????????????
            if (nums[i] > nums[(i + 1) % nums.length]) {
                count++;
            }
        }
        return count <= 1;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/largest-sum-of-averages/">813. ???????????????????????????</a>
     */
    public double largestSumOfAverages(int[] nums, int k) {
        // dp[i][k]?????????i????????????k?????????????????????????????????, ?????????????????? 0 <= j <= i-1
        // dp[i][k] = Math.max(dp[i][k], dp[j][k-1] + (A[j+1] + ... + A[i+1]) / (i-j))
        double[][] dp = new double[nums.length + 1][k + 1];
        // ?????????
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
     * @see <a href="https://leetcode.cn/problems/minimum-changes-to-make-alternating-binary-string/">1758. ????????????????????????????????????????????????</a>
     */
    public int minOperations1758(String s) {
        if (s.length() == 1) {
            return 0;
        }
        char[] arr = s.toCharArray();
        int c0 = 0;
        for (int i = 0; i < s.length(); i++) {
            // ???0??????0
            if (arr[i] % 2 == i % 2) {
                c0++;
            }
        }
        return Math.min(c0, s.length() - c0);
    }

    /**
     * @see <a href="https://leetcode.cn/problems/find-nearest-point-that-has-the-same-x-or-y-coordinate/">1779. ???????????????????????? X ??? Y ????????????</a>
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
     * @see <a href="https://leetcode.cn/problems/minimum-number-of-operations-to-move-all-balls-to-each-box/">1769. ??????????????????????????????????????????????????????</a>
     */
    public int[] minOperations(String boxes) {
        // ???i???????????????????????????????????????
        int left = boxes.charAt(0) - '0';
        // ???i???????????????????????????????????????
        int right = 0;
        // ????????????????????????i??????????????????????????????
        int opertaion = 0;
        int len = boxes.length();
        for (int i = 1; i < len; i++) {
            if (boxes.charAt(i) == '1') {
                right++;
                opertaion += i;
            }
        }
        int[] result = new int[boxes.length()];
        // ?????????0
        result[0] = opertaion;
        for (int i = 1; i < len; i++) {
            result[i] = opertaion + left - right;
            opertaion += left - right;
            // ????????????????????????????????????
            if (boxes.charAt(i) == '1') {
                left++;
                right--;
            }
        }
        return result;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/second-largest-digit-in-a-string/">1796. ??????????????????????????????</a>
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
     * @see <a href="https://leetcode.cn/problems/closest-dessert-cost/">1774. ????????????????????????????????????</a>
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
                // ???????????????????????????
                dp[cost] = true;
            }
        }
        // ??????
        for (int top : toppingCosts) {
            // ??????????????????????????????????????????1/2??????????????????
            // ????????????????????????????????????1?????????dp[j - top]????????????????????????????????????
            for (int i = 0; i < 2; i++) {
                for (int j = target; j >= 0; j--) {
                    // ??????
                    if (dp[j] && j + top > target) {
                        ans = Math.min(ans, j + top);
                    }
                    if (j > top) {
                        // ??????j-top????????????????????????j????????????????????????????????????????????????
                        dp[j] |= dp[j - top];
                    }
                }
            }
        }

        // ans ?????????target?????????????????????ans-target??????target?????????????????????
        // ???????????????[target - ans + target, target]???????????????????????????
        for (int i = target; i >= target - ans + target && i >= 0; i--) {
            if (dp[i]) {
                return i;
            }
        }
        return ans;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/number-of-different-integers-in-a-string/">1805. ?????????????????????????????????</a>
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
                // ????????????0
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
     * @see <a href="https://leetcode.cn/problems/equal-sum-arrays-with-minimum-number-of-operations/">1775. ?????????????????????????????????????????????</a>
     */
    public int minOperations(int[] nums1, int[] nums2) {
        // ???????????????
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
        // ???map[i]?????????????????????diff ??????i
        int[] map = new int[6];
        for (int i = 0; i < nums1.length; i++) {
            // ????????????
            map[nums1[i] - 1]++;
        }
        for (int i = 0; i < nums2.length; i++) {
            // ????????????
            map[6 - nums2[i]]++;
        }
        int diff = sum1 - sum2;
        int time = 0;
        // ?????????????????????????????????????????????nuns[1]?????????1???????????????nums[2]?????????6
        for (int i = 5; ; i--) {
            // ???????????????????????????????????????
            if (diff - i * map[i] <= 0) {
                return time + (diff + i - 1) / i;
            } else {
                // diff ??????????????????????????????diff
                time += map[i];
                diff = diff - i * map[i];
            }
        }
    }

    /**
     * @see <a href="https://leetcode.cn/problems/determine-color-of-a-chessboard-square/">1812. ????????????????????????????????????????????????</a>
     */
    public boolean squareIsWhite(String coordinates) {
        return coordinates.charAt(0) % 2 + coordinates.charAt(1) % 2 == 1;
    }

    /**
     * @see <a href="https://leetcode.cn/problems/check-if-number-is-a-sum-of-powers-of-three/">1780. ??????????????????????????????????????????????????????</a>
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
     * @see <a href="https://leetcode.cn/problems/minimum-operations-to-make-the-array-increasing/">1827. ???????????????????????????</a>
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
     * <a href="https://leetcode.cn/problems/sum-of-beauty-of-all-substrings/">1781. ?????????????????????????????????</a>
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
     * <a href="https://leetcode.cn/problems/check-if-the-sentence-is-pangram/">1832. ?????????????????????????????????</a>
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
     * @see <a href="https://leetcode.cn/problems/sum-of-digits-of-string-after-convert/">1945. ???????????????????????????????????????</a>
     */
    public int getLucky(String s, int k) {
        int n = s.length(), res = 0;
        //s????????????????????????????????????????????????k-1
        for (int i = 0; i < n; ++i) {
            int num = s.charAt(i) - 'a' + 1;
            while (num > 0) {
                res += num % 10;
                num /= 10;
            }
        }
        --k;
        //??????k-1???????????????
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
     * @see <a href="https://leetcode.cn/problems/minimum-elements-to-add-to-form-a-given-sum/">1785. ??????????????????????????????????????????</a>
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
     * @see <a href="https://leetcode.cn/problems/form-array-by-concatenating-subarrays-of-another-array/">1764. ?????????????????????????????????????????????????????????</a>
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
                    // ?????????????????????????????????????????????
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
     * <a href="https://leetcode.cn/problems/find-if-path-exists-in-graph/">1971. ??????????????????????????????</a>
     */
    public boolean validPath(int n, int[][] edges, int source, int destination) {
        if (source == destination) {
            return true;
        }
        // ??????????????? fa?????????????????????
        int[] fa = new int[n];
        for (int i = 0; i < n; ++i) {
            fa[i] = i;
        }
        for (int[] edge : edges) {
            union1971(fa, edge[0], edge[1]);
        }
        // ????????????????????????
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
        // ??????
        fa[fp] = fq;
    }

    /**
     * <a href="https://leetcode.cn/problems/minimum-limit-of-balls-in-a-bag/">1760. ???????????????????????????</a>
     */
    public int minimumSize(int[] nums, int maxOperations) {
        int max = 0;
        for (int e : nums) {
            max = Math.max(max, e);
        }
        // ??????maxOperations ?????????????????????????????????????????????????????????????????????????????????y
        int l = 1;
        int pos = max;
        while (l <= max) {
            int mid = l + ((max - l) >> 1);
            int cnt = 0;
            for (int e : nums) {
                // ??????i????????????mid?????????????????????
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
     * <a href="https://leetcode.cn/problems/maximum-score-from-removing-stones/">1753. ???????????????????????????</a>
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
     * @see <a href="https://leetcode.cn/problems/final-value-of-variable-after-performing-operations/">2011. ???????????????????????????</a>
     */
    public int finalValueAfterOperations(String[] operations) {
        int result = 0;
        //?????????X++ ++X --X X-- ???????????????????????????
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
     * @see <a href="https://leetcode.cn/problems/largest-merge-of-two-strings/">1754. ???????????????????????????????????????</a>
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
     * @see <a href="https://leetcode.cn/problems/count-number-of-homogenous-substrings/submissions/">1759. ?????????????????????????????????</a>
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
     * @see <a href="https://leetcode.cn/problems/minimum-moves-to-convert-string/submissions/">2027. ????????????????????????????????????</a>
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

    //todo 1687??? 1691, 1697, 1703, 1799??? 1739

    public static void main(String[] args) {
        Solution s = new Solution();
        s.minOperations("0100");
        s.minimumMoves("OXOOX");
    }

}
