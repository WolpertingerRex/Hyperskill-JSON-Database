package server;

import com.google.gson.JsonElement;

public class Response {
    private String response;
    private JsonElement value;
    private String reason;

    public Response() {
    }

    public Response (String response, JsonElement value, String reason) {
        this.response = response;
        this.value = value;
        this.reason = reason;
    }

    public static ResponseBuilder newBuilder() {
        return new Response().new ResponseBuilder();
    }

    public class ResponseBuilder {
        private ResponseBuilder() {
        }

        public ResponseBuilder setStatus(Status response) {
            Response.this.response = response.name();
            return this;
        }

        public ResponseBuilder setValue(JsonElement value) {
            Response.this.value = value;
            return this;
        }

        public ResponseBuilder setReason(String reason) {
            Response.this.reason = reason;
            return this;
        }


        public Response build() {
            return Response.this;
        }
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public JsonElement getValue() {
        return value;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }

}

