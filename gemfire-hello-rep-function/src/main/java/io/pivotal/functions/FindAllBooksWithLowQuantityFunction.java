package io.pivotal.functions;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.geode.cache.*;
import org.apache.geode.cache.Region;
import org.apache.geode.cache.execute.*;
import org.apache.geode.cache.partition.PartitionRegionHelper;
import org.apache.geode.cache.query.*;

import io.pivotal.bookshop.domain.BookMaster;

public class FindAllBooksWithLowQuantityFunction implements Function<BookMaster> {

	private static final String findBookWithLowQuality = "select  b " + "from /BookMaster b, /Inventory i "
			+ "where b.itemNumber = i.itemNumber and  i.quantityInStock < 2";

	private static Log log = LogFactory.getLog(FindAllBooksWithLowQuantityFunction.class);

	@Override
	public void execute(FunctionContext context) {

		ResultSender resultSender = context.getResultSender();

		if (!(context instanceof RegionFunctionContext)) {
			resultSender.lastResult(null);
			return;
		}

		List<BookMaster> books = executeQueryInFunctionContext(toRegionFunctionContext(context));

		ResultSender<BookMaster> bookMasterResultSender = context.getResultSender();

		if (books == null || books.isEmpty()) {
			bookMasterResultSender.lastResult(null);
		} else if (books.size() == 1) {
			bookMasterResultSender.lastResult(books.get(0));
		} else {
			for (int index = 0; index < books.size() - 1; index++) {
				bookMasterResultSender.sendResult(books.get(index));
			}
			bookMasterResultSender.lastResult(books.get(books.size() - 1));
		}

	}

	@SuppressWarnings("unchecked")
	protected List<BookMaster> executeQueryInFunctionContext(RegionFunctionContext functionContext) {
		try {
			QueryService queryService = getQueryService(functionContext);
			Query query = queryService.newQuery(findBookWithLowQuality);
			Object results = query.execute(functionContext);

			return ((SelectResults<BookMaster>) results).asList();
		} catch (Exception e) {
			log.error(e.getMessage());
		}

		return null;
	}

	protected QueryService getQueryService(RegionFunctionContext functionContext) {
		return getRegion(functionContext).getRegionService().getQueryService();
	}

	protected <K, V> Region<K, V> getRegion(RegionFunctionContext functionContext) {
		return PartitionRegionHelper.getLocalDataForContext(functionContext);
	}

	protected RegionFunctionContext toRegionFunctionContext(FunctionContext functionContext) {
		return (RegionFunctionContext) functionContext;
	}

	public String getId() {
		return "findAllBooksWithLowQuantity";
	}

}
