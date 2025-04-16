package com.fujitsu.challenge.anagram;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnagramGeneratorTest {

    private final AnagramGenerator anagramGenerator = new AnagramGenerator();

    @Test
    void shouldGenerateAllPermutationsInLexicalOrder() {
        final List<String> result = anagramGenerator.generateSortedPermutations("abc".toCharArray());
        final List<String> expected = List.of("abc", "acb", "bac", "bca", "cab", "cba");

        assertEquals(expected, result, "Permutations should match expected list in lexicographic order");
    }

    @Test
    void shouldAlwaysReturnPermutationsInLexicalOrderRegardlessOfInputOrder() {
        final List<String> result = anagramGenerator.generateSortedPermutations("cba".toCharArray());
        final List<String> expected = List.of("abc", "acb", "bac", "bca", "cab", "cba");

        assertEquals(expected, result, "Permutations must be in lexicographic order regardless of input order");
    }

    @Test
    void shouldReturnSinglePermutationForOneLetter() {
        final List<String> result = anagramGenerator.generateSortedPermutations("x".toCharArray());

        assertEquals(List.of("x"), result, "Single-letter input should return only one permutation");
    }

    @Test
    void shouldThrowExceptionForEmptyInput() {
        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> anagramGenerator.generateSortedPermutations("".toCharArray())
        );

        assertEquals("Input must not be null or empty.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForNullInput() {
        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> anagramGenerator.generateSortedPermutations(null)
        );

        assertEquals("Input must not be null or empty.", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForInvalidCharacters() {
        final IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> anagramGenerator.generateSortedPermutations("a1b".toCharArray())
        );

        assertEquals("Input must contain only letters (a-z or A-Z).", exception.getMessage());
    }
}