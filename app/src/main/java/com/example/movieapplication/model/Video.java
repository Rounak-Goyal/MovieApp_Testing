package com.example.movieapplication.model;

import com.google.gson.annotations.SerializedName;

public class Video {
        private String id;
        private String key;
        private String name;
        private String site;
        private int size;
        private String type;

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKey() {
            return this.key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSite() {
            return this.site;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public int getSize() {
            return this.size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getType() {
            return this.type;
        }

        public void setType(String type) {
            this.type = type;
        }

}
