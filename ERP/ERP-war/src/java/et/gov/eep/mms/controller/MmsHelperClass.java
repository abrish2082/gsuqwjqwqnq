/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.controller;

import et.gov.eep.mms.businessLogic.MmsBinCardBeanLocal;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author sadik
 */
@Named(value = "mmsHelperClass")
@ViewScoped
public class MmsHelperClass implements Serializable{

    @EJB
    MmsBinCardBeanLocal binCardBeanLocal;

    public boolean updateBinCard(int storeId, String srNo) {
        int status=0;
        //RequestContext context = RequestContext.getCurrentInstance();
        System.out.println("==========store========"+storeId);
        System.out.println("==========SrNo========"+srNo);
        binCardBeanLocal.deductFromBinCard(storeId, srNo);

        return status == 1;

    }
}
