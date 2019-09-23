package io.pivotal.functions;

import java.util.List;

import org.springframework.data.gemfire.function.annotation.FunctionId;
import org.springframework.data.gemfire.function.annotation.OnRegion;
import org.springframework.stereotype.Component;

import io.pivotal.bookshop.domain.BookMaster;

@Component
@OnRegion(region = "BookMaster")
public interface IBookMasterCustomFunctionExecutions {

	@FunctionId("findAllBooksWithLowQuantity")
	List<BookMaster> findAllBooksWithLowQuantity();
}

