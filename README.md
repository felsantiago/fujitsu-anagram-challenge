# 🧠 Fujitsu - Anagram Generator Challenge

This repository contains the solution to Fujitsu's technical challenge. The goal is to generate all possible permutations (anagrams) of a given word, sorted in lexicographic order, with proper input validation.

---

## 📌 Description

The implemented algorithm is based on [Heap's Algorithm](https://rosettacode.org/wiki/Permutations_by_swapping#Java), which efficiently generates all permutations of a character array. The output is a list of unique strings sorted alphabetically.

---

## 🚀 Getting Started

### ✅ Requirements

- Java 17+
- Git
- Gradle (or use the provided wrapper: `./gradlew`)

### 🔧 How to Run

```bash
# Clone the repository
git clone https://github.com/felsantiago/fujitsu-anagram-challenge.git
cd fujitsu-anagram-challenge

# Run tests
./gradlew test

# Or compile and run the main program
./gradlew run
```

> 💡 By default, the program prints all permutations of the word `abc` or `cba`. You can modify the `main` method in 
> `com.fujitsu.challenge.anagram.Application.java` to test with different words.

---

## 🧪 Tests

Unit tests were written using JUnit 5 and cover:
- Permutation generation in lexicographic order
- Validation of invalid inputs (null, empty, or containing non-letter characters)
- Edge cases like a single-character input

---

## 📁 Project Structure

```plaintext
.
├── src/
│   ├── main/java/com/fujitsu/challenge/anagram/
│   │   └── AnagramGenerator.java
│   │   └── Application.java
│   └── test/java/com/fujitsu/challenge/anagram/
│       └── AnagramGeneratorTest.java
├── build.gradle
├── settings.gradle
├── gradlew / gradlew.bat
└── .gitignore
```

---

## 🛠️ Technologies Used

- Java 17
- Gradle
- JUnit 5

---

## ✍️ Author

Developed by **[Luiz Santigo]** as part of a technical evaluation for **Fujitsu**.

---

## 📃 License

This project is for technical assessment purposes only.
