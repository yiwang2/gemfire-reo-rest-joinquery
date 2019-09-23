package io.pivotal;

import java.util.Properties;

import org.apache.geode.cache.DataPolicy;
import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.PartitionAttributes;
import org.apache.geode.cache.RegionAttributes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.CacheFactoryBean;
import org.springframework.data.gemfire.PartitionAttributesFactoryBean;
import org.springframework.data.gemfire.PartitionedRegionFactoryBean;
import org.springframework.data.gemfire.RegionAttributesFactoryBean;
import org.springframework.data.gemfire.config.annotation.EnableLocator;
import org.springframework.data.gemfire.config.annotation.EnableManager;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctionExecutions;
import org.springframework.data.gemfire.function.config.EnableGemfireFunctions;

import io.pivotal.bookshop.domain.BookMaster;
import io.pivotal.bookshop.domain.Inventory;
import io.pivotal.functions.BookMasterCustomFunction;

@Configuration
@EnableLocator
@EnableManager(start = true)
@EnableGemfireFunctions
@EnableGemfireFunctionExecutions(basePackages = "io.pivotal.functions")
public class AccessingGemFireDataRestApplicationConfiguration {

	
	  @Autowired BookMasterCustomFunction bookMasterCustomFunction;
	 

	
	  @Bean BookMasterCustomFunction booMasterCustomFunctions() { return new
	  BookMasterCustomFunction(); }
	 

	/*
	 * @Bean Properties gemfireProperties() { Properties gemfireProperties = new
	 * Properties(); gemfireProperties.setProperty("name",
	 * "AccessingGemFireDataRestFunctionApplication");
	 * gemfireProperties.setProperty("mcast-port", "0");
	 * gemfireProperties.setProperty("log-level", "error"); return
	 * gemfireProperties; }
	 * 
	 * @Bean
	 * 
	 * @Autowired CacheFactoryBean gemfireCache() { CacheFactoryBean gemfireCache =
	 * new CacheFactoryBean(); gemfireCache.setClose(true);
	 * gemfireCache.setProperties(gemfireProperties()); return gemfireCache; }
	 */

	@Bean(name = "BookMaster")
	@Autowired
	PartitionedRegionFactoryBean<Integer, BookMaster> getBookMaster(final GemFireCache cache) {
		PartitionedRegionFactoryBean<Integer, BookMaster> bookMasterRegion = new PartitionedRegionFactoryBean<Integer, BookMaster>();
		bookMasterRegion.setCache(cache);
		bookMasterRegion.setClose(false);
		bookMasterRegion.setName("BookMaster");
		bookMasterRegion.setPersistent(false);
		//employeeRegion.setDataPolicy(DataPolicy.PRELOADED);
		return bookMasterRegion;
	}
	
	@Bean(name = "Inventory")
	@Autowired
	PartitionedRegionFactoryBean<Integer, Inventory> getInventory(final GemFireCache cache, 
			@Qualifier("partitionInventoryRegionAttributes") RegionAttributes regionAttributes) {
		PartitionedRegionFactoryBean<Integer, Inventory> inventoryRegion = new PartitionedRegionFactoryBean<Integer, Inventory>();
		inventoryRegion.setCache(cache);
		inventoryRegion.setClose(false);
		inventoryRegion.setName("Inventory");
		inventoryRegion.setPersistent(false);
		inventoryRegion.setAttributes(regionAttributes);
		
		//employeeRegion.setDataPolicy(DataPolicy.PRELOADED);
		return inventoryRegion;
	}
	
	@Bean
	@Autowired
	public  RegionAttributesFactoryBean partitionInventoryRegionAttributes(@Qualifier("inventoryPartitionAttributes") PartitionAttributes partitionAttributes) {

		RegionAttributesFactoryBean regionAttributes = new RegionAttributesFactoryBean();

		regionAttributes.setPartitionAttributes(partitionAttributes);

		return regionAttributes;
	}
	
	
	@Bean
	public PartitionAttributesFactoryBean inventoryPartitionAttributes (){
		PartitionAttributesFactoryBean partitionAttributes = new PartitionAttributesFactoryBean();

		partitionAttributes.setColocatedWith("BookMaster");

		return partitionAttributes;
	}
}
