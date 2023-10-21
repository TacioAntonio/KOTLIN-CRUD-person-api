package com.example.demo.app.controllers

import com.example.demo.types.Person
import com.example.demo.utils.PERSONS
import com.example.demo.utils.generateRandomID
import com.example.demo.utils.isValidEmail
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@CrossOrigin("*")
@RestController
@RequestMapping("/")
class PersonsController {
    @GetMapping("/persons")
    fun getAll(): MutableList<Person> {
        return PERSONS;
    }

    @GetMapping("/person/{id}")
    fun getById(@PathVariable id: String): Any {
        val currentPerson = PERSONS.filter { it.id == id }

        if (currentPerson.isEmpty()) {
            return ResponseEntity.badRequest().body("Person not found");
        }

        return currentPerson[0];
    }

    @DeleteMapping("/person/delete/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<String> {
        val newPersons = PERSONS.filter { it.id != id }
        PERSONS.clear()
        PERSONS.addAll(newPersons);

        return ResponseEntity.status(HttpStatus.OK).body("Person deleted successfully!");
    }

    @PostMapping("/person/create")
    fun create(@RequestBody newDataPerson: Map<String, Any>): ResponseEntity<String> {
        try {
            val avatar = newDataPerson["Avatar"] as String
            val firstname = newDataPerson["Firstname"] as String
            val lastname = newDataPerson["Lastname"] as String
            val email = newDataPerson["Email"] as String
            val age = newDataPerson["Age"] as Int

            if (avatar.isEmpty()) {
                return ResponseEntity.badRequest().body("Avatar is a required field")
            }

            if (firstname.isEmpty()) {
                return ResponseEntity.badRequest().body("Firstname is a required field")
            }

            if (lastname.isEmpty()) {
                return ResponseEntity.badRequest().body("Lastname is a required field")
            }

            if (email.isEmpty()) {
                return ResponseEntity.badRequest().body("Email is a required field")
            }

            if (!isValidEmail(email)) {
                return ResponseEntity.badRequest().body("Email is invalid")
            }

            if (age <= 0 || age > 120) {
                return ResponseEntity.badRequest().body("The Age field must be greater than 0 and less than or equal to 120")
            }

            PERSONS.add(Person(generateRandomID(), avatar, firstname, lastname, email, age));

            return ResponseEntity.ok("Stored successfully!")
        } catch (e: Exception) {
            val errorMessage = "Error: $e"
            return ResponseEntity.status(400).body(errorMessage)
        }
    }

    @PutMapping("/person/update/{id}")
    fun update(@PathVariable id: String, @RequestBody newDataPerson: Map<String, Any>): ResponseEntity<Any> {
        try {
            val indexToUpdate = PERSONS.indexOfFirst { it.id == id }

            if (indexToUpdate == -1) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Person not found")
            }

            val avatar = newDataPerson["Avatar"] as String
            val firstname = newDataPerson["Firstname"] as String
            val lastname = newDataPerson["Lastname"] as String
            val email = newDataPerson["Email"] as String
            val age = newDataPerson["Age"] as Int

            if (avatar.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Avatar is a required field")
            }

            if (firstname.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Firstname is a required field")
            }

            if (lastname.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Lastname is a required field")
            }

            if (email.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is a required field")
            }

            if (!isValidEmail(email)) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is invalid")
            }

            if (age <= 0 || age > 120) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The Age field must be greater than 0 and less than or equal to 120")
            }

            PERSONS[indexToUpdate] = Person(id, avatar, firstname, lastname, email, age)

            return ResponseEntity.status(HttpStatus.OK).body("Person updated successfully")
        } catch (e: Exception) {
            val errorMessage = "Error: $e"
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage)
        }
    }
}
