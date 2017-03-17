package pl.pelotasplus.teamcity.tiktalik;

import io.reactivex.schedulers.Schedulers;
import jetbrains.buildServer.clouds.CloudErrorInfo;
import jetbrains.buildServer.clouds.CloudImage;
import jetbrains.buildServer.clouds.CloudInstance;
import jetbrains.buildServer.clouds.InstanceStatus;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.AgentDescription;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.pelotasplus.tiktalik.api.TiktalikApi;
import pl.pelotasplus.tiktalik.api.model.Instance;

import java.util.Date;
import java.util.Map;

public class TiktalikCloudInstance implements CloudInstance {
    private static final Logger LOG = Logger.getLogger(TiktalikCloudInstance.class);

    private final TiktalikApi tiktalikApi;
    private final TiktalikCloudImage image;
    private final String instanceSize;
    private final String sshKey;
    private final Date startTime;

    private Instance instance;
    private volatile CloudErrorInfo cloudErrorInfo;
    private volatile InstanceStatus status;

    public TiktalikCloudInstance(TiktalikApi tiktalikApi, TiktalikCloudImage image, String instanceSize, String sshKey) {
        this.tiktalikApi = tiktalikApi;
        this.image = image;
        this.instanceSize = instanceSize;
        this.sshKey = sshKey;
        this.startTime = new Date();
        this.status = InstanceStatus.SCHEDULED_TO_START;
    }

    @NotNull
    @Override
    public String getInstanceId() {
        return String.format("%s-%d", Constants.CLOUD_CODE, startTime.getTime());
    }

    @NotNull
    @Override
    public String getName() {
        if (instance == null) {
            return String.format("ID: %s IP: not-started-yet", getInstanceId());
        } else {
            return String.format("ID: %s IP: %s", getInstanceId(), getNetworkIdentity());
        }
    }

    @NotNull
    @Override
    public String getImageId() {
        return image.getId();
    }

    @NotNull
    @Override
    public CloudImage getImage() {
        return image;
    }

    @NotNull
    @Override
    public Date getStartedTime() {
        return startTime;
    }

    @Nullable
    @Override
    public String getNetworkIdentity() {
        if (instance == null) {
            return null;
        } else if (instance.getInterfaces().size() > 0) {
            return instance.getInterfaces().get(0).getIp();
        } else {
            return null;
        }
    }

    @NotNull
    @Override
    public InstanceStatus getStatus() {
        return status;
    }

    @Nullable
    @Override
    public CloudErrorInfo getErrorInfo() {
        return cloudErrorInfo;
    }

    @Override
    public boolean containsAgent(@NotNull AgentDescription agent) {
        final Map<String, String> configParams = agent.getConfigurationParameters();
        for (String key : configParams.keySet()) {
            Loggers.AGENT.info("key " + key + " value " + configParams.get(key));
        }
        return true;
    }

    public void terminate() {
        Loggers.AGENT.info("TiktalikCloudInstance terminate " + instance);

        tiktalikApi
                .destroyInstance(instance.getUuid())
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    status = InstanceStatus.STOPPING;
                })
                .subscribe(
                        newInstance -> {
                            instance = null;
                            cloudErrorInfo = null;
                            status = InstanceStatus.STOPPED;
                        },
                        throwable -> {
                            cloudErrorInfo = new CloudErrorInfo(
                                    "Error while terminating instance " + throwable.getMessage()
                            );
                            status = InstanceStatus.ERROR;
                        }
                );
    }

    public void restart() {
        Loggers.AGENT.info("TiktalikCloudInstance restart " + image);
    }

    public void start() {
        Loggers.AGENT.info("TiktalikCloudInstance start " + image + " " + Thread.currentThread());

        tiktalikApi
                .createInstance(
                        "test7",
                        image.getId(),
                        instanceSize,
                        "auto",
                        sshKey,
                        "20"
                )
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    status = InstanceStatus.STARTING;
                })
                .subscribe(
                        newInstance -> {
                            instance = newInstance;

                            Loggers.AGENT.info("TiktalikCloudInstance start got " + instance.toString());

                            cloudErrorInfo = null;
                            status = parseStatus(instance.getState());
                        },
                        throwable -> {
                            cloudErrorInfo = new CloudErrorInfo(
                                    "Error while starting instance",
                                    "Error while starting instance",
                                    throwable
                            );
                            status = InstanceStatus.ERROR;
                        }
                );
    }

    private InstanceStatus parseStatus(int tiktalikStatus) {
        Loggers.AGENT.info("TiktalikCloudInstance parse status " + tiktalikStatus);

        switch (tiktalikStatus) {
            case TiktalikApi.State.INSTALLING:
                return InstanceStatus.STARTING;
            case TiktalikApi.State.RUNNING:
                return InstanceStatus.RUNNING;
            case TiktalikApi.State.STOPPED:
                return InstanceStatus.STOPPED;
            default:
                return InstanceStatus.UNKNOWN;
        }
    }
}