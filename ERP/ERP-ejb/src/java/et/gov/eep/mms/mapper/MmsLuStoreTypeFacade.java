/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsLuStoreType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author insa
 */
@Stateless
public class MmsLuStoreTypeFacade extends AbstractFacade<MmsLuStoreType> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsLuStoreTypeFacade() {
        super(MmsLuStoreType.class);
    }
    
}
