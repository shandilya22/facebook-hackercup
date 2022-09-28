package round.qualifying;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class A1 {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("consistency_chapter_1_input.txt")));
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt")));
            int t = Integer.parseInt(br.readLine());
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= t; i++) {
                String str = br.readLine();
                sb.append("Case #").append(i).append(": ").append(getMin(str));
                bw.write(sb.toString());
                sb.setLength(0);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getMin(String str) {
        if (str.length() == 1) {
            return 0;
        }

        int ev = 0;
        int ec = 0;

        Map<Character, Integer> vMap = new HashMap<>();
        Map<Character, Integer> cMap = new HashMap<>();

        Map<Character, Integer> map;
        for (char c : str.toCharArray()) {
            if (c == 'A' || c == 'E' || c == 'I' || c == 'O' || c == 'U') {
                ev++;
                map = vMap;
            } else {
                ec++;
                map = cMap;
            }
            if (!map.containsKey(c)) {
                map.put(c, 0);
            }
            map.put(c, map.get(c) + 1);
        }
        int dc = cMap.keySet().size();
        int dv = vMap.keySet().size();

        int count;
        if (dc == 1 && dv == 1) {
            count = Math.min(ec, ev);
        } else if (dc == 1) {
            if (dv == 0) {
                count = 0;
            } else {
                count = ev;
            }
        } else if (dv == 1) {
            if (dc == 0) {
                count = 0;
            } else {
                count = ec;
            }
        } else if (dc == 0) {
            count = ev;
        } else if (dv == 0) {
            count = ec;
        } else {
            count = Math.min(getCount(ev, ec, cMap), getCount(ec, ev, vMap));
        }
        return count;
    }

    private static int getCount(int e1, int e2, Map<Character, Integer> map) {
        int count = e1;
        int freq = 0;
        for (int v : map.values()) {
            if (freq < v) {
                freq = v;
            }
        }
        count += (2 * (e2 - freq));
        return count;
    }
}