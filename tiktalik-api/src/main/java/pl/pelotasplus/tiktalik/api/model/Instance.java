package pl.pelotasplus.tiktalik.api.model;

import com.google.gson.annotations.SerializedName;

public class Instance {
    @SerializedName("actions_pending_count")
    private int actionsPendingCount;

    @SerializedName("service_name")
    private String serviceName;

    // interfaces

    // actions

    @SerializedName("running")
    private boolean running;

    // vpsimage

    @SerializedName("owner")
    private String owner;

    @SerializedName("size")
    private int size;

    @SerializedName("uuid")
    private String uuid;

    @SerializedName("hostname")
    private String hostname;

    @SerializedName("state")
    private int state;

    @SerializedName("gross_cost_per_hour")
    private float grossCostPerHour;

    @SerializedName("default_password")
    private String defaultPassword;

    @SerializedName("vpsimage_uuid")
    private String vpsImageUuid;

    public String getUuid() {
        return uuid;
    }

    public String getHostname() {
        return hostname;
    }

    public int getState() {
        return state;
    }

    /*
    {
    "interfaces": [
      {
        "mac": "e6:59:17:f5:75:0b",
        "vps": "7ced6c63-db80-456c-a088-dd75e91d8688",
        "uuid": "6aabe832-3cf9-4949-ba25-f0cd6a7a4214",
        "seq": 0,
        "ip": "37.233.99.102",
        "floating": false,
        "revdns": null,
        "network": {
          "full": false,
          "uuid": "212c7fd1-6018-41ff-9a01-a37956517237",
          "domainname": "p3.tiktalik.io",
          "owner": "system",
          "net": "37.233.99.0/24",
          "public": true,
          "name": "pub3"
        }
      }
    ],
    "actions": [
      {
        "code": 3,
        "add_source": "pelotasplus",
        "end_time": null,
        "uuid": "81d3e520-985e-4f14-aff7-42ab81cce64b",
        "progress": 0,
        "end_code": null,
        "start_time": "2017-03-10 19:53:50 +0100 (UTC)",
        "description": "Create_Install: worker-1",
        "end_desc": null,
        "add_time": "2017-03-10 19:53:50 +0100 (UTC)"
      }
    ],
    "vpsimage": {
      "source_vps_size_name": "1 Std Unit",
      "create_time": "2017-03-04 23:06:58 +0100 (UTC)",
      "uuid": "881a36bc-036b-446e-b679-28d9388c5c71",
      "source_vps_size_disk": 0,
      "owner": "pelotasplus",
      "is_public": false,
      "description": null,
      "type": "backup",
      "source_vps_size": 1001,
      "name": "android teamcity worker (20.0 GB)"
    },
     */

    @Override
    public String toString() {
        return "Instance{" +
                "actionsPendingCount=" + actionsPendingCount +
                ", serviceName='" + serviceName + '\'' +
                ", running=" + running +
                ", owner='" + owner + '\'' +
                ", size=" + size +
                ", uuid='" + uuid + '\'' +
                ", hostname='" + hostname + '\'' +
                ", state=" + state +
                ", grossCostPerHour=" + grossCostPerHour +
                ", defaultPassword='" + defaultPassword + '\'' +
                ", vpsImageUuid='" + vpsImageUuid + '\'' +
                '}';
    }
}
