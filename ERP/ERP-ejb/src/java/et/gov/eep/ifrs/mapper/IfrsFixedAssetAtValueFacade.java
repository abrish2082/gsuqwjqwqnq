/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.mapper;

import et.gov.eep.ifrs.entity.IfrsFixedAssetAtValue;
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
public class IfrsFixedAssetAtValueFacade extends AbstractFacade<IfrsFixedAssetAtValue> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IfrsFixedAssetAtValueFacade() {
        super(IfrsFixedAssetAtValue.class);
    }

    public List<Integer> getListOfOptionValueList(String fieldType, Integer attributeId) {
        List<Integer> optionvalueList;
        Query query = em.createNamedQuery("IfrsFixedAssetAtValue.findAll", IfrsOptionValues.class);
        query.setParameter("fieldType", fieldType);
        query.setParameter("attributeId", attributeId);
        optionvalueList = (List<Integer>) query.getResultList();
        System.out.println(" query  " + query);
        return optionvalueList;
    }

}
