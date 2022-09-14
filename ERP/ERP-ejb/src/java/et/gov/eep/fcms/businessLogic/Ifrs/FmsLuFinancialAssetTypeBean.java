/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Ifrs;

import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Ifrs.FmsLuFinancialAssetType;
import et.gov.eep.fcms.mapper.Ifrs.FmsLuFinancialAssetTypeFacade;

/**
 *
 * @author mz
 */
@Stateless
public class FmsLuFinancialAssetTypeBean implements FmsLuFinancialAssetTypeBeanLocal {

    @EJB
    FmsLuFinancialAssetTypeFacade finAssetTypeFacade;

    @Override
    public ArrayList<FmsLuFinancialAssetType> searchFinAssetType(FmsLuFinancialAssetType finAssetType) {
        return finAssetTypeFacade.searchFinAssetType(finAssetType);
    }
}
