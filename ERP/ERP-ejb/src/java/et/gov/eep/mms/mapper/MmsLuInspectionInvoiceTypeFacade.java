/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsLuInspectionInvoiceType;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsLuInspectionInvoiceTypeFacade extends AbstractFacade<MmsLuInspectionInvoiceType> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsLuInspectionInvoiceTypeFacade() {
        super(MmsLuInspectionInvoiceType.class);
    }
    
}
