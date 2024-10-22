package no.hvl.dat152.rest.ws.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import no.hvl.dat152.rest.ws.exceptions.AuthorNotFoundException;
import no.hvl.dat152.rest.ws.model.Author;
import no.hvl.dat152.rest.ws.model.Book;
import no.hvl.dat152.rest.ws.service.AuthorService;

@RestController
@RequestMapping("/elibrary/api/v1")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // Obtener todos los autores
    @GetMapping("/authors")
    public ResponseEntity<List<Author>> getAllAuthors() {
        List<Author> authors = authorService.findAll();
        if (authors.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }

    // Obtener un autor por ID
    @GetMapping("/authors/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable("id") long id) throws AuthorNotFoundException {
        Author author = authorService.findById(id);
        return new ResponseEntity<>(author, HttpStatus.OK);
    }

    // Obtener los libros de un autor por ID
    @GetMapping("/authors/{id}/books")
    public ResponseEntity<Set<Book>> getBooksByAuthorId(@PathVariable("id") long id) throws AuthorNotFoundException {
        Set<Book> books = authorService.findBooksByAuthorId(id);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    // Crear un nuevo autor
    @PostMapping("/authors")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author newAuthor = authorService.saveAuthor(author);
        return new ResponseEntity<>(newAuthor, HttpStatus.CREATED);
    }

    // Actualizar un autor
    @PutMapping("/authors/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable("id") long id, @RequestBody Author author) throws AuthorNotFoundException {
        Author updatedAuthor = authorService.updateAuthor(author, id);
        return new ResponseEntity<>(updatedAuthor, HttpStatus.OK);
    }
}
