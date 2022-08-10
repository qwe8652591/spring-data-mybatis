/*
 * Copyright 2019-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.easybest.mybatis.mapping.precompile;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * .
 *
 * @author Jarvis Song
 */
@SuperBuilder
@Getter
public class SQL implements Segment {

	/**
	 * Empty SQL.
	 */
	public static final SQL EMPTY = SQL.of("");

	/**
	 * QUOTE.
	 */
	public static final SQL QUOTE = SQL.of("'");

	/**
	 * PAH.
	 */
	public static final SQL PAH = SQL.of("'%'");

	/**
	 * EQUALS.
	 */
	public static final SQL EQUALS = SQL.of("=");

	/**
	 * GREATER.
	 */
	public static final SQL GREATER_THAN = SQL.of("&gt;");

	/**
	 * GREATER_THAN_EQUAL.
	 */
	public static final SQL GREATER_THAN_EQUAL = SQL.of("&gt;=");

	/**
	 * LESS_THAN.
	 */
	public static final SQL LESS_THAN = SQL.of("&lt;");

	/**
	 * LESS_THAN_EQUAL.
	 */
	public static final SQL LESS_THAN_EQUAL = SQL.of("&lt;=");

	/**
	 * NOT EQUALS.
	 */
	public static final SQL NOT_EQUALS = SQL.of("&lt;&gt;");

	/**
	 * BETWEEN.
	 */
	public static final SQL BETWEEN = SQL.of("BETWEEN");

	/**
	 * EQUALS.
	 */
	public static final SQL LIKE = SQL.of("LIKE");

	/**
	 * NOT LIKE.
	 */
	public static final SQL NOT_LIKE = SQL.of("NOT LIKE");

	/**
	 * FROM.
	 */
	public static final SQL FROM = SQL.of("FROM");

	/**
	 * Order by.
	 */
	public static final SQL ORDER_BY = SQL.of("ORDER BY");

	/**
	 * Select.
	 */
	public static final SQL SELECT = SQL.of("SELECT");

	/**
	 * DISTINCT.
	 */
	public static final SQL DISTINCT = SQL.of("DISTINCT");

	/**
	 * Update.
	 */
	public static final SQL UPDATE = SQL.of("UPDATE");

	/**
	 * Delete from.
	 */
	public static final SQL DELETE_FROM = SQL.of("DELETE FROM");

	/**
	 * Insert into.
	 */
	public static final SQL INSERT_INTO = SQL.of("INSERT INTO");

	/**
	 * Count asterisk (*).
	 */
	public static final SQL COUNTS = SQL.of("COUNT(*)");

	/**
	 * OR.
	 */
	public static final SQL OR = SQL.of("OR");

	/**
	 * AND.
	 */
	public static final SQL AND = SQL.of("AND");

	/**
	 * TRUE.
	 */
	public static final SQL TRUE = SQL.of("TRUE");

	/**
	 * FALSE.
	 */
	public static final SQL FALSE = SQL.of("FALSE");

	/**
	 * Base entity alias.
	 */
	public static final SQL ROOT_ALIAS = SQL.of("__root");

	private String value;

	public static SQL of(String value) {
		return new SQL(value);
	}

	public SQL(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return this.value;
	}

}
