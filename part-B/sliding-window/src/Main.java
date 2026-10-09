
import java.io.*;
import java.util.*;
public class Main {
    public static long solve(long[] arr, long target) {
      int left = 0;
      int n = arr.length;
      long sum = 0, count = 0;
      for(int right = 0; right<n;right++){
          sum += arr[right];
          while(sum>target){
              sum -= arr[left];
              left++;
          }
          if(sum == target)count++;
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

/*sliding window fails becuase there are some numbers which are negative
to over come this we use prefix and hashmap
 */
