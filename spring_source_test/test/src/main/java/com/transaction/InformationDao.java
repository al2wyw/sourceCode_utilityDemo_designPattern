package com.transaction;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.model.Information;

/**
 * Created by apple on 29/04/2015.
 */
public interface InformationDao extends TableDao {
    public Information getInformationById(String id);
    public void insertInformation(Information p);
    public List<Information> selectInformationByLimit(@Param("offset")int offset,@Param("limit")int limit);
}
