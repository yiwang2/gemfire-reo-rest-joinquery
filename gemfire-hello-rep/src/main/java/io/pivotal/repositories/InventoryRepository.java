package io.pivotal.repositories;

import org.springframework.data.gemfire.repository.GemfireRepository;

import io.pivotal.bookshop.domain.Inventory;

public interface InventoryRepository extends GemfireRepository<Inventory, Integer>{

}
