import java.math.BigDecimal;

/**
 * see [<a href="https://leetcode.cn/problems/operations-lcci/submissions/">面试题 16.09. 运算</a>]
 */
class Operations {

    public Operations() {

    }

    public int minus(int a, int b) {
        return BigDecimal.valueOf(a).subtract(BigDecimal.valueOf(b)).intValue();
    }

    public int multiply(int a, int b) {
        return BigDecimal.valueOf(a).multiply(BigDecimal.valueOf(b)).intValue();
    }

    public int divide(int a, int b) {
        return BigDecimal.valueOf(a).divide(BigDecimal.valueOf(b)).intValue();
    }
}