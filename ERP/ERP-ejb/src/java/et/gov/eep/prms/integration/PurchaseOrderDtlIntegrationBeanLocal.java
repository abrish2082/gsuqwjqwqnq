/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.integration;

import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsPurOrderDetail;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface PurchaseOrderDtlIntegrationBeanLocal {
    public PrmsPurOrderDetail getInfoByMatId(MmsItemRegistration itemRegistrationEntity);
}
