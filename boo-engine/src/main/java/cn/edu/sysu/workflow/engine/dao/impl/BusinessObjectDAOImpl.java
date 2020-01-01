package cn.edu.sysu.workflow.engine.dao.impl;

import cn.edu.sysu.workflow.common.entity.BusinessObject;
import cn.edu.sysu.workflow.common.entity.jdbc.BooPreparedStatementSetter;
import cn.edu.sysu.workflow.common.util.JdbcUtil;
import cn.edu.sysu.workflow.engine.dao.BusinessObjectDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

/**
 * @see cn.edu.sysu.workflow.engine.dao.BusinessObjectDAO
 * @author Skye
 * Created on 2019/12/31
 */
@Repository
public class BusinessObjectDAOImpl implements BusinessObjectDAO {

    private static final Logger log = LoggerFactory.getLogger(BusinessObjectDAOImpl.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int update(BusinessObject businessObject) {
        String sql = "UPDATE boo_business_object SET last_update_timestamp = NOW()";
        // businessObjectName
        if (!StringUtils.isEmpty(businessObject.getBusinessObjectName())) {
            sql += ", business_object_name = ?";
        }
        // processId
        if (!StringUtils.isEmpty(businessObject.getProcessId())) {
            sql += ", process_id = ?";
        }
        // status
        if (null != businessObject.getStatus()) {
            sql += ", status = ?";
        }
        // content
        if (!StringUtils.isEmpty(businessObject.getContent())) {
            sql += ", content = ?";
        }
        // serialization
        if (null != businessObject.getSerialization() && 0 != businessObject.getSerialization().length) {
            sql += ", serialization = ?";
        }
        // businessRoles
        if (!StringUtils.isEmpty(businessObject.getBusinessRoles())) {
            sql += ", business_roles = ?";
        }
        // businessObjectId
        sql += " WHERE bin_step_id = ?";
        try {
            return jdbcTemplate.update(sql, new BooPreparedStatementSetter() {
                @Override
                public void customSetValues(PreparedStatement ps) throws SQLException {
                    // businessObjectName
                    if (!StringUtils.isEmpty(businessObject.getBusinessObjectName())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getBusinessObjectName(), Types.VARCHAR);
                    }
                    // processId
                    if (!StringUtils.isEmpty(businessObject.getProcessId())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getProcessId(), Types.VARCHAR);
                    }
                    // status
                    if (null != businessObject.getStatus()) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getStatus(), Types.INTEGER);
                    }
                    // content
                    if (!StringUtils.isEmpty(businessObject.getContent())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getContent(), Types.VARCHAR);
                    }
                    // serialization
                    if (null != businessObject.getSerialization() && 0 != businessObject.getSerialization().length) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getSerialization(), Types.BLOB);
                    }
                    // businessRoles
                    if (!StringUtils.isEmpty(businessObject.getBusinessRoles())) {
                        JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getBusinessRoles(), Types.VARCHAR);
                    }
                    // businessObjectId
                    JdbcUtil.preparedStatementSetter(ps, index(), businessObject.getBusinessObjectId(), Types.VARCHAR);
                }
            });
        } catch (Exception e) {
            log.error("[" + businessObject.getBusinessObjectId() + "]Error on updating business object by businessObjectId.", e);
            return 0;
        }
    }

    @Override
    public List<BusinessObject> findBusinessObjectsByProcessId(String processId) {
        String sql = "SELECT business_object_id, business_object_name, process_id, status, content, serialization, business_roles " +
                "FROM boo_business_object " +
                "WHERE process_id = ?";
        try {
            return jdbcTemplate.query(sql, new Object[]{processId}, new RowMapper<BusinessObject>() {
                @Override
                public BusinessObject mapRow(ResultSet resultSet, int i) throws SQLException {
                    BusinessObject businessObject = new BusinessObject();
                    businessObject.setBusinessObjectId(resultSet.getString("business_object_id"));
                    businessObject.setBusinessObjectName(resultSet.getString("business_object_name"));
                    businessObject.setProcessId(resultSet.getString("process_id"));
                    businessObject.setStatus(resultSet.getInt("status"));
                    businessObject.setContent(resultSet.getString("content"));
                    businessObject.setSerialization(resultSet.getBytes("serialization"));
                    businessObject.setBusinessRoles(resultSet.getString("business_roles"));
                    return businessObject;
                }
            });
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            log.error("[" + processId + "]Error on querying business object list by processId.", e);
            return null;
        }
    }

}
