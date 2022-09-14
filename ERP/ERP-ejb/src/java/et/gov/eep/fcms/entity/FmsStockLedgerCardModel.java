/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

/**
 *
 * @author memube
 */
public class FmsStockLedgerCardModel {

    int slcID;
    double slcCurrentPrice;
    double slcCurrentQty;
    int slcTotalQuanty;
    String slcMatCode;
    double slcMatID;
    String slcMatName;
    int slcPrevQty;
    String slcRefNo;
    String slcRefType;
    String slcStoreNo;
    double slcWtAvPrice;
//    double slcGrnDetailId;
//    String slcTransactionDate;
//    double slcAmountInBirr;

    int overage;
    int shortage;

    //MmsInventoryBalanceSheet Attributes
    int ibsID;
    int ibsInvCountID;
    int ibsMatID;
    int ibsStoreID;
    String ibsBudgetYear;
    Long ibsCountingValue;
    Long ibsDifference;
    Long ibsOldValue;
    String ibsRemark;
    String ibsApprovedDate;
    Integer ibsStatus;
    Integer ibsProcessedBy;
    String ibsProcessedOn;
    String ibsCommentGiven;

    public int getSlcID() {
        return slcID;
    }

    public void setSlcID(int slcID) {
        this.slcID = slcID;
    }

    public double getSlcMatID() {
        return slcMatID;
    }

    public void setSlcMatID(double slcMatID) {
        this.slcMatID = slcMatID;
    }

    public String getSlcMatCode() {
        return slcMatCode;
    }

    public void setSlcMatCode(String slcMatCode) {
        this.slcMatCode = slcMatCode;
    }

    public String getSlcMatName() {
        return slcMatName;
    }

    public void setSlcMatName(String slcMatName) {
        this.slcMatName = slcMatName;
    }

    public int getSlcTotalQuanty() {
        return slcTotalQuanty;
    }

    public void setSlcTotalQuanty(int slcTotalQuanty) {
        this.slcTotalQuanty = slcTotalQuanty;
    }

    public double getSlcCurrentPrice() {
        return slcCurrentPrice;
    }

    public void setSlcCurrentPrice(double slcCurrentPrice) {
        this.slcCurrentPrice = slcCurrentPrice;
    }

    public double getSlcCurrentQty() {
        return slcCurrentQty;
    }

    public void setSlcCurrentQty(double slcCurrentQty) {
        this.slcCurrentQty = slcCurrentQty;
    }

    public int getSlcPrevQty() {
        return slcPrevQty;
    }

    public void setSlcPrevQty(int slcPrevQty) {
        this.slcPrevQty = slcPrevQty;
    }

    public String getSlcRefNo() {
        return slcRefNo;
    }

    public void setSlcRefNo(String slcRefNo) {
        this.slcRefNo = slcRefNo;
    }

    public String getSlcRefType() {
        return slcRefType;
    }

    public void setSlcRefType(String slcRefType) {
        this.slcRefType = slcRefType;
    }

    public String getSlcStoreNo() {
        return slcStoreNo;
    }

    public void setSlcStoreNo(String slcStoreNo) {
        this.slcStoreNo = slcStoreNo;
    }

    public double getSlcWtAvPrice() {
        return slcWtAvPrice;
    }

    public void setSlcWtAvPrice(double slcWtAvPrice) {
        this.slcWtAvPrice = slcWtAvPrice;
    }

    public int getOverage() {
        return overage;
    }

    public void setOverage(int overage) {
        this.overage = overage;
    }

    public int getShortage() {
        return shortage;
    }

    public void setShortage(int shortage) {
        this.shortage = shortage;
    }

    public int getIbsID() {
        return ibsID;
    }

    public void setIbsID(int ibsID) {
        this.ibsID = ibsID;
    }

    public Long getIbsCountingValue() {
        return ibsCountingValue;
    }

    public void setIbsCountingValue(Long ibsCountingValue) {
        this.ibsCountingValue = ibsCountingValue;
    }

    public int getIbsInvCountID() {
        return ibsInvCountID;
}

    public void setIbsInvCountID(int ibsInvCountID) {
        this.ibsInvCountID = ibsInvCountID;
    }

    public int getIbsMatID() {
        return ibsMatID;
    }

    public void setIbsMatID(int ibsMatID) {
        this.ibsMatID = ibsMatID;
    }

    public int getIbsStoreID() {
        return ibsStoreID;
    }

    public void setIbsStoreID(int ibsStoreID) {
        this.ibsStoreID = ibsStoreID;
    }

    public String getIbsBudgetYear() {
        return ibsBudgetYear;
    }

    public void setIbsBudgetYear(String ibsBudgetYear) {
        this.ibsBudgetYear = ibsBudgetYear;
    }

    public Long getIbsDifference() {
        return ibsDifference;
    }

    public void setIbsDifference(Long ibsDifference) {
        this.ibsDifference = ibsDifference;
    }

    public Long getIbsOldValue() {
        return ibsOldValue;
    }

    public void setIbsOldValue(Long ibsOldValue) {
        this.ibsOldValue = ibsOldValue;
    }

    public String getIbsRemark() {
        return ibsRemark;
    }

    public void setIbsRemark(String ibsRemark) {
        this.ibsRemark = ibsRemark;
    }

    public String getIbsApprovedDate() {
        return ibsApprovedDate;
    }

    public void setIbsApprovedDate(String ibsApprovedDate) {
        this.ibsApprovedDate = ibsApprovedDate;
    }

    public Integer getIbsStatus() {
        return ibsStatus;
    }

    public void setIbsStatus(Integer ibsStatus) {
        this.ibsStatus = ibsStatus;
    }

    public Integer getIbsProcessedBy() {
        return ibsProcessedBy;
    }

    public void setIbsProcessedBy(Integer ibsProcessedBy) {
        this.ibsProcessedBy = ibsProcessedBy;
    }

    public String getIbsProcessedOn() {
        return ibsProcessedOn;
    }

    public void setIbsProcessedOn(String ibsProcessedOn) {
        this.ibsProcessedOn = ibsProcessedOn;
    }

    public String getIbsCommentGiven() {
        return ibsCommentGiven;
    }

    public void setIbsCommentGiven(String ibsCommentGiven) {
        this.ibsCommentGiven = ibsCommentGiven;
    }

}
