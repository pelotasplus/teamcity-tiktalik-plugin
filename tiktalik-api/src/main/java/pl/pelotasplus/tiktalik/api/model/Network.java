package pl.pelotasplus.tiktalik.api.model;

import com.google.gson.annotations.SerializedName;

public class Network {
    @SerializedName("full")
    private boolean full;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("domainname")
    private String domainName;

    @SerializedName("owner")
    private String owner;

    @SerializedName("net")
    private String net;

    @SerializedName("public")
    private boolean _public;

    @SerializedName("name")
    private String name;
}