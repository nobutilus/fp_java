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
//        List<String> ranking = rankedWords(w -> score(w), words);
//        List<String> ranking = rankedWords(w -> scoreWithBonus(w), words);
        List<String> ranking = rankedWords(w -> scoreWithPenalty(w), words);
//         TODO:  関数をリストとして受け取り、簡単にロジックの追加と削除ができるようにしたい(strategy pattern)

        System.out.println(ranking);
    }

     static int score(String word) {
        return word.replaceAll("a", "").length();
    }

    static int scoreWithBonus(String word) {
        int base = score(word);
        if (word.contains("c")) return base + 5;
        else return base;
    }

//    static Comparator<String> scoreComparator = new Comparator<String>() {
//        public int compare(String w1, String w2) {
//            return Integer.compare(score(w2), score(w1));
//        }
//    };

    static int scoreWithPenalty(String word) {
        int base = score(word);
        if (word.contains("s")) return base - 7;
        return base;
    }

    static Comparator<String> scoreComparator = (w1, w2) -> Integer.compare(score(w2), score(w1));


    //    static Comparator<String> scoreWithBonusComparator = new Comparator<String>() {
//        public int compare(String w1, String w2) {
//            return Integer.compare(scoreWithBonus(w2), scoreWithBonus(w1));
//        }
//    };
    static Comparator<String> scoreWithBonusComparator = (w1, w2) -> Integer.compare(scoreWithBonus(w2), scoreWithBonus(w1));


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
