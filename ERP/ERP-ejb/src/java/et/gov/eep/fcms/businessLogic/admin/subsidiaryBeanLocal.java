/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.admin;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.admin.FmsCostcSystemJunction;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.mms.entity.IfrsFixedAsset;

/**
 *
 * @author user
 */
@Local
public interface subsidiaryBeanLocal {

    public void create(FmsSubsidiaryLedger subsidiary);

    public void edit(FmsSubsidiaryLedger subsidiary);

    public void delete(FmsSubsidiaryLedger subsidiary);

    public List<FmsSubsidiaryLedger> findAll();

    public List<FmsSubsidiaryLedger> findSubsideryCodeByGlCode(FmsGeneralLedger fmsGeneralLedger);

    public List<FmsSubsidiaryLedger> findBySsCcJuncAndGL(FmsCostcSystemJunction fmsCostcSystemJunction, FmsGeneralLedger fmsGeneralLedger);

    public List<IfrsFixedAsset> getSLListByGlId(Integer generalLedgerId);

    public List<FmsSubsidiaryLedger> getsubsidiaryLedgerList(FmsGeneralLedger generalLedger);

    public ArrayList<FmsSubsidiaryLedger> searchSubsidiaryCode(FmsSubsidiaryLedger subsidiary);

    public ArrayList<FmsSubsidiaryLedger> findSubLedger(FmsGeneralLedger fmsGeneralLedger);

    public ArrayList<FmsSubsidiaryLedger> findSLbyGLandCCSS(FmsGeneralLedger fmsGeneralLedger, FmsCostcSystemJunction costcSystemJunction);

    public ArrayList<FmsSubsidiaryLedger> getSubsidiaryLedgerCodeList();

    public ArrayList<FmsSubsidiaryLedger> searchSubsidiaryByName(String subsidaryName);

    public ArrayList<FmsSubsidiaryLedger> findSubsidiaryName(String subsidaryName);

    public ArrayList<FmsSubsidiaryLedger> searchSubsidiaries(FmsSubsidiaryLedger fmsSubsidiaryLedger);

    public FmsSubsidiaryLedger getSubsidiary(FmsSubsidiaryLedger subsidiary);

    public FmsSubsidiaryLedger getSlCode(FmsSubsidiaryLedger fmsSubsidiaryLedger);

    public FmsSubsidiaryLedger getLastSubsidiary();

    public FmsSubsidiaryLedger getSubsidiaryCode(int parseInt);

    public FmsSubsidiaryLedger getSubsidiaryInfo(FmsSubsidiaryLedger subsidiary);

    public FmsSubsidiaryLedger findById(int fmsSubsidiaryLedger);

    public List<FmsSubsidiaryLedger> getAllSubListList();

    public List<FmsSubsidiaryLedger> searchAllVochNo(FmsSubsidiaryLedger fmsSubsidiaryLedger);
}
