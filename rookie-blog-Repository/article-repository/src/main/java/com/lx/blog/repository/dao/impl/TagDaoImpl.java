package com.lx.blog.repository.dao.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.blog.repository.dao.TagDao;
import com.lx.blog.repository.dao.impl.mapper.TagMapper;
import com.lx.blog.repository.dao.impl.mapper.entity.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author LX
 * @date 2025/12/03
 * @description 标签数据访问实现
 */
@Repository
@RequiredArgsConstructor
public class TagDaoImpl extends ServiceImpl<TagMapper, Tag> implements TagDao {

    /**
     * 根据标签编码查询标签
     *
     * @param code 标签编码
     * @return 标签实体
     */
    @Override
    public Tag getByCode(String code) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Tag>().eq(Tag::getCode, code));
    }
}

