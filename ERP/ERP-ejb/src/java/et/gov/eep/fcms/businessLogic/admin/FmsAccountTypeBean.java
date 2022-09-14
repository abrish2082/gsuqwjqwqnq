/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.admin.FmsAccountType;
import et.gov.eep.fcms.mapper.admin.FmsAccountTypeFacade;

/**
 *
 * @author memube
 */
@Stateless
public class FmsAccountTypeBean implements FmsAccountTypeBeanLocal {
    
 //<editor-fold defaultstate="collapsed" desc="@EJB">
    @EJB
    private FmsAccountTypeFacade fmsAccountTypeFacade;
//</editor-fold>
    
    @Override
    public void create(FmsAccountType fmsAccountType) {
        fmsAccountTypeFacade.create(fmsAccountType);
    }

    @Override
    public void edit(FmsAccountType fmsAccountType) {
        fmsAccountTypeFacade.edit(fmsAccountType);
    }

    @Override
    public Boolean findDupByAcctType(FmsAccountType fmsAccountType) {
        return fmsAccountTypeFacade.findDupByAcctType(fmsAccountType);
    }

    @Override
    public List<FmsAccountType> findAll() {
        return fmsAccountTypeFacade.findAll();
    }

}
