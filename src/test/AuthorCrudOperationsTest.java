package test;

import dao.AuthorCrudOperations;
import dao.Criteria;
import entity.Author;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
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
		ArrayList<Criteria> criteria = new ArrayList<>();
		criteria.add(new Criteria("name", "rado"));
		criteria.add(new Criteria("birth_date", LocalDate.of(2000, 1, 1)));
		List<Author> expected = List.of(
			authorJJR(),
			authorRado());

		List<Author> actual = subject.findByCriteria(criteria, 1, 10);

		assertEquals(expected, actual);
		assertTrue(actual.stream()
				.allMatch(author -> author.getName().toLowerCase().contains("rado")
						|| author.getBirthDate().equals(LocalDate.of(2000, 1, 1))));

	}

	private Author authorRado() {
		return newAuthor("author2_idRado", "Rado", Sex.MALE, LocalDate.of(1990, 1, 1));
	}

	// TODO : make the changes inside the CrudOperations and its implementation to
	// handle this
	// Once test passed, set UnitTest corresponding
	@Test
	void read_authors_order_by_name_or_birthday_or_both() {
		// Criteria for ordering by name
		ArrayList<Criteria> criteriaByName = new ArrayList<>();
		criteriaByName.add(new Criteria("name"));

		// Criteria for ordering by birth_date
		ArrayList<Criteria> criteriaByBirthDate = new ArrayList<>();
		criteriaByBirthDate.add(new Criteria("birth_date"));

		// Criteria for ordering by both name and birth_date
		ArrayList<Criteria> criteriaByNameAndBirthDate = new ArrayList<>();
		criteriaByNameAndBirthDate.add(new Criteria("name"));
		criteriaByNameAndBirthDate.add(new Criteria("birth_date"));

		List<Author> expectedByName = subject.getAll(1, 10);
		expectedByName.sort((a1, a2) -> a1.getName().compareTo(a2.getName()));

		List<Author> expectedByBirthDate = subject.getAll(1, 10);
		expectedByBirthDate.sort((a1, a2) -> a1.getBirthDate().compareTo(a2.getBirthDate()));

		List<Author> expectedByNameAndBirthDate = subject.getAll(1, 10);
		expectedByNameAndBirthDate.sort((a1, a2) -> {
			int nameComparison = a1.getName().compareTo(a2.getName());
			if (nameComparison == 0) {
				return a1.getBirthDate().compareTo(a2.getBirthDate());
			}
			return nameComparison;
		});

		// Test ordering by name
		List<Author> actualByName = subject.orderByCriteria(criteriaByName, 1, 10);
		assertEquals(expectedByName, actualByName);

		// Test ordering by birth_date
		List<Author> actualByBirthDate = subject.orderByCriteria(criteriaByBirthDate, 1, 10);
		assertEquals(expectedByBirthDate, actualByBirthDate);

		// Test ordering by both name and birth_date
		List<Author> actualByNameAndBirthDate = subject.orderByCriteria(criteriaByNameAndBirthDate, 1, 10);
		assertEquals(expectedByNameAndBirthDate, actualByNameAndBirthDate);
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
