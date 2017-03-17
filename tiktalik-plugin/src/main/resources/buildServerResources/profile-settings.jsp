<%@ page import="pl.pelotasplus.teamcity.tiktalik.Constants" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="api_key" value="<%=Constants.PROFILE_API_KEY%>"/>
<c:set var="api_secret" value="<%=Constants.PROFILE_API_SECRET%>"/>
<c:set var="image_uuid" value="<%=Constants.PROFILE_IMAGE_UUID%>"/>
<c:set var="ssh_key_uuid" value="<%=Constants.PROFILE_SSH_KEY_UUID%>"/>
<c:set var="instance_size" value="<%=Constants.PROFILE_INSTANCE_SIZE%>"/>
<c:set var="instances_limit" value="<%=Constants.PROFILE_INSTANCES_LIMIT%>"/>

<tr>
    <th><label for="${api_key}">API Key:</label></th>
    <td><props:textProperty name="${api_key}" className="textProperty longField"/></td>
</tr>

<tr>
    <th><label for="${api_secret}">API Secret:</label></th>
    <td><props:textProperty name="${api_secret}" className="textProperty longField"/></td>
</tr>

<tr>
    <th><label for="${image_uuid}">Image UUID:</label></th>
    <td>
        <props:textProperty name="${image_uuid}" className="textProperty longField"/>
        <span class="smallNote">
            Use image UUID found at
            <a target="_blank"
               href="https://tiktalik.com/en/panel/#templates">https://tiktalik.com/en/panel/#templates</a>
        </span>
    </td>
</tr>


<tr>
    <th><label for="${ssh_key_uuid}">SSH key UUID:</label></th>
    <td>
        <props:textProperty name="${ssh_key_uuid}" className="textProperty longField"/>
        <span class="smallNote">
            Use SSH key UUID found at
            <a target="_blank"
               href="https://tiktalik.com/en/panel/#sshkeys">https://tiktalik.com/en/panel/#sshkeys</a>
        </span>
    </td>
</tr>

<tr>
    <th><label for="${instance_size}">Instance size:</label></th>
    <td>
        <props:selectProperty name="${instance_size}" className="longField">
        <props:option value="1s">
            <c:out value="1 Unit STD - 1 GB RAM - 1 CPU - 20 GB HDD - 10 PLN/mo"/>
        </props:option>
        <props:option value="0.25">
            <c:out value="0.25 Unit PRO - 0.25 GB RAM - 0.25 GHz CPU - 2.5 GB SSD - 23.91 PLN/mo"/>
        </props:option>
        <props:option value="2">
            <c:out value="2 Unit PRO - 2 GB RAM - 2 GHz CPU - 20 GB SSD - ?? PLN/mo"/>
        </props:option>
        </props:selectProperty>
</tr>

<tr>
    <th><label for="${instances_limit}">Instances limit:</label></th>
    <td>
        <props:textProperty name="${instances_limit}" className="textProperty longField"/>
        <span class="smallNote">Maximum number of instances that can be started</span>
    </td>
</tr>