package com.fujitsu.challenge.anagram;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class AnagramGenerator {

  /**
   * Generates all unique permutations of the given character array
   * and returns them in lexicographic order.
   *
   * @param characters Array of distinct characters
   * @return Sorted list of all permutations
   */
  public List<String> generateSortedPermutations(final char[] characters) {
    validate(characters);

    final List<String> permutations = new ArrayList<>();
    generatePermutations(characters, perm -> permutations.add(new String(perm)));
    Collections.sort(permutations);
    return permutations;
  }

  /**
   * Heap's Algorithm: generates all permutations and applies the given callback.
   * This method uses Heap's algorithm to generate all permutations of the input array.
   *
   * @param characters Array of characters to permute
   * @param callback Consumer that processes each generated permutation
   * @returns void
   */
  private void generatePermutations(final char[] characters, final Consumer<char[]> callback) {
    final int size = characters.length;
    final char[] current = Arrays.copyOf(characters, size);
    final int[] control = new int[size];

    callback.accept(Arrays.copyOf(current, size));

    int position = 0;
    while (position < size) {
      if (control[position] < position) {
        int indexToSwap = (position % 2 == 0) ? 0 : control[position];
        swap(current, position, indexToSwap);
        callback.accept(Arrays.copyOf(current, size));
        control[position]++;
        position = 0;
      } else {
        control[position] = 0;
        position++;
      }
    }
  }

  /**
   * Swaps two elements in the array.
   *
   * @param array Array of characters
   * @param leftIndex     Index of the first element
   * @param rightIndex     Index of the second element
   * @returns void
   */
  private void swap(final char[] array, final int leftIndex, final int rightIndex) {
    if (leftIndex != rightIndex) {
      final char temp = array[leftIndex];
      array[leftIndex] = array[rightIndex];
      array[rightIndex] = temp;
    }
  }

  /**
   * Validates the input character array.
   *
   * @param characters Array of characters to validate
   * @throws IllegalArgumentException if the array is null, empty, or contains non-letter characters
   * @returns void
   */
  private void validate(final char[] characters) {
    if (characters == null || characters.length == 0) {
      throw new IllegalArgumentException("Input must not be null or empty.");
    }

    final boolean containsInvalidCharacter = new String(characters)
        .chars()
        .anyMatch(codePoint -> !Character.isLetter(codePoint));

    if (containsInvalidCharacter) {
      throw new IllegalArgumentException("Input must contain only letters (a-z or A-Z).");
    }
  }
}