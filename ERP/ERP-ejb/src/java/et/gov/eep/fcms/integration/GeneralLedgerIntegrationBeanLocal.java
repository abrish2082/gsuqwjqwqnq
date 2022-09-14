/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.integration;

import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Sadik
 */
@Local
public interface GeneralLedgerIntegrationBeanLocal {
    public List<FmsGeneralLedger> findActiveGeneralLedgersList();
}
