package com.mtc.mapper.app;

import com.mtc.entity.app.SLPwTrace;
import java.util.List;

public interface SLPwTraceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_l_pw_trace
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_l_pw_trace
     *
     * @mbggenerated
     */
    int insert(SLPwTrace record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_l_pw_trace
     *
     * @mbggenerated
     */
    SLPwTrace selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_l_pw_trace
     *
     * @mbggenerated
     */
    List<SLPwTrace> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table s_l_pw_trace
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(SLPwTrace record);
}