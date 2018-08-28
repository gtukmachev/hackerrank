package LeetCode.problems.medium.compare_version_numbers;

import java.util.Iterator;

/**
 * Created by grigory@clearscale.net on 8/28/2018.
 */
public class CompareVersionNumbers {

    public class Solution {

        public int compareVersion(String version1, String version2) {
            Iterator<String> v1Iterator = new VersionIterator(version1);
            Iterator<String> v2Iterator = new VersionIterator(version2);

            for(;;){
                String v1 = v1Iterator.next();
                String v2 = v2Iterator.next();

                if (v1 == null && v2 == null) return  0;
                if (v1 != null && v2 == null) return  1;
                if (v1 == null /*&& v2 != null*/) return -1;

                int r = compare(v1, v2);
                if (r != 0) return r;
            }

        }

        int compare(String v1, String v2) {
            int l1 = v1.length() - 1;
            int l2 = v2.length() - 1;

            if (l1 > l2) return  1;
            if (l1 < l2) return -1;

            while (v1.charAt(l1) == v2.charAt(l1) && l1 > 0 ) l1--;

            if (v1.charAt(l1) > v2.charAt(l1)) return  1;
            if (v1.charAt(l1) < v2.charAt(l1)) return -1;
            return 0;
        }

    }

    public static class VersionIterator implements Iterator<String> {
        String s;
        int p = 0;

        public VersionIterator(String s) { this.s = s; }

        @Override
        public boolean hasNext() {
            return p >= 0;
        }

        @Override
        public String next() {
            if (p == -1) return null;
            int n = s.indexOf('.', p);
            String r;
            if (n == -1) {
                r = s.substring(p);
                p = -1;
            } else {
                r = s.substring(p, n);
                p = n+1;
            }

            return r;
        }
    }



}
