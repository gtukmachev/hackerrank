package LeetCode.problems.medium.shortes_substring;


import java.util.*;
import java.util.function.Consumer;

public class ShortestSubstring {

    public String shortest(String src, String[] words) {
        if (src == null || src.length() == 0) return "";
        if (words == null || words.length == 0) return "";


        // initialize the words index
        Map<String, Integer> counts = new HashMap<>(words.length);
        Map<String, ArrayList<Integer>> index = new HashMap<>(words.length);


        for (String w : words) {
            index.put(w, new ArrayList<>());
            counts.merge(w, 1, (o, n) -> o + n);
        }

        // fill the index
        fillIndex(src, index);

        // examine the index
        if ( index.entrySet().stream()
                .anyMatch( e -> e.getValue().size() < counts.get( e.getKey() ) )
        ) {
            return ""; // one or more target words were not found
        }


        // build a min heap
        PriorityQueue<Map.Entry<String, ArrayList<Integer>>> minHeap
                = heapOf(index, Comparator.comparingInt(e -> e.getValue().get(0)));
        // reduce min
        reduce( minHeap, counts, entry -> entry.getValue().remove(0) );


        // build a max heap
        PriorityQueue<Map.Entry<String, ArrayList<Integer>>> maxHeap
                = heapOf(index,
                            (e1, e2) -> e2.getValue().get(e2.getValue().size()-1) - e1.getValue().get(e1.getValue().size()-1)
                    );
        // reduce max
        reduce(maxHeap, counts, entry -> entry.getValue().remove(entry.getValue().size()-1) );



        int start = minHeap.peek().getValue().get(0);

        ArrayList<Integer> endList = maxHeap.peek().getValue();
        int end = endList.get(endList.size()-1) + maxHeap.peek().getKey().length();

        return src.substring(start, end);
    }


    private void fillIndex(String src, Map<String, ArrayList<Integer>> index) {
        int LENGTH = src.length();
        int beginOfTheCurrentWord = -1;
        for (int i = 0; i < LENGTH; i++) {
            if (src.charAt(i) == ' ') {
                beginOfTheCurrentWord = putWord(src, index, beginOfTheCurrentWord, i);
            }
        }
        putWord(src, index, beginOfTheCurrentWord, LENGTH);
    }

    private int putWord(String src, Map<String, ArrayList<Integer>> index, int begin, int end) {
        int b = begin + 1;
        if ( b < end ) {
            String word = src.substring(b, end);
            index.computeIfPresent(word, (w, arr) -> {
                arr.add(b);
                return arr;
            });
        }

        return end;
    }

    private void reduce(PriorityQueue<Map.Entry<String, ArrayList<Integer>>> heap,
                        Map<String, Integer> counts,
                        Consumer<Map.Entry<String, ArrayList<Integer>>> f
                        ) {
        while ( heap.peek().getValue().size() > counts.get( heap.peek().getKey() ) ) {
            Map.Entry<String, ArrayList<Integer>> entry = heap.poll();
            f.accept(entry);
            heap.offer(entry);
        }

    }


    private <K,V > PriorityQueue<Map.Entry<K, V>> heapOf(Map<K,V> map, Comparator<Map.Entry<K, V>> comparator) {
        PriorityQueue<Map.Entry<K,V>> heap = new PriorityQueue<>( comparator );
        heap.addAll( map.entrySet() );
        return heap;
    }

}
