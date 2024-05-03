package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.HuodongLiuyanEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 活动留言
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("huodong_liuyan")
public class HuodongLiuyanView extends HuodongLiuyanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表

	//级联表 活动
		/**
		* 活动标题
		*/

		@ColumnInfo(comment="活动标题",type="varchar(200)")
		private String huodongName;
		/**
		* 活动类型
		*/
		@ColumnInfo(comment="活动类型",type="int(11)")
		private Integer huodongTypes;
			/**
			* 活动类型的值
			*/
			@ColumnInfo(comment="活动类型的字典表值",type="varchar(200)")
			private String huodongValue;
		/**
		* 活动图片
		*/

		@ColumnInfo(comment="活动图片",type="varchar(200)")
		private String huodongPhoto;
		/**
		* 赞
		*/

		@ColumnInfo(comment="赞",type="int(11)")
		private Integer zanNumber;
		/**
		* 踩
		*/

		@ColumnInfo(comment="踩",type="int(11)")
		private Integer caiNumber;
		/**
		* 点击量
		*/

		@ColumnInfo(comment="点击量",type="int(11)")
		private Integer huodongClicknum;
		/**
		* 活动详情
		*/

		@ColumnInfo(comment="活动详情",type="longtext")
		private String huodongContent;
	//级联表 学员
		/**
		* 学员名称
		*/

		@ColumnInfo(comment="学员名称",type="varchar(200)")
		private String yonghuName;
		/**
		* 学员手机号
		*/

		@ColumnInfo(comment="学员手机号",type="varchar(200)")
		private String yonghuPhone;
		/**
		* 学员身份证号
		*/

		@ColumnInfo(comment="学员身份证号",type="varchar(200)")
		private String yonghuIdNumber;
		/**
		* 学员头像
		*/

		@ColumnInfo(comment="学员头像",type="varchar(200)")
		private String yonghuPhoto;
		/**
		* 学员邮箱
		*/

		@ColumnInfo(comment="学员邮箱",type="varchar(200)")
		private String yonghuEmail;
		/**
		* 逻辑删除
		*/

		@ColumnInfo(comment="逻辑删除",type="int(11)")
		private Integer yonghuDelete;



	public HuodongLiuyanView() {

	}

	public HuodongLiuyanView(HuodongLiuyanEntity huodongLiuyanEntity) {
		try {
			BeanUtils.copyProperties(this, huodongLiuyanEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}





	//级联表的get和set 活动

		/**
		* 获取： 活动标题
		*/
		public String getHuodongName() {
			return huodongName;
		}
		/**
		* 设置： 活动标题
		*/
		public void setHuodongName(String huodongName) {
			this.huodongName = huodongName;
		}
		/**
		* 获取： 活动类型
		*/
		public Integer getHuodongTypes() {
			return huodongTypes;
		}
		/**
		* 设置： 活动类型
		*/
		public void setHuodongTypes(Integer huodongTypes) {
			this.huodongTypes = huodongTypes;
		}


			/**
			* 获取： 活动类型的值
			*/
			public String getHuodongValue() {
				return huodongValue;
			}
			/**
			* 设置： 活动类型的值
			*/
			public void setHuodongValue(String huodongValue) {
				this.huodongValue = huodongValue;
			}

		/**
		* 获取： 活动图片
		*/
		public String getHuodongPhoto() {
			return huodongPhoto;
		}
		/**
		* 设置： 活动图片
		*/
		public void setHuodongPhoto(String huodongPhoto) {
			this.huodongPhoto = huodongPhoto;
		}

		/**
		* 获取： 赞
		*/
		public Integer getZanNumber() {
			return zanNumber;
		}
		/**
		* 设置： 赞
		*/
		public void setZanNumber(Integer zanNumber) {
			this.zanNumber = zanNumber;
		}

		/**
		* 获取： 踩
		*/
		public Integer getCaiNumber() {
			return caiNumber;
		}
		/**
		* 设置： 踩
		*/
		public void setCaiNumber(Integer caiNumber) {
			this.caiNumber = caiNumber;
		}

		/**
		* 获取： 点击量
		*/
		public Integer getHuodongClicknum() {
			return huodongClicknum;
		}
		/**
		* 设置： 点击量
		*/
		public void setHuodongClicknum(Integer huodongClicknum) {
			this.huodongClicknum = huodongClicknum;
		}

		/**
		* 获取： 活动详情
		*/
		public String getHuodongContent() {
			return huodongContent;
		}
		/**
		* 设置： 活动详情
		*/
		public void setHuodongContent(String huodongContent) {
			this.huodongContent = huodongContent;
		}
	//级联表的get和set 学员

		/**
		* 获取： 学员名称
		*/
		public String getYonghuName() {
			return yonghuName;
		}
		/**
		* 设置： 学员名称
		*/
		public void setYonghuName(String yonghuName) {
			this.yonghuName = yonghuName;
		}

		/**
		* 获取： 学员手机号
		*/
		public String getYonghuPhone() {
			return yonghuPhone;
		}
		/**
		* 设置： 学员手机号
		*/
		public void setYonghuPhone(String yonghuPhone) {
			this.yonghuPhone = yonghuPhone;
		}

		/**
		* 获取： 学员身份证号
		*/
		public String getYonghuIdNumber() {
			return yonghuIdNumber;
		}
		/**
		* 设置： 学员身份证号
		*/
		public void setYonghuIdNumber(String yonghuIdNumber) {
			this.yonghuIdNumber = yonghuIdNumber;
		}

		/**
		* 获取： 学员头像
		*/
		public String getYonghuPhoto() {
			return yonghuPhoto;
		}
		/**
		* 设置： 学员头像
		*/
		public void setYonghuPhoto(String yonghuPhoto) {
			this.yonghuPhoto = yonghuPhoto;
		}

		/**
		* 获取： 学员邮箱
		*/
		public String getYonghuEmail() {
			return yonghuEmail;
		}
		/**
		* 设置： 学员邮箱
		*/
		public void setYonghuEmail(String yonghuEmail) {
			this.yonghuEmail = yonghuEmail;
		}

		/**
		* 获取： 逻辑删除
		*/
		public Integer getYonghuDelete() {
			return yonghuDelete;
		}
		/**
		* 设置： 逻辑删除
		*/
		public void setYonghuDelete(Integer yonghuDelete) {
			this.yonghuDelete = yonghuDelete;
		}


	@Override
	public String toString() {
		return "HuodongLiuyanView{" +
			", huodongName=" + huodongName +
			", huodongPhoto=" + huodongPhoto +
			", zanNumber=" + zanNumber +
			", caiNumber=" + caiNumber +
			", huodongClicknum=" + huodongClicknum +
			", huodongContent=" + huodongContent +
			", yonghuName=" + yonghuName +
			", yonghuPhone=" + yonghuPhone +
			", yonghuIdNumber=" + yonghuIdNumber +
			", yonghuPhoto=" + yonghuPhoto +
			", yonghuEmail=" + yonghuEmail +
			", yonghuDelete=" + yonghuDelete +
			"} " + super.toString();
	}
}
