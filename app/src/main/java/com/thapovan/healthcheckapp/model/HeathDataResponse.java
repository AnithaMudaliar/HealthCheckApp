package com.thapovan.healthcheckapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HeathDataResponse {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("data")
    @Expose
    private Data data;

    public HeathDataResponse(Integer statusCode, Boolean success, Data data) {
        this.statusCode = statusCode;
        this.success = success;
        this.data = data;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {

        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("health")
        @Expose
        private ArrayList<Health> health = null;


        public Data(String message, ArrayList<Health> health) {
            this.message = message;
            this.health = health;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public ArrayList<Health> getHealth() {
            return health;
        }

        public void setHealth(ArrayList<Health> health) {
            this.health = health;
        }
    }

    public static class Health {

        @SerializedName("name")
        @Expose
        private String name;

        @SerializedName("accessible")
        @Expose
        private List<Accessible> accessible = null;

        public Health(String name, List<Accessible> accessible) {
            this.name = name;
            this.accessible = accessible;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Accessible> getAccessible() {
            return accessible;
        }

        public void setAccessible(List<Accessible> accessible) {
            this.accessible = accessible;
        }
    }

    public static class Accessible {
        @SerializedName("message")
        @Expose
        private String message;

        @SerializedName("success")
        @Expose
        private Boolean success;

        @SerializedName("type")
        @Expose
        private String type;

        @SerializedName("name")
        @Expose
        private String name;

        public Accessible(String message, Boolean success, String type, String name) {
            this.message = message;
            this.success = success;
            this.type = type;
            this.name = name;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Boolean getSuccess() {
            return success;
        }

        public void setSuccess(Boolean success) {
            this.success = success;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
