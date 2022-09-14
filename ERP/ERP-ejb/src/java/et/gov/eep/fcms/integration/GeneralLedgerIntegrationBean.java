/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.integration;

import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.mapper.admin.FmsGeneralLedgerFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class GeneralLedgerIntegrationBean implements GeneralLedgerIntegrationBeanLocal {
@EJB
FmsGeneralLedgerFacade generalLedgerFacade;
    @Override
    public List<FmsGeneralLedger> findActiveGeneralLedgersList() {
        return generalLedgerFacade.findAll();
    }

    
}
