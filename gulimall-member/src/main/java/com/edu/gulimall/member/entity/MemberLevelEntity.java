package com.edu.gulimall.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 会员等级
 * 
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-27 21:15:45
 */
@Data
@TableName("ums_member_level")
public class MemberLevelEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long id;
	/**
	 * $column.comments
	 */
	private String name;
	/**
	 * $column.comments
	 */
	private Integer growthPoint;
	/**
	 * $column.comments
	 */
	private Integer defaultStatus;
	/**
	 * $column.comments
	 */
	private BigDecimal freeFreightPoint;
	/**
	 * $column.comments
	 */
	private Integer commentGrowthPoint;
	/**
	 * $column.comments
	 */
	private Integer priviledgeFreeFreight;
	/**
	 * $column.comments
	 */
	private Integer priviledgeMemberPrice;
	/**
	 * $column.comments
	 */
	private Integer priviledgeBirthday;
	/**
	 * $column.comments
	 */
	private String note;

}
