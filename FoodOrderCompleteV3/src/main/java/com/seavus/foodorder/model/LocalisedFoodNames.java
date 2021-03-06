package com.seavus.foodorder.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class LocalisedFoodNames {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
//    private int dummy = 0;
    
    @Fetch(FetchMode.JOIN)
    @ElementCollection
    private Map<String,String> strings = new HashMap<String, String>();

    //private String locale;    
    //private String text;

    public LocalisedFoodNames() {}

    public LocalisedFoodNames(Map<String, String> map) {
        this.strings = map;
    }

    public void addString(String locale, String text) {
        strings.put(locale, text);
    }

    public String getString(String locale) {
        String returnValue = strings.get(locale);
        return (returnValue != null ? returnValue : null);
    }
	
}
