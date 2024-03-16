package com.cfx.furns.web.servlet;

import com.cfx.furns.entity.Furn;
import com.cfx.furns.entity.Page;
import com.cfx.furns.service.IFurnService;
import com.cfx.furns.service.serviceimpl.FurnServiceImpl;
import com.cfx.furns.utils.Constants;
import com.cfx.furns.utils.DataUtils;
import com.cfx.furns.utils.Logit;
import com.cfx.furns.utils.StringToNumUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * Project: FurnMall
 * Create By: Chen.F.X
 * DateTime: 2024/1/27 15:37
 *
 * 后台管理处理家居的 servlet
 **/
public class FurnServlet extends BaseServlet {
    private static final String TAG = "FurnServlet";
    private static final String DISPATCHER_FURN_MANAGE = "/views/manage/furn_manage.jsp";
    private static final String DISPATCHER_FURN_ADD = "/views/manage/furn_add.jsp";
    private static final String DISPATCHER_FURN_UPDATE = "/views/manage/furn_update.jsp";
    private static final String DISPATCHER_SHOW_FURN = "/manage/furn?action=showFurn";
    private static final String DISPATCHER_SHOW_PAGE = "/manage/furn?action=page";
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
        // resp.sendRedirect(getServletContext().getContextPath() + DISPATCHER_SHOW_FURN);

        // 重定向到 /manage/furn?action=page 原有页面进行分页显示
        resp.sendRedirect(getServletContext().getContextPath() + DISPATCHER_SHOW_PAGE + "&pageNo=" + req.getParameter("pageNo"));
    }

    /**
     * 添加家居, 包含家居图片
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void addFurnByPic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "addFurnByPic");
        Furn furn = new Furn();
        int pageNo = 1;
        // 1.判断提交的表单是不是文件表单 enctype="multipart/form-data"
        if (ServletFileUpload.isMultipartContent(req)) {
            Logit.d(TAG, "该提交为文件表单");
            // 2.创建一个 DiskFileItemFactory 对象，用于构建一个解析上传数据的工具对象
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            // 3.创建一个解析上传数据的工具对象 servletFileUpload
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            // 解决接收的文件名中文乱码的问题
            servletFileUpload.setHeaderEncoding("utf-8");
            /**
             * 4.表单提交的数据就是 input 的元素数据
             */
            try {
                /**
                 * 5.servletFileUpload 对象可以把表单提交是数据 text / 文件
                 * 将其封装到 FileItem 中
                 */
                List<FileItem> fileItems = servletFileUpload.parseRequest(req);
                /**
                 * name: 文件名
                 * StoreLocation: 文件临时存放目录路径
                 * size：文件大小
                 * isFormField：是否是一个表单字段，true 表示普通文本，false 表示一个文件
                 * FieldName：表单中定义的 input 元素中的 name
                 *
                 * [name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000000.tmp, size=1bytes, isFormField=true, FieldName=id,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000001.tmp, size=33bytes, isFormField=true, FieldName=imgUrl,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000002.tmp, size=1bytes, isFormField=true, FieldName=pageNo,
                 * name=san.jpg, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000003.tmp, size=25125bytes, isFormField=false, FieldName=imgPath,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000004.tmp, size=21bytes, isFormField=true, FieldName=name,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000005.tmp, size=12bytes, isFormField=true, FieldName=maker,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000006.tmp, size=6bytes, isFormField=true, FieldName=price,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000007.tmp, size=3bytes, isFormField=true, FieldName=sales,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000008.tmp, size=1bytes, isFormField=true, FieldName=stock]
                 */
                Logit.d(TAG, "fileItems: " + fileItems);
                // 6.遍历表单项 FileItem 并进行处理
                for (FileItem fileItem : fileItems) {
                    // Logit.d(TAG, "fileItem: " + fileItem);
                    if (!fileItem.isFormField()) {
                        // 表明是一个文件
                        Logit.d(TAG, "是一个文件");
                        // 7.获取上传的文件名
                        String fileName = fileItem.getName();
                        if (!"".equals(fileName)) {
                            Logit.d(TAG, "fileName: " + fileName);
                            /**
                             * 8.把上传到服务器临时目录 D:\E\apache-tomcat-8.0.50\temp\ upload_3851c80b_18d03064b9c__7f45_00000001.tmp
                             * 下的文件保存到指定的目录
                             */
                            // 8.1 指定一个目录，放在网站的工作目录下面
                            String filePath = "/assets/images/product-image/";
                            /**
                             * 8.2 获取完整目录，这个目录是动态的，和 web 项目运行环境绑定的
                             * D:\F\GitProgram\FurnMall\out\artifacts\FurnMall_war_exploded\assets\images\product-image
                             */
                            String realFilePath = req.getServletContext().getRealPath(filePath);
                            realFilePath = realFilePath.replace("\\", "/");
                            Logit.d(TAG, "realFilePath: " + realFilePath);

                            // 8.3 创建这个上传的目录
                            File file = new File(realFilePath);
                            if (!file.exists()) {
                                // 如果文件夹不存在，就创建
                                file.mkdir();
                            }
                            // 8.4 将临时目录下的文件 copy 到创建的这个目录 realFilePath 下面
                            String fileAbsolutePath = realFilePath + fileName;
                            Logit.d(TAG, "fileAbsolutePath: " + fileAbsolutePath);
                            fileItem.write(new File(fileAbsolutePath));
                        } else {
                            fileName = "default.jpg";
                        }
                        furn.setImgUrl("assets/images/product-image/" + fileName);
                    } else {
                        // 表明是普通文本
                        String editBoxValue = fileItem.getString("utf-8");
                        switch (fileItem.getFieldName()) {
                            case "name":
                                furn.setName(editBoxValue);
                                break;
                            case "maker":
                                furn.setMaker(editBoxValue);
                                break;
                            case "price":
                                furn.setPrice(BigDecimal.valueOf(Double.parseDouble(editBoxValue)));
                                break;
                            case "sales":
                                furn.setSales(Integer.valueOf(editBoxValue));
                                break;
                            case "stock":
                                furn.setStock(Integer.valueOf(editBoxValue));
                                break;
                            case "pageNo":
                                pageNo = StringToNumUtils.parseInt(editBoxValue, 0);
                                break;
                            default:
                                break;
                        }
                    }
                }
            } catch (Exception e) {
                Logit.d(TAG, "doPost Exception: " + e);
            }
        } else {
            Logit.d(TAG, "该提交不是文件表单");
        }
        Logit.d(TAG, "furn: " + furn);

        boolean isAddSucceed = mFurnService.addFurn(furn);
        if (!isAddSucceed) {
            Logit.d(TAG, "添加家居失败");
            req.getRequestDispatcher(DISPATCHER_FURN_ADD).forward(req, resp);
            return;
        }
        Logit.d(TAG, "添加家居成功");

        // 重定向到 /manage/furn?action=page 原有页面进行分页显示
        resp.sendRedirect(getServletContext().getContextPath() + DISPATCHER_SHOW_PAGE + "&pageNo=" + pageNo);
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
        // resp.sendRedirect(getServletContext().getContextPath() + DISPATCHER_SHOW_FURN);

        resp.sendRedirect(getServletContext().getContextPath() + DISPATCHER_SHOW_PAGE + "&pageNo=" + req.getParameter("pageNo"));
    }

    /**
     * 根据 id 查询家居信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void queryFurnById(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "queryFurnById...");
        String furnId = req.getParameter("furnId");
        Integer integerFurnId = StringToNumUtils.parseInteger(furnId);
        Logit.d(TAG, "integerFurnId: " + integerFurnId);
        if (integerFurnId < 0) {
            Logit.d(TAG, "integerFurnId is error");
            req.getRequestDispatcher(DISPATCHER_FURN_MANAGE).forward(req, resp);
            return;
        }
        Furn furn = mFurnService.queryFurnById(integerFurnId);
        if (furn == null) {
            // 未查询到家居信息
            Logit.d(TAG, "not find furn info");
            req.getRequestDispatcher(DISPATCHER_FURN_MANAGE).forward(req, resp);
            return;
        }
        Logit.d(TAG, "查询到家居信息");
        req.setAttribute("furn", furn);
        // 转发页面到 DISPATCHER_SHOW_FURN 界面
        // 如果请求带来的参数 pageNo，转发到下一个界面，在下个界面可以通过 param.pageNo 获取到
        req.getRequestDispatcher(DISPATCHER_FURN_UPDATE).forward(req, resp);
    }

    /**
     * 更新家居信息
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void updateFurn(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "updateFurn...");
        // =============使用 BeanUtils 方式对 Furn 进行封装===========
        Furn furn = new Furn();
        DataUtils.convertParamsToBean(furn, req.getParameterMap());
        // =============使用 BeanUtils 方式对 Furn 进行封装===========
        Logit.d(TAG, "furn: " + furn);
        boolean isUpdateSuccess = mFurnService.updateFurn(furn);
        if (!isUpdateSuccess) {
            Logit.d(TAG, "update furn data error");
            req.getRequestDispatcher(DISPATCHER_FURN_UPDATE).forward(req, resp);
            return;
        }
        Logit.d(TAG, "update furn data success");
        // 更新数据成功，重定向到 DISPATCHER_SHOW_FURN 界面
        // resp.sendRedirect(getServletContext().getContextPath() + DISPATCHER_SHOW_FURN);

        // 这个时候需要考虑分页情况，修改了某一页的数据后，更新之后再回到该页，中间过程中需要传输 pageNo，否则不知道之前修改的是哪一页
        page(req, resp);
    }

    /**
     * 更新家居信息，包含文件的上传
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void updateFurnByPic(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "updateFurnByPic...");
        Furn furn = new Furn();
        // 1.判断提交的表单是不是文件表单 enctype="multipart/form-data"
        if (ServletFileUpload.isMultipartContent(req)) {
            Logit.d(TAG, "该提交为文件表单");
            // 2.创建一个 DiskFileItemFactory 对象，用于构建一个解析上传数据的工具对象
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
            // 3.创建一个解析上传数据的工具对象 servletFileUpload
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
            // 解决接收的文件名中文乱码的问题
            servletFileUpload.setHeaderEncoding("utf-8");
            /**
             * 4.表单提交的数据就是 input 的元素数据
             */
            try {
                /**
                 * 5.servletFileUpload 对象可以把表单提交是数据 text / 文件
                 * 将其封装到 FileItem 中
                 */
                List<FileItem> fileItems = servletFileUpload.parseRequest(req);
                /**
                 * name: 文件名
                 * StoreLocation: 文件临时存放目录路径
                 * size：文件大小
                 * isFormField：是否是一个表单字段，true 表示普通文本，false 表示一个文件
                 * FieldName：表单中定义的 input 元素中的 name
                 *
                 * [name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000000.tmp, size=1bytes, isFormField=true, FieldName=id,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000001.tmp, size=33bytes, isFormField=true, FieldName=imgUrl,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000002.tmp, size=1bytes, isFormField=true, FieldName=pageNo,
                 * name=san.jpg, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000003.tmp, size=25125bytes, isFormField=false, FieldName=imgPath,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000004.tmp, size=21bytes, isFormField=true, FieldName=name,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000005.tmp, size=12bytes, isFormField=true, FieldName=maker,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000006.tmp, size=6bytes, isFormField=true, FieldName=price,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000007.tmp, size=3bytes, isFormField=true, FieldName=sales,
                 * name=null, StoreLocation=D:\E\apache-tomcat-8.0.50\temp\ upload_12554c9f_18e37c7be6a__7f16_00000008.tmp, size=1bytes, isFormField=true, FieldName=stock]
                 */
                Logit.d(TAG, "fileItems: " + fileItems);
                // 6.遍历表单项 FileItem 并进行处理
                for (FileItem fileItem : fileItems) {
                    // Logit.d(TAG, "fileItem: " + fileItem);
                    if (!fileItem.isFormField()) {
                        // 表明是一个文件
                        Logit.d(TAG, "是一个文件");
                        // 7.获取上传的文件名
                        String fileName = fileItem.getName();
                        if (!"".equals(fileName)) {
                            Logit.d(TAG, "fileName: " + fileName);
                            /**
                             * 8.把上传到服务器临时目录 D:\E\apache-tomcat-8.0.50\temp\ upload_3851c80b_18d03064b9c__7f45_00000001.tmp
                             * 下的文件保存到指定的目录
                             */
                            // 8.1 指定一个目录，放在网站的工作目录下面
                            String filePath = "/assets/images/product-image/";
                            /**
                             * 8.2 获取完整目录，这个目录是动态的，和 web 项目运行环境绑定的
                             * D:\F\GitProgram\FurnMall\out\artifacts\FurnMall_war_exploded\assets\images\product-image
                             */
                            String realFilePath = req.getServletContext().getRealPath(filePath);
                            realFilePath = realFilePath.replace("\\", "/");
                            Logit.d(TAG, "realFilePath: " + realFilePath);

                            // 8.3 创建这个上传的目录
                            File file = new File(realFilePath);
                            if (!file.exists()) {
                                // 如果文件夹不存在，就创建
                                file.mkdir();
                            }
                            // 8.4 将临时目录下的文件 copy 到创建的这个目录 realFilePath 下面
                            String fileAbsolutePath = realFilePath + fileName;
                            Logit.d(TAG, "fileAbsolutePath: " + fileAbsolutePath);
                            fileItem.write(new File(fileAbsolutePath));
                        } else {
                            fileName = "default.jpg";
                        }

                        furn.setImgUrl("assets/images/product-image/" + fileName);
                    } else {
                        // 表明是普通文本
                        String editBoxValue = fileItem.getString("utf-8");
                        switch (fileItem.getFieldName()) {
                            case "id":
                                furn.setId(Integer.valueOf(editBoxValue));
                                break;
                            case "name":
                                furn.setName(editBoxValue);
                                break;
                            case "maker":
                                furn.setMaker(editBoxValue);
                                break;
                            case "price":
                                furn.setPrice(BigDecimal.valueOf(Double.parseDouble(editBoxValue)));
                                break;
                            case "sales":
                                furn.setSales(Integer.valueOf(editBoxValue));
                                break;
                            case "stock":
                                furn.setStock(Integer.valueOf(editBoxValue));
                                break;
                            default:
                                break;
                        }
                    }
                }
            } catch (Exception e) {
                Logit.d(TAG, "doPost Exception: " + e);
            }
        } else {
            Logit.d(TAG, "该提交不是文件表单");
        }
        Logit.d(TAG, "furn: " + furn);
        boolean isUpdateSuccess = mFurnService.updateFurn(furn);
        if (!isUpdateSuccess) {
            Logit.d(TAG, "update furn data error");
            req.getRequestDispatcher(DISPATCHER_FURN_UPDATE).forward(req, resp);
            return;
        }
        Logit.d(TAG, "update furn data success");

        // 这个时候需要考虑分页情况，修改了某一页的数据后，更新之后再回到该页，中间过程中需要传输 pageNo，否则不知道之前修改的是哪一页
        page(req, resp);
    }

    /**
     * 根据请求的分页展示分页数据
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    private void page(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logit.d(TAG, "page...");
        // 获取当前页数
        String pageNo = req.getParameter("pageNo");
        // 获取每页展示的个数
        String pageSize = req.getParameter("pageSize");
        int pageNoInt = StringToNumUtils.parseInt(pageNo, 1);
        int pageSizeInt = StringToNumUtils.parseInt(pageSize, Constants.PAGE_SIZE);
        Logit.d(TAG, "pageNoInt: " + pageNoInt + " pageSizeInt: " + pageSizeInt);
        Page<Furn> page = mFurnService.page(pageNoInt, pageSizeInt);
        req.setAttribute("page", page);
        req.getRequestDispatcher(DISPATCHER_FURN_MANAGE).forward(req, resp);
    }
}
