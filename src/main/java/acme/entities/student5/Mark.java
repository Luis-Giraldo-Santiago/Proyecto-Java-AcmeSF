
package acme.entities.student5;

public enum Mark {

	A_PLUS("A+"), A("A"), B("B"), C("C"), F("F"), F_MINUS("F-");


	private String valueM;


	Mark(final String mark) {
		this.valueM = mark;
	}

	public String getMark() {
		return this.valueM;
	}

	public static Mark parse(final String mark) {
		return Mark.valueOf(mark);
	}

}
