/////////
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.mapper;

import Utility.JsfUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Sahele
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    /**
     *
     * @return
     */
    protected abstract EntityManager getEntityManager();

    /**
     *
     * @param entity
     */
    public void create(T entity) {

        getEntityManager().persist(entity);
        clearCach();
          getEntityManager().persist(entity);
    }
//    public void create(T entity) {
//
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        Validator validator = factory.getValidator();
//        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
//        if (constraintViolations.size() > 0) {
//            Iterator<ConstraintViolation<T>> iterator = constraintViolations.iterator();
//            while (iterator.hasNext()) {
//                ConstraintViolation<T> cv = iterator.next();
//                System.err.println(cv.getRootBeanClass().getName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
//
//                JsfUtil.addErrorMessage(cv.getRootBeanClass().getSimpleName() + "." + cv.getPropertyPath() + " " + cv.getMessage());
//            }
//        } else {
//            getEntityManager().persist(entity);
//        }
//    }

    /**
     *
     * @param entity
     */
    public void edit(T entity) {

        getEntityManager().merge(entity);
        clearCach();
    }

    public void clearCach() {
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
    }

    /**
     *
     * @param entity
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
        clearCach();
    }

    /**
     *
     * @param id
     * @return
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);

    }

    /**
     *
     * @return
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        getEntityManager().getEntityManagerFactory().getCache().evictAll();
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     *
     * @param range
     * @return
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     *
     * @return
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

    /**
     *
     */
    public final static String HRMS = "HRMS";

    /**
     *
     */
    public final static String FCMS = "FCMS";

    /**
     *
     */
    public final static String PRMS = "PRMS";

    /**
     *
     */
    public final static String SMS = "SMS";
    ;

    /**
     *
     */
    public final static String DMS = "DMS";

    /**
     *
     */
    public final static String LOOKUP = "LOOKUP";

    /**
     *
     */
    public final static String ERRORLOG = "ERRORLOG";

//    public final static String HRMS = "HRMS";
    /**
     *
     */
    public final static String IBFMS = "IBFMS";

    /**
     *
     */
    public final static String PAPMS = "PAPMS";

    /**
     *
     */
    public final static String SCMS = "SCMS";
//    public final static String SMS = "SMS";

    /**
     *
     */
    public final static String SPRAS = "SPRAS";

    /**
     *
     */
    public final static String LIMS = "LIMS";

    /**
     *
     */
    public final static String IFRS = "IFRS";

    /**
     *
     */
    public final static String PMMS = "PMMS";
//    public final static String DMS = "DMS";
//    public final static String LOOKUP = "LOOKUP";

    /**
     *
     */
    public final static String MMS = "MMS";
    /**
     *
     */
    private Class<T> entityClass;

    /**
     *
     * @param entityClass
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Used for save update operation per entity bases and normally if its a
     * master table and a detail table association it will be the master entity
     * facade that normally does the Persistence and update of the child entites
     *
     * @param entity
     * @return entity
     */
    public T saveOrUpdate(@NotNull T entity) {
        for (Field field : entity.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (annotation instanceof Id) {
                    try {
                        Id id = Id.class.cast(annotation);
                        field.setAccessible(true); // so we can access the private fields for bean type
                        if (!isContainingValue(field.get(entity))) {
                            //TODO  if  any
                            // so we can access the private fields for bean type  
                            create(entity);
                            getEntityManager().getEntityManagerFactory().getCache().evictAll();
                            return entity;

                        } else {
                            edit(entity);
                            getEntityManager().getEntityManagerFactory().getCache().evictAll();
                            return entity;
                        }
                    } catch (IllegalArgumentException | IllegalAccessException ex) {
                        Logger.getLogger(entityClass.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        }
        clearCach();

        return entity;

    }

    /**
     * Method to check the values passed againest null.
     *
     * @param value to be checked.
     * @return true if not null, elase false.
     */
    private boolean isContainingValue(Object value) {
        boolean containsValue = false;
        if (value != null) {
            if (value instanceof String) {
                containsValue = !String.class.cast(value).trim().isEmpty();
            } else if ((value instanceof Integer)) {
                containsValue = !Integer.class.cast(value).equals(0);
            } else {
                containsValue = true;
            }
        }

        return containsValue;
    }

}
