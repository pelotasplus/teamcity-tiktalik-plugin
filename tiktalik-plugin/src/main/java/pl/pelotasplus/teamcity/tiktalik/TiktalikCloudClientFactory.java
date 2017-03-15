package pl.pelotasplus.teamcity.tiktalik;

import jetbrains.buildServer.clouds.CloudClientFactory;
import jetbrains.buildServer.clouds.CloudClientParameters;
import jetbrains.buildServer.clouds.CloudRegistrar;
import jetbrains.buildServer.clouds.CloudState;
import jetbrains.buildServer.serverSide.AgentDescription;
import jetbrains.buildServer.serverSide.PropertiesProcessor;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class TiktalikCloudClientFactory implements CloudClientFactory {
    private final String profileSettingsJsp;

    public TiktalikCloudClientFactory(@NotNull final CloudRegistrar cloudRegistrar,
                                      @NotNull final PluginDescriptor pluginDescriptor) {
        profileSettingsJsp = pluginDescriptor.getPluginResourcesPath(Constants.PROFILE_SETTINGS_PAGE);

        cloudRegistrar.registerCloudFactory(this);
    }

    @NotNull
    @Override
    public String getCloudCode() {
        return Constants.CLOUD_CODE;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return "Tiktalik Cloud";
    }

    @Nullable
    @Override
    public String getEditProfileUrl() {
        return profileSettingsJsp;
    }

    @NotNull
    @Override
    public Map<String, String> getInitialParameterValues() {
        final HashMap<String, String> map = new HashMap<>();
        map.put(Constants.PROFILE_INSTANCES_LIMIT, Constants.DEFAULT_INSTANCES_LIMIT);
        map.put(Constants.PROFILE_INSTANCE_SIZE, Constants.DEFAULT_INSTANCES_SIZE);
        return map;
    }

    @NotNull
    @Override
    public PropertiesProcessor getPropertiesProcessor() {
        return properties -> Collections.emptyList();
    }

    @Override
    public boolean canBeAgentOfType(@NotNull AgentDescription description) {
        final Map<String, String> configParams = description.getConfigurationParameters();
        return configParams.containsKey(Constants.INSTANCE_ID);
    }

    @NotNull
    public TiktalikCloudClient createNewClient(@NotNull final CloudState state,
                                               @NotNull final CloudClientParameters params) {
        return new TiktalikCloudClient(params);
    }
}

