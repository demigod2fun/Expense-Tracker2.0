package model;

/**
 * Enum for standardizing expense categories
 * Demonstrates OOP enum usage and constants
 */
public enum ExpenseCategory {
    FOOD("Food & Dining", "🍔"),
    TRANSPORT("Transportation", "🚗"),
    SHOPPING("Shopping", "🛍️"),
    ENTERTAINMENT("Entertainment", "🎬"),
    UTILITIES("Utilities", "💡"),
    HEALTH("Healthcare", "🏥"),
    EDUCATION("Education", "📚"),
    SUBSCRIPTION("Subscriptions", "📱"),
    OTHER("Others", "📌");
    
    private final String displayName;
    private final String icon;
    
    ExpenseCategory(String displayName, String icon) {
        this.displayName = displayName;
        this.icon = icon;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getIcon() {
        return icon;
    }
    
    public static ExpenseCategory fromString(String value) {
        for (ExpenseCategory category : ExpenseCategory.values()) {
            if (category.displayName.equalsIgnoreCase(value)) {
                return category;
            }
        }
        return OTHER;
    }
}
