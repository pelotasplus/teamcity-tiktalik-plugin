package pl.pelotasplus.tiktalik.api.model;

import com.google.gson.annotations.SerializedName;

public class Product {

    @SerializedName("hourly_gross_cost")
    private float hourlyGrossCost;

    @SerializedName("hourly_net_cost")
    private float hourlyNetCost;

    @SerializedName("monthly_gross_cost")
    private float monthlyGrossCost;

    @SerializedName("monthly_net_cost")
    private float monthlyNetCost;

    @SerializedName("name")
    private String name;

    @SerializedName("sort_by")
    private String sortBy;

    @SerializedName("disk_gb")
    private float diskGb;

    @SerializedName("srv_size")
    private String srvSize;

    @SerializedName("cpu_cores")
    private int cpuCores;

    @SerializedName("api_name")
    private String apiName;

    @SerializedName("mem_gb")
    private float memGb;

    @SerializedName("cpu_units")
    private String cpuUnits;

    @SerializedName("product_group")
    private String productGroup;

    @SerializedName("id")
    private int id;

    public String getName() {
        return name;
    }

    public String getApiName() {
        return apiName;
    }

    public int getId() {
        return id;
    }
}