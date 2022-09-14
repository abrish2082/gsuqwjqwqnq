/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.controller;

import et.gov.eep.prms.entity.PrmsFinancialEvaluaDetail;
import et.gov.eep.prms.entity.PrmsQuotationDetail;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author user
 */
public class RankCalculatorController implements Comparator<PrmsFinancialEvaluaDetail> {

    List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailList;
    List<PrmsQuotationDetail> prmsQuotationDetailList;

    public List<PrmsQuotationDetail> getPrmsQuotationDetailList() {
        if (prmsQuotationDetailList == null) {
            prmsQuotationDetailList = new ArrayList<>();
        }
        return prmsQuotationDetailList;
    }

    public void setPrmsQuotationDetailList(List<PrmsQuotationDetail> prmsQuotationDetailList) {
        this.prmsQuotationDetailList = prmsQuotationDetailList;
    }

    public List<PrmsFinancialEvaluaDetail> getPrmsFinancialEvaluaDetailList() {
        if (prmsFinancialEvaluaDetailList == null) {
            prmsFinancialEvaluaDetailList = new ArrayList<>();
        }
        return prmsFinancialEvaluaDetailList;
    }

    public void setPrmsFinancialEvaluaDetailList(List<PrmsFinancialEvaluaDetail> prmsFinancialEvaluaDetailList) {
        this.prmsFinancialEvaluaDetailList = prmsFinancialEvaluaDetailList;
    }

    public int compare(PrmsFinancialEvaluaDetail o1, PrmsFinancialEvaluaDetail o2) {

        for (PrmsQuotationDetail v : prmsQuotationDetailList) {
            System.out.println("jjj" + v);

        }
        if (o1.getUnitPrice() > (o2.getUnitPrice())) {
            return 1;
        } else {
            return -1;
        }
    }

    public double add() {
        double n = 0.0;
        for (int i = 0; i < prmsQuotationDetailList.size(); i++) {
            n = n*prmsFinancialEvaluaDetailList.get(i).getUnitPrice();
        }
        return n;
    }

}
