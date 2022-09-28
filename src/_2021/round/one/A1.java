package _2021.round.one;

import java.io.*;
import java.util.concurrent.atomic.AtomicLong;

public class A1 {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream("weak_typing_chapter_2_input.txt")));
            BufferedWriter bw =
                    new BufferedWriter(new OutputStreamWriter(new FileOutputStream("output.txt")));
            int t = Integer.parseInt(br.readLine());
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i <= t; i++) {
                int n = Integer.parseInt(br.readLine());
                String str = br.readLine();
                sb.append("Case #").append(i).append(": ").append(getMinOfAllSubStr(str));
                bw.write(sb.toString());
                sb.setLength(0);
                bw.newLine();
            }
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int getMinOfAllSubStr(String str) throws InterruptedException {
        AtomicLong count = new AtomicLong();
        System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<str len: " + str.length());
        //Thread.sleep(2000);

        int charCount = 0;

        boolean hasF = false;
        boolean hasO = false;
        boolean hasX = false;
        for (char c : str.toCharArray()) {
            if (c == 'F' && !hasF) {
                hasF = true;
                charCount += 1;
            }
            if (c == 'O' && !hasO) {
                hasO = true;
                charCount += 1;
            }
            if (c == 'X' && !hasX) {
                hasX = true;
                charCount += 1;
            }
            if (hasF && hasO && hasX) {
                break;
            }
        }
        System.out.println("char count: " + charCount);
        //Thread.sleep(1000);

        if (charCount == 1 || (charCount == 2 && ((hasF && hasO) || (hasF && hasX)))) {
            count.set(0);
        } else {
            Thread t1 = new Thread(() -> {
                count.addAndGet(t1(str));
            });
            Thread t2 = new Thread(() -> {
                count.addAndGet(t2(str));
            });
            t1.start();
            t2.start();
        }
        return (int) (count.get() % 1000000007L);
    }

    private static int t2(String str) {
        int count = 0;
        for (int i = 0; i < str.length(); i++) {
            for (int j = 1 + str.length() / 2; j < str.length(); j++) {
                count += getMin(str.substring(i, j));
            }
        }
        return count;
    }

    private static int t1(String str) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char curr = '\0';
            for (int j = i; j < str.length(); j++) {
                char c = str.charAt(j);
                sb.append(c);
                if (c != 'F' && curr != c) {
                    if (curr == '\0') {
                        curr = c;
                    } else {
                        curr = c;
                        count += 1;
                    }
                }
                if (sb.length() == str.length() / 2) {
                    sb.setLength(0);
                    break;
                }
            }
        }
        return count;
    }

    private static int getMin(String str) {
        System.out.println("getMin, size: " + str.length());
        int count = 0;
        char curr = '\0';

        for (char c : str.toCharArray()) {
            if (c != 'F' && curr != c) {
                if (curr == '\0') {
                    curr = c;
                } else {
                    curr = c;
                    count++;
                }
            }
        }

        return count;
    }
}
