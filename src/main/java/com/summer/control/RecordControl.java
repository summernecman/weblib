package com.summer.control;

import com.google.gson.reflect.TypeToken;
import com.summer.base.bean.BaseResBean;
import com.summer.base.bean.Tools;
import com.summer.mybatis.DBTools;
import com.summer.mybatis.entity.Record;
import com.summer.mybatis.mapper.RecordMapper;
import com.summer.util.DateFormatUtil;
import com.summer.util.GsonUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SWSD on 2018-03-26.
 */

@Controller
@RequestMapping("/record")
public class RecordControl {

    @RequestMapping(value = "/getAllRecords",method = RequestMethod.GET)
    public void getAllRecords(HttpServletRequest req, HttpServletResponse res){
        Tools.init(req,res);
        String atype = req.getParameter("atype");
        SqlSession session  =  DBTools.getSession();
        BaseResBean baseResBean = new BaseResBean();
        RecordMapper recordMapper = session.getMapper(RecordMapper.class);
        baseResBean.setData(recordMapper.selectAllByAtype(atype));
        baseResBean.setOther(recordMapper.getUploadNum(atype)+"/"+recordMapper.getRecordCount(atype));
        Tools.printOut(res,baseResBean);
        session.close();
    }

    @RequestMapping(value = "/updateRecords",method = RequestMethod.POST)
    public void updateRecords(HttpServletRequest req, HttpServletResponse res){
        Tools.init(req,res);
        ArrayList<Record> records = GsonUtil.getInstance().fromJson(req.getParameter("data"), new TypeToken<ArrayList<Record>>(){}.getType());

        SqlSession session  =  DBTools.getSession();
        for(int i=0;records!=null && i<records.size();i++){
            RecordMapper recordMapper = session.getMapper(RecordMapper.class);
            if(recordMapper.selectRecordNumWhereLocalPath(records.get(i).getLocpath())!=0){
                continue;
            }
            recordMapper.insert(records.get(i));
        }
        session.commit();
        session.close();
    }

    @RequestMapping(value = "/uploadRecords",method = RequestMethod.POST)
    public void uploadRecords(HttpServletRequest req, HttpServletResponse rep){
        Tools.init(req,rep);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        File parent = new File("E://record");
        if(!parent.exists()){
            parent.mkdirs();
        }
        File temp = new File("E://temp");
        if(!temp.exists()){
            temp.mkdirs();
        }
        factory.setRepository(temp);
        factory.setSizeThreshold(1024*1024);
        ServletFileUpload upload = new ServletFileUpload(factory);
        BaseResBean baseResBean = new BaseResBean();
        ArrayList<String> files = new ArrayList<String>();
        try {
            ArrayList<FileItem> list = (ArrayList<FileItem>) upload.parseRequest(req);
            String local = null;
            for(int i=0;i<list.size();i++){
                switch (list.get(i).getFieldName()){
                    case "file":

                        break;
                    case "locpath":
                        local = list.get(i).getString();
                        break;
                }

            }

            for(int i=0;i<list.size();i++){
                switch (list.get(i).getFieldName()){
                    case "file":
                        if(local==null){
                            break;
                        }
                        SqlSession session = DBTools.getSession();
                        RecordMapper recordMapper = session.getMapper(RecordMapper.class);
                        ArrayList<Record> records = (ArrayList<Record>) recordMapper.selectRecordWhereLocalPath(local);
                        if(records!=null&&records.size()==1){
                            File typeFile = new File(parent, DateFormatUtil.getdDateStr(DateFormatUtil.YYYYMMDD,new Date(records.get(0).getCtime())));
                            if(!typeFile.exists()){
                                typeFile.mkdirs();
                            }
                            File file = new File(typeFile,list.get(i).getName());
                            System.out.println(file.getPath());
                            files.add(file.getPath());
                            if(!file.exists()){
                                list.get(i).write(file);
                                list.get(i).delete();
                                recordMapper.updateNetPath(file.getPath(),local);
                                session.commit();
                            }
                        }
                        session.close();
                        break;
                    case "locpath":

                        break;
                }
            }
            baseResBean.setData(files);

        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Tools.printOut(rep,baseResBean);
    }
}
