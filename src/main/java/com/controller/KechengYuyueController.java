
package com.controller;

import java.io.File;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import com.alibaba.fastjson.JSONObject;
import java.util.*;
import org.springframework.beans.BeanUtils;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.ContextLoader;
import javax.servlet.ServletContext;
import com.service.TokenService;
import com.utils.*;
import java.lang.reflect.InvocationTargetException;

import com.service.DictionaryService;
import org.apache.commons.lang3.StringUtils;
import com.annotation.IgnoreAuth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.entity.*;
import com.entity.view.*;
import com.service.*;
import com.utils.PageUtils;
import com.utils.R;
import com.alibaba.fastjson.*;

/**
 * 选课信息
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/kechengYuyue")
public class KechengYuyueController {
    private static final Logger logger = LoggerFactory.getLogger(KechengYuyueController.class);

    private static final String TABLE_NAME = "kechengYuyue";

    @Autowired
    private KechengYuyueService kechengYuyueService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private ChatService chatService;//咨询信息
    @Autowired
    private DictionaryService dictionaryService;//字典
    @Autowired
    private ForumService forumService;//论坛
    @Autowired
    private HuodongService huodongService;//活动
    @Autowired
    private HuodongCollectionService huodongCollectionService;//活动收藏
    @Autowired
    private HuodongLiuyanService huodongLiuyanService;//活动留言
    @Autowired
    private JiaoshiService jiaoshiService;//教师
    @Autowired
    private KechengService kechengService;//课程
    @Autowired
    private KechengCollectionService kechengCollectionService;//课程收藏
    @Autowired
    private KechengLiuyanService kechengLiuyanService;//课程留言
    @Autowired
    private NewsService newsService;//公告通知
    @Autowired
    private SingleSeachService singleSeachService;//单页数据
    @Autowired
    private YonghuService yonghuService;//学员
    @Autowired
    private UsersService usersService;//管理员


    /**
    * 后端列表
    */
    @RequestMapping("/page")
    public R page(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("page方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永不会进入");
        else if("学员".equals(role))
            params.put("yonghuId",request.getSession().getAttribute("userId"));
        else if("教师".equals(role))
            params.put("jiaoshiId",request.getSession().getAttribute("userId"));
        CommonUtil.checkMap(params);
        PageUtils page = kechengYuyueService.queryPage(params);

        //字典表数据转换
        List<KechengYuyueView> list =(List<KechengYuyueView>)page.getList();
        for(KechengYuyueView c:list){
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(c, request);
        }
        return R.ok().put("data", page);
    }

    /**
    * 后端详情
    */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("info方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        KechengYuyueEntity kechengYuyue = kechengYuyueService.selectById(id);
        if(kechengYuyue !=null){
            //entity转view
            KechengYuyueView view = new KechengYuyueView();
            BeanUtils.copyProperties( kechengYuyue , view );//把实体数据重构到view中
            //级联表 课程
            //级联表
            KechengEntity kecheng = kechengService.selectById(kechengYuyue.getKechengId());
            if(kecheng != null){
            BeanUtils.copyProperties( kecheng , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setKechengId(kecheng.getId());
            }
            //级联表 学员
            //级联表
            YonghuEntity yonghu = yonghuService.selectById(kechengYuyue.getYonghuId());
            if(yonghu != null){
            BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createTime", "insertTime", "updateTime", "yonghuId"});//把级联的数据添加到view中,并排除id和创建时间字段,当前表的级联注册表
            view.setYonghuId(yonghu.getId());
            }
            //修改对应字典表字段
            dictionaryService.dictionaryConvert(view, request);
            return R.ok().put("data", view);
        }else {
            return R.error(511,"查不到数据");
        }

    }

    /**
    * 后端保存
    */
    @RequestMapping("/save")
    public R save(@RequestBody KechengYuyueEntity kechengYuyue, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,kechengYuyue:{}",this.getClass().getName(),kechengYuyue.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");
        else if("学员".equals(role))
            kechengYuyue.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

        Wrapper<KechengYuyueEntity> queryWrapper = new EntityWrapper<KechengYuyueEntity>()
            .eq("kecheng_id", kechengYuyue.getKechengId())
            .eq("yonghu_id", kechengYuyue.getYonghuId())
            .in("kecheng_yuyue_yesno_types", new Integer[]{1,2})
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        KechengYuyueEntity kechengYuyueEntity = kechengYuyueService.selectOne(queryWrapper);
        if(kechengYuyueEntity==null){
            kechengYuyue.setInsertTime(new Date());
            kechengYuyue.setKechengYuyueYesnoTypes(1);
            kechengYuyue.setCreateTime(new Date());
            kechengYuyueService.insert(kechengYuyue);
            return R.ok();
        }else {
            if(kechengYuyueEntity.getKechengYuyueYesnoTypes()==1)
                return R.error(511,"有相同的待审核的数据");
            else if(kechengYuyueEntity.getKechengYuyueYesnoTypes()==2)
                return R.error(511,"有相同的审核通过的数据");
            else
                return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody KechengYuyueEntity kechengYuyue, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,kechengYuyue:{}",this.getClass().getName(),kechengYuyue.toString());
        KechengYuyueEntity oldKechengYuyueEntity = kechengYuyueService.selectById(kechengYuyue.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
//        else if("学员".equals(role))
//            kechengYuyue.setYonghuId(Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId"))));

            kechengYuyueService.updateById(kechengYuyue);//根据id更新
            return R.ok();
    }


    /**
    * 审核
    */
    @RequestMapping("/shenhe")
    public R shenhe(@RequestBody KechengYuyueEntity kechengYuyueEntity, HttpServletRequest request){
        logger.debug("shenhe方法:,,Controller:{},,kechengYuyueEntity:{}",this.getClass().getName(),kechengYuyueEntity.toString());

        KechengYuyueEntity oldKechengYuyue = kechengYuyueService.selectById(kechengYuyueEntity.getId());//查询原先数据

//        if(kechengYuyueEntity.getKechengYuyueYesnoTypes() == 2){//通过
//            kechengYuyueEntity.setKechengYuyueTypes();
//        }else if(kechengYuyueEntity.getKechengYuyueYesnoTypes() == 3){//拒绝
//            kechengYuyueEntity.setKechengYuyueTypes();
//        }
        kechengYuyueService.updateById(kechengYuyueEntity);//审核

        return R.ok();
    }

    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<KechengYuyueEntity> oldKechengYuyueList =kechengYuyueService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        kechengYuyueService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }


    /**
     * 批量上传
     */
    @RequestMapping("/batchInsert")
    public R save( String fileName, HttpServletRequest request){
        logger.debug("batchInsert方法:,,Controller:{},,fileName:{}",this.getClass().getName(),fileName);
        Integer yonghuId = Integer.valueOf(String.valueOf(request.getSession().getAttribute("userId")));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //.eq("time", new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
        try {
            List<KechengYuyueEntity> kechengYuyueList = new ArrayList<>();//上传的东西
            Map<String, List<String>> seachFields= new HashMap<>();//要查询的字段
            Date date = new Date();
            int lastIndexOf = fileName.lastIndexOf(".");
            if(lastIndexOf == -1){
                return R.error(511,"该文件没有后缀");
            }else{
                String suffix = fileName.substring(lastIndexOf);
                if(!".xls".equals(suffix)){
                    return R.error(511,"只支持后缀为xls的excel文件");
                }else{
                    URL resource = this.getClass().getClassLoader().getResource("static/upload/" + fileName);//获取文件路径
                    File file = new File(resource.getFile());
                    if(!file.exists()){
                        return R.error(511,"找不到上传文件，请联系管理员");
                    }else{
                        List<List<String>> dataList = PoiUtil.poiImport(file.getPath());//读取xls文件
                        dataList.remove(0);//删除第一行，因为第一行是提示
                        for(List<String> data:dataList){
                            //循环
                            KechengYuyueEntity kechengYuyueEntity = new KechengYuyueEntity();
//                            kechengYuyueEntity.setKechengYuyueUuidNumber(data.get(0));                    //选课编号 要改的
//                            kechengYuyueEntity.setKechengId(Integer.valueOf(data.get(0)));   //课程 要改的
//                            kechengYuyueEntity.setYonghuId(Integer.valueOf(data.get(0)));   //用户 要改的
//                            kechengYuyueEntity.setKechengYuyueText(data.get(0));                    //选课理由 要改的
//                            kechengYuyueEntity.setInsertTime(date);//时间
//                            kechengYuyueEntity.setKechengYuyueYesnoTypes(Integer.valueOf(data.get(0)));   //选课状态 要改的
//                            kechengYuyueEntity.setKechengYuyueYesnoText(data.get(0));                    //审核回复 要改的
//                            kechengYuyueEntity.setCreateTime(date);//时间
                            kechengYuyueList.add(kechengYuyueEntity);


                            //把要查询是否重复的字段放入map中
                                //选课编号
                                if(seachFields.containsKey("kechengYuyueUuidNumber")){
                                    List<String> kechengYuyueUuidNumber = seachFields.get("kechengYuyueUuidNumber");
                                    kechengYuyueUuidNumber.add(data.get(0));//要改的
                                }else{
                                    List<String> kechengYuyueUuidNumber = new ArrayList<>();
                                    kechengYuyueUuidNumber.add(data.get(0));//要改的
                                    seachFields.put("kechengYuyueUuidNumber",kechengYuyueUuidNumber);
                                }
                        }

                        //查询是否重复
                         //选课编号
                        List<KechengYuyueEntity> kechengYuyueEntities_kechengYuyueUuidNumber = kechengYuyueService.selectList(new EntityWrapper<KechengYuyueEntity>().in("kecheng_yuyue_uuid_number", seachFields.get("kechengYuyueUuidNumber")));
                        if(kechengYuyueEntities_kechengYuyueUuidNumber.size() >0 ){
                            ArrayList<String> repeatFields = new ArrayList<>();
                            for(KechengYuyueEntity s:kechengYuyueEntities_kechengYuyueUuidNumber){
                                repeatFields.add(s.getKechengYuyueUuidNumber());
                            }
                            return R.error(511,"数据库的该表中的 [选课编号] 字段已经存在 存在数据为:"+repeatFields.toString());
                        }
                        kechengYuyueService.insertBatch(kechengYuyueList);
                        return R.ok();
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return R.error(511,"批量插入数据异常，请联系管理员");
        }
    }




    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = kechengYuyueService.queryPage(params);

        //字典表数据转换
        List<KechengYuyueView> list =(List<KechengYuyueView>)page.getList();
        for(KechengYuyueView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        KechengYuyueEntity kechengYuyue = kechengYuyueService.selectById(id);
            if(kechengYuyue !=null){


                //entity转view
                KechengYuyueView view = new KechengYuyueView();
                BeanUtils.copyProperties( kechengYuyue , view );//把实体数据重构到view中

                //级联表
                    KechengEntity kecheng = kechengService.selectById(kechengYuyue.getKechengId());
                if(kecheng != null){
                    BeanUtils.copyProperties( kecheng , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setKechengId(kecheng.getId());
                }
                //级联表
                    YonghuEntity yonghu = yonghuService.selectById(kechengYuyue.getYonghuId());
                if(yonghu != null){
                    BeanUtils.copyProperties( yonghu , view ,new String[]{ "id", "createDate"});//把级联的数据添加到view中,并排除id和创建时间字段
                    view.setYonghuId(yonghu.getId());
                }
                //修改对应字典表字段
                dictionaryService.dictionaryConvert(view, request);
                return R.ok().put("data", view);
            }else {
                return R.error(511,"查不到数据");
            }
    }


    /**
    * 前端保存
    */
    @RequestMapping("/add")
    public R add(@RequestBody KechengYuyueEntity kechengYuyue, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,kechengYuyue:{}",this.getClass().getName(),kechengYuyue.toString());
        Wrapper<KechengYuyueEntity> queryWrapper = new EntityWrapper<KechengYuyueEntity>()
            .eq("kecheng_yuyue_uuid_number", kechengYuyue.getKechengYuyueUuidNumber())
            .eq("kecheng_id", kechengYuyue.getKechengId())
            .eq("yonghu_id", kechengYuyue.getYonghuId())
            .eq("kecheng_yuyue_text", kechengYuyue.getKechengYuyueText())
            .in("kecheng_yuyue_yesno_types", new Integer[]{1,2})
            .eq("kecheng_yuyue_yesno_text", kechengYuyue.getKechengYuyueYesnoText())
//            .notIn("kecheng_yuyue_types", new Integer[]{102})
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        KechengYuyueEntity kechengYuyueEntity = kechengYuyueService.selectOne(queryWrapper);
        if(kechengYuyueEntity==null){
            kechengYuyue.setInsertTime(new Date());
            kechengYuyue.setKechengYuyueYesnoTypes(1);
            kechengYuyue.setCreateTime(new Date());
        kechengYuyueService.insert(kechengYuyue);

            return R.ok();
        }else {
            if(kechengYuyueEntity.getKechengYuyueYesnoTypes()==1)
                return R.error(511,"有相同的待审核的数据");
            else if(kechengYuyueEntity.getKechengYuyueYesnoTypes()==2)
                return R.error(511,"有相同的审核通过的数据");
            else
                return R.error(511,"表中有相同数据");
        }
    }

}

