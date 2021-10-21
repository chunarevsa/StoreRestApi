package com.chunarevsa.Website.service.valid;

import com.chunarevsa.Website.Entity.Price;
import com.chunarevsa.Website.Entity.Status;
import com.chunarevsa.Website.Exception.FormIsEmpty;
import com.chunarevsa.Website.Exception.InvalidPriceFormat;
import com.chunarevsa.Website.repo.PriceRepository;

import org.springframework.stereotype.Service;

@Service
public class PriceValid {

	private final PriceRepository priceRepository;

	public PriceValid(PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}

	// Проверка на наличие 
	public boolean priceIsPresent (long id) {
		Price price = priceRepository.findById(id).orElse(null);
		if (price == null) {
			return true;
		}
		return false; 
	}

	// Проверка не выключен ли active = true
	public boolean priceIsActive (Long id) {
		Status status =priceRepository.findById(id).orElseThrow().getStatus();
		if (status == Status.ACTIVE) {
			return true;
	}
		return false;
	}

	// Проверка на формат числа
	public boolean amountIsCorrect (Price priceBody) throws InvalidPriceFormat {
		
		int i = Integer.parseInt(priceBody.getAmount());
		if (i < 0) {
			return false;
		}
		return true;
	}

	// Проверка на незаполеннные данные
	public boolean bodyIsEmpty (Price priceBody) throws FormIsEmpty {
		if (priceBody.getAmount().isEmpty()) {
			return true;
		}
		return false;
	}
	
}
