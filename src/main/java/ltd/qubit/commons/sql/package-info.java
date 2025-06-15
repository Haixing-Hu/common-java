////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2025.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////

/**
 * 提供用于 SQL 数据库操作和条件构建的类。
 *
 * <p>此包包含用于处理 SQL 数据库的实用工具类，包括 JDBC 操作、查询构建器、
 * 分页工具以及支持类型安全比较和复杂查询条件的高级条件构建器。</p>
 *
 * <p>主要组件包括：</p>
 * <ul>
 *   <li>{@link ltd.qubit.commons.sql.JdbcTemplate} - JDBC 操作的辅助类</li>
 *   <li>{@link ltd.qubit.commons.sql.JdbcUtils} - JDBC 操作的实用工具方法</li>
 *   <li>{@link ltd.qubit.commons.sql.Criterion} - 查询条件接口</li>
 *   <li>{@link ltd.qubit.commons.sql.ComposedCriterion} - 具有多个条件的复杂查询条件</li>
 *   <li>{@link ltd.qubit.commons.sql.ComposedCriterionBuilder} - 用于构建复杂条件的流畅 API</li>
 *   <li>{@link ltd.qubit.commons.sql.Page} - 查询结果的分页支持</li>
 *   <li>{@link ltd.qubit.commons.sql.PageRequest} - 分页查询的请求参数</li>
 *   <li>{@link ltd.qubit.commons.sql.Sort} - 查询结果的排序支持</li>
 *   <li>{@link ltd.qubit.commons.sql.SortRequest} - 排序查询的请求参数</li>
 * </ul>
 *
 * <p>此包中的条件构建器支持属性路径字符串和方法引用两种方式来进行类型安全的属性访问，
 * 使其既适用于动态查询构建场景，也适用于静态查询构建场景。</p>
 *
 * @author 胡海星
 */
package ltd.qubit.commons.sql;