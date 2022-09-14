/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.Bond;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.Bond.FmsBondLocal;

/**
 *
 * @author mora
 */
@Local
public interface BondLocalBeanLocals {

    public void Create(FmsBondLocal BondLocal);

    public void Edit(FmsBondLocal BondLocal);

    public FmsBondLocal searchBondIdinfo(FmsBondLocal BondLocal);

    public ArrayList<FmsBondLocal> searchBondId(FmsBondLocal BondLocal);

    public ArrayList<FmsBondLocal> searchBond(FmsBondLocal BondLocal);

    public ArrayList<FmsBondLocal> searchname(FmsBondLocal BondLocal);

    public ArrayList<FmsBondLocal> findByPrincipal(FmsBondLocal BondLocal);

    public ArrayList<FmsBondLocal> findBySerialNameAndPrincipal(FmsBondLocal BondLocal);

    public ArrayList<FmsBondLocal> findBySerialAndPrincipal(FmsBondLocal BondLocal);

    public ArrayList<FmsBondLocal> findByPrincipalAndName(FmsBondLocal BondLocal);

    public List<FmsBondLocal> searchall();

    public List<FmsBondLocal> findBySerialNo(FmsBondLocal BondLocal);

}
