package com.chunarevsa.website.service;

import com.chunarevsa.website.entity.User;
import com.chunarevsa.website.exception.InvalidAmountFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Функции администратора 
 */
@Service
public class AdminService {

	private static final Logger logger = LogManager.getLogger(AdminService.class);

	private final UserService userService;

	@Autowired
	public AdminService(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Добавление суммы пользователю
	 * @param username
	 * @param amount
	 */
	public void addMoneyToUser(String username, String amount) {

		User user = userService.findByUsername(username);
		if (!validateAmount(amount)) {
			logger.error("Сумма " + amount +" указана неверно");
			throw new InvalidAmountFormat("Cумма", "$", amount);
		}

		double oldBalance = Math.round(Double.parseDouble(amount));
		double toAdd = Math.round(Double.parseDouble(user.getBalance()));
		double sum =  Math.round(oldBalance + toAdd);

		user.setBalance(Double.toString(sum));
		userService.saveUser(user);
		logger.info("Новый баланс " + username + " :"+ user.getBalance() + " $");
	}

	/**
	 * Валидация суммы поступления
	 */
	private boolean validateAmount (String amount) {
		try {
			double value = Double.parseDouble(amount);
			if (value <= 0 ) {
				return false;
			}
			return true;

		} catch (NumberFormatException e) { 
			return false;
		}
	}
}
