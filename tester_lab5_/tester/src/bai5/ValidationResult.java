package bai5;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public final class ValidationResult {

    private final Map<String, String> errors = new LinkedHashMap<String, String>();

    public void addError(String field, String message) {
        if (field == null || field.trim().isEmpty()) {
            throw new IllegalArgumentException("Field must not be blank");
        }
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Message must not be blank");
        }
        if (!errors.containsKey(field)) {
            errors.put(field, message);
        }
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public String getError(String field) {
        return errors.get(field);
    }

    public Map<String, String> getErrors() {
        return Collections.unmodifiableMap(errors);
    }
}
