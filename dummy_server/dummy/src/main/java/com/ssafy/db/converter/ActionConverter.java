package com.ssafy.db.converter;

import javax.persistence.AttributeConverter;

public class ActionConverter implements AttributeConverter<String, Integer> {

	@Override
	public Integer convertToDatabaseColumn(String attribute) {
		// TODO Auto-generated method stub
		if("CREATE".equals(attribute)) {
			return 0;
		}else if("JOIN".equals(attribute)) {
			return 1;
		}
		
		return 2;
	}

	@Override
	public String convertToEntityAttribute(Integer dbData) {
		// TODO Auto-generated method stub
		if(0 == dbData) {
			return "CREATE";
		}else if(1 == dbData) {
			return "JOIN";
		}
		
		return "EXIT";
	}

}
