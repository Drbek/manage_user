package com.example.demo;

import java.util.List;
import java.util.Map;
import java.io.*;
import java.lang.*;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import javax.validation.Valid;
import javax.validation.constraints.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
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

@CrossOrigin
@RestController
@Validated
@RequestMapping("/api")
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
      
      User newUser=new User(name,surname,file.getOriginalFilename(),age,email,false);
      Set<ConstraintViolation<User>> constraintViolation = validator.validate(newUser);
      if (!constraintViolation.isEmpty()) {
        throw new ConstraintViolationException(constraintViolation);
      }
      String fileName = fileStorageService.storeFile(file);
      String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/images/")
            .path(fileName)
            .toUriString();
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
  User replaceUser(@RequestParam(name="file", required=false) MultipartFile file,
                   @RequestParam(name="name")  String name,
                   @RequestParam(name="surname") String surname,
                   @RequestParam(name="email")  String email,
                   @RequestParam(name="age")  int age,
                   @RequestParam(name="actif")  int actif,
                   @PathVariable Long id) {
  //try{
        return repository.findById(id)
              .map(user -> {
                if(actif==0)
                {
                  user.setActif(false);
                }else if(actif==1)
                {
                  user.setActif(true);
                }/* else
                {
                  throw new UpdatedUserException("Entrer 0 ou 1 !!!");
                } */
                user.setName(name);
                user.setSurname(surname);
                if(file!=null)
                {
                  String fileName = fileStorageService.storeFile(file);
                  String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                      .path("/images/")
                      .path(fileName)
                      .toUriString();
                  user.setPhotoUrl(fileDownloadUri);
                }
                
                user.setEmail(email);
                user.setAge(age);
                
                return repository.save(user);
              })
              .orElseGet(() -> {
                User u=new User();
                u.setId(id);
                return repository.save(u);
              });
      /* }catch(Exception e)
      {
        throw new UpdatedUserException("Something wrong happened!!");
      } */
    
  }

  @DeleteMapping("/users/{id}")
  void deleteUser(@PathVariable Long id) {
    repository.deleteById(id);
  }
}