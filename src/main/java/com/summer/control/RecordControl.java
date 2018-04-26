package com.summer.control;

import com.google.gson.reflect.TypeToken;
import com.summer.base.bean.BaseResBean;
import com.summer.base.bean.Tools;
import com.summer.bean.Records;
import com.summer.global.Value;
import com.summer.mybatis.DBTools;
import com.summer.mybatis.entity.Record;
import com.summer.mybatis.mapper.RecordMapper;
import com.summer.util.DateFormatUtil;
import com.summer.util.GsonUtil;
import com.summer.util.NullUtil;
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

    @RequestMapping(value = "/getRecordInfo",method = RequestMethod.GET)
    public void getRecordInfo(HttpServletRequest req, HttpServletResponse res){
        Tools.init(req,res);
        String atype = req.getParameter("atype");
        SqlSession session  =  DBTools.getSession();
        BaseResBean baseResBean = new BaseResBean();
        RecordMapper recordMapper = session.getMapper(RecordMapper.class);
        Records records = new Records();
        records.setAllNum(recordMapper.getRecordCount(atype));
        records.setDoneNum(recordMapper.getUploadNum(atype));
        baseResBean.setData(records);
        Tools.printOut(res,baseResBean);
        session.close();
    }

    @RequestMapping(value = "/updateRecords",method = RequestMethod.POST)
    public void updateRecords(HttpServletRequest req, HttpServletResponse res){
        Tools.init(req,res);
        ArrayList<Record> records = GsonUtil.getInstance().fromJson(req.getParameter("data"), new TypeToken<ArrayList<Record>>(){}.getType());
        ArrayList<Record> list = new ArrayList<>();
        SqlSession session  =  DBTools.getSession();
        for(int i=0;records!=null && i<records.size();i++){
            RecordMapper recordMapper = session.getMapper(RecordMapper.class);
            ArrayList<Record> selects = (ArrayList<Record>) recordMapper.selectRecordWhereLocalPath(records.get(i).getLocpath());
            if(selects!=null&&selects.size()>0){
               if(NullUtil.isStrEmpty(selects.get(0).getNetpath())){
                   list.add(records.get(i));
               }else{
                   File file = new File(selects.get(0).getNetpath());
                   if(!file.exists()){
                       list.add(records.get(i));
                   }
               }
                continue;
            }
            recordMapper.insert(records.get(i));
            list.add(records.get(i));
        }
        session.commit();
        session.close();
        BaseResBean baseResBean = new BaseResBean();
        baseResBean.setData(list);
        Tools.printOut(res,baseResBean);
    }


    @RequestMapping(value = "/isRecordUploaded",method = RequestMethod.POST)
    public void isRecordUploaded(HttpServletRequest req, HttpServletResponse res){
        Tools.init(req,res);
        Record record = GsonUtil.getInstance().fromJson(req.getParameter("data"),Record.class);
        SqlSession session  =  DBTools.getSession();
        RecordMapper recordMapper = session.getMapper(RecordMapper.class);
        ArrayList<Record> records = (ArrayList<Record>) recordMapper.selectRecordWhereLocalPath(record.getLocpath());
        BaseResBean baseResBean = new BaseResBean();
        if(record==null||records.size()==0){
            Record record1 = new Record();
            record1.setIsUploaded(0);
            baseResBean.setData(record1);
        }else{
            File typeFile = new File(Value.getRecordFile(), DateFormatUtil.getdDateStr(DateFormatUtil.YYYYMMDD,new Date(records.get(0).getCtime())));
            String[] strs = record.getLocpath().split("/");
            File file = new File(typeFile,strs[strs.length-1]);
            if(NullUtil.isStrEmpty(records.get(0).getNetpath())||!file.exists()){
                Record record1 = new Record();
                record1.setIsUploaded(0);
                baseResBean.setData(record1);
            }else{
                Record record1 = new Record();
                record1.setIsUploaded(1);
                baseResBean.setData(record1);
            }
        }
        Tools.printOut(res,baseResBean);
        session.commit();
        session.close();
    }


    @RequestMapping(value = "/uploadRecords",method = RequestMethod.POST)
    public void uploadRecords(HttpServletRequest req, HttpServletResponse rep){
        Tools.init(req,rep);
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(Value.getTempFile());
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
                            File typeFile = new File(Value.getRecordFile(), DateFormatUtil.getdDateStr(DateFormatUtil.YYYYMMDD,new Date(records.get(0).getCtime())));
                            if(!typeFile.exists()){
                                typeFile.mkdirs();
                            }
                            String[] strs = local.split("/");

                            File file = new File(typeFile,strs[strs.length-1]);
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
