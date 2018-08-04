package hackerrank.CrackingTheCodingInterview.easy.strings;

public class HighestValuePalindrome {

    // Complete the highestValuePalindrome function below.
    static String highestValuePalindrome(String st, int n, int k) {
        if (n == 0) return "-1";
        if (k >= n) return new String(new char[n]).replace('\0','9');

        char[] s = st.toCharArray();
        int half = n / 2;
        int j = n - 1;

        for (int i = 0; i < half; i++) {
            if      ( s[i] > s[j] ) { s[j] = s[i];  k--; }
            else if ( s[j] > s[i] ) { s[i] = s[j];  k--; }
            if (k < 0) return "-1";
            j--;
        }


        j = n - 1;
        for (int i = 0; i <= half && k > 0; i++) {
            if (s[i] < '9') {
                if (i == j) {
                  s[i] = '9'; k--;
                } else if ( s[i] != st.charAt(i) || s[j] != st.charAt(j) ) {
                    k --;
                    s[i] = '9';  s[j] = '9';
                } else {
                    if ( k >= 2) {
                        k -= 2;
                        s[i] = '9';  s[j] = '9';
                    }
                }
            }
            j--;
        }

        return new String(s);
    }



    public static void main(String[] args) {

        System.out.println( highestValuePalindrome("092282", 6, 3) ); // 992299
        System.out.println( highestValuePalindrome("12321", 5, 1) ); //12921

    }
}
