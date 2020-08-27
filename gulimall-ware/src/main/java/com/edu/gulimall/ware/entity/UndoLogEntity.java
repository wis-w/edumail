package com.edu.gulimall.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-27 21:40:02
 */
@Data
@TableName("undo_log")
public class UndoLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * $column.comments
	 */
	@TableId
	private Long id;
	/**
	 * $column.comments
	 */
	private Long branchId;
	/**
	 * $column.comments
	 */
	private String xid;
	/**
	 * $column.comments
	 */
	private String context;
	/**
	 * $column.comments
	 */
	//TODO 我也不知道这个时怎么回事
	// private Longblob rollbackInfo;
	/**
	 * $column.comments
	 */
	private Integer logStatus;
	/**
	 * $column.comments
	 */
	private Date logCreated;
	/**
	 * $column.comments
	 */
	private Date logModified;
	/**
	 * $column.comments
	 */
	private String ext;

}
