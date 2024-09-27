package ru.example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static final String HASHTAG_PATTERN = "^#\\S+";

    public static void main(String[] args) {
        // Есть список из String'ов, каждый из которых содержит какой-то текст. В тексте встречаются хэштеги. Хэштегом считается часть String'а, который начинается с символа '#' и заканчивается либо пробелом, либо концом строки.
        //Например, List<String> posts = List.of("Привет #Питер", "Привет #Москва", "У нас #Питер светит солнце", "А у нас дождь");
        var exampleStrings = List.of("Привет #Питер", "Привет #Москва", "У нас #Питер светит солнце", "А у нас дождь");

        var uniqueHashTags = getSortedByAlphabet(exampleStrings);
        //Если брать пример, то ожидается на выходе List.of("#Москва", "#Питер")
        System.out.println(uniqueHashTags);
        var sortedHashTags = getSortedByFrequency(exampleStrings);
        //Если брать пример, то '#Питер' встречается 2 раза, а '#Москва' - 1 раз.
        // И метод должен вернуть List.of("#Питер", "#Москва")
        System.out.println(sortedHashTags);
    }

    //1. Нужно написать метод, который принимает на вход список строк и
    // возвращает список найденных уникальных хештегов в алфавитном порядке.
    private static List<String> getSortedByAlphabet(List<String> strings) {
        return strings.stream()
                .map(post -> post.split(" "))
                .flatMap(Arrays::stream)
                .filter(word -> word.matches(HASHTAG_PATTERN))
                .distinct()
                .sorted()
                .toList();
    }

    //2. Переделать реализацию метода из первого пункта так, чтобы возвращался список хештегов по популярности.
    private static List<String> getSortedByFrequency(List<String> strings) {
        return strings.stream()
                .map(post -> post.split(" "))
                .flatMap(Arrays::stream)
                .filter(word -> word.matches(HASHTAG_PATTERN))
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .toList();
    }
}
