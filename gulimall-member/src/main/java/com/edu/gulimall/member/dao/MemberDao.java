package com.edu.gulimall.member.dao;

import com.edu.gulimall.member.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author wis
 * @email wyg@qq.com
 * @date 2020-08-27 21:15:44
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
