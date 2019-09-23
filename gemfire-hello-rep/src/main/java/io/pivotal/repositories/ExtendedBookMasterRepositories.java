package io.pivotal.repositories;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.pivotal.bookshop.domain.BookMaster;
import io.pivotal.functions.IBookMasterCustomFunctionExecutions;

@Component
public class ExtendedBookMasterRepositories implements IExtendedBookMasterRepositories{

	
	@Autowired
	private IBookMasterCustomFunctionExecutions bookMasterCustomFunctionExecutions;

	@Override
	public List<BookMaster> findAllBooksWithLowQuantity() {
		return bookMasterCustomFunctionExecutions.findAllBooksWithLowQuantity();
	}
	

}
