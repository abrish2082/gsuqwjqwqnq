/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import javax.ejb.Local;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.pms.entity.PmsWorkAuthorization;

/**
 *
 * @author XX
 */
@Local
public interface FmsSystemJobJunctionBeanLocal {

    public FmsSystemJobJunction findBySSPMSId(FmsLuSystem fmsLuSystem, PmsWorkAuthorization pmsWorkAuthorization);
}
