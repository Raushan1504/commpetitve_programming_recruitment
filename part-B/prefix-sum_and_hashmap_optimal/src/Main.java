import java.io.*;
import java.util.*;

public class Main {
    public static long solve(long[] arr, long target) {
        HashMap<Long, Long> p_count = new HashMap<>();
        long curr_sum = 0;
        long total = 0;
        p_count.put(0L, 1L);
        for (int i = 0; i < arr.length; i++) {
            curr_sum += arr[i];
            total += p_count.getOrDefault(curr_sum - target, 0L);
            p_count.put(curr_sum, p_count.getOrDefault(curr_sum, 0L) + 1);

        }
        return total;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long x = Long.parseLong(st.nextToken());
        st = new StringTokenizer(br.readLine());
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }
        long answer = solve(arr, x);
        System.out.println(answer);

    }
}


/*Here we know that if is x+target = curr_sum where curr_sum is the prefix
sum at the i'th index, then we do x = curr_sum - target to see whether the prefix sum
x is present in the HashMap or not, and if so increase the count. We avoid the trap of
negative numbers through this method

Time Complexity - O(n)
 */
