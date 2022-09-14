/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper;

import et.gov.eep.fcms.entity.FmsTaxRateRegister;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author kaleab
 */
@Stateless
public class FmsTaxRateRegisterFacade extends AbstractFacade<FmsTaxRateRegister> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsTaxRateRegisterFacade() {
        super(FmsTaxRateRegister.class);
    }
    
}
