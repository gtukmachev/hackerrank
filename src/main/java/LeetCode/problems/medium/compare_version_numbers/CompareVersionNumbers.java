package LeetCode.problems.medium.compare_version_numbers;

/**
 * Created by grigory@clearscale.net on 8/28/2018.
 */
public class CompareVersionNumbers {

    public class Solution {

        public int compareVersion(String version1, String version2) {
            int p1 = 0, l1 = skipZeros(version1, p1);
            int p2 = 0, l2 = skipZeros(version2, p2);

            for(;;){
                p1 = next(version1, l1);
                p2 = next(version2, l2);


                if (p1 == -1 && p2 == -1) return 0;
                if (p1 != -1 && p2 == -1) return checkForZeroLeaf(version1, l1,  1);
                if (p1 == -1            ) return checkForZeroLeaf(version2, l2, -1);

                int r = compare(version1, p1, l1, version2, p2, l2);
                if (r != 0) return r;

                l1 = skipZeros(version1, p1+1);
                l2 = skipZeros(version2, p2+1);;
            }

        }

        int checkForZeroLeaf(String s, int from, int result) {
            while (from < s.length()) {
                char ch = s.charAt(from);
                if (ch != '0' && ch != '.') {
                    return result;
                }
                from++;
            }
            return 0;
        }

        int skipZeros(String s, int p) {
            int n = p;
            while (n < s.length() && s.charAt(n) == '0') n++;
            return n;
        }

        int next(String s, int p) {
            if (p > s.length()) return -1;
            int n = s.indexOf('.', p);
            return n == -1 ? s.length() : n;
        }

        int compare(String v1, int p1, int last1, String v2, int p2, int last2) {
            int len1 = p1 - last1;
            int len2 = p2 - last2;

            if (len1 > len2) return  1;
            if (len1 < len2) return -1;

            int i1 = last1, i2 = last2;
            while (i1 < p1 && i2 < p2 && v1.charAt(i1) == v2.charAt(i2)) {i1++; i2++;}

            if (i1 == p1) return 0;
            if (v1.charAt(i1) > v2.charAt(i2)) return  1;
            if (v1.charAt(i1) < v2.charAt(i2)) return -1;
            return 0;
        }

    }

}
