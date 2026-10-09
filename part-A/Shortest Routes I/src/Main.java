import java.io.*;
import java.util.*;
public class Main {
    static class Edge {
        int city;
        long distance;
        Edge(int city, long distance) {
            this.city = city;
            this.distance = distance;
        }
    }
    /* Comparable<Node> is an interface that uses the CompareTo method internally
    It is used for sorting nodes like it is used in binary tress and other data structure
    where data is stored. The compareTo method returns either -1,0 or 1 which indicates whether
    it is greater than, less than or equal to other nodes
     */
    static class Node implements Comparable<Node> {
        int city;
        long distance;
        Node(int city, long distance) {
            this.city = city;
            this.distance = distance;
        }
        @Override
        public int compareTo(Node other) {
            return Long.compare(this.distance, other.distance);
        }
    }
    public static void main(String[] args) throws Exception {
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        ArrayList<ArrayList<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            // u is current edge and v is next edge
            int v = Integer.parseInt(st.nextToken());
            long weight = Long.parseLong(st.nextToken());
            u--;
            v--;
            graph.get(u).add(new Edge(v, weight));
        }
        long[] dist = new long[n];
        Arrays.fill(dist, Long.MAX_VALUE);
        dist[0] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>();

        pq.add(new Node(0, 0));
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int currNode = current.city;
            long currDist = current.distance;
            if (currDist > dist[currNode]) {
                continue;
            }
            for (Edge edge : graph.get(currNode)) {
                int neighbor = edge.city;
                long edgeWeight = edge.distance;
                long newDist = currDist + edgeWeight;
                if (newDist < dist[neighbor]) {
                    dist[neighbor] = newDist;
                    pq.add(new Node(neighbor, newDist));
                }
            }
        }

        StringBuilder ans = new StringBuilder();

        for(long distance: dist){
            ans.append(distance).append(" ");
        }

        System.out.println(ans);

    }
}