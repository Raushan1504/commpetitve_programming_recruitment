import java.io.*;
import java.util.*;
public class Main {
    public static long solve(String s1, String s2) {
        int n = s1.length();
        int m = s2.length();
        long[][] arr = new long[n+1][m+1];
        for (int i = 0; i <= n; i++) {
            arr[i][0] = i;
        }
        for (int j = 0; j <= m; j++) {
            arr[0][j] = j;
        }
        for (int i = 1; i < n+1; i++) {
            for (int j = 1; j < m+1; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    arr[i][j] = arr[i - 1][j - 1];
                } else {
                    arr[i][j] = 1 + Math.min(arr[i - 1][j], Math.min(arr[i][j - 1], arr[i - 1][j - 1]));
                }
            }

        }
        return arr[n][m];
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s1 = br.readLine();
        String s2 = br.readLine();
        long answer = solve(s1, s2);
        System.out.println(answer);

    }
}