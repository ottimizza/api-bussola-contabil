package br.com.ottimizza.dashboard.constraints;

import org.json.JSONArray;
import org.json.JSONObject;

public class Response {

    public static class Defaults {

        public static JSONObject getErrorResponseWithMessage(String message) {
            JSONObject response = new JSONObject();
            response.put("status", "error");
            response.put("message", message);

            return response;
        }

        public static JSONObject getSuccessResponseWithMessage(String message) {
            JSONObject response = new JSONObject();
            response.put("status", "success");
            response.put("message", message);

            return response;
        }

        public static JSONObject getSuccessResponseWithRecord(JSONObject record) {
            JSONObject response = new JSONObject();
            response.put("status", "success");
            response.put("record", record);

            return response;
        }

        public static JSONObject getSuccessResponseWithRecords(JSONArray records) {
            JSONObject response = new JSONObject();
            response.put("status", "success");
            response.put("records", records);

            return response;
        }

        public static JSONObject getSuccessResponseWithMessageAndRecords(String message, JSONArray records) {
            JSONObject response = new JSONObject();
            response.put("status", "success");
            response.put("records", records);

            return response;
        }

        public static JSONObject getResponse(String status, String message) {
            JSONObject response = new JSONObject();
            response.put("status", status);
            response.put("message", message);

            return response;
        }

        public static JSONObject getResponse(String status, JSONObject record) {
            JSONObject response = new JSONObject();
            response.put("status", status);
            response.put("record", record);

            return response;
        }

        public static JSONObject getResponse(String status, JSONArray records) {
            JSONObject response = new JSONObject();
            response.put("status", status);
            response.put("records", records);

            return response;
        }

        public static JSONObject getResponse(String status, String message, JSONObject record) {
            JSONObject response = new JSONObject();
            response.put("status", status);
            response.put("message", message);
            response.put("record", record);

            return response;
        }

        public static JSONObject getResponse(String status, String message, JSONArray records) {
            JSONObject response = new JSONObject();
            response.put("status", status);
            response.put("message", message);
            response.put("records", records);

            return response;
        }

        public static JSONObject getNoResultMessage() {
            JSONObject response = new JSONObject();
            response.put("status", "error");
            response.put("message", "Could not find any records!");

            return response;
        }

        public static JSONObject getNonUniqueResultMessage(JSONArray records) {
            JSONObject response = new JSONObject();
            response.put("status", "error");
            response.put("message", "More than one result was returned!");
            response.put("records", records);

            return response;
        }

        public static JSONObject getUnexpectedExceptiontMessage() {
            JSONObject response = new JSONObject();
            response.put("status", "error");
            response.put("message", "Oops, something went wrong!");

            return response;
        }
    }

}
