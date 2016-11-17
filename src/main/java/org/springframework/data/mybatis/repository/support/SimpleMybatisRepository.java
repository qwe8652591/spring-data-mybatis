/*
 *
 *   Copyright 2016 the original author or authors.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package org.springframework.data.mybatis.repository.support;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.data.domain.*;
import org.springframework.data.mybatis.domains.Auditable;
import org.springframework.data.mybatis.repository.MybatisRepository;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;

/**
 * Default implementation of the {@link org.springframework.data.repository.CrudRepository} interface.
 *
 * @author Jarvis Song
 */
//@Repository
//@Transactional(readOnly = true)
public class SimpleMybatisRepository<T, ID extends Serializable> extends SqlSessionRepositorySupport
        implements MybatisRepository<T, ID> {

    private static final String STATEMENT_INSERT       = "_insert";
    private static final String STATEMENT_UPDATE       = "_update";
    private static final String STATEMENT_GET_BY_ID    = "_getById";
    private static final String STATEMENT_DELETE_BY_ID = "_deleteById";

    private final MybatisEntityInformation<T, ID> entityInformation;
    private       AuditorAware<Long>              auditorAware;

    public SimpleMybatisRepository(
            MybatisEntityInformation<T, ID> entityInformation,
            SqlSessionTemplate sqlSessionTemplate) {
        super(sqlSessionTemplate);
        this.entityInformation = entityInformation;
    }

    @Override
    protected String getNamespace() {
        return entityInformation.getJavaType().getName();
    }


    @Override
    public <S extends T> S save(S entity) {
        Assert.notNull(entity);

        if (entityInformation.isNew(entity)) {
            // insert
            if (entity instanceof Auditable) {
                ((Auditable) entity).setCreatedDate(new Date());
                if (null != auditorAware) {
                    ((Auditable) entity).setCreatedBy(auditorAware.getCurrentAuditor());
                }
            }

            insert(STATEMENT_INSERT, entity);
        } else {
            // update
            if (entity instanceof Auditable) {
                ((Auditable) entity).setLastModifiedDate(new Date());
                if (null != auditorAware) {
                    ((Auditable) entity).setLastModifiedBy(auditorAware.getCurrentAuditor());
                }
            }

            update(STATEMENT_UPDATE, entity);
        }

        return entity;
    }

    @Override
    public T findOne(ID id) {
        Assert.notNull(id);
        return selectOne(STATEMENT_GET_BY_ID, id);
    }

    @Override
    public T findBasicOne(ID id, String... columns) {
        Assert.notNull(id);
        return selectOne("_getBasicById", id);
    }

    @Override
    public boolean exists(ID id) {
        return null != findOne(id);
    }

    @Override
    public long count() {
        return selectOne("_countByPager");
    }

    @Override
    public void delete(ID id) {
        Assert.notNull(id);
        super.delete(STATEMENT_DELETE_BY_ID, id);
    }

    @Override
    public void delete(T entity) {
        Assert.notNull(entity);
        delete(entityInformation.getId(entity));
    }

    @Override
    public void delete(Iterable<? extends T> entities) {
        if (null == entities) {
            return;
        }
        for (T entity : entities) {
            delete(entity);
        }
    }

    @Override
    public void deleteAll() {
        super.delete("_deleteAll");
    }


    @Override
    public List<T> findAll() {
        return findAll((T) null);
    }

    @Override
    public List<T> findAll(Sort sort) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("sorts", sort);
        return selectList("_selectByPager", params);
    }

    @Override
    public List<T> findAll(Iterable<ID> ids) {
        if (null == ids) {
            return Collections.emptyList();
        }

        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("ids", ids);
        return selectList("_selectByIds", params);
    }

    @Override
    public <S extends T> Iterable<S> save(Iterable<S> entities) {
        if (null == entities) return entities;
        for (S entity : entities) {
            save(entity);
        }
        return entities;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends T> S findOne(Example<S> example) {
        return null;
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        return findAll(pageable, null);
    }

    @Override
    public <X extends T> T findOne(X condition, String... columns) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("condition", condition);
        if (null != columns) {
            params.put("_specifiedFields", columns);
        }
        return selectOne("_selectByPager", params);
    }

    @Override
    public <X extends T> List<T> findAll(X condition, String... columns) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("condition", condition);
        if (null != columns) {
            params.put("_specifiedFields", columns);
        }
        return selectList("_selectByPager", params);
    }

    @Override
    public <X extends T> List<T> findAll(Sort sort, X condition, String... columns) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("condition", condition);
        params.put("sorts", sort);
        if (null != columns) {
            params.put("_specifiedFields", columns);
        }
        return selectList("_selectByPager", params);
    }

    @Override
    public <X extends T> Page<T> findAll(Pageable pageable, X condition, String... columns) {
        Map<String, Object> otherParam = new HashMap<String, Object>();
        if (null != columns) {
            otherParam.put("_specifiedFields", columns);
        }
        return findByPager(pageable, "_selectByPager", "_countByPager", condition, otherParam);
    }

    @Override
    public <X extends T> Long countAll(X condition) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("condition", condition);
        return selectOne("_countByPager", params);
    }

    @Override
    public <X extends T> T findBasicOne(X condition, String... columns) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("condition", condition);
        if (null != columns) {
            params.put("_specifiedFields", columns);
        }
        return selectOne("_selectBasicByPager", params);
    }

    @Override
    public <X extends T> List<T> findBasicAll(X condition, String... columns) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("condition", condition);
        if (null != columns) {
            params.put("_specifiedFields", columns);
        }
        return selectList("_selectBasicByPager", params);
    }

    @Override
    public <X extends T> List<T> findBasicAll(Sort sort, X condition, String... columns) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("condition", condition);
        params.put("sorts", sort);
        if (null != columns) {
            params.put("_specifiedFields", columns);
        }
        return selectList("_selectBasicByPager", params);
    }

    @Override
    public <X extends T> Page<T> findBasicAll(Pageable pageable, X condition, String... columns) {
        return findByPager(pageable, "_selectBasicByPager", "_countBasicByPager", condition, columns);
    }

    @Override
    public <X extends T> Long countBasicAll(X condition) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("condition", condition);
        return selectOne("_countBasicByPager", params);
    }

    @Override
    public <X extends T> int deleteByCondition(X condition) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("condition", condition);
        return super.delete("_deleteByCondition", params);
    }
}
