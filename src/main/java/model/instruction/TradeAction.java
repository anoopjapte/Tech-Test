package model.instruction;

public enum TradeAction {
    BUY("B"),
    SELL("S");

    private String text;

    TradeAction(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }

    public static TradeAction fromString(String text) {

        if (text != null) {
            for (TradeAction tmp : TradeAction.values()) {
                if (text.equalsIgnoreCase(tmp.text)) {
                    return tmp;
                }
            }
        }
		return null;
	}
}