
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
 * 活动
 * 后端接口
 * @author
 * @email
*/
@RestController
@Controller
@RequestMapping("/huodong")
public class HuodongController {
    private static final Logger logger = LoggerFactory.getLogger(HuodongController.class);

    private static final String TABLE_NAME = "huodong";

    @Autowired
    private HuodongService huodongService;


    @Autowired
    private TokenService tokenService;

    @Autowired
    private ChatService chatService;//咨询信息
    @Autowired
    private DictionaryService dictionaryService;//字典
    @Autowired
    private ForumService forumService;//论坛
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
    private KechengYuyueService kechengYuyueService;//选课信息
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
        PageUtils page = huodongService.queryPage(params);

        //字典表数据转换
        List<HuodongView> list =(List<HuodongView>)page.getList();
        for(HuodongView c:list){
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
        HuodongEntity huodong = huodongService.selectById(id);
        if(huodong !=null){
            //entity转view
            HuodongView view = new HuodongView();
            BeanUtils.copyProperties( huodong , view );//把实体数据重构到view中
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
    public R save(@RequestBody HuodongEntity huodong, HttpServletRequest request){
        logger.debug("save方法:,,Controller:{},,huodong:{}",this.getClass().getName(),huodong.toString());

        String role = String.valueOf(request.getSession().getAttribute("role"));
        if(false)
            return R.error(511,"永远不会进入");

        Wrapper<HuodongEntity> queryWrapper = new EntityWrapper<HuodongEntity>()
            .eq("huodong_name", huodong.getHuodongName())
            .eq("huodong_types", huodong.getHuodongTypes())
            .eq("zan_number", huodong.getZanNumber())
            .eq("cai_number", huodong.getCaiNumber())
            ;

        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        HuodongEntity huodongEntity = huodongService.selectOne(queryWrapper);
        if(huodongEntity==null){
            huodong.setHuodongClicknum(1);
            huodong.setInsertTime(new Date());
            huodong.setCreateTime(new Date());
            huodongService.insert(huodong);
            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

    /**
    * 后端修改
    */
    @RequestMapping("/update")
    public R update(@RequestBody HuodongEntity huodong, HttpServletRequest request) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        logger.debug("update方法:,,Controller:{},,huodong:{}",this.getClass().getName(),huodong.toString());
        HuodongEntity oldHuodongEntity = huodongService.selectById(huodong.getId());//查询原先数据

        String role = String.valueOf(request.getSession().getAttribute("role"));
//        if(false)
//            return R.error(511,"永远不会进入");
        if("".equals(huodong.getHuodongPhoto()) || "null".equals(huodong.getHuodongPhoto())){
                huodong.setHuodongPhoto(null);
        }

            huodongService.updateById(huodong);//根据id更新
            return R.ok();
    }



    /**
    * 删除
    */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids, HttpServletRequest request){
        logger.debug("delete:,,Controller:{},,ids:{}",this.getClass().getName(),ids.toString());
        List<HuodongEntity> oldHuodongList =huodongService.selectBatchIds(Arrays.asList(ids));//要删除的数据
        huodongService.deleteBatchIds(Arrays.asList(ids));

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
            List<HuodongEntity> huodongList = new ArrayList<>();//上传的东西
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
                            HuodongEntity huodongEntity = new HuodongEntity();
//                            huodongEntity.setHuodongName(data.get(0));                    //活动标题 要改的
//                            huodongEntity.setHuodongTypes(Integer.valueOf(data.get(0)));   //活动类型 要改的
//                            huodongEntity.setHuodongPhoto("");//详情和图片
//                            huodongEntity.setZanNumber(Integer.valueOf(data.get(0)));   //赞 要改的
//                            huodongEntity.setCaiNumber(Integer.valueOf(data.get(0)));   //踩 要改的
//                            huodongEntity.setHuodongClicknum(Integer.valueOf(data.get(0)));   //点击量 要改的
//                            huodongEntity.setInsertTime(date);//时间
//                            huodongEntity.setHuodongContent("");//详情和图片
//                            huodongEntity.setCreateTime(date);//时间
                            huodongList.add(huodongEntity);


                            //把要查询是否重复的字段放入map中
                        }

                        //查询是否重复
                        huodongService.insertBatch(huodongList);
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
    * 个性推荐
    */
    @IgnoreAuth
    @RequestMapping("/gexingtuijian")
    public R gexingtuijian(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("gexingtuijian方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));
        CommonUtil.checkMap(params);
        List<HuodongView> returnHuodongViewList = new ArrayList<>();

        //查看收藏
        Map<String, Object> params1 = new HashMap<>(params);params1.put("sort","id");params1.put("yonghuId",request.getSession().getAttribute("userId"));
        params1.put("shangxiaTypes",1);
        params1.put("huodongYesnoTypes",2);
        PageUtils pageUtils = huodongCollectionService.queryPage(params1);
        List<HuodongCollectionView> collectionViewsList =(List<HuodongCollectionView>)pageUtils.getList();
        Map<Integer,Integer> typeMap=new HashMap<>();//购买的类型list
        for(HuodongCollectionView collectionView:collectionViewsList){
            Integer huodongTypes = collectionView.getHuodongTypes();
            if(typeMap.containsKey(huodongTypes)){
                typeMap.put(huodongTypes,typeMap.get(huodongTypes)+1);
            }else{
                typeMap.put(huodongTypes,1);
            }
        }
        List<Integer> typeList = new ArrayList<>();//排序后的有序的类型 按最多到最少
        typeMap.entrySet().stream().sorted((o1, o2) -> o2.getValue() - o1.getValue()).forEach(e -> typeList.add(e.getKey()));//排序
        Integer limit = Integer.valueOf(String.valueOf(params.get("limit")));
        for(Integer type:typeList){
            Map<String, Object> params2 = new HashMap<>(params);params2.put("huodongTypes",type);
            params2.put("shangxiaTypes",1);
            params2.put("huodongYesnoTypes",2);
            PageUtils pageUtils1 = huodongService.queryPage(params2);
            List<HuodongView> huodongViewList =(List<HuodongView>)pageUtils1.getList();
            returnHuodongViewList.addAll(huodongViewList);
            if(returnHuodongViewList.size()>= limit) break;//返回的推荐数量大于要的数量 跳出循环
        }
        params.put("shangxiaTypes",1);
        params.put("huodongYesnoTypes",2);
        //正常查询出来商品,用于补全推荐缺少的数据
        PageUtils page = huodongService.queryPage(params);
        if(returnHuodongViewList.size()<limit){//返回数量还是小于要求数量
            int toAddNum = limit - returnHuodongViewList.size();//要添加的数量
            List<HuodongView> huodongViewList =(List<HuodongView>)page.getList();
            for(HuodongView huodongView:huodongViewList){
                Boolean addFlag = true;
                for(HuodongView returnHuodongView:returnHuodongViewList){
                    if(returnHuodongView.getId().intValue() ==huodongView.getId().intValue()) addFlag=false;//返回的数据中已存在此商品
                }
                if(addFlag){
                    toAddNum=toAddNum-1;
                    returnHuodongViewList.add(huodongView);
                    if(toAddNum==0) break;//够数量了
                }
            }
        }else {
            returnHuodongViewList = returnHuodongViewList.subList(0, limit);
        }

        for(HuodongView c:returnHuodongViewList)
            dictionaryService.dictionaryConvert(c, request);
        page.setList(returnHuodongViewList);
        return R.ok().put("data", page);
    }

    /**
    * 前端列表
    */
    @IgnoreAuth
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params, HttpServletRequest request){
        logger.debug("list方法:,,Controller:{},,params:{}",this.getClass().getName(),JSONObject.toJSONString(params));

        CommonUtil.checkMap(params);
        PageUtils page = huodongService.queryPage(params);

        //字典表数据转换
        List<HuodongView> list =(List<HuodongView>)page.getList();
        for(HuodongView c:list)
            dictionaryService.dictionaryConvert(c, request); //修改对应字典表字段

        return R.ok().put("data", page);
    }

    /**
    * 前端详情
    */
    @RequestMapping("/detail/{id}")
    public R detail(@PathVariable("id") Long id, HttpServletRequest request){
        logger.debug("detail方法:,,Controller:{},,id:{}",this.getClass().getName(),id);
        HuodongEntity huodong = huodongService.selectById(id);
            if(huodong !=null){

                //点击数量加1
                huodong.setHuodongClicknum(huodong.getHuodongClicknum()+1);
                huodongService.updateById(huodong);

                //entity转view
                HuodongView view = new HuodongView();
                BeanUtils.copyProperties( huodong , view );//把实体数据重构到view中

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
    public R add(@RequestBody HuodongEntity huodong, HttpServletRequest request){
        logger.debug("add方法:,,Controller:{},,huodong:{}",this.getClass().getName(),huodong.toString());
        Wrapper<HuodongEntity> queryWrapper = new EntityWrapper<HuodongEntity>()
            .eq("huodong_name", huodong.getHuodongName())
            .eq("huodong_types", huodong.getHuodongTypes())
            .eq("zan_number", huodong.getZanNumber())
            .eq("cai_number", huodong.getCaiNumber())
            .eq("huodong_clicknum", huodong.getHuodongClicknum())
//            .notIn("huodong_types", new Integer[]{102})
            ;
        logger.info("sql语句:"+queryWrapper.getSqlSegment());
        HuodongEntity huodongEntity = huodongService.selectOne(queryWrapper);
        if(huodongEntity==null){
                huodong.setZanNumber(1);
                huodong.setCaiNumber(1);
            huodong.setHuodongClicknum(1);
            huodong.setInsertTime(new Date());
            huodong.setCreateTime(new Date());
        huodongService.insert(huodong);

            return R.ok();
        }else {
            return R.error(511,"表中有相同数据");
        }
    }

}

