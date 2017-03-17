package pl.pelotasplus.tiktalik.api.model;

import com.google.gson.annotations.SerializedName;

public class Interface {
    @SerializedName("mac")
    private String mac;

    @SerializedName("vps")
    private String vps;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("seq")
    private int seq;

    @SerializedName("ip")
    private String ip;

    @SerializedName("floating")
    private boolean floating;

    @SerializedName("revdns")
    private String revdns;

    @SerializedName("network")
    private Network network;

    public String getIp() {
        return ip;
    }
}