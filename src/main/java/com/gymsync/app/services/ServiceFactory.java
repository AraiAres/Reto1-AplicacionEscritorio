package com.gymsync.app.services;

public class ServiceFactory {

	private static ServiceFactory instance = null;

	public static final String DATA_VALIDATION_SERVICE = "dataValidationService";
	public static final String DATA_CONVERSION_SERVICE = "dataConversionService";
	public static final String USER_HISTORY_PANEL_SERVICE = "userHistoryPanelService";
	public static final String ENTITY_CONVERSION_SERVICE = "entityConversionService";

	private ServiceFactory() {
	}

	public static ServiceFactory getInstance() {
		return instance = instance == null ? new ServiceFactory() : instance;
	}

	public AbstractService getService(String serviceOption) {
		AbstractService ret = null;

		switch (serviceOption) {
		case DATA_VALIDATION_SERVICE:
			ret = new DataValidationService();
			break;
		case DATA_CONVERSION_SERVICE:
			ret = new DataConversionService();
			break;
		case USER_HISTORY_PANEL_SERVICE:
			ret = new UserHistoryService();
			break;
		case ENTITY_CONVERSION_SERVICE:
			ret = new EntityConversionService();
			break;
		}

		return ret;
	}

}
