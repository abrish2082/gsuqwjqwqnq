/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.controller.Bond;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.primefaces.convert.NumberConverter;
import org.primefaces.event.SelectEvent;
import securityBean.SessionBean;
import et.gov.eep.commonApplications.utility.JsfUtil;
import et.gov.eep.fcms.businessLogic.Bond.FmsBondCouponExtendBeanLocal;
import et.gov.eep.fcms.entity.Bond.FmsBondCoupon;
import et.gov.eep.fcms.entity.Bond.FmsBondCouponExtend;

/**
 *
 * @author mz
 */
@Named(value = "bondCouponExtendController")
@ViewScoped
public class BondCouponExtendController implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="Inject Entities and @ EJB">
//inject entity
    @Inject
    SessionBean SessionBean;
    @Inject
    FmsBondCoupon BondCoupon;
    @Inject
    FmsBondCouponExtend couponExtend;
// inject EJB for business logic
    @EJB
    FmsBondCouponExtendBeanLocal couponExtendBeanLocal;
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="variables declaration">
    DataModel<FmsBondCouponExtend> BondCouponExtendAddDatamodel;
    private List<FmsBondCouponExtend> BondCouponExtendList;
    private List<FmsBondCoupon> BondCouponList = new ArrayList<>();
    private FmsBondCouponExtend BondCouponExtendselect;
    int updateStatus = 1;
    String saveUpdate = "Save";
    private String createOrSearchBundle = "New";
    private String icone = "ui-icon-plus";
    private boolean renderPnlCreateAdditional = false;
    private boolean renderPnlMainPage = true;
    private NumberConverter numberConverter = new NumberConverter();
//</editor-fold>

    public BondCouponExtendController() {
        numberConverter.setPattern("#,##0.00##");
        numberConverter.setMinIntegerDigits(1);
        numberConverter.setMaxIntegerDigits(40);
        numberConverter.setMaxFractionDigits(2);
    }
//<editor-fold defaultstate="collapsed" desc="Getter and Setter Methods">

    public FmsBondCouponExtend getCouponExtend() {
        if (couponExtend == null) {
            couponExtend = new FmsBondCouponExtend();
        }
        return couponExtend;
    }

    public void setCouponExtend(FmsBondCouponExtend couponExtend) {
        this.couponExtend = couponExtend;
    }

    public FmsBondCoupon getBondCoupon() {
        return BondCoupon;
    }

    public void setBondCoupon(FmsBondCoupon BondCoupon) {
        this.BondCoupon = BondCoupon;
    }

    public FmsBondCouponExtendBeanLocal getCouponExtendBeanLocal() {
        return couponExtendBeanLocal;
    }

    public void setCouponExtendBeanLocal(FmsBondCouponExtendBeanLocal couponExtendBeanLocal) {
        this.couponExtendBeanLocal = couponExtendBeanLocal;
    }

    public NumberConverter getNumberConverter() {
        return numberConverter;
    }

    public void setNumberConverter(NumberConverter numberConverter) {
        this.numberConverter = numberConverter;
    }

    public FmsBondCouponExtend getBondCouponExtendselect() {
        return BondCouponExtendselect;
    }

    public void setBondCouponExtendselect(FmsBondCouponExtend BondCouponExtendselect) {
        this.BondCouponExtendselect = BondCouponExtendselect;
    }

    public int getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(int updateStatus) {
        this.updateStatus = updateStatus;
    }

    public String getCreateOrSearchBundle() {
        return createOrSearchBundle;
    }

    public void setCreateOrSearchBundle(String createOrSearchBundle) {
        this.createOrSearchBundle = createOrSearchBundle;
    }

    public boolean isRenderPnlCreateAdditional() {
        return renderPnlCreateAdditional;
    }

    public void setRenderPnlCreateAdditional(boolean renderPnlCreateAdditional) {
        this.renderPnlCreateAdditional = renderPnlCreateAdditional;
    }

    public boolean isRenderPnlMainPage() {
        return renderPnlMainPage;
    }

    public void setRenderPnlMainPage(boolean renderPnlMainPage) {
        this.renderPnlMainPage = renderPnlMainPage;
    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getSaveUpdate() {
        return saveUpdate;
    }

    public void setSaveUpdate(String saveUpdate) {
        this.saveUpdate = saveUpdate;
    }

    public DataModel<FmsBondCouponExtend> getBondCouponExtendAddDatamodel() {
        return BondCouponExtendAddDatamodel;
    }

    public void setBondCouponExtendAddDatamodel(DataModel<FmsBondCouponExtend> BondCouponExtendAddDatamodel) {
        this.BondCouponExtendAddDatamodel = BondCouponExtendAddDatamodel;
    }

    public List<FmsBondCouponExtend> getBondCouponExtendList() {
        return BondCouponExtendList;
    }

    public void setBondCouponExtendList(List<FmsBondCouponExtend> BondCouponExtendList) {
        this.BondCouponExtendList = BondCouponExtendList;
    }

    public List<FmsBondCoupon> getBondCouponList() {
        return BondCouponList;
    }

    public void setBondCouponList(List<FmsBondCoupon> BondCouponList) {
        this.BondCouponList = BondCouponList;
    }
//</editor-fold>

    /* recreate method for assigning BondCouponExtendList data to BondCouponExtendAddDatamodel*/
    public void recreateModelSrnDetailPop() {
        BondCouponExtendAddDatamodel = null;
        BondCouponExtendAddDatamodel = new ListDataModel<>(getBondCouponExtendList());
    }

// save extended coupon Bond method
    public void saveExtendCoupon() {

        if (updateStatus == 1) {
            couponExtendBeanLocal.Create(couponExtend);
            JsfUtil.addSuccessMessage("coupon Bond successfully added");
            saveUpdateClear();
        } else if (updateStatus == 2) {
            couponExtendBeanLocal.Edit(couponExtend);
            JsfUtil.addSuccessMessage("coupon Bond successfully updated");
            saveUpdateClear();
            updateStatus = 1;
            saveUpdate = "Save";
        }

    }

    /* search all list from Bond coupon extend using coupon extend id*/
    public void searchBondCouponExtend() {
        BondCouponExtendList = couponExtendBeanLocal.searchCouponExtendId(couponExtend);
        recreateModelSrnDetailPop();
    }

    /*select event for coupon extend to fetch data of the selected coupon*/
    public void getByBondNo(SelectEvent event) {
        couponExtend = (FmsBondCouponExtend) event.getObject();
        recreateModelSrnDetailPop();
        renderPnlCreateAdditional = true;
        renderPnlMainPage = false;
        createOrSearchBundle = "Search";
        setIcone("ui-icon-search");
        saveUpdate = "Update";
        updateStatus = 2;
    }

    /*save update clear method to clear inputs*/
    private void saveUpdateClear() {
        couponExtend = null;
        BondCouponExtendAddDatamodel = null;
    }

    /* create and search render method*/
    public void createAdditional() {
        if (createOrSearchBundle.equals("New")) {
            renderPnlCreateAdditional = true;
            createOrSearchBundle = "Search";
            renderPnlMainPage = false;
            setIcone("ui-icon-search");
        } else {
            renderPnlCreateAdditional = false;
            createOrSearchBundle = "New";
            renderPnlMainPage = true;
            setIcone("ui-icon-plus");
        }
    }

}
