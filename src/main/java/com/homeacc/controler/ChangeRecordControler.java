package com.homeacc.controler;

import static com.homeacc.appconst.AppConst.EMPTY_STRING;
import static com.homeacc.appconst.AppConst.TEXT_BLUE;
import static com.homeacc.appconst.AppConst.TEXT_RED;

import javafx.scene.control.Label;

public abstract class ChangeRecordControler {

	protected static boolean recordsChanged;

	protected void createError(Label error, String message) {
		error.setText(message);
		error.setStyle(TEXT_RED);
	}

	protected void createInfo(Label error, String message) {
		error.setText(message);
		error.setStyle(TEXT_BLUE);
	}

	protected void clearError(Label error) {
		error.setText(EMPTY_STRING);
		error.setStyle(EMPTY_STRING);
	}
}
