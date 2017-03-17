package pl.pelotasplus.tiktalik.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Instance {
    @SerializedName("actions_pending_count")
    private int actionsPendingCount;

    @SerializedName("service_name")
    private String serviceName;

    @SerializedName("interfaces")
    private List<Interface> interfaces = new ArrayList<>();

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

    public List<Interface> getInterfaces() {
        return interfaces;
    }

    /*
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
