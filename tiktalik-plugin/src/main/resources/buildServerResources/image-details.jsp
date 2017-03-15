<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="image" type="pl.pelotasplus.teamcity.tiktalik.TiktalikCloudImage" scope="request"/>
<c:set var="img" value="${image}"/>
Image UUID: <c:out value="${img.id}"/><br/>
Name: <c:out value="${img.name}"/><br/>
