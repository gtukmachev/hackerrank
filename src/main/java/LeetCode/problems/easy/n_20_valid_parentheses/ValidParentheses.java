package LeetCode.problems.easy.n_20_valid_parentheses;

import org.junit.Test;

import java.util.EmptyStackException;
import java.util.Stack;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ValidParentheses {

    public boolean isValid(String s) {
        if (s == null || s.isEmpty()) return true;

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else {
                char pair = 0;
                     if (ch == ')') pair = '(';
                else if (ch == ']') pair = '[';
                else if (ch == '}') pair = '{';
                if (pair != 0) { // ignore all other symbols
                    try {
                        if (stack.pop() != pair) return false;
                    } catch (EmptyStackException e) {
                        return false;
                    }
                }
            }
        }

        return stack.isEmpty();
    }


    @Test public void t0() { assertThat(isValid(null),           is(true)); }
    @Test public void t1() { assertThat(isValid(""),             is(true)); }
    @Test public void t2() { assertThat(isValid("([{}])([{}])"), is(true)); }

    @Test public void f0() { assertThat(isValid(" "),           is(true)); }
    @Test public void f1() { assertThat(isValid("([{}]([{}])"), is(false)); }

}
