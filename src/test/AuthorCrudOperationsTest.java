package test;

import dao.AuthorCrudOperations;
import entity.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class AuthorCrudOperationsTest {
    // Always rename the class to test to 'subject'
    AuthorCrudOperations subject = new AuthorCrudOperations();

    @Test
    void read_all_authors_ok() {
        // Test for data and potential mock
        Author expectedAuthor = new Author();
        expectedAuthor.setId("author1_id");
        expectedAuthor.setName("JJR");
        expectedAuthor.setBirthDate(LocalDate.of(2000, 1, 1));

        // Subject and the function to test
        List<Author> actual = subject.getAll();

        // Assertions : verification to be made automatically
        assertTrue(actual.contains(expectedAuthor));
    }

    @Test
    void read_author_by_id_ok() {
        throw new UnsupportedOperationException("TODO: not supported yet.");
    }

    @Test
    void create_then_update_author_ok() {
        throw new UnsupportedOperationException("TODO: not supported yet.");
    }
}

