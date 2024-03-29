<%@ page import="com.cfx.furns.utils.Logit" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <base href="<%=request.getContextPath() + "/"%>">
    <title>荒天帝-家居网购</title>
    <!-- 移动端适配 -->
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css">
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function () {
            $("i.icon-close").click(function () {
                /**
                 * 找到当前删除家居的姓名
                 * $(this).parent() 表示当前节点的父节点 a class="delete_class"
                 * $(this).parent().parent() 表示 a class="delete_class" 的父节点 td class="product-remove"
                 * $(this).parent().parent().parent() 表示 td class="product-remove" 的父节点 <tr>
                 * find("td:eq(1)") 表示 <tr> 下面的第二个子节点
                 */
                var furnName = $(this).parent().parent().parent().find("td:eq(1)").text();
                var result = confirm("确定删除家居 【" + furnName + "】 信息吗");
                return result;
            })
        })
    </script>
</head>

<body>

<%
    Object furns = request.getAttribute("furns");
    if (furns != null) {
        Logit.d("TAG", furns.toString());
    }
%>

<!-- Header Area start  -->
<div class="header section">
    <!-- Header Top  End -->
    <!-- Header Bottom  Start -->
    <div class="header-bottom d-none d-lg-block">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.html"><img src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->

                <!-- Header Action Start -->
                <div class="col align-self-center">
                    <div class="header-actions">
                        <div class="header_account_list">
                            <a href="javascript:void(0)" class="header-action-btn search-btn"><i
                                    class="icon-magnifier"></i></a>
                            <div class="dropdown_search">
                                <form class="action-form" action="#">
                                    <input class="form-control" placeholder="Enter your search key" type="text">
                                    <button class="submit" type="submit"><i class="icon-magnifier"></i></button>
                                </form>
                            </div>
                        </div>
                        <!-- Single Wedge Start -->
                        <div class="header-bottom-set dropdown">
                            <a href="#">后台管理</a>
                        </div>
                        <div class="header-bottom-set dropdown">
                            <a href="views/manage/furn_add.jsp?pageNo=${requestScope.page.currentPageNo}">添加家居</a>
                        </div>
                    </div>
                </div>
                <!-- Header Action End -->
            </div>
        </div>
    </div>
    <!-- Header Bottom  End -->
    <!-- Header Bottom  Start 手机端的header -->
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.html"><img width="280px" src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->
            </div>
        </div>
    </div>
    <!-- Main Menu Start -->
    <div style="width: 100%;height: 50px;background-color: black"></div>
    <!-- Main Menu End -->
</div>
<!-- Cart Area Start -->
<div class="cart-main-area pt-100px pb-100px">
    <div class="container">
        <h3 class="cart-page-title">家居后台管理</h3>
        <div class="row">
            <div class="col-lg-12 col-md-12 col-sm-12 col-12">
                <form action="#">
                    <div class="table-content table-responsive cart-table-content">
                        <table>
                            <thead>
                            <tr>
                                <th>图片</th>
                                <th>家居名</th>
                                <th>商家</th>
                                <th>价格</th>
                                <th>销量</th>
                                <th>库存</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${requestScope.page.itemList}" var="furn">
                                <tr>
                                    <td class="product_img">
                                        <a href="#"><img class="img-responsive ml-3" src="${furn.imgUrl}" alt=""
                                                         height="200px"/></a>
                                    </td>
                                    <td class="product_name"><a href="#">${furn.name}</a></td>
                                    <td class="product_maker_name"><a href="#">${furn.maker}</a></td>
                                    <td class="product_price"><span class="amount">${furn.price}</span></td>
                                    <td class="product_sales">${furn.sales}</td>
                                    <td class="product_stock">${furn.stock}</td>
                                    <td class="product-remove">
                                        <a href="manage/furn?action=queryFurnById&furnId=${furn.id}&pageNo=${requestScope.page.currentPageNo}"><i
                                                class="icon-pencil"></i></a>
                                        <a class="delete_class"
                                           href="manage/furn?action=deleteFurn&furnId=${furn.id}&pageNo=${requestScope.page.currentPageNo}"><i
                                                class="icon-close"></i></a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </form>
            </div>
        </div>
        <%--导航条开始位置--%>
        <div class="pro-pagination-style text-center mb-md-30px mb-lm-30px mt-6" data-aos="fade-up">
            <%--1 如果总页数 <=5, 就全部显示--%>
            <%--2 如果总页数 >5, 按照如下规则显示(这个规则是程序员 / 业务来确定)--%>
            <%--2.1 如果当前页是前 3 页, 就显示 1-5--%>
            <%--2.2 如果当前页是后 3 页, 就显示最后 5 页--%>
            <%--2.3 如果当前页是中间页, 就显示当前页前 2 页, 当前页, 当前页后两页--%>
            <c:choose>
                <%--1 如果总页数 <=5, 就全部显示--%>
                <c:when test="${requestScope.page.totalPageCount <= 5}">
                    <c:set var="begin" value="1" />
                    <c:set var="end" value="${requestScope.page.totalPageCount}" />
                </c:when>
                <c:when test="${requestScope.page.totalPageCount > 5}">
                    <c:choose>
                        <%--2.1 如果当前页是前 3 页, 就显示 1-5--%>
                        <c:when test="${requestScope.page.currentPageNo <= 3}">
                            <c:set var="begin" value="1" />
                            <c:set var="end" value="5" />
                        </c:when>
                        <%--2.2 如果当前页是后 3 页, 就显示最后 5 页--%>
                        <c:when test="${requestScope.page.currentPageNo > requestScope.page.totalPageCount - 3}">
                            <c:set var="begin" value="${requestScope.page.totalPageCount - 4}" />
                            <c:set var="end" value="${requestScope.page.totalPageCount}" />
                        </c:when>
                        <%--2.3 如果当前页是中间页, 就显示当前页前 2 页, 当前页, 当前页后两页--%>
                        <c:otherwise>
                            <c:set var="begin" value="${requestScope.page.currentPageNo - 2}" />
                            <c:set var="end" value="${requestScope.page.currentPageNo + 2}" />
                        </c:otherwise>
                    </c:choose>
                </c:when>
            </c:choose>
            <ul>
                <%--如果当前页大于 1，才显示上一页--%>
                <c:if test="${requestScope.page.currentPageNo > 1}">
                    <li><a href="manage/furn?action=page&pageNo=${requestScope.page.currentPageNo - 1}">上页</a></li>
                </c:if>

                <%--requestScope.page.totalPageCount 表示总页数，从 1 到总页数循环展示--%>
                <%--这样展示所有页数，如果页数过多会显示不下，后续优化导航条最多展示个数--%>
                <c:forEach begin="${begin}" end="${end}" var="index">
                    <c:choose>
                        <%--如果是当前选中的页数，需要设置 class="active" 属性--%>
                        <c:when test="${requestScope.page.currentPageNo == index}">
                            <li><a class="active" href="manage/furn?action=page&pageNo=${index}">${index}</a></li>
                        </c:when>
                        <c:otherwise>
                            <li><a href="manage/furn?action=page&pageNo=${index}">${index}</a></li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

                <%--如果当前页小于总页数，才显示下页--%>
                <c:if test="${requestScope.page.currentPageNo < requestScope.page.totalPageCount}">
                    <li><a href="manage/furn?action=page&pageNo=${requestScope.page.currentPageNo + 1}">下页</a></li>
                </c:if>

                <li><a href="#">共 ${requestScope.page.currentPageNo} / ${requestScope.page.totalPageCount} 页</a></li>
            </ul>
        </div>
        <%--导航条结束位置--%>
    </div>
</div>
<!-- Cart Area End -->

<!-- Footer Area Start -->
<div class="footer-area">
    <div class="footer-container">
        <div class="footer-top">
            <div class="container">
                <div class="row">
                    <!-- Start single blog -->
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-sm-6 col-lg-3 mb-md-30px mb-lm-30px" data-aos="fade-up"
                         data-aos-delay="400">
                        <div class="single-wedge">
                            <h4 class="footer-herading">信息</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="about.html">关于我们</a></li>
                                        <li class="li"><a class="single-link" href="#">交货信息</a></li>
                                        <li class="li"><a class="single-link" href="privacy-policy.html">隐私与政策</a></li>
                                        <li class="li"><a class="single-link" href="#">条款和条件</a></li>
                                        <li class="li"><a class="single-link" href="#">制造</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-2 col-sm-6 mb-lm-30px" data-aos="fade-up" data-aos-delay="600">
                        <div class="single-wedge">
                            <h4 class="footer-herading">我的账号</h4>
                            <div class="footer-links">
                                <div class="footer-row">
                                    <ul class="align-items-center">
                                        <li class="li"><a class="single-link" href="my-account.html">我的账号</a>
                                        </li>
                                        <li class="li"><a class="single-link" href="cart.html">我的购物车</a></li>
                                        <li class="li"><a class="single-link" href="login.html">登录</a></li>
                                        <li class="li"><a class="single-link" href="wishlist.html">感兴趣的</a></li>
                                        <li class="li"><a class="single-link" href="checkout.html">结账</a></li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- End single blog -->
                    <!-- Start single blog -->
                    <div class="col-md-6 col-lg-3" data-aos="fade-up" data-aos-delay="800">

                    </div>
                    <!-- End single blog -->
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <div class="container">
                <div class="row flex-sm-row-reverse">
                    <div class="col-md-6 text-right">
                        <div class="payment-link">
                            <%--<img src="#" alt="">--%>
                        </div>
                    </div>
                    <div class="col-md-6 text-left">
                        <p class="copy-text">Copyright &copy; 2021 荒天帝~</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- Footer Area End -->
<script src="assets/js/vendor/vendor.min.js"></script>
<script src="assets/js/plugins/plugins.min.js"></script>
<!-- Main Js -->
<script src="assets/js/main.js"></script>
</body>
</html>