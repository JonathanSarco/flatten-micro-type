package services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.Json;
import play.libs.F;

import java.util.Iterator;

public class JsonService {

    private final static String VALUE_NAME = "value";

    public JsonNode flattenJson(JsonNode source) {
        ObjectNode flattenedResult = JsonNodeFactory.instance.objectNode();

        Iterator<String> fieldNames = source.fieldNames();

        while (fieldNames.hasNext()) {
            String fieldName = fieldNames.next();
            JsonNode srcChild = source.get(fieldName);
            JsonNode jn = getPlainValues(srcChild, fieldName, JsonNodeFactory.instance.objectNode());
            flattenedResult.set(fieldName, jn.get(fieldName));
        }

        return Json.toJson(flattenedResult);
    }

    private F.Tuple<String, String> getFinalTuple(JsonNode source, String keyName) {
        if (source.get(VALUE_NAME) != null) {
            return new F.Tuple<>(keyName, source.get(VALUE_NAME).textValue());
        }
        return null;
    }

    private JsonNode getPlainValues(JsonNode source, String keyName, ObjectNode target)  {
        F.Tuple<String, String> newCustomTuple = getFinalTuple(source, keyName);
        if (newCustomTuple != null) {
            ObjectNode newNode = JsonNodeFactory.instance.objectNode();
            return newNode.put(newCustomTuple._1, newCustomTuple._2);
        }
        target.putNull(keyName);

        ObjectNode toReplace = JsonNodeFactory.instance.objectNode();
        for (Iterator<String> itChildrenKey = source.fieldNames(); itChildrenKey.hasNext();) {
            String fieldChildName = itChildrenKey.next();
            JsonNode srcChild = source.get(fieldChildName);
            JsonNode jn = getPlainValues(srcChild, fieldChildName, target);
            toReplace.put(fieldChildName, jn.get(fieldChildName).textValue());
        }
        target.replace(keyName, toReplace);
        return target;
    }

}
