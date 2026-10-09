import java.io.*;
import java.util.*;

public class Main {
    public static boolean isPossible(long [] machines, long time, long target){
       long TotalProducts = 0;
       for(long machineTime : machines){
           TotalProducts += time/machineTime;
           if(TotalProducts>= target)return true;
       }
       return false;

    }
    public static long search(long [] machines, long low, long high, long target){
        while(low<high){
            long mid = low+ (high - low)/2;
            if(isPossible(machines,mid, target)){
                high = mid;
            }else{
                low =  mid+1;
            }
        }
        return high;
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        long target = Long.parseLong(st.nextToken());
        st = new StringTokenizer(br.readLine());
        long[] machines = new long[n];
        for (int i = 0; i < n; i++) {
            machines[i] = Long.parseLong(st.nextToken());
        }
        long maxTime = Integer.MIN_VALUE;
        for(long machine: machines){
            maxTime = Math.max(maxTime,machine);
        }
        long low = 1;
        long high = maxTime*target;
        long answer =  search(machines,  low,  high,  target);
        System.out.println(answer);






    }

}



