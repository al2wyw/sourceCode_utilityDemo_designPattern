package xpadro.tutorial.rest.repositorytest;

import java.util.HashSet;
import java.util.Set;

import org.testng.annotations.*;
import org.testng.AssertJUnit;
import org.easymock.EasyMock;

import xpadro.tutorial.rest.model.Product;
import xpadro.tutorial.rest.model.Warehouse;
import xpadro.tutorial.rest.repository.WarehouseRepositoryImpl;

public class WarehouseRepositoryImplTest {
	@Test
	public void testGetWarehouse(){
		WarehouseRepositoryImpl target = new WarehouseRepositoryImpl();
		Warehouse warehouse = target.getWarehouse(1);
		Set<Product> products = new HashSet<Product>();
		Product product = new Product(1, "PROD_004");
		products.add(product);
		product = new Product(2, "PROD_015");
		products.add(product);
		product = new Product(3, "PROD_125");
		products.add(product);
		Warehouse expected = new Warehouse(1, "WAR_BCN_004", products);
		AssertJUnit.assertSame(warehouse, expected);
	}
	
}
