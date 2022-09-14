/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.mapper;

import et.gov.eep.ifrs.entity.IfrsOptionValues;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class IfrsOptionValuesFacade extends AbstractFacade<IfrsOptionValues> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IfrsOptionValuesFacade() {
        super(IfrsOptionValues.class);
    }

    public IfrsOptionValues getListOfOptionValue(Integer optionValueId) {
        IfrsOptionValues optionvalue;
        try {
            Query query = em.createNamedQuery("IfrsOptionValues.findById", IfrsOptionValues.class);
            query.setParameter("id", optionValueId);
            optionvalue = (IfrsOptionValues) query.getSingleResult();
            return optionvalue;
        } catch (Exception e) {
            return null;
        }
    }

    public List<IfrsOptionValues> getListOfOptionValueList(Integer fAAId) {
        List<IfrsOptionValues> optionvalueList;
        try {
            Query query = em.createNamedQuery("IfrsOptionValues.findByFAAId", IfrsOptionValues.class);
            query.setParameter("fAAId", fAAId);
            optionvalueList = (List<IfrsOptionValues>) query.getResultList();
            return optionvalueList;
        } catch (Exception e) {
            return null;
        }
    }
}
