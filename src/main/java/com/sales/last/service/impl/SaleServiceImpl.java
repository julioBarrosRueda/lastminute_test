package com.sales.last.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sales.last.model.ProductDTO;
import com.sales.last.model.ProductType;
import com.sales.last.model.SaleResponseDTO;
import com.sales.last.repo.TaxesRepository;
import com.sales.last.service.SaleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SaleServiceImpl implements SaleService{
	
	protected final TaxesRepository taxesRepo;
	
	@Autowired
	public SaleServiceImpl(TaxesRepository taxesRepo) {
		this.taxesRepo = taxesRepo;
	}
	
	@Override
	public SaleResponseDTO porcessSale(List<ProductDTO> productDTO) {
		log.info("Start Proccess Sale");
		
		SaleResponseDTO response = new SaleResponseDTO();
		response.setSaleTaxes((double) 0);
		response.setSaleTotal((double) 0);
		
		for (ProductDTO product : productDTO) {
			Double tax = null;
			List<String> listTax = new ArrayList<>();
			if(product.getProductName().toLowerCase().contains("imported")) {
				listTax.add(ProductType.DUTY.toString());
			}
			if( product.getProductType() != null ) {
				listTax.add(product.getProductType().toString());
				tax = taxesRepo.findByTypeIn( listTax );	
			}else {
				listTax.add(ProductType.GENERIC.toString());
				tax = taxesRepo.findByTypeIn(listTax);
			}
			
			double taxApplied = product.getProductPrice() * tax;
			product.setProductPrice( sum (product.getProductPrice() , taxApplied));
			product.setProductType(null);
			response.setSaleTaxes( sum (response.getSaleTaxes() , taxApplied)  );
			response.setSaleTotal( sumS (response.getSaleTotal() , product.getProductPrice()) );
			response.addSaleProductsItem(product);
		}
		
		return response;
	}
	
	private static double sum(double val, double tax) {
		int dotPos = 0;
		boolean dotPas = false;
		double d = val + tax;
		var bigDecimal = new BigDecimal(Double.toString(d));
		double norR = bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();

		String valS = Double.toString(norR);
		StringBuilder rval = new StringBuilder();
		for (var i = 0; i < valS.length(); i++) {
			var chr = valS.charAt(i);
			if(dotPas) {
				dotPos ++;
			}
			if (dotPos == 2) {
				rval.append(((Character.getNumericValue(chr) < 5) ? '5' : chr));
			} else {
				rval.append(chr);
			}
			if(chr == '.') {
				dotPas = true;
			}
		}
		return Double.parseDouble(rval.toString());
	}
	
	private static double sumS(double val, double tax) {
		double d = val + tax;
		var bigDecimal = new BigDecimal(Double.toString(d));
		return bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
	}
	
}
