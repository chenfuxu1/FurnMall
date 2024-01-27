package com.cfx.furns.web.servlet;

import com.cfx.furns.entity.Furn;
import com.cfx.furns.service.IFurnService;
import com.cfx.furns.service.serviceimpl.FurnServiceImpl;
import com.cfx.furns.utils.Logit;

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
}
