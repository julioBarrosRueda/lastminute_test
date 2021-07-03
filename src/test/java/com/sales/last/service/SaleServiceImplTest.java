package com.sales.last.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sales.last.model.ProductDTO;
import com.sales.last.model.ProductType;
import com.sales.last.model.SaleResponseDTO;
import com.sales.last.repo.TaxesRepository;
import com.sales.last.rest.SaleController;
import com.sales.last.service.SaleService;
import com.sales.last.service.impl.SaleServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class SaleServiceImplTest {

	protected ObjectMapper mapper = new ObjectMapper();

	@Mock
	private TaxesRepository taxesRepo;

	SaleServiceImpl cut;

	private List<ProductDTO> productos = new ArrayList<>();	
	private List<ProductDTO> productosInput2 = new ArrayList<>();
	private List<ProductDTO> productosInput3 = new ArrayList<>();

	@Before
	public void setup() {
		MockitoAnnotations.openMocks(this);
		cut = new SaleServiceImpl(taxesRepo);

		try {

			JsonNode nodeObject = mapper.readValue(
					Files.readAllBytes(Paths.get("src", "test", "resources", "json", "firstInput.json")), JsonNode.class);
			for (JsonNode jsonNode : nodeObject) {
				productos.add( mapper.convertValue(jsonNode, new TypeReference<ProductDTO>() {}) );
			}
			
			nodeObject = mapper.readValue(
					Files.readAllBytes(Paths.get("src", "test", "resources", "json", "SecondInput.json")), JsonNode.class);
			for (JsonNode jsonNode : nodeObject) {
				productosInput2.add( mapper.convertValue(jsonNode, new TypeReference<ProductDTO>() {}) );
			}
			
			nodeObject = mapper.readValue(
					Files.readAllBytes(Paths.get("src", "test", "resources", "json", "thirdInput.json")), JsonNode.class);
			for (JsonNode jsonNode : nodeObject) {
				productosInput3.add( mapper.convertValue(jsonNode, new TypeReference<ProductDTO>() {}) );
			}

		} catch (Exception e) {
			log.debug("Could not read installations json file");
		}
	}

	@Test
	public void porcessSaleTest() {
		
		when(taxesRepo.findByTypeIn( Arrays.asList( ProductType.BOOK.toString() )  ) ).thenReturn(0.0);
		when(taxesRepo.findByTypeIn( Arrays.asList( ProductType.FOOD.toString() )  ) ).thenReturn(0.0);
		when(taxesRepo.findByTypeIn( Arrays.asList( ProductType.GENERIC.toString() )  ) ).thenReturn(0.1);
		
		SaleResponseDTO resp = cut.porcessSale(productos);
		
		assertThat( resp.getSaleProducts().get(0).getProductPrice().doubleValue() , is( 12.49) );
		assertThat( resp.getSaleProducts().get(1).getProductPrice().doubleValue() , is( 16.49) );
		assertThat( resp.getSaleProducts().get(2).getProductPrice().doubleValue() , is( 0.85) );
		assertThat( resp.getSaleTaxes() , is( 1.5) );
		assertThat( resp.getSaleTotal() , is( 29.83) );
	}
	
	@Test
	public void porcessSaleTest_2() {
		
		when(taxesRepo.findByTypeIn( Arrays.asList( ProductType.DUTY.toString(), ProductType.FOOD.toString() )  ) ).thenReturn(0.05);
		when(taxesRepo.findByTypeIn( Arrays.asList( ProductType.DUTY.toString(), ProductType.GENERIC.toString() ) ) ).thenReturn(0.15);
		
		SaleResponseDTO resp = cut.porcessSale(productosInput2);
		
		assertThat( resp.getSaleProducts().get(0).getProductPrice().doubleValue() , is( 10.50) );
		assertThat( resp.getSaleProducts().get(1).getProductPrice().doubleValue() , is( 54.65) );
		assertThat( resp.getSaleTaxes() , is( 7.65) );
		assertThat( resp.getSaleTotal() , is( 65.15) );
	}
	
	@Test
	public void porcessSaleTest_3() {
		
		when(taxesRepo.findByTypeIn( Arrays.asList( ProductType.DUTY.toString(), ProductType.FOOD.toString() )  ) ).thenReturn(0.05);
		when(taxesRepo.findByTypeIn( Arrays.asList( ProductType.MEDICAL.toString() )  ) ).thenReturn(0.0);
		when(taxesRepo.findByTypeIn( Arrays.asList( ProductType.GENERIC.toString() )  ) ).thenReturn(0.1);
		when(taxesRepo.findByTypeIn( Arrays.asList( ProductType.DUTY.toString(), ProductType.GENERIC.toString() )  ) ).thenReturn(0.15);
		
		SaleResponseDTO resp = cut.porcessSale(productosInput3);
		
		assertThat( resp.getSaleProducts().get(0).getProductPrice().doubleValue() , is( 32.19) );
		assertThat( resp.getSaleProducts().get(1).getProductPrice().doubleValue() , is( 20.89) );
		assertThat( resp.getSaleProducts().get(2).getProductPrice().doubleValue() , is( 9.75) );
		assertThat( resp.getSaleProducts().get(3).getProductPrice().doubleValue() , is( 11.85) );
		assertThat( resp.getSaleTaxes() , is( 6.66) );
		assertThat( resp.getSaleTotal() , is( 74.68) );
	}
}
