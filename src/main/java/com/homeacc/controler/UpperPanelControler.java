package com.homeacc.controler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpperPanelControler {

	@Autowired
	private ManageCategoryControler manageCategoryControler;

	public void manageCategory() {
		try {
			manageCategoryControler.openModal();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
