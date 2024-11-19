package persistence;

import org.json.JSONObject;


// interface for Writable
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}
