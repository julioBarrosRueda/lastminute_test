package com.sales.last.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sales.last.model.ProductType;
import com.sales.last.model.Tax;
import com.sales.last.repo.TaxesRepository;

@Component
public class DataLoader {
	
	private TaxesRepository taxesRepo;
	
	@Autowired
    public DataLoader(TaxesRepository taxesRepo) {
        this.taxesRepo = taxesRepo;
        LoadTaxes();
    }

	private void LoadTaxes() {
		taxesRepo.save(new Tax(null, ProductType.BOOK.getValue(), 0.0));
		taxesRepo.save(new Tax(null, ProductType.FOOD.getValue(), 0.0));
		taxesRepo.save(new Tax(null, ProductType.MEDICAL.getValue(), 0.0));
		taxesRepo.save(new Tax(null, ProductType.GENERIC.getValue(), 0.1));
		taxesRepo.save(new Tax(null, ProductType.DUTY.getValue(), 0.05));
	}

}
