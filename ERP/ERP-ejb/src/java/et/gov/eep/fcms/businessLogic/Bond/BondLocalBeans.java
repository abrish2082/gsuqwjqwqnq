/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.fcms.entity.Bond.FmsBondLocal;
import et.gov.eep.fcms.mapper.Bond.FmsBondLocalFacade;

/**
 *
 * @author mora
 */
@Stateless
public class BondLocalBeans implements BondLocalBeanLocals {

    @EJB
    FmsBondLocalFacade BondLocalFacade;

    @Override
    public void Create(FmsBondLocal BondLocal) {
        BondLocalFacade.create(BondLocal);
    }

    @Override
    public ArrayList<FmsBondLocal> searchBondId(FmsBondLocal BondLocal) {
        return BondLocalFacade.searchFmsBondSerial(BondLocal);
    }

    @Override
    public FmsBondLocal searchBondIdinfo(FmsBondLocal BondLocal) {
        return BondLocalFacade.searchFmsBondSerialinfo(BondLocal);
    }

    @Override
    public ArrayList<FmsBondLocal> searchname(FmsBondLocal BondLocal) {
        return BondLocalFacade.searchFmsBondname(BondLocal);
    }

    @Override
    public ArrayList<FmsBondLocal> searchBond(FmsBondLocal BondLocal) {
        return BondLocalFacade.searchFmsBond(BondLocal);
    }

    @Override
    public void Edit(FmsBondLocal BondLocal) {
        BondLocalFacade.edit(BondLocal);
    }

    @Override
    public List<FmsBondLocal> searchall() {
        return BondLocalFacade.findAll();
    }

    @Override
    public List<FmsBondLocal> findBySerialNo(FmsBondLocal BondLocal) {
        return BondLocalFacade.findBySerialNo(BondLocal);
    }

    @Override
    public ArrayList<FmsBondLocal> findByPrincipal(FmsBondLocal BondLocal) {
        return BondLocalFacade.findByValueBirr(BondLocal);
    }

    @Override
    public ArrayList<FmsBondLocal> findBySerialNameAndPrincipal(FmsBondLocal BondLocal) {
        return BondLocalFacade.findBySerialNameAndPrincipal(BondLocal);
    }

    @Override
    public ArrayList<FmsBondLocal> findBySerialAndPrincipal(FmsBondLocal BondLocal) {
        return BondLocalFacade.findBySerialAndPrincipal(BondLocal);
    }

    @Override
    public ArrayList<FmsBondLocal> findByPrincipalAndName(FmsBondLocal BondLocal) {
        return BondLocalFacade.findByPrincipalAndName(BondLocal);
    }
}
