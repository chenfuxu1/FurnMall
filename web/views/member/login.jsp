<%@ page import="com.cfx.furns.utils.Logit" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <!--设置 base 标签，后续改为 jsp 再进行优化-->
    <%--<base href="<http://localhost:8080/furnMall>/">--%>
    <%--更加灵活的设置 base 标签--%>
    <base href="<%=request.getContextPath() + "/"%>">
    <meta charset="UTF-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge"/>
    <title>荒天帝-家居网购</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no"/>
    <link rel="stylesheet" href="assets/css/vendor/vendor.min.css"/>
    <link rel="stylesheet" href="assets/css/plugins/plugins.min.css"/>
    <link rel="stylesheet" href="assets/css/style.min.css"/>
    <script type="text/javascript" src="script/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">
        $(function () {
            // 如果是注册失败，返回到注册 tab，这里强行设置点击一下
            if ("register" == "${requestScope.active}") {
                $("#register_tab")[0].click() // 模拟点击
            }

            var $sub_btn = $("#sub-btn");
            $sub_btn.click(function () {
                // 1.验证用户名：必须字母，数字下划线组成，并且长度为 6 到 10 位
                var $username = $("#username");
                var regUserName = /^\w{6,10}$/;
                if (!$username.val().match(regUserName)) {
                    $("span[class='errorMsg']").text("用户名格式不正确！");
                    return false;
                }

                // 2.验证密码：必须由字母，数字下划线组成，并且长度为 6 到 10 位
                var $password = $("#password");
                var regPsd = /^\w{6,10}$/;
                if (!$password.val().match(regPsd)) {
                    $("span[class='errorMsg']").text("密码格式不正确！");
                    return false;
                }

                // 3.两次密码是否相同
                var $repwd = $("#repwd");
                if ($password.val() != $repwd.val()) {
                    $("span.errorMsg").text("两次输入的密码不相同！")
                    return false;
                }

                // 4.邮箱格式验证：常规验证即可
                var $email = $("#email");
                var regEmail = /^[\w-]+@([a-zA-Z]+\.)+[a-zA-Z]+$/;
                if (!$email.val().match(regEmail)) {
                    $("span.errorMsg").text("邮箱格式不正确！");
                    return false;
                }

                // 5.验证验证码不能为空
                var codeText = $("#code").val()
                codeText = $.trim(codeText)
                if (codeText == null || "" == codeText) {
                    $("span.errorMsg").text("验证码为空！");
                    return false;
                }

                // 验证成功
                return true;

            })

            // 找到会员登录按钮
            var $login = $("#login-btn");
            $login.click(function () {
                // 1.验证用户名：必须字母，数字下划线组成，并且长度为 6 到 10 位
                var $loginUserName = $("#login_user_name");
                var regUserName = /^\w{6,10}$/;
                if (!$loginUserName.val().match(regUserName)) {
                    $("span[class='login_error_msg']").text("用户名格式不正确！");
                    return false;
                }

                // 2.验证密码：必须由字母，数字下划线组成，并且长度为 6 到 10 位
                var $login_password = $("#login_password");
                var regPsd = /^\w{6,10}$/;
                if (!$login_password.val().match(regPsd)) {
                    $("span[class='login_error_msg']").text("密码格式不正确！");
                    return false;
                }
                return true;
            })

            // 增加验证码点击事件，点击切换验证码
            $("#codeImg").click(function () {
                /**
                 * 点击图片时，改变图片的 src
                 * 为了防止 src 一致，图片有缓存机制，不会去请求，可以在后边拼接时间
                 */
                this.src = <%=request.getContextPath() + "/"%> +"kaptcha?date=" + new Date();
            })
        })
    </script>
</head>

<body>

<%
    // 获取 LoginServlet 携带的回显数据
    String loginErrorMsg = (String) request.getAttribute("login_error_msg");
    String loginUserName = (String) request.getAttribute("login_user_name");
    Logit.d("TAG", "loginErrorMsg: " + loginErrorMsg + " loginUserName: " + loginUserName);
%>

<!-- Header Area start  -->
<div class="header section">
    <!-- Header Top Message Start -->
    <!-- Header Top  End -->
    <!-- Header Bottom  Start -->
    <div class="header-bottom d-none d-lg-block">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.jsp"><img src="assets/images/logo/logo.png" alt="Site Logo"/></a>
                    </div>
                </div>
                <!-- Header Logo End -->

            </div>
        </div>
    </div>
    <!-- Header Bottom  Start 手机端的header -->
    <div class="header-bottom d-lg-none sticky-nav bg-white">
        <div class="container position-relative">
            <div class="row align-self-center">
                <!-- Header Logo Start -->
                <div class="col-auto align-self-center">
                    <div class="header-logo">
                        <a href="index.html"><img width="280px" src="assets/images/logo/logo.png"
                                                  alt="Site Logo"/></a>
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
<!-- Header Area End  -->
<!-- login area start -->
<div class="login-register-area pt-70px pb-100px">
    <div class="container">
        <div class="row">
            <div class="col-lg-7 col-md-12 ml-auto mr-auto">
                <div class="login-register-wrapper">
                    <div class="login-register-tab-list nav">
                        <a class="active" data-bs-toggle="tab" href="#lg1">
                            <h4>会员登录</h4>
                        </a>
                        <a id="register_tab" data-bs-toggle="tab" href="#lg2">
                            <h4>会员注册</h4>
                        </a>
                    </div>
                    <div class="tab-content">
                        <div id="lg1" class="tab-pane active">
                            <div class="login-form-container">
                                <div class="login-register-form">
                                    <span class="login_error_msg"
                                          style="font-weight: bold; font-size: 20pt; justify-content: center; display: flex; color: red;">${login_error_msg}</span>
                                    <form action="member" method="post">
                                        <%--隐藏域，表明是登录的表单--%>
                                        <input type="hidden" name="action" value="login">
                                        <input type="text" name="login_user_name" id="login_user_name"
                                               placeholder="Username" value="${login_user_name}"/>
                                        <input type="password" name="login_password" id="login_password"
                                               placeholder="Password"/>
                                        <div class="button-box">
                                            <div class="login-toggle-btn">
                                                <input type="checkbox"/>
                                                <a class="flote-none" href="javascript:void(0)">Remember me</a>
                                                <a href="#">Forgot Password?</a>
                                            </div>
                                            <button type="submit" id="login-btn"><span>Login</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                        <div id="lg2" class="tab-pane">
                            <div class="login-form-container">
                                <div class="login-register-form">
                                    <!--设置 div 中的 span 居中显示-->
                                    <!--justify-content: center; display: flex;-->
                                    <span class="register_error_msg"
                                          style="font-weight: bold; font-size: 20pt; justify-content: center; display: flex; color: red;">${requestScope.register_error_msg}</span>
                                    <!--注册表单-->
                                    <form action="member" method="post">
                                        <%--隐藏域，表明是注册的表单--%>
                                        <input type="hidden" name="action" value="register">
                                        <input type="text" id="username" name="username" placeholder="Username" value="${requestScope.register_user_name}"/>
                                        <input type="password" id="password" name="password" placeholder="输入密码"/>
                                        <input type="password" id="repwd" name="repassword" placeholder="确认密码"/>
                                        <input name="email" id="email" placeholder="电子邮件" type="email" value="${requestScope.register_email}"/>
                                        <input type="text" id="code" name="code" style="width: 50%"
                                               placeholder="验证码"/>　　<img id="codeImg" alt="" src="kaptcha">
                                        <div class="button-box">
                                            <button type="submit" id="sub-btn"><span>会员注册</span></button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- login area end -->

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
                                        <li class="li"><a class="single-link" href="login.jsp">登录</a></li>
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
                            <img src="#" alt="">
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