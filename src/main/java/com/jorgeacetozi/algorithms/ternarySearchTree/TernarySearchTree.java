package com.jorgeacetozi.algorithms.ternarySearchTree;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

class TernarySearchTree {

  Node root;

  void put(String key, Object value) {
    root = putRecursive(this.root, key, value, 0);
  }

  private Node putRecursive(Node currentNode, String key, Object value, int i) {
    char currentCharacterFromKey = key.charAt(i);

    if (currentNode == null) {
      currentNode = new Node(currentCharacterFromKey);
    }

    if (currentCharacterFromKey < currentNode.character) {
      currentNode.leftChild = putRecursive(currentNode.leftChild, key, value, i);
    } else if (currentCharacterFromKey > currentNode.character) {
      currentNode.rightChild = putRecursive(currentNode.rightChild, key, value, i);
    } else if (i < key.length() - 1) {
      currentNode.middleChild = putRecursive(currentNode.middleChild, key, value, i + 1);
    } else if (i == key.length() - 1) {
      currentNode.value = value;
    }
    return currentNode;
  }

  Optional<Object> get(String key) {
    return get(root, key, 0);
  }

  private Optional<Object> get(Node currentNode, String key, int i) {
    if (currentNode == null) {
      return Optional.empty();
    }

    char currentChar = key.charAt(i);

    if (currentChar < currentNode.character) {
      return get(currentNode.leftChild, key, i);
    } else if (currentChar > currentNode.character) {
      return get(currentNode.rightChild, key, i);
    } else if (i < key.length() - 1) {
      return get(currentNode.middleChild, key, i + 1);
    } else if (i == key.length() - 1) {
      return Optional.of(currentNode.value);
    }

    return Optional.empty();
  }

  List<String> findPartials(String prefix) {
    return findPartials(this.root, prefix, 0, new ArrayList<>());
  }

  List<String> findPartials(Node currentNode, String prefix, int i, List<String> partials) {
    if (currentNode == null) {
      return partials;
    }

    char currentCharacterFromPrefix = prefix.charAt(i);

    if (currentCharacterFromPrefix < currentNode.character) {
      findPartials(currentNode.leftChild, prefix, i, partials);
    } else if (currentCharacterFromPrefix > currentNode.character) {
      findPartials(currentNode.rightChild, prefix, i, partials);
    } else if (i < prefix.length() - 1) {
      findPartials(currentNode.middleChild, prefix, i + 1, partials);
    } else if (i == prefix.length() - 1) {
      return traverse(currentNode.middleChild, partials);
    }
    return partials;
  }

  private List<String> traverse(Node currentNode, List<String> partials) {
    if (currentNode == null) {
      return partials;
    }

    traverse(currentNode.leftChild, partials);
    traverse(currentNode.middleChild, partials);
    traverse(currentNode.rightChild, partials);

    if (currentNode.value != null) {
      partials.add((String) currentNode.value);
    }
    return partials;
  }

  String longestCommonPrefix(List<String> strings) {
    for (String s : strings) {
      this.put(s, s);
    }
    return findLongestCommonPrefix(this.root, 0, "", "");
  }

  String findLongestCommonPrefix(Node currentNode, int i, String tempPrefix,
      String longestCommonPrefix) {
    if (currentNode == null) {
      return longestCommonPrefix;
    }

    // If the currentNode has a left or right child, that means concatenation of the characters from
    // the root to its parent node is the longest common prefix
    if (currentNode.leftChild != null || currentNode.rightChild != null) {
      return tempPrefix;
    } else if (currentNode.value != null && longestCommonPrefix.isEmpty()) { // covers the case jor, jorge, jorgetest
      longestCommonPrefix = tempPrefix + currentNode.character;
    }
    return findLongestCommonPrefix(currentNode.middleChild, i + 1,
        tempPrefix + currentNode.character, longestCommonPrefix);
  }
}
