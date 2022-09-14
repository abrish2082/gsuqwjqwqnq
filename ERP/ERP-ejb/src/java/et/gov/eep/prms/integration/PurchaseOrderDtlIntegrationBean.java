/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.integration;

import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsPurOrderDetail;
import et.gov.eep.prms.mapper.PrmsPurOrderDetailFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class PurchaseOrderDtlIntegrationBean implements PurchaseOrderDtlIntegrationBeanLocal {

    @EJB
    PrmsPurOrderDetailFacade poDetailFacade;
    @Override
    public PrmsPurOrderDetail getInfoByMatId(MmsItemRegistration itemRegistrationEntity) {
       return poDetailFacade.findInfoByMaterialId(itemRegistrationEntity);
    }

    
}
