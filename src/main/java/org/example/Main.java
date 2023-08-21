package org.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 単語のスコアは"a"以外の文字ごとに1ポイントを与える
 * 単語に'c'が含まれている場合は、５ポイントのボーナススコアを加算する。
 * 単語に's'が含まれている場合は、７ポイントのペナルティスコアをスコアから差し引く。
 * 単語のリストが与えられたら、スコアの高い順に単語を並べ替えたリストを返す
 */
public class Main {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("ada", "haskell", "scala", "java", "rust");
        List<String> ranking = rankedWords(w ->score.apply(w) + bonus.apply(w) - penalty.apply(w), words);

        System.out.println(ranking);
    }

    static Function<String, Integer> score = word ->  word.replaceAll("a", "").length();
     static Function<String, Integer> bonus = word -> word.contains("c") ? 5 : 0;
    static Function<String, Integer> penalty = word -> word.contains("s") ? 7 : 0;


    static List<String> rankedWords(
            Function<String, Integer> wordScore,
            List<String> words
    ) {
        Comparator<String> wordComparator = (w1, w2) -> Integer.compare(wordScore.apply(w2), wordScore.apply(w1));
        return words.stream()
                .sorted(wordComparator)
                .collect(Collectors.toList());
    }
}
