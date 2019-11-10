package hackerrank;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

class PrintMenu {
    public static void main(String[] args) {

        // preparing data
        Set<String> todayFood = getTodayFood();
        Map<String, List<String>> categories = getCompleteFoodCategories();

        // build a menu by filtering all categories with today food only
        Map<String, List<String>> menu = makeTodayMenu(todayFood, categories);

        printMenu(menu);

    }

    private static Map<String, List<String>> getCompleteFoodCategories() {
        Map<String, List<String>> categories = new HashMap<>();
        categories.put("starters", Arrays.asList("peanuts", "bread", "salad", "dumplings"));
        categories.put("mains",    Arrays.asList("steak", "bento", "chicken", "pizza", "sandwich", "taco", "sushi", "burger", "hotdog", "pasta", "wrap", "rice"));
        categories.put("desserts", Arrays.asList("apple", "strawberry", "cheese", "icecream", "pie", "cake"));
        return categories;
    }

    private static Set<String> getTodayFood() {

        return Arrays.stream(
                new String[]{"strawberry", "dumplings", "pasta", "rice", "apple", "salad", "peanuts", "cheese", "bento", "sushi"}
        ).collect(Collectors.toSet());
    }

    private static void printMenu(Map<String, List<String>> menu) {
        System.out.println("\n==== Just Falafs ====" +
                "\n      ~ Menu ~       ");

        menu.forEach((category, listOfFood) -> {
            System.out.println(category);
            System.out.println(String.join(", ", listOfFood));
            System.out.println();
        });
    }

    private static Map<String, List<String>> makeTodayMenu(Set<String> actualFood, Map<String, List<String>> categories) {

        return categories.entrySet().stream()

                // unzip to flat structure for filtering: to  stream of pairs (category name, food name)
                .flatMap( e -> e.getValue().stream().map( food -> new Pair<>(e.getKey(), food)) )

                // filter only the current menu items
                .filter( categoryFood -> actualFood.contains(categoryFood.getValue()))

                // map pairs back to the <category name> -> <a list of <food names>> dictionary
                .collect( groupingBy( Pair::getKey, mapping(Pair::getValue, toList())  ));
    }

}


/* 
Your previous Plain Text content is preserved below:

This is just a simple shared plaintext pad, with no execution capabilities.

When you know what language you'd like to use for your interview,
simply choose it from the dropdown in the top bar.

You can also change the default language your pads are created with
in your account settings: https://coderpad.io/settings

Enjoy your interview!

foodCategories = {
    "starters": ["peanuts", "bread", "salad", "dumplings"],
    "mains": ["steak", "bento", "chicken", "pizza", "sandwich", "taco", "sushi", "burger", "hotdog", "pasta", "wrap", "rice"],
    "desserts": [ "apple", "strawberry", "cheese", "icecream", "pie", "cake"]
}

    
chefDishes = ["strawberry", "dumplings", "pasta", "rice", "apple", "salad", "peanuts", "cheese", "bento", "sushi"]

==== Just Falafs ====
      ~ Menu ~
Starters
dumplings, peanuts or salad, 

Mains
bento, sushi, pasta or rice

Desserts
apple, strawberry or cheese

 */