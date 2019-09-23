package io.pivotal.repositories;

import java.util.List;

import io.pivotal.bookshop.domain.BookMaster;

public interface IExtendedBookMasterRepositories {

	/*
	 * query as
	 * 
	 * "select  b " + "from /BookMaster b, /Inventory i "
				+ "where b.itemNumber = i.itemNumber and  i.quantityInStock < 2"
	 * */
	List<BookMaster> findAllBooksWithLowQuantity();
}
