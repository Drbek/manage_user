package com.example.demo;

class UserNotFoundException extends RuntimeException {

  UserNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
}