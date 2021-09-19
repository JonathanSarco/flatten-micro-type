package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import services.JsonService;

import javax.inject.Inject;

import static play.mvc.Results.ok;

public class JsonController {

    private JsonService jsonService;

    @Inject
    public JsonController(JsonService jsonService) {
        this.jsonService = jsonService;
    }

    public Result flattenJson(Http.Request request) {
        JsonNode json = request.body().asJson();
        JsonNode result = jsonService.flattenJson(json);
        return ok(Json.toJson(result));
    }
}
