package io.pivotal.rest.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.bookshop.domain.BookMaster;
import io.pivotal.repositories.BookMasterRepository;
import io.pivotal.repositories.ExtendedBookMasterRepositories;

  

@RestController
public class BookMasterController {

	@Autowired
	private BookMasterRepository bookMasterRepository;
	@Autowired
	private ExtendedBookMasterRepositories extendedBookMasterRepository;
	

	@RequestMapping(value = "/books", method = RequestMethod.GET)
	public List<BookMaster> getBooks() {
		List<BookMaster> bookMasterResults = new ArrayList<BookMaster>();

		Iterator<BookMaster> bookMasterIterator =  bookMasterRepository.findAll().iterator();
		while (bookMasterIterator.hasNext()) {
			bookMasterResults.add(bookMasterIterator.next());
		}

		return bookMasterResults;
	}
	
	@RequestMapping(value = "/books/low_quantity", method = RequestMethod.GET)
	public List<BookMaster> getBooksWithLowQuantity() {		
		List<BookMaster> bookMasterResults = extendedBookMasterRepository.findAllBooksWithLowQuantity();
		return bookMasterResults;
	
	}
	
	
	@RequestMapping(value = "/books", method = RequestMethod.POST)
	@Transactional
	public void addBook(@RequestBody BookMaster book) {
		bookMasterRepository.save(book);
		
	}
	
	
	@RequestMapping(value = "/books/{item_number}", method = RequestMethod.GET)
	public BookMaster getBookById(@PathVariable Integer item_number) {
		

		Optional<BookMaster> bookMasterOptional =  bookMasterRepository.findById(item_number);
		return bookMasterOptional.get();
		
	}
	
	@RequestMapping(value = "/books/{item_number}", method = RequestMethod.DELETE)
	@Transactional
	public void deleteBooksbyId(@PathVariable Integer item_number) {
		this.bookMasterRepository.deleteById(item_number);
	}
}
