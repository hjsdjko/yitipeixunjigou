package com.entity.view;

import org.apache.tools.ant.util.DateUtils;
import com.annotation.ColumnInfo;
import com.entity.KechengLiuyanEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import com.utils.DateUtil;

/**
* 课程留言
* 后端返回视图实体辅助类
* （通常后端关联的表或者自定义的字段需要返回使用）
*/
@TableName("kecheng_liuyan")
public class KechengLiuyanView extends KechengLiuyanEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	//当前表

	//级联表 课程
		/**
		* 课程标题
		*/

		@ColumnInfo(comment="课程标题",type="varchar(200)")
		private String kechengName;
		/**
		* 展示照片
		*/

		@ColumnInfo(comment="展示照片",type="varchar(200)")
		private String kechengPhoto;
		/**
		* 课程类型
		*/
		@ColumnInfo(comment="课程类型",type="int(11)")
		private Integer kechengTypes;
			/**
			* 课程类型的值
			*/
			@ColumnInfo(comment="课程类型的字典表值",type="varchar(200)")
			private String kechengValue;
		/**
		* 上课时间
		*/

		@ColumnInfo(comment="上课时间",type="varchar(200)")
		private String kechengDa;
		/**
		* 课表信息
		*/

		@ColumnInfo(comment="课表信息",type="varchar(200)")
		private String kechengFile;
		/**
		* 热度
		*/

		@ColumnInfo(comment="热度",type="int(11)")
		private Integer kechengClicknum;
					 
		/**
		* 课程 的 教师
		*/
		@ColumnInfo(comment="教师",type="int(11)")
		private Integer kechengJiaoshiId;
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
		* 课程详情
		*/

		@ColumnInfo(comment="课程详情",type="longtext")
		private String kechengContent;
		/**
		* 逻辑删除
		*/

		@ColumnInfo(comment="逻辑删除",type="int(11)")
		private Integer kechengDelete;
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



	public KechengLiuyanView() {

	}

	public KechengLiuyanView(KechengLiuyanEntity kechengLiuyanEntity) {
		try {
			BeanUtils.copyProperties(this, kechengLiuyanEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}





	//级联表的get和set 课程

		/**
		* 获取： 课程标题
		*/
		public String getKechengName() {
			return kechengName;
		}
		/**
		* 设置： 课程标题
		*/
		public void setKechengName(String kechengName) {
			this.kechengName = kechengName;
		}

		/**
		* 获取： 展示照片
		*/
		public String getKechengPhoto() {
			return kechengPhoto;
		}
		/**
		* 设置： 展示照片
		*/
		public void setKechengPhoto(String kechengPhoto) {
			this.kechengPhoto = kechengPhoto;
		}
		/**
		* 获取： 课程类型
		*/
		public Integer getKechengTypes() {
			return kechengTypes;
		}
		/**
		* 设置： 课程类型
		*/
		public void setKechengTypes(Integer kechengTypes) {
			this.kechengTypes = kechengTypes;
		}


			/**
			* 获取： 课程类型的值
			*/
			public String getKechengValue() {
				return kechengValue;
			}
			/**
			* 设置： 课程类型的值
			*/
			public void setKechengValue(String kechengValue) {
				this.kechengValue = kechengValue;
			}

		/**
		* 获取： 上课时间
		*/
		public String getKechengDa() {
			return kechengDa;
		}
		/**
		* 设置： 上课时间
		*/
		public void setKechengDa(String kechengDa) {
			this.kechengDa = kechengDa;
		}

		/**
		* 获取： 课表信息
		*/
		public String getKechengFile() {
			return kechengFile;
		}
		/**
		* 设置： 课表信息
		*/
		public void setKechengFile(String kechengFile) {
			this.kechengFile = kechengFile;
		}

		/**
		* 获取： 热度
		*/
		public Integer getKechengClicknum() {
			return kechengClicknum;
		}
		/**
		* 设置： 热度
		*/
		public void setKechengClicknum(Integer kechengClicknum) {
			this.kechengClicknum = kechengClicknum;
		}
		/**
		* 获取：课程 的 教师
		*/
		public Integer getKechengJiaoshiId() {
			return kechengJiaoshiId;
		}
		/**
		* 设置：课程 的 教师
		*/
		public void setKechengJiaoshiId(Integer kechengJiaoshiId) {
			this.kechengJiaoshiId = kechengJiaoshiId;
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
		* 获取： 课程详情
		*/
		public String getKechengContent() {
			return kechengContent;
		}
		/**
		* 设置： 课程详情
		*/
		public void setKechengContent(String kechengContent) {
			this.kechengContent = kechengContent;
		}

		/**
		* 获取： 逻辑删除
		*/
		public Integer getKechengDelete() {
			return kechengDelete;
		}
		/**
		* 设置： 逻辑删除
		*/
		public void setKechengDelete(Integer kechengDelete) {
			this.kechengDelete = kechengDelete;
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
		return "KechengLiuyanView{" +
			", kechengName=" + kechengName +
			", kechengPhoto=" + kechengPhoto +
			", kechengDa=" + kechengDa +
			", kechengFile=" + kechengFile +
			", kechengClicknum=" + kechengClicknum +
			", zanNumber=" + zanNumber +
			", caiNumber=" + caiNumber +
			", kechengContent=" + kechengContent +
			", kechengDelete=" + kechengDelete +
			", yonghuName=" + yonghuName +
			", yonghuPhone=" + yonghuPhone +
			", yonghuIdNumber=" + yonghuIdNumber +
			", yonghuPhoto=" + yonghuPhoto +
			", yonghuEmail=" + yonghuEmail +
			", yonghuDelete=" + yonghuDelete +
			"} " + super.toString();
	}
}
