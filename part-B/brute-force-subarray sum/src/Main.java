import java.io.*;
import java.util.*;

public class Main {
    public static long solve(long[] arr, long target) {
        long count = 0;
        for(int i = 0; i<arr.length; i++){
            for(int j = i; j<arr.length; j++){
                long sum = 0;
                for(int k = i; k<j; k++){
                    sum += arr[k];
                }
                if(sum ==target)count++;
            }
        }

        return count;
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
/*This method is not good because it takes O(n^3) */