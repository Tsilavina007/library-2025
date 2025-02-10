package test;

import dao.AuthorCrudOperations;
import entity.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static entity.Sex.FEMALE;
import static entity.Sex.MALE;
import entity.Sex;
import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.*;

class AuthorCrudOperationsTest {
	// Always rename the class to test to 'subject'
	AuthorCrudOperations subject = new AuthorCrudOperations();

	@Test
	void read_all_authors_ok() {
		// Test for data and potential mock
		Author expectedAuthor = authorJJR();

		// Subject and the function to test
		List<Author> actual = subject.getAll(1, 1000);

		// Assertions : verification to be made automatically
		assertTrue(actual.contains(expectedAuthor));
	}

	@Test
	void read_author_by_id_ok() {
		Author expectedAuthor = authorJJR();

		Author actual = subject.findById(expectedAuthor.getId());

		assertEquals(expectedAuthor, actual);
	}


	@Test
	void create_then_update_author_ok() {
		// Create a new author
		Author author = newAuthor(randomUUID().toString(), "Initial Author", FEMALE, LocalDate.of(1990, 1, 1));
		subject.saveAll(List.of(author));

		// Update the author's details
		author.setName("Updated Author");
		author.setSex(MALE);
		author.setBirthDate(LocalDate.of(1985, 5, 15));
		subject.saveAll(List.of(author));

		// Retrieve the updated author
		Author updatedAuthor = subject.findById(author.getId());

		// Verify the updates
		assertEquals("Updated Author", updatedAuthor.getName());
		assertEquals(MALE, updatedAuthor.getSex());
		assertEquals(LocalDate.of(1985, 5, 15), updatedAuthor.getBirthDate());
	}

	// TODO : make the changes inside the CrudOperations and its implementation to
	// handle this
	// Once test passed, set UnitTest corresponding
	@Test
	void read_authors_filter_by_name_or_birthday_between_intervals() {
		assertThrows(UnsupportedOperationException.class, () -> {
			throw new UnsupportedOperationException("Not implemented yet");
		});
	}

	// TODO : make the changes inside the CrudOperations and its implementation to
	// handle this
	// Once test passed, set UnitTest corresponding
	@Test
	void read_authors_order_by_name_or_birthday_or_both() {
		assertThrows(UnsupportedOperationException.class, () -> {
			throw new UnsupportedOperationException("Not implemented yet");
		});
	}

	private Author authorJJR() {
		Author expectedAuthor = new Author();
		expectedAuthor.setId("author1_id");
		expectedAuthor.setName("JJR");
		expectedAuthor.setSex(MALE);
		expectedAuthor.setBirthDate(LocalDate.of(2000, 1, 1));
		return expectedAuthor;
	}

	private Author newAuthor(String id, String name, Sex sex, LocalDate birthDate) {
		Author author = new Author();
		author.setId(id);
		author.setName(name);
		author.setSex(sex);
		author.setBirthDate(birthDate);
		return author;
	}
}

