package ch.hevs.medgift.imageclef2025.registration.config;

import lombok.Getter;

public enum AI4MediaFields {
	PERSON("4"), TEAM("3"), COUNTRY("5"), EMAIL("6"), AFFILIATION("13"), TASKS("8");

	@Getter
	private String key;

	AI4MediaFields(String key) {
		this.key = key;
	}

	public static String[] getKeys() {
		var fields = AI4MediaFields.values();
		String[] keys = new String[fields.length];

		for (int i = 0; i < fields.length; i++) {
			keys[i] = fields[i].getKey();
		}
		return keys;
	}

	public static String getName(String key) {

		for (AI4MediaFields field : AI4MediaFields.values()) {
			if (field.getKey().equals(key))
				return field.name().toLowerCase();
		}
		throw new IllegalArgumentException("Key " + key + " not found in enum");
	}

}
