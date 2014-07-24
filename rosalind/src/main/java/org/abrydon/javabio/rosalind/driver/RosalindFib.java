package org.abrydon.javabio.rosalind.driver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

public class RosalindFib {
    private final static Logger logger = LoggerFactory.getLogger(RosalindFib.class);

    // modified fibonacci with F_n = F_{n-1} + k F_{n-2}
    private static class ModifiedFibonacci {
        private final int k;

        ModifiedFibonacci(int k) {
            this.k = k;
        }

        long get(int n) {
            Preconditions.checkArgument(n > 0);
            Preconditions.checkArgument(k > 0);

            if (n <= 2) {
                return 1;
            }
            return get(n - 1) + k * get(n - 2);
        }
    }

    public static long fib(int n, int k) {
        return new ModifiedFibonacci(k).get(n);
    }

    public static void main(String[] args) {
        logger.info("{}", fib(30, 4));
    }
}
