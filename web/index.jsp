<%--
  Project: FurnMall
  Create By: Chen.F.X
  DateTime: 2024/1/16 23:48
  
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<base href="<%=request.getContextPath() + "/"%>">
<%--它的功能就是请求转发 page 属性设置请求转发的路径--%>
<jsp:forward page="customerFurn?action=showIndex&pageNo=1"></jsp:forward>
