package com.cfx.furns.web.servlet;

import com.cfx.furns.entity.Furn;
import com.cfx.furns.service.IFurnService;
import com.cfx.furns.service.serviceimpl.FurnServiceImpl;
import com.cfx.furns.utils.DataUtils;
import com.cfx.furns.utils.Logit;
import com.cfx.furns.utils.StringToNumUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 15:37
 *
 * 处理家居的 servlet
 **/
public class FurnServlet extends BaseServlet {
    private static final String TAG = "FurnServlet";
    private static final String DISPATCHER_FURN_MANAGE = "/views/manage/furn_manage.jsp";
    private static final String DISPATCHER_FURN_ADD = "/views/manage/furn_add.jsp";
    private static final String DISPATCHER_SHOW_FURN = "/manage/furn?action=showFurn";
    private IFurnService mFurnService = new FurnServiceImpl();

    /**
     * 显示所有家居
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void showFurn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "showFurn");
        List<Furn> furnList = mFurnService.getAllFurns();
        if (furnList != null) {
            Logit.d(TAG, "furnList.size(): " + furnList.size());
        }
        req.setAttribute("furns", furnList);
        req.getRequestDispatcher(DISPATCHER_FURN_MANAGE).forward(req, resp);
    }

    /**
     * 添加家居
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void addFurn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "addFurn");

        // =============使用原有方式对 Furn 进行封装===========
        // String name = req.getParameter("name");
        // String maker = req.getParameter("maker");
        // String price = req.getParameter("price");
        // String sales = req.getParameter("sales");
        // String stock = req.getParameter("stock");
        // Logit.d(TAG, "name: " + name + " maker: " + maker + " price: " + price + " sales: " + sales + " stock: " + stock);
        //
        // String priceReq = "^[0-9]+(.[0-9]{2})?$";
        // if (!price.matches(priceReq)) {
        //     Logit.d(TAG, "输入价格有误");
        //     req.setAttribute("errorMsg", "输入价格有误");
        //     req.getRequestDispatcher(DISPATCHER_FURN_ADD).forward(req, resp);
        //     return;
        // }
        // String salesReq = "^(0|[1-9]\\d*)$";
        // if (!sales.matches(salesReq)) {
        //     Logit.d(TAG, "输入销量有误");
        //     req.setAttribute("errorMsg", "输入销量有误");
        //     req.getRequestDispatcher(DISPATCHER_FURN_ADD).forward(req, resp);
        //     return;
        // }
        // String stockReq = "^(0|[1-9]\\d*)$";
        // if (!stock.matches(stockReq)) {
        //     Logit.d(TAG, "输入库存有误");
        //     req.setAttribute("errorMsg", "输入库存有误");
        //     req.getRequestDispatcher(DISPATCHER_FURN_ADD).forward(req, resp);
        //     return;
        // }
        //
        // BigDecimal bigDecimalPrice = StringToNumUtils.parseBigDecimal(price);
        // Integer integerSales = StringToNumUtils.parseInteger(sales);
        // Integer integerStock = StringToNumUtils.parseInteger(stock);
        // Furn furn = new Furn(null, name, maker, bigDecimalPrice, integerSales, integerStock, DEFAULT_IMG_URL);
        // =============使用原有方式对 Furn 进行封装===========

        // =============使用 BeanUtils 方式对 Furn 进行封装===========
        Furn furn = new Furn();
        DataUtils.convertParamsToBean(furn, req.getParameterMap());
        // =============使用 BeanUtils 方式对 Furn 进行封装===========

        boolean isAddSucceed = mFurnService.addFurn(furn);
        if (!isAddSucceed) {
            Logit.d(TAG, "添加家居失败");
            req.getRequestDispatcher(DISPATCHER_FURN_ADD).forward(req, resp);
            return;
        }
        Logit.d(TAG, "添加家居成功");

        /**
         * 方式 1
         * 重新获取一遍数据，然后将数据发送到 /views/manage/furn_manage.jsp 进行展示
         */
        // List<Furn> furnList = mFurnService.getAllFurns();
        // if (furnList != null) {
        //     Logit.d(TAG, "furnList.size(): " + furnList.size());
        // }
        // req.setAttribute("furns", furnList);
        // req.getRequestDispatcher(DISPATCHER_FURN_MANAGE).forward(req, resp);

        /**
         * 方式 2
         * 直接请求转发 manage/furn?action=showFurn
         * 会再走到 FurnServlet 的 showFurn 方法进行展示页面
         */
        // req.getRequestDispatcher(DISPATCHER_SHOW_FURN).forward(req, resp);

        /**
         * 方式 1 与 方式 2 的缺点
         * 当用户刷新页面，就会发现又重新添加一次家居
         * 原因：当用户提交完请求，浏览器会记录下最后一次请求的全部信息。当用户刷新页面 (f5)，就会发起浏览器记录的上一次请求
         * 解决方法：使用重定向，因为重定向本质是两次请求，而且最后一次就是请求显示家居，而不是添加家居
         */

        /**
         * 方式 3：重定向
         */
        resp.sendRedirect(getServletContext().getContextPath() + DISPATCHER_SHOW_FURN);
    }

    /**
     * 删除某条家居
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void deleteFurn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "deleteFurn...");
        String furnId = req.getParameter("furnId");
        Integer integerFurnId = StringToNumUtils.parseInteger(furnId);
        Logit.d(TAG, "integerFurnId: " + integerFurnId);
        if (integerFurnId < 0) {
            Logit.d(TAG, "integerFurnId is error");
            req.getRequestDispatcher(DISPATCHER_FURN_MANAGE).forward(req, resp);
            return;
        }
        boolean isDeleteSuccess = mFurnService.deleteFurnById(integerFurnId);
        if (!isDeleteSuccess) {
            // 删除失败
            Logit.d(TAG, "delete error");
            req.getRequestDispatcher(DISPATCHER_FURN_MANAGE).forward(req, resp);
            return;
        }
        Logit.d(TAG, "delete success");
        // 删除成功，重定向到 DISPATCHER_SHOW_FURN 界面
        resp.sendRedirect(getServletContext().getContextPath() + DISPATCHER_SHOW_FURN);
    }
}