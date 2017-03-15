package pl.pelotasplus.teamcity.tiktalik;

import jetbrains.buildServer.clouds.web.CloudImageDetailsExtensionBase;
import jetbrains.buildServer.web.openapi.PagePlaces;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;

public class TiktalikCloudImageDetailsExtension extends CloudImageDetailsExtensionBase<TiktalikCloudImage> {
    public TiktalikCloudImageDetailsExtension(@NotNull PagePlaces pagePlaces, @NotNull PluginDescriptor pluginDescriptor) {
        super(TiktalikCloudImage.class, pagePlaces, pluginDescriptor, Constants.IMAGE_DETAILS_PAGE);
    }
}
