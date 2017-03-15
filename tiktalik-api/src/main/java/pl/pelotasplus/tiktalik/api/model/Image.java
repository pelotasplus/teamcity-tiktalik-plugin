package pl.pelotasplus.tiktalik.api.model;

import com.google.gson.annotations.SerializedName;

public class Image {
    @SerializedName("source_vps_size_name")
    private String sourceVpsSizeName;

    @SerializedName("source_vps_size_disk")
    private int sourceVpsSizeDisk;

    @SerializedName("create_time")
    private String createTime;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("owner")
    private String owner;

    @SerializedName("name")
    private String name;

    @SerializedName("type")
    private String type;

    @SerializedName("source_vps_size")
    private int sourceVpsSize;

    @SerializedName("description")
    private String description;

    @SerializedName("is_public")
    private boolean _public;

    public String getName() {
        return name;
    }

    public String getSourceVpsSizeName() {
        return sourceVpsSizeName;
    }

    public int getSourceVpsSizeDisk() {
        return sourceVpsSizeDisk;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUuid() {
        return uuid;
    }

    public String getOwner() {
        return owner;
    }

    public String getType() {
        return type;
    }

    public int getSourceVpsSize() {
        return sourceVpsSize;
    }

    public String getDescription() {
        return description;
    }

    public boolean isPublic() {
        return _public;
    }
}