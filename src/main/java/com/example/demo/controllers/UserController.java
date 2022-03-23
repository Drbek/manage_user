package com.example.demo;

import java.util.List;
import java.util.Map;
import java.io.*;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Autowired;


@RestController
@Validated
class UserController {

  private final UserRepository repository;

  UserController(UserRepository repository) {
    this.repository = repository;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/users")
  List<User> all() {
    return repository.findAll();
  }
  // end::get-aggregate-root[]
  @Autowired
  private FileStorageService fileStorageService;
  @Autowired
	Validator validator;
  @PostMapping("/users")
  User newUser(@RequestParam(name="file") MultipartFile file,
              @RequestParam(name="name")  String name,
              @RequestParam(name="surname") String surname,
              @RequestParam(name="email")  String email,
              @RequestParam(name="age")  int age) {
      String fileName = fileStorageService.storeFile(file);
      String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/images/")
            .path(fileName)
            .toUriString();
      User newUser=new User(name,surname,fileDownloadUri,age,email,false);
      Set<ConstraintViolation<User>> constraintViolation = validator.validate(newUser);
      if (!constraintViolation.isEmpty()) {
        throw new ConstraintViolationException(constraintViolation);
      }
      newUser.setPhotoUrl(fileDownloadUri);
      return repository.save(newUser);
  }

  // Single item
  
  @GetMapping("/users/{id}")
  User one(@PathVariable Long id) {
    
    return repository.findById(id)
      .orElseThrow(() -> new UserNotFoundException(id));
  }

  @PutMapping("/users/{id}")
  User replaceUser(@RequestBody User newUser, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(employee -> {
        employee.setName(newUser.getName());
        employee.setSurname(newUser.getSurname());
        employee.setPhotoUrl(newUser.getPhotoUrl());
        employee.setEmail(newUser.getEmail());
        employee.setAge(newUser.getAge());
        employee.setActif(newUser.getActif());
        return repository.save(employee);
      })
      .orElseGet(() -> {
        newUser.setId(id);
        return repository.save(newUser);
      });
  }

  @DeleteMapping("/users/{id}")
  void deleteUser(@PathVariable Long id) {
    repository.deleteById(id);
  }
}