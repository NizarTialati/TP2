package com.uds.coloration;

public enum ColorsEnum {

	Red("Rouge"), Yellow("Jaune"), Green("Vert"), Blue("Bleu"), Orange("Orange"), 
	Azure("Azure"), Black("Noir"), White("Blanc"), Pink("Rose"), Purple("Violet"), Brown("Marron");

	private final String value;

	private ColorsEnum(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

}
