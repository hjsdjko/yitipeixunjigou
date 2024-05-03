package com.entity;

import com.annotation.ColumnInfo;
import javax.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;
import java.io.Serializable;
import java.util.*;
import org.apache.tools.ant.util.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.utils.DateUtil;


/**
 * 选课信息
 *
 * @author 
 * @email
 */
@TableName("kecheng_yuyue")
public class KechengYuyueEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public KechengYuyueEntity() {

	}

	public KechengYuyueEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ColumnInfo(comment="主键",type="int(11)")
    @TableField(value = "id")

    private Integer id;


    /**
     * 选课编号
     */
    @ColumnInfo(comment="选课编号",type="varchar(200)")
    @TableField(value = "kecheng_yuyue_uuid_number")

    private String kechengYuyueUuidNumber;


    /**
     * 课程
     */
    @ColumnInfo(comment="课程",type="int(11)")
    @TableField(value = "kecheng_id")

    private Integer kechengId;


    /**
     * 用户
     */
    @ColumnInfo(comment="用户",type="int(11)")
    @TableField(value = "yonghu_id")

    private Integer yonghuId;


    /**
     * 选课理由
     */
    @ColumnInfo(comment="选课理由",type="longtext")
    @TableField(value = "kecheng_yuyue_text")

    private String kechengYuyueText;


    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="添加时间",type="timestamp")
    @TableField(value = "insert_time",fill = FieldFill.INSERT)

    private Date insertTime;


    /**
     * 选课状态
     */
    @ColumnInfo(comment="选课状态",type="int(11)")
    @TableField(value = "kecheng_yuyue_yesno_types")

    private Integer kechengYuyueYesnoTypes;


    /**
     * 审核回复
     */
    @ColumnInfo(comment="审核回复",type="longtext")
    @TableField(value = "kecheng_yuyue_yesno_text")

    private String kechengYuyueYesnoText;


    /**
     * 创建时间  listShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @ColumnInfo(comment="创建时间",type="timestamp")
    @TableField(value = "create_time",fill = FieldFill.INSERT)

    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }
    /**
	 * 设置：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：选课编号
	 */
    public String getKechengYuyueUuidNumber() {
        return kechengYuyueUuidNumber;
    }
    /**
	 * 设置：选课编号
	 */

    public void setKechengYuyueUuidNumber(String kechengYuyueUuidNumber) {
        this.kechengYuyueUuidNumber = kechengYuyueUuidNumber;
    }
    /**
	 * 获取：课程
	 */
    public Integer getKechengId() {
        return kechengId;
    }
    /**
	 * 设置：课程
	 */

    public void setKechengId(Integer kechengId) {
        this.kechengId = kechengId;
    }
    /**
	 * 获取：用户
	 */
    public Integer getYonghuId() {
        return yonghuId;
    }
    /**
	 * 设置：用户
	 */

    public void setYonghuId(Integer yonghuId) {
        this.yonghuId = yonghuId;
    }
    /**
	 * 获取：选课理由
	 */
    public String getKechengYuyueText() {
        return kechengYuyueText;
    }
    /**
	 * 设置：选课理由
	 */

    public void setKechengYuyueText(String kechengYuyueText) {
        this.kechengYuyueText = kechengYuyueText;
    }
    /**
	 * 获取：添加时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }
    /**
	 * 设置：添加时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：选课状态
	 */
    public Integer getKechengYuyueYesnoTypes() {
        return kechengYuyueYesnoTypes;
    }
    /**
	 * 设置：选课状态
	 */

    public void setKechengYuyueYesnoTypes(Integer kechengYuyueYesnoTypes) {
        this.kechengYuyueYesnoTypes = kechengYuyueYesnoTypes;
    }
    /**
	 * 获取：审核回复
	 */
    public String getKechengYuyueYesnoText() {
        return kechengYuyueYesnoText;
    }
    /**
	 * 设置：审核回复
	 */

    public void setKechengYuyueYesnoText(String kechengYuyueYesnoText) {
        this.kechengYuyueYesnoText = kechengYuyueYesnoText;
    }
    /**
	 * 获取：创建时间  listShow
	 */
    public Date getCreateTime() {
        return createTime;
    }
    /**
	 * 设置：创建时间  listShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "KechengYuyue{" +
            ", id=" + id +
            ", kechengYuyueUuidNumber=" + kechengYuyueUuidNumber +
            ", kechengId=" + kechengId +
            ", yonghuId=" + yonghuId +
            ", kechengYuyueText=" + kechengYuyueText +
            ", insertTime=" + DateUtil.convertString(insertTime,"yyyy-MM-dd") +
            ", kechengYuyueYesnoTypes=" + kechengYuyueYesnoTypes +
            ", kechengYuyueYesnoText=" + kechengYuyueYesnoText +
            ", createTime=" + DateUtil.convertString(createTime,"yyyy-MM-dd") +
        "}";
    }
}
