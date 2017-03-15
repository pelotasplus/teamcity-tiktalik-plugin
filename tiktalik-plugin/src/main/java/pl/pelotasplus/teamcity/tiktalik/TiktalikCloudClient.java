package pl.pelotasplus.teamcity.tiktalik;

import jetbrains.buildServer.clouds.*;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.serverSide.AgentDescription;
import jetbrains.buildServer.serverSide.BuildServerAdapter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import pl.pelotasplus.tiktalik.api.TiktalikApi;

import java.util.Collection;
import java.util.Collections;

public class TiktalikCloudClient extends BuildServerAdapter implements CloudClientEx {
    private final TiktalikApi tiktalikApi;

    private final String instanceSize;
    private final String imageUuid;
    private final String sshKey;

    private TiktalikCloudImage cloudImage = null;
    private CloudErrorInfo cloudErrorInfo = null;


    public TiktalikCloudClient(CloudClientParameters params) {
        tiktalikApi = new TiktalikApi
                .Builder(params.getParameter(Constants.PROFILE_API_KEY), params.getParameter(Constants.PROFILE_API_SECRET))
                .withLogging(Loggers.AGENT::info)
                .build();

        instanceSize = params.getParameter(Constants.PROFILE_INSTANCE_SIZE);
        imageUuid = params.getParameter(Constants.PROFILE_IMAGE_UUID);
        sshKey = params.getParameter(Constants.PROFILE_SSH_KEY_UUID);

        tiktalikApi
                .getImage(imageUuid)
                .subscribe(
                        image -> cloudImage = new TiktalikCloudImage(image),
                        throwable -> cloudErrorInfo = new CloudErrorInfo("Cannot find image with UUID " + imageUuid)
                );
    }

    @NotNull
    @Override
    public CloudInstance startNewInstance(@NotNull CloudImage image, @NotNull CloudInstanceUserData tag) throws QuotaException {
        TiktalikCloudImage cloudImage = (TiktalikCloudImage) image;
        return cloudImage.startNewInstance(tiktalikApi, instanceSize, sshKey);
    }

    @Override
    public void restartInstance(@NotNull CloudInstance instance) {
        ((TiktalikCloudInstance) instance).restart();
    }

    @Override
    public void terminateInstance(@NotNull CloudInstance instance) {
        ((TiktalikCloudInstance) instance).terminate();
    }

    @Override
    public void dispose() {
        if (cloudImage != null) {
            cloudImage.dispose();
        }
    }

    @Override
    public boolean isInitialized() {
        return cloudImage != null || cloudErrorInfo != null;
    }

    @Nullable
    @Override
    public CloudImage findImageById(@NotNull String imageId) throws CloudException {
        Loggers.AGENT.info("TiktalikCloudClient findImageById " + imageId);

        if (cloudImage == null) {
            return null;
        } else if (cloudImage.getId().equals(imageId)) {
            return cloudImage;
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public CloudInstance findInstanceByAgent(@NotNull AgentDescription agent) {
        Loggers.AGENT.info("TiktalikCloudClient findInstanceByAgent " + agent);

        if (cloudImage == null) {
            return null;
        } else {
            return cloudImage.findInstanceByAgent(agent);
        }
    }

    @NotNull
    @Override
    public Collection<? extends CloudImage> getImages() throws CloudException {
        if (cloudImage == null) {
            return Collections.emptyList();
        } else {
            return Collections.singleton(cloudImage);
        }
    }

    @Nullable
    @Override
    public CloudErrorInfo getErrorInfo() {
        return cloudErrorInfo;
    }

    @Override
    public boolean canStartNewInstance(@NotNull CloudImage image) {
        if (cloudImage == null) {
            return false;
        } else {
            return cloudImage.canStartNewInstance();
        }
    }

    @Nullable
    @Override
    public String generateAgentName(@NotNull AgentDescription agent) {
        return "foobar";
    }
}