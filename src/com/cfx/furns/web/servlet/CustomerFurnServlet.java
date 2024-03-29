package com.cfx.furns.web.servlet;

import com.cfx.furns.entity.Furn;
import com.cfx.furns.entity.Page;
import com.cfx.furns.service.IFurnService;
import com.cfx.furns.service.serviceimpl.FurnServiceImpl;
import com.cfx.furns.utils.Constants;
import com.cfx.furns.utils.Logit;
import com.cfx.furns.utils.StringToNumUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/2/6 0:03
 *
 * 用户界面展示家居的 servlet
 **/
public class CustomerFurnServlet extends BaseServlet {
    private static final String TAG = "CustomerFurnServlet";
    private static final String DISPATCHER_CUSTOMER_INDEX = "/views/customer/index.jsp";
    private static final String ACTION_SHOW_INDEX = "showIndex";
    private static final String ACTION_PAGE_BY_KEYWORD = "pageByKeyword";
    private static final String ACTION = "action";
    private IFurnService mFurnService = new FurnServiceImpl();

    /**
     * 展示首页界面
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void showIndex(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "showIndex...");
        // 获取当前页数
        String pageNo = req.getParameter("pageNo");
        // 获取每页展示的个数
        String pageSize = req.getParameter("pageSize");
        int pageNoInt = StringToNumUtils.parseInt(pageNo, 1);
        int pageSizeInt = StringToNumUtils.parseInt(pageSize, Constants.PAGE_SIZE);
        Logit.d(TAG, "pageNoInt: " + pageNoInt + " pageSizeInt: " + pageSizeInt);
        Page<Furn> page = mFurnService.page(pageNoInt, pageSizeInt);
        Logit.d(TAG, "cfx page: " + page);
        req.setAttribute("page", page);
        req.setAttribute(ACTION, ACTION_SHOW_INDEX);
        req.getRequestDispatcher(DISPATCHER_CUSTOMER_INDEX).forward(req, resp);
    }

    /**
     * 根据搜索的关键词展示分页数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void pageByKeyword(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "pageByKeyword...");
        /**
         * 获取搜索关键词
         * 如果有参数 keyword，但是没有值，接收到的是 ""
         * 如果没有参数 keyword，接收到的是 null
         */
        String keyword = req.getParameter("keyword");
        // 获取当前页数
        String pageNo = req.getParameter("pageNo");
        // 获取每页展示的个数
        String pageSize = req.getParameter("pageSize");
        int pageNoInt = StringToNumUtils.parseInt(pageNo, 1);
        int pageSizeInt = StringToNumUtils.parseInt(pageSize, Constants.PAGE_SIZE);
        Logit.d(TAG, "keyword: " + keyword + " pageNoInt: " + pageNoInt + " pageSizeInt: " + pageSizeInt);
        Page<Furn> page = mFurnService.pageByKeyword(keyword, pageNoInt, pageSizeInt);
        req.setAttribute("page", page);
        req.setAttribute("keyword", keyword);
        req.setAttribute(ACTION, ACTION_PAGE_BY_KEYWORD);
        req.getRequestDispatcher(DISPATCHER_CUSTOMER_INDEX).forward(req, resp);
    }
}
