/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.mapper;

import et.gov.eep.ifrs.entity.IfrsFaRevaluationHistory;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author memube
 */
@Stateless
public class IfrsFaRevaluationHistoryFacade extends AbstractFacade<IfrsFaRevaluationHistory> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public IfrsFaRevaluationHistoryFacade() {
        super(IfrsFaRevaluationHistory.class);
    }
    
}
