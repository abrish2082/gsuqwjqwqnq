/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.integration;

import et.gov.eep.prms.entity.PrmsPurchaseOrder;
import et.gov.eep.prms.mapper.PrmsPurchaseOrderFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class PurchaseOrderIntegrationBean implements PurchaseOrderIntegrationBeanLocal {

    @EJB
    PrmsPurchaseOrderFacade poFacade;

    @Override
    public List<PrmsPurchaseOrder> findPurchaseOrdersByWorkFlowStatus(int approvedStatus) {
        return poFacade.findPurchaseOrdersListByWfStatus(approvedStatus);
    }

    @Override
    public PrmsPurchaseOrder getbyPoNo(PrmsPurchaseOrder poNo) {
        return poFacade.getPoId(poNo);
    }

}
