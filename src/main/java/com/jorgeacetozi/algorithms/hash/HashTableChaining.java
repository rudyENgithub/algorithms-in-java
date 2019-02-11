package com.jorgeacetozi.algorithms.hash;

import java.util.Optional;

class HashTableChaining<K extends Comparable<K>, V> {

  HashItem<K, V>[] table;
  int capacity;
  int size;

  @SuppressWarnings("unchecked")
  HashTableChaining(int capacity) {
    table = (HashItem<K, V>[]) new HashItem[capacity];
    this.capacity = capacity;
  }

  // O(1) because we add to the head of the Singly Linked List
  void put(K key, V value) {
    int index = hashFunction(key);
    HashItem<K, V> newItem = new HashItem<>(key, value);

    if (table[index] == null) {
      table[index] = newItem;
    } else {
      newItem.next = table[index];
      table[index] = newItem;
    }
    size++;
  }
  
  // O(N) because we have to iterate over the list and check the key
  Optional<V> get(K key) {
    int index = hashFunction(key);

    if (table[index] == null) {
      return Optional.empty();
    } else {
      HashItem<K, V> currentItem = table[index];
      
      while (currentItem != null) {
        if (currentItem.key.equals(key)) {
          return Optional.of(currentItem.value);
        }
        currentItem = currentItem.next;
      }
      return Optional.empty();
    }
  }

  private int hashFunction(K key) {
    return Math.abs(key.hashCode() % capacity);
  }
}
