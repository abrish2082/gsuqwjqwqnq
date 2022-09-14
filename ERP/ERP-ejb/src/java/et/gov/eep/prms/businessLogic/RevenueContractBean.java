/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

    // <editor-fold defaultstate="collapsed" desc="Imports">
import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.prms.entity.PrmsRevenueContarct;
import et.gov.eep.prms.entity.PrmsRevenueContractDetail;
import et.gov.eep.prms.mapper.PrmsRevenueContarctFacade;
import et.gov.eep.prms.mapper.PrmsRevenueContractDetailFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
// </editor-fold>

/**
 *
 * @author mora1
 */
@Stateless
public class RevenueContractBean implements RevenueContractBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsRevenueContarctFacade revenueContarctFacade;
    @EJB
    PrmsRevenueContractDetailFacade prmsRevenueContractDetailFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    // </editor-fold>

    @Override
    public void save(PrmsRevenueContarct prmsRevenueContarct) {
        revenueContarctFacade.create(prmsRevenueContarct);
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    @Override
    public List<FmsLuCurrency> currencyNameLists() {
        return revenueContarctFacade.currencyNameLists();
    }

    @Override
    public void edit(PrmsRevenueContarct prmsRevenueContarct) {
        revenueContarctFacade.edit(prmsRevenueContarct);
    }

    @Override
    public List<PrmsRevenueContarct> searchAllRevContract() {
        return revenueContarctFacade.findAll();
    }

    @Override
    public void updateDetail(PrmsRevenueContractDetail prmsRevenueContractDetail) {
        prmsRevenueContractDetailFacade.edit(prmsRevenueContractDetail);
    }

    @Override
    public List<PrmsRevenueContractDetail> howMPaidByProdOrService(String prodOrServName) {
        return prmsRevenueContractDetailFacade.howMPaidByProdOrService(prodOrServName);
    }

    @Override
    public String generateRevContNo() {
        String ethYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String revContNo = null;
        String prefix = "RevCont-No";
        int maxNo = 0;
        List<PrmsRevenueContarct> revenueContarctLists = revenueContarctFacade.getRevContSeqNo(prefix, ethYear);
        for (int j = 0; j < revenueContarctLists.size(); j++) {
            revContNo = revenueContarctLists.get(j).getRevContractNo();
            String[] lastRevNo = revContNo.split("-");
            String lastDatePatterns = lastRevNo[2];
            String[] lastDatePatten = lastDatePatterns.split("/");
            int increament = Integer.parseInt(lastDatePatten[0]);
            if (maxNo < increament) {
                maxNo = increament;
            }

        }
        maxNo = maxNo + 1;
        revContNo = (prefix + "-" + maxNo + "/" + ethYear);
        return revContNo;
    }

    @Override
    public List<PrmsRevenueContarct> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String columnValue) {
        return revenueContarctFacade.searchByCol_NameAndCol_Value(columnNameResolver, columnValue);
    }

    @Override
    public List<ColumnNameResolver> findColoumns() {
        return revenueContarctFacade.findColumns();
    }
}
