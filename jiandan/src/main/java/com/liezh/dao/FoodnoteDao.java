package com.liezh.dao;

import com.liezh.domain.dto.foodnote.FoodnoteInfoDto;
import com.liezh.domain.entity.Foodnote;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Administrator on 2018/2/15.
 */
@Repository
public interface FoodnoteDao {
    List<FoodnoteInfoDto> queryFoodnote(Foodnote foodnote);

    FoodnoteInfoDto queryFoodnoteById(Long foodnoteId);

    Integer insertFoodnote(Foodnote foodnote);

    Integer updateFoodnote(Foodnote foodnote);

    Integer deleteFoodnote(Long foodnoteId);

}
