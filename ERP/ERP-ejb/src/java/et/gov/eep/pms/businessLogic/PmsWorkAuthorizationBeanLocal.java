/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.businessLogic;

import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Binyam
 */
@Local
public interface PmsWorkAuthorizationBeanLocal {
    public List<PmsWorkAuthorization> findAll() ;
    public List<PmsWorkAuthorization> findNotInLoan() ;
    public PmsWorkAuthorization findJobID(PmsWorkAuthorization pmsWorkAuthorization);    
    public PmsWorkAuthorization findByWorkAutId(PmsWorkAuthorization pmsWorkAuthorization);    
    public List<PmsWorkAuthorization> findJobNoByGL(FmsGeneralLedger fmsGeneralLedger);
}
