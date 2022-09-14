/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.admin.FmsLuSystem;
import et.gov.eep.fcms.entity.admin.FmsSystemJobJunction;
import et.gov.eep.fcms.mapper.admin.FmsSystemJObJunctionFacade;
import et.gov.eep.pms.entity.PmsWorkAuthorization;

/**
 *
 * @author XX
 */
@Stateless
public class FmsSystemJobJunctionBean implements FmsSystemJobJunctionBeanLocal {

//<editor-fold defaultstate="collapsed" desc="@EJB">
    @EJB
    FmsSystemJObJunctionFacade fmsSystemJObJunctionFacade;

    //</editor-fold> 

    @Override
    public FmsSystemJobJunction findBySSPMSId(FmsLuSystem fmsLuSystem, PmsWorkAuthorization pmsWorkAuthorization) {
        return fmsSystemJObJunctionFacade.findBySSPMSId(fmsLuSystem, pmsWorkAuthorization);
    }
}
