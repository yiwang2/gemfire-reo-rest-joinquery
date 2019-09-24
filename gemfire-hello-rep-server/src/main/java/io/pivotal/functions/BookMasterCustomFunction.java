package io.pivotal.functions;

import java.util.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.geode.cache.*;
import org.apache.geode.cache.execute.*;
import org.apache.geode.cache.partition.PartitionRegionHelper;
import org.apache.geode.cache.query.*;
import org.springframework.data.gemfire.function.annotation.*;
import org.springframework.stereotype.Component;

import io.pivotal.bookshop.domain.BookMaster;

@Component
public class BookMasterCustomFunction {

	
	private static final String findBookWithLowQuality = "select  b " + "from /BookMaster b, /Inventory i "
			+ "where b.itemNumber = i.itemNumber and  i.quantityInStock < 2";
	
	private static Log log = LogFactory.getLog(BookMasterCustomFunction.class);

		//@Autowired
		//private GemfireMappingContext mappingContext;

		@GemfireFunction(id = "findAllBooksWithLowQuantity", optimizeForWrite = true) //, 
		public List<BookMaster> findAllBooksWithLowQuantity(FunctionContext functionContext) {
			return executeQueryInFunctionContext(toRegionFunctionContext(functionContext));
			//return executeQueryWithRepository(toRegionFunctionContext(functionContext));
		}

		protected List<BookMaster> executeQueryInFunctionContext(FunctionContext functionContext) {
			return executeQueryInFunctionContext(toRegionFunctionContext(functionContext));
		}

		@SuppressWarnings("unchecked")
		protected List<BookMaster> executeQueryInFunctionContext(RegionFunctionContext functionContext) {
			try {
				QueryService queryService = getQueryService(functionContext);
				Query query = queryService.newQuery(findBookWithLowQuality);
				Object results = query.execute(functionContext);

				return ((SelectResults<BookMaster>) results).asList();
			}
			catch (Exception e) {
				log.error(e.getMessage());
				return null;
			}
		}

	/*
	 * protected List<BookMaster> executeQueryWithRepository(FunctionContext
	 * functionContext) { return
	 * executeQueryWithRepository(toRegionFunctionContext(functionContext)); }
	 * 
	 * protected List<BookMaster> executeQueryWithRepository(RegionFunctionContext
	 * functionContext) { return
	 * newBookMasterRepository(getRegion(functionContext)).
	 * findAllBooksWithLowQuantity(); }
	 */

		protected QueryService getQueryService(RegionFunctionContext functionContext) {
			return getRegion(functionContext).getRegionService().getQueryService();
		}

		protected <K, V> Region<K, V> getRegion(RegionFunctionContext functionContext) {
			return PartitionRegionHelper.getLocalDataForContext(functionContext);
		}

	/*
	 * protected BookMasterRepository newBookMasterRepository(Region<Long,
	 * BookMaster> books) { return new
	 * GemfireRepositoryFactory(Collections.singleton(books), mappingContext)
	 * .getRepository(BookMasterRepository.class, null); }
	 */

		protected RegionFunctionContext toRegionFunctionContext(FunctionContext functionContext) {
			return (RegionFunctionContext) functionContext;
		}
}
