package pl.pelotasplus.teamcity.tiktalik;

import com.intellij.util.containers.ConcurrentHashSet;
import jetbrains.buildServer.clouds.CloudErrorInfo;
import jetbrains.buildServer.clouds.CloudImage;
import jetbrains.buildServer.clouds.CloudInstance;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.AgentDescription;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.pelotasplus.tiktalik.api.TiktalikApi;
import pl.pelotasplus.tiktalik.api.model.Image;

import java.util.Collection;
import java.util.Collections;

public class TiktalikCloudImage implements CloudImage {
    private Image image;
    private CloudErrorInfo cloudErrorInfo = null;

    private ConcurrentHashSet<TiktalikCloudInstance> instances = new ConcurrentHashSet<>();

    public TiktalikCloudImage(Image image) {
        this.image = image;
    }

    @NotNull
    @Override
    public String getId() {
        return image.getUuid();
    }

    @NotNull
    @Override
    public String getName() {
        return image.getName();
    }

    @NotNull
    @Override
    public Collection<? extends CloudInstance> getInstances() {
        return Collections.unmodifiableCollection(instances);
    }

    @Nullable
    @Override
    public CloudInstance findInstanceById(@NotNull String id) {
        Loggers.AGENT.info("TiktalikCloudInstance findInstanceById " + id);

        for (TiktalikCloudInstance instance : instances) {
            if (instance.getInstanceId().equals(id)) {
                return instance;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public Integer getAgentPoolId() {
        return null;
    }

    @Nullable
    @Override
    public CloudErrorInfo getErrorInfo() {
        return cloudErrorInfo;
    }

    public void dispose() {
        for (TiktalikCloudInstance instance : instances) {
            instance.terminate();
        }
        instances.clear();
    }

    public boolean canStartNewInstance() {
        return instances.size() < 1;
    }

    public CloudInstance startNewInstance(TiktalikApi tiktalikApi, String instanceSize, String sshKey) {
        TiktalikCloudInstance instance = new TiktalikCloudInstance(
                tiktalikApi,
                this,
                instanceSize,
                sshKey
        );
        instance.start();
        instances.add(instance);
        return instance;
    }

    public CloudInstance findInstanceByAgent(AgentDescription agent) {
        Loggers.AGENT.info("TiktalikCloudInstance findInstanceByAgent " + agent);

        if (instances.size() > 0) {
            return instances.iterator().next();
        } else {
            return null;
        }
    }
}