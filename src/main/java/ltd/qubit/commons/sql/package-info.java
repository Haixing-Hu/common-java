////////////////////////////////////////////////////////////////////////////////
//
//    Copyright (c) 2022 - 2024.
//    Haixing Hu, Qubit Co. Ltd.
//
//    All rights reserved.
//
////////////////////////////////////////////////////////////////////////////////

/**
 * Provides classes for SQL database operations and criteria building.
 *
 * <p>This package contains utility classes for working with SQL databases,
 * including JDBC operations, query builders, pagination utilities, and
 * sophisticated criteria builders that support type-safe comparisons
 * and complex query conditions.</p>
 *
 * <p>Key components include:</p>
 * <ul>
 *   <li>{@link ltd.qubit.commons.sql.JdbcTemplate} - A helper class for JDBC operations</li>
 *   <li>{@link ltd.qubit.commons.sql.JdbcUtils} - Utility methods for JDBC operations</li>
 *   <li>{@link ltd.qubit.commons.sql.Criterion} - Interface for query criteria</li>
 *   <li>{@link ltd.qubit.commons.sql.ComposedCriterion} - Complex query criteria with multiple conditions</li>
 *   <li>{@link ltd.qubit.commons.sql.ComposedCriterionBuilder} - A fluent API for building complex criteria</li>
 *   <li>{@link ltd.qubit.commons.sql.Page} - Pagination support for query results</li>
 *   <li>{@link ltd.qubit.commons.sql.PageRequest} - Request parameters for paginated queries</li>
 *   <li>{@link ltd.qubit.commons.sql.Sort} - Sorting support for query results</li>
 *   <li>{@link ltd.qubit.commons.sql.SortRequest} - Request parameters for sorted queries</li>
 * </ul>
 *
 * <p>The criteria builders in this package support both property path strings and
 * method references for type-safe property access, making them suitable for
 * both dynamic and static query building scenarios.</p>
 *
 * @author Haixing Hu
 */
package ltd.qubit.commons.sql;