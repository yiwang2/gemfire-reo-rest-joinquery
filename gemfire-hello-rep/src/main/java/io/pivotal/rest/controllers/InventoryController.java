package io.pivotal.rest.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.pivotal.bookshop.domain.Inventory;
import io.pivotal.repositories.InventoryRepository;

@RestController
public class InventoryController {

	
	@Autowired
	private InventoryRepository inventoryRepository;

	@RequestMapping(value = "/inventories", method = RequestMethod.GET)
	public List<Inventory> getInventories() {
		List<Inventory> inventoryResults = new ArrayList<Inventory>();

		Iterator<Inventory> inventoryIterator =  inventoryRepository.findAll().iterator();
		while (inventoryIterator.hasNext()) {
			inventoryResults.add(inventoryIterator.next());
		}

		return inventoryResults;
	}
	
	@RequestMapping(value = "/inventories", method = RequestMethod.POST)
	@Transactional
	public void addBook(@RequestBody Inventory inventory) {
		inventoryRepository.save(inventory);
		
	}
}
